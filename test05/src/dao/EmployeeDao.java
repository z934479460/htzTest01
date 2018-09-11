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
import entity.Employee;

//�����ݿ���ɾ�Ĳ�ķ�����
public class EmployeeDao extends BaseDao {

	// ����ķ����Ǵ洢����������Ҫһ����ȡ�ķ������ٴ����г����ʱ���ܿ�����һ����������ݣ�Ҳ����������
	// ����û��void ���з���ֵ�ķ�����List<Employee>���Ƕ������������������ֵ������
	public List<Employee> search() {
		List<Employee> list = new ArrayList<Employee>();
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
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from employee ";
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Employee> search(int begin, int size) {
		List<Employee> list = new ArrayList<Employee>();
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
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from employee limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
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
			String sql = "select count(*) from employee ";
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

	public int searchCount(Employee condition) {
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
			String where = "where 1=1";
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}

			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			// ��������������в��ŵĻ�����������ŵ�idȥemployee������d_id
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += " and d_id=" + condition.getDep().getId();
			}

			// ��ѯemployee���е�����
			String sql = "select count(*) from employee as e left join department as d on e.d_id=d.id " + where;
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

	public boolean add(Employee emp) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;

		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {

			// 3.�����ݿ⽨������
			conn = getConnection();
			// 4.����statement sql���ִ����

			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "insert into employee(name,sex,age,d_id,photo)values(?,?,?,?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			//��Ϊdep��id��integer���ͣ������ӵ�ʱ��ѡ���ţ������Ǹ�null����ô������ᱨ��ָ���쳣
			//���ڴ���ȥ��ʱ��ᱨ�����Ǹ���������ӣ�������object
			pstat.setObject(4, emp.getDep().getId());
			pstat.setString(5, emp.getPhoto());
			rs = pstat.executeUpdate();
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return rs > 0;
	}

	public boolean update(Employee emp) {
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
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setInt(5, emp.getId());
			// employee����û�о���Ĳ��ţ�ֻ��d_id
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

	// ��������ǵ���޸ĵ�ʱ����ѡ�е���һ�е�idȥ���ݿ��в���һ�飬��ʾ��updateҳ��
	public Employee search(int id) {
		Employee emp = new Employee();
		Department dep=new Department();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "select e.*,d.name as dName from employee as e left join department as d on e.d_id=d.id where e.id =" + id;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				emp.setDep(dep);

			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
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
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "delete from employee where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
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

	public boolean deleteBatch(String ids) {
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
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "delete from employee where id in (" + ids + ")";
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

	public List<Employee> search(String ids) {
		List<Employee> list = new ArrayList<Employee>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "select e.* ,d.name as dName from employee as e left join department as d on e.d_id=d.id where e.id in (" + ids + ")";
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep=new Department();
				dep.setName(rs.getString("dName"));
				dep.setId(rs.getInt("d_id"));
				emp.setDep(dep);
				list.add(emp);
			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public boolean updateBatch1(Employee emp, String ids) {
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
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id in (" + ids + ")";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			// employee����û�о���Ĳ��ţ�ֻ��d_id
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

	public boolean updateBatch2(List<Employee> list) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2���÷��� ���������ݿ�����,����MySQL����������ôд
		try {
			conn = getConnection();
			// ѭ��list�������޸�
			for (int i = 0; i < list.size(); i++) {
				Employee emp = list.get(i);
				String sql = "update employee set name=?,sex=?,age=?,d_id=? where id = ?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, emp.getName());
				pstat.setString(2, emp.getSex());
				pstat.setInt(3, emp.getAge());
				pstat.setObject(4, emp.getDep().getId());
				pstat.setInt(5, emp.getId());
				// employee����û�о���Ĳ��ţ�ֻ��d_id
				int rs = pstat.executeUpdate();

				// 6.�Խ�������д���
				// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
				if (rs > 0) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public List<Employee> search(Employee condition, int begin, int size) {
		List<Employee> list = new ArrayList<Employee>();
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
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}

			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			// ��������������в��ŵĻ�����������ŵ�idȥemployee������d_id
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += " and d_id=" + condition.getDep().getId();
			}
			//
			// if (condition.getDep().getId() != -1) {
			// // ������벿������ѯ����ôdep��id�Ͳ���-1
			// where += " and d_id= " + condition.getDep().getId();
			// // employee��department�����ϵ����d_id
			// // �����������һ��Ա��������֮һ��Ҳ����dep������department��ģ�����id�������id����employee���е�d_id
			// }
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as d on e.d_id=d.id "
			// + where;
			// ע��ֺ���Χ�ӿո�
			String sql = " select e.*,d.name as dName from employee as e left join department as d on e.d_id=d.id " + where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setPhoto(rs.getString("photo"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				emp.setDep(dep);
				list.add(emp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}
}
