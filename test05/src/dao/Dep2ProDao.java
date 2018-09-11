package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Project;

public class Dep2ProDao extends BaseDao {

	public List<Project> searchByDepartment(int depId) {

		List<Project> list = new ArrayList<Project>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			// 3.�����ݿ⽨������
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();

			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

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

	public List<Project> searchByNotDepartment(int depId) {

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
			// rs = stat.executeQuery("select * from project");

			rs = stat.executeQuery(
					"select * from project where id not in (select p_id from v_dep_pro where d_id=" + depId + ")");

			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				/*
				 * Department dep = new Department(); dep.setId(rs.getInt("d_id"));
				 * dep.setName(rs.getString("d_Name"));
				 */
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));

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

	public boolean add(int depId, int proId) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
		
			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + depId + "," + proId + ")";
			pstat = conn.prepareStatement(sql);
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

	public boolean add(Connection conn, int depId, int proId) {
		boolean flag = false;

		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {

		
			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + depId + "," + proId + ")";
			pstat = conn.prepareStatement(sql);
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
			closeAll(null, pstat, null);
		}
		return flag;
	}

	public boolean addBatch(int depId, String[] proIds) {
		boolean flag = true;
		Connection conn=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			// ��������ֱ��ѭ��proIds��ȥ��add����
			for (int i = 0; i < proIds.length; i++) {
				add(conn,depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		} finally {
			closeAll(conn, null, null);
		}
		return flag;
	}
	public boolean delete(Connection conn,int depId, int proId) {
		boolean flag = false;
	
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
	
	
			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depId);
			pstat.setInt(2, proId);
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
			closeAll(null, pstat, null);
		}
		return flag;
	}
	
	public boolean deleteBatch(int depId, String[] proIds) {
		boolean flag = true;
		Connection conn=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			// ��������ֱ��ѭ��proIds��ȥ��add����
			for (int i = 0; i < proIds.length; i++) {
				delete(conn,depId, Integer.parseInt(proIds[i]));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		} finally {
			closeAll(conn, null, null);
		}
		return flag;
	}

	public boolean delete(int depId, int proId) {
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

			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depId);
			pstat.setInt(2, proId);
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

}
