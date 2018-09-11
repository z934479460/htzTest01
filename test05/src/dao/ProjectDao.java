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

//对数据库增删改查的方法类
public class ProjectDao extends BaseDao {

	// 设置一个有参的方法把list传进去
	public boolean add(Project pro) {
		boolean flag = false;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			int rs = stat.executeUpdate("insert into project(name)values('" + pro.getName() + "')");
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs > 0) {
				flag = true;
			}

			// 7.关闭

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

	// 上面的方法是存储方法，还需要一个读取的方法，再次运行程序的时候能看到上一次输入的数据，也就是输入流
	// 这里没有void 是有返回值的方法，List<Project>这是定义这个方法返回来的值的类型
	public List<Project> search() {
		List<Project> list = new ArrayList<Project>();
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			ResultSet rs = stat.executeQuery("select * from project ");
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("Name"));

				list.add(pro);
			}

			// 7.关闭
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
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器
			Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			ResultSet rs = stat.executeQuery("select * from project where id=" + id);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs.next()) {

				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("Name"));

			}

			// 7.关闭
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
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器
			Statement stat = conn.createStatement();
			String where=" where 1=1 ";
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			ResultSet rs = stat.executeQuery("select count(*) from project "+where);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				count=rs.getInt(1);
			}

			// 7.关闭
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

	// 搜索按钮

	public List<Project> search(Project condition, int begin, int size) {
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
			String where = "where 1=1";
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}

			// if(condition.getName().equals("")&&condition.getSex().equals("")&&condition.getAge()
			// == -1) {
			// where+=" and 2=2 ";
			// }

			rs = stat.executeQuery("select * from project " + where + " limit " + begin + "," + size);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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

	// 设置一个有参的方法把list传进去
	public boolean update(Project pro) {
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
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "update project set name=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, pro.getName());
			pstat.setInt(2, pro.getId());
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

	public boolean addRelationship(Department selectDep, Project pro) {
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
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "insert into r_dep_pro (d_id,p_id) VALUES " + "(" + selectDep.getId() + "," + pro.getId()
					+ ")";
			pstat = conn.prepareStatement(sql);
			// pstat.setString(1, pro.getName());
			// pstat.setInt(2, pro.getId());
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

	public boolean deleteRelationship(int id1, int id2) {
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

			String sql = "delete from r_dep_pro where r_dep_pro.d_id=? and r_dep_pro.p_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id1);
			pstat.setInt(2, id2);
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

	public boolean delete(int id) {
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
			conn.setAutoCommit(false);
			// 用事务把它弄成一个整体
			String sql = "delete from project where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			pstat.close();
			// 删除部门的时候，把员工表里这个部门的人的d_id设为null
			sql = "delete from r_dep_pro where p_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			conn.commit();
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

	// 批量删除
	public boolean delete(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement stat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update project set name=('hh')where
			// '"+pro.getName()+"'=name");

			String sql = "delete from project where id in (" + ids + ")";

			stat = conn.createStatement();
			int rs = stat.executeUpdate(sql);

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
