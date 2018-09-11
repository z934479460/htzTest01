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

//对数据库增删改查的方法类
public class ScoreDao extends BaseDao {

	// 上面的方法是存储方法，还需要一个读取的方法，再次运行程序的时候能看到上一次输入的数据，也就是输入流
	// 这里没有void 是有返回值的方法，List<Score>这是定义这个方法返回来的值的类型
	public List<Score> search(Score condition,int begin, int size) {
		List<Score> list = new ArrayList<Score>();
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器
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
		
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			ResultSet rs = stat.executeQuery("select * from v_emps_s " + where + " limit  " + begin + "," + size);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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

	// 搜索按钮

	public List<Score> searchByCondition(Score condition) {
		List<Score> list = new ArrayList<Score>();
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
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
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
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器

			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "insert into score (e_id,p_id,value) values(?,?,?) ";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, sc.getEmployee().getId());
			pstat.setInt(2, sc.getProject().getId());
			pstat.setInt(3, sc.getValue());
			rs = pstat.executeUpdate();

			// 7.关闭

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
		// 在这里新建一个list
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.与数据库建立连接
			Connection conn = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/company?characterEncoding=utf-8", "root", "123456");
			// 4.建立statement sql语句执行器

			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "update score set value=? where id=? ";
			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, sc.getValue());
			pstat.setInt(2, sc.getId());
			rs = pstat.executeUpdate();
			// 7.关闭

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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 查询employee表中的人数
			String sql = "select count(*) from v_emps_s ";
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				// 查出来的结果集就1列，直接设置1
				count = rs.getInt(1);
			}

			// 7.关闭
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
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 查询employee表中的人数
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
			// 7.关闭
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
