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

//������
//�����������ֱ�ӵ�����ҳ�棬����һ��Ψһ�����
public class LoginFilter implements Filter {

	public void destroy() {

	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ServletRequest��HttpServletRequest�ĸ��࣬ǿ������ת��
		HttpSession session = ((HttpServletRequest) request).getSession();
		if (session.getAttribute("user") == null) {
			((HttpServletResponse) response).sendRedirect("user");
		} else {
			// chain ��ʽ�ṹ������ж���������Ļ���������������û�о�ȥ��������
			//��web.xml����·��
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
