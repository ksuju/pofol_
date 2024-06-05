package com.portfolio.www.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
/*
 * @WebFilter(dispatcherTypes = {DispatcherType.REQUEST } , servletNames = {
 * "pf" })
 */
public class LoginFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
	// 생성자
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
        System.out.println("--------LoginFilter constructor--------");
    }

	/**
	 * @see Filter#destroy()
	 */
    // 필터 죽으면
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("LoginFilter > destroy");
	}
	
	// 로그인이 필요한 페이지의 URI
	private final String[] LOGIN_REQUIRED_URI = {
			"/pf/index.do"
			//,"/pf/auth/joinPage.do"
	};

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	// 필터 동작하면
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		//System.out.println("URI==============================="+uri);
		List<String> UriList = new ArrayList<String>(Arrays.asList(LOGIN_REQUIRED_URI));
		//System.out.println("UriList==============================="+UriList);
		if(UriList.contains(uri)) {
			HttpSession session = req.getSession();
			//System.out.println("session================>"+session.getAttribute("memberId"));
			if(ObjectUtils.isEmpty(session.getAttribute("logInUser"))) {
				
				// redirect를 사용하면 새로운 페이지가 반환되기 때문에 스크립트가 실행되지 않음
				//resp.sendRedirect(req.getContextPath() + "/auth/loginPage.do");
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('로그인 후 이용해주세요.'); location.href='" + req.getContextPath() + "/auth/loginPage.do';</script>");
	            return;
			}
			// 로그인 상태를 확인하여 메뉴에 표시될 버튼을 설정
	        request.setAttribute("loggedIn", true);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	// 필터 등록되면
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("LoginFilter > init");
	}

}
