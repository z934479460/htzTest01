package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import util.Grade;

//�����ݿ���ɾ�Ĳ�ķ�����
public class ScoreDao extends BaseDao {

	// ����ķ����Ǵ洢����������Ҫһ����ȡ�ķ������ٴ����г����ʱ���ܿ�����һ����������ݣ�Ҳ����������
	// ����û��void ���з���ֵ�ķ�����List<Score>���Ƕ������������������ֵ������
	public List<Score> search(Score condition,int begin, int size) {
		List<Score> list = new ArrayList<Score>();
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����
			Statement stat = conn.createStatement();
			String where = "where 1=1";
			if (condition.getEmployee().getName() != null && !condition.getEmployee().getName().equals("")) {
				where += " and e_name='" + condition.getEmployee().getName() + "'";
			}
			if (condition.getEmployee().getDep().getName() != null && !condition.getEmployee().getDep().getName().equals("")) {
				where += " and d_name='" + condition.getEmployee().getDep().getName() + "'";
			}

			if (condition.getProject().getName() != null&& !condition.getProject().getName().equals("")) {
				where += " and p_name=" + condition.getProject().getName();
			}
		
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			ResultSet rs = stat.executeQuery("select * from v_emps_s " + where + " limit  " + begin + "," + size);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Score sc = new Score();
				sc.setId(rs.getInt("s_id"));
				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);
				sc.setEmployee(emp);

				sc.setValue((Integer) rs.getObject("VALUE"));
				Grade g=Grade.getGrade(rs.getString("grade"));
				sc.setGrade(g);
				Project pro = new Project();
				pro.setName(rs.getString("p_name"));
				pro.setId(rs.getInt("p_id"));
				sc.setProject(pro);
				list.add(sc);
			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// ������ť

	public List<Score> searchByCondition(Score condition) {
		List<Score> list = new ArrayList<Score>();
		// �������½�һ��list
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {

			// 3.�����ݿ⽨������
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String where = "where 1=1";
			if (!condition.getEmployee().getName().equals("")) {
				where += " and e_name='" + condition.getEmployee().getName() + "'";
			}
			if (condition.getEmployee().getDep().getName() != null) {
				where += " and d_name='" + condition.getEmployee().getDep().getName() + "'";
			}

			if (condition.getProject() != null) {
				where += " and p_name='" + condition.getProject().getName() + "'";
			}

			rs = stat.executeQuery("select * from v_emps_s " + where);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Score sc = new Score();
				sc.setId(rs.getInt("s_id"));
				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);
				sc.setEmployee(emp);

				sc.setValue((Integer) rs.getObject("VALUE"));
				Grade g=Grade.getGrade(rs.getString("grade"));
				sc.setGrade(g);
				Project pro = new Project();
				pro.setName(rs.getString("p_name"));
				pro.setId(rs.getInt("p_id"));
				sc.setProject(pro);
				list.add(sc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

//	public void save(Set<Score> set) {
//		for (Score sc : set) {
//
//			if (sc.getId() == 0) {
//				add(sc);
//			} else {
//				update(sc);
//			}
//
//		}
//
//	}

	public boolean add(Score sc) {
		int rs = 0;
		PreparedStatement pstat = null;
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����

			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "insert into score (e_id,p_id,value) values(?,?,?) ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sc.getEmployee().getId());
			pstat.setInt(2, sc.getProject().getId());
			pstat.setInt(3, sc.getValue());
			rs = pstat.executeUpdate();

			// 7.�ر�

			pstat.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs > 0;
	}

	public boolean update(Score sc) {
		int rs = 0;
		PreparedStatement pstat = null;
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����

			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "update score set value=? where id=? ";
			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, sc.getValue());
			pstat.setInt(2, sc.getId());
			rs = pstat.executeUpdate();
			// 7.�ر�

			pstat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs > 0;
	}

	public int searchCount() {
		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {

			// 3.�����ݿ⽨������
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// ��ѯemployee���е�����
			String sql = "select count(*) from v_emps_s ";
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				// ������Ľ������1�У�ֱ������1
				count = rs.getInt(1);
			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
	
	public Score search(int id) {
		Score sc = new Score();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {

			// 3.�����ݿ⽨������
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// ��ѯemployee���е�����
			String sql = "select * from v_emps_s where s_id= "+id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				sc.setId(rs.getInt("s_id"));
				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);
				sc.setEmployee(emp);

				sc.setValue((Integer) rs.getObject("VALUE"));
				Grade g=Grade.getGrade(rs.getString("grade"));
				sc.setGrade(g);
				Project pro = new Project();
				pro.setName(rs.getString("p_name"));
				pro.setId(rs.getInt("p_id"));
				sc.setProject(pro);
				
			}
			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sc;
	}


}
