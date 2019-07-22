package com.kunyi.bitamexJava.filter;

import com.kunyi.bitamexJava.model.JsonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器
 *
 * @author GAIA
 * @create 2019-05-22-12:21
 */

public class LoginFilter implements Filter {
    private String NO_LOGIN = "请先登录";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req  = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        if(session != null){
            logger.info("登录过滤，sessionId为" + session.getId());
        }

        if(session != null){
            //已经登录
            String name = (String)session.getAttribute("name");
            logger.info("管理员：name = " + name + " 已登录");
            chain.doFilter(req,res);
        }else {
            JsonMessage message = new JsonMessage();
            message.setCode(300);
            message.setMsg(this.NO_LOGIN);
            res.setContentType("text/html;charset=UTF-8");
            res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            logger.info("路径：" + uri + "未登录，已被过滤");
            res.getWriter().write(message.toJson());
        }
    }
}
