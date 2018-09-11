package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.User;

public class UserDao extends BaseDao {
	// 用用户名和密码去数据库中匹配
	public boolean search(User user) {

		boolean flag = false;
		PreparedStatement pstat = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			// 为了防止有人用sql注入 的方法侵入，使用prepareSatatment
			String sql = "select * from user where username =? and password=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			rs = pstat.executeQuery();
			if (rs.next()) {
				flag = true;

			}

			// if("abc".equals(un)&&"123".equals(pw)){
			// out.print("登录成功");
			// 跳转页面两种方法
			// 转发页面:在Tomcat中转到指定页面，但是地址还是dologin，浏览器不知道内部地址变了，而重定向知道变了

			// request 请求 response 响应 还有一个out。这都是内置对象，不用new的
			// request.getRequestDispatcher("success.jsp").forward(request,response);
			// 内部处理，访问到一个界面，没最终完成，就要转发，处理完了，要去一个新的页面就重定向
			// 重定向：它跟转发的区别就是他会请求两次，响应两次，第一次请求到dologin响应回来，会携带302码和一个location
			// 第二次请求到success.jsp，因为请求两次，所以第二次的request就是null，在响应回来，转发只请求响应一次
			// response.sendRedirect("success.jsp");

			// }else{
			// out.print("登录失败");
			// request.getRequestDispatcher("fail.jsp").forward(request,response);
			// response.sendRedirect("fail.jsp");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, rs);
		}
		return flag;

	}

	public boolean register(User user) {

		
		PreparedStatement pstat = null;
		Connection conn = null;
		int rs = 0;
		try {
			conn = getConnection();
			// 为了防止有人用sql注入 的方法侵入，使用prepareSatatment
			String sql = "insert into user (username,password) values (?,?) ";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			//这里跟上边的search不一样,添加信息的rs是int类型的
			rs = pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs>0;

	}
}
