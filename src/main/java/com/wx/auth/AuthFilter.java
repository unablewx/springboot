package com.wx.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mysql.cj.util.StringUtils;
import com.wx.constant.Constant;
import com.wx.exception.CrmAuthException;
import com.wx.mdc.MDCScope;
import com.wx.resp.Result;
import com.wx.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 1.初始化的时候先从 config 包下的 AUthConfig 获取不用鉴权的路径，经过过滤后存储到私有属性 uriFilter 中
 * 2.当前路径去匹配不用鉴权的路径，例如登录页面，直接放行，并结束 doFilter()方法
 * 3.当前路径前往需要鉴权的路径，需要验证token，通过调用 Authenticator接口中的方法返回 AuthInfo,并将该对象放入作用域中
 */
@Slf4j
public class AuthFilter implements Filter {
    //    private Authenticator authenticator;
    private AuthenticatorImpl authenticatorImpl;
    private AuthenticatorRedisImpl authenticatorRedis;
    private Set<String> uriFilter;

    //无参构造器
    public AuthFilter() {
    }

    //redis关闭时的构造器
    public AuthFilter(AuthenticatorImpl authenticator) {
        this.authenticatorImpl = authenticator;
    }

    //redis开启时的构造器
    public AuthFilter(AuthenticatorImpl authenticatorImpl, AuthenticatorRedisImpl authenticatorRedis) {
        this.authenticatorImpl = authenticatorImpl;
        this.authenticatorRedis = authenticatorRedis;
    }


    /**
     * 过滤器拦截
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取当前的uri
        String uri = request.getRequestURI();
        //如果 uri 路径匹配放行路径的集合
        if (uriFilter.stream().anyMatch(uri::startsWith)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try (MDCScope mdcScope = new MDCScope()) {
            //获取token
            String header = request.getHeader("token");
            String token = StringUtils.isNullOrEmpty(header) ? request.getParameter("token") : header;
            //验证token
            if (StringUtils.isNullOrEmpty(token)) {
                throw new CrmAuthException("未携带token");
            }
            AuthInfo authInfo = null;
            if (authenticatorRedis != null) {
                // redis验证,可能返回空值
                authInfo = authenticatorRedis.auth(token);
            } else {
                //普通验证
                authInfo = authenticatorImpl.auth(token);
            }
            if (authInfo == null) {
                throw new CrmAuthException("鉴权失败");
            }
            //todo 不应该放入常量
            mdcScope.put(Constant.USER_ID, String.valueOf(authInfo.getUserId()));
            mdcScope.put(Constant.USER_NAME, authInfo.getUsername());
            mdcScope.put(Constant.USER_ROLE, String.valueOf(authInfo.getRoleId()));
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (CrmAuthException e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            String json = JsonUtil.objectToJson(new Result(false, null, e.getMessage()));
            response.getOutputStream().write(json.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取放行路径的集合
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        //获取单个servlet中的初始化参数
        String initParameter = filterConfig.getInitParameter(Constant.UN_FILTER_KEY);
        List<String> list = null;
        if (StringUtils.isNullOrEmpty(initParameter)) {
            //参数为空
            list = Collections.emptyList();
        } else {
            //参数不为空
            list = Lists.newArrayList(initParameter.split(","));
            //去掉空值参数
            list.removeIf(String::isEmpty);
        }
        //去重处理
        uriFilter = ImmutableSet.copyOf(list);
    }

}
