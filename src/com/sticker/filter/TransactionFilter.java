package com.sticker.filter;

import com.sticker.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();// 提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();
            //根据前面代码，最终所有的异常都会到Filter中来处理，在Filter中抛出异常是抛给
            //Tomcat服务器。如果只捕获，不抛出的话，Tomcat是不知道的，就不能进行异常处理。
            throw new RuntimeException(e);//把异常抛给Tomcat管理展示友好的错误页面
        }
    }

    @Override
    public void destroy() {

    }
}
