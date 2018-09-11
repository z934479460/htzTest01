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

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();

			rs = stat.executeQuery("select * from v_dep_pro where d_id=" + depId);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
		// 在这里新建一个list
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			// rs = stat.executeQuery("select * from project");

			rs = stat.executeQuery(
					"select * from project where id not in (select p_id from v_dep_pro where d_id=" + depId + ")");

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
		
			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + depId + "," + proId + ")";
			pstat = conn.prepareStatement(sql);
			int rs = pstat.executeUpdate();
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

		
			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + depId + "," + proId + ")";
			pstat = conn.prepareStatement(sql);
			int rs = pstat.executeUpdate();
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
			// 批量增加直接循环proIds，去调add方法
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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
	
	
			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depId);
			pstat.setInt(2, proId);
			int rs = pstat.executeUpdate();

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
			// 批量增加直接循环proIds，去调add方法
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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update pro set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=? ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, depId);
			pstat.setInt(2, proId);
			int rs = pstat.executeUpdate();

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
