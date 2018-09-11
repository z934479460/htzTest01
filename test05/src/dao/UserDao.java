package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.User;

public class UserDao extends BaseDao {
	// ���û���������ȥ���ݿ���ƥ��
	public boolean search(User user) {

		boolean flag = false;
		PreparedStatement pstat = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			// Ϊ�˷�ֹ������sqlע�� �ķ������룬ʹ��prepareSatatment
			String sql = "select * from user where username =? and password=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			rs = pstat.executeQuery();
			if (rs.next()) {
				flag = true;

			}

			// if("abc".equals(un)&&"123".equals(pw)){
			// out.print("��¼�ɹ�");
			// ��תҳ�����ַ���
			// ת��ҳ��:��Tomcat��ת��ָ��ҳ�棬���ǵ�ַ����dologin���������֪���ڲ���ַ���ˣ����ض���֪������

			// request ���� response ��Ӧ ����һ��out���ⶼ�����ö��󣬲���new��
			// request.getRequestDispatcher("success.jsp").forward(request,response);
			// �ڲ��������ʵ�һ�����棬û������ɣ���Ҫת�����������ˣ�Ҫȥһ���µ�ҳ����ض���
			// �ض�������ת����������������������Σ���Ӧ���Σ���һ������dologin��Ӧ��������Я��302���һ��location
			// �ڶ�������success.jsp����Ϊ�������Σ����Եڶ��ε�request����null������Ӧ������ת��ֻ������Ӧһ��
			// response.sendRedirect("success.jsp");

			// }else{
			// out.print("��¼ʧ��");
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
			// Ϊ�˷�ֹ������sqlע�� �ķ������룬ʹ��prepareSatatment
			String sql = "insert into user (username,password) values (?,?) ";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			//������ϱߵ�search��һ��,�����Ϣ��rs��int���͵�
			rs = pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return rs>0;

	}
}
