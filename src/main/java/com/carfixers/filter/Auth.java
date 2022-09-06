package com.carfixers.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/tasks/*", "/dashboard", "/comments/*", "/new/*", "/mark/*", "/assign/*"})
public class Auth implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null) {
            res.setStatus(403);
            req.setAttribute("msg", "Unauthorized");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
