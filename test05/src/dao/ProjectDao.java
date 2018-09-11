package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import entity.Project;

//�����ݿ���ɾ�Ĳ�ķ�����
public class ProjectDao extends BaseDao {

	// ����һ���вεķ�����list����ȥ
	public boolean add(Project pro) {
		boolean flag = false;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			int rs = stat.executeUpdate("insert into project(name)values('" + pro.getName() + "')");
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

			// 7.�ر�

			stat.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	// ����ķ����Ǵ洢����������Ҫһ����ȡ�ķ������ٴ����г����ʱ���ܿ�����һ����������ݣ�Ҳ����������
	// ����û��void ���з���ֵ�ķ�����List<Project>���Ƕ������������������ֵ������
	public List<Project> search() {
		List<Project> list = new ArrayList<Project>();
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			ResultSet rs = stat.executeQuery("select * from project ");
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("Name"));

				list.add(pro);
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

	public Project search(int id) {
		Project pro = new Project();
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����
			Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			ResultSet rs = stat.executeQuery("select * from project where id=" + id);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs.next()) {

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("Name"));

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

		return pro;
	}
	
	public int searchCount(Project condition) {
		int count=0;
		// �������½�һ��list
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.�����ݿ⽨������
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.����statement sql���ִ����
			Statement stat = conn.createStatement();
			String where=" where 1=1 ";
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			ResultSet rs = stat.executeQuery("select count(*) from project "+where);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				count=rs.getInt(1);
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

		return count;
	}

	// ������ť

	public List<Project> search(Project condition, int begin, int size) {
		List<Project> list = new ArrayList<Project>();
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
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}

			// if(condition.getName().equals("")&&condition.getSex().equals("")&&condition.getAge()
			// == -1) {
			// where+=" and 2=2 ";
			// }

			rs = stat.executeQuery("select * from project " + where + " limit " + begin + "," + size);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("Name"));

				list.add(pro);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}

	// ����һ���вεķ�����list����ȥ
	public boolean update(Project pro) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			// Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "update project set name=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, pro.getName());
			pstat.setInt(2, pro.getId());
			int rs = pstat.executeUpdate();

			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public boolean addRelationship(Department selectDep, Project pro) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			// Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + selectDep.getId() + "," + pro.getId()
					+ ")";
			pstat = conn.prepareStatement(sql);
			// pstat.setString(1, pro.getName());
			// pstat.setInt(2, pro.getId());
			int rs = pstat.executeUpdate();

			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public boolean deleteRelationship(int id1, int id2) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			// Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			// int rs = stat.executeUpdate("update pro set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id1);
			pstat.setInt(2, id2);
			int rs = pstat.executeUpdate();

			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public boolean delete(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			// Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			// int rs = stat.executeUpdate("update pro set name=('hh')where
			// '"+pro.getName()+"'=name");
			conn.setAutoCommit(false);
			// ���������Ū��һ������
			String sql = "delete from project where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			pstat.close();
			// ɾ�����ŵ�ʱ�򣬰�Ա������������ŵ��˵�d_id��Ϊnull
			sql = "delete from r_dep_pro where p_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			conn.commit();
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	// ����ɾ��
	public boolean delete(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement stat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			// Statement stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// �������������,�����Ľ��������int��Ӱ�������
			// ��ɾ��ȫ���������
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "delete from project where id in (" + ids + ")";

			stat = conn.createStatement();
			int rs = stat.executeUpdate(sql);

			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}
		return flag;
	}

}
