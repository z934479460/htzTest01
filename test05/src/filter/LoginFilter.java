package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//过滤器
//让浏览器不能直接单独的页面，设置一个唯一的入口
public class LoginFilter implements Filter {

	public void destroy() {

	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ServletRequest是HttpServletRequest的父类，强制类型转换
		HttpSession session = ((HttpServletRequest) request).getSession();
		if (session.getAttribute("user") == null) {
			((HttpServletResponse) response).sendRedirect("user");
		} else {
			// chain 链式结构，如果有多个过滤器的话把它们连起来，没有就去做该做的
			//在web.xml设置路径
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
