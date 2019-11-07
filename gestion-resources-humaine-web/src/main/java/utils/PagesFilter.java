package utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managedBeans.LoginBean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/pages/*")
public class PagesFilter implements Filter{

	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("from pages filter url = " + ((HttpServletRequest) request).getRequestURL());
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse servletResponse = (HttpServletResponse) response;
    
    LoginBean loginBean = (LoginBean) servletRequest.getSession().getAttribute("loginBean");
    JSessionStaticID.id=servletRequest.getRequestedSessionId();
    
    if( (loginBean != null && loginBean.isLoggedln()) && loginBean.getUser().getStatus()==Status.active || servletRequest.getRequestURL().toString().contains("login.jsf"))
    {
    		chain.doFilter(servletRequest, servletResponse);
    }
    else 
    {
    		servletResponse.sendRedirect(servletRequest.getContextPath() + "/login.jsf");
    }
    
    }	
	
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}














	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
}
