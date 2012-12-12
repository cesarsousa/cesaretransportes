package br.com.cesaretransportes.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class RequestFilter implements Filter {
    
    public RequestFilter() {}
    public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException {		
		
		HttpServletRequest req = (HttpServletRequest) request;
		String url = (req.getRequestURL().toString());
		
		HttpSession session = ((HttpServletRequest) request).getSession();		
		String flag = (String) session.getAttribute("flag");

		if(url.contains("/Android") || flag != null){
			chain.doFilter(request, response);
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
			session.setAttribute("flag", "flag");
		}		
	}
}