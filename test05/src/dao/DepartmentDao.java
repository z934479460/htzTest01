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

//�����ݿ���ɾ�Ĳ�ķ�����
public class DepartmentDao extends BaseDao {

	// ����ķ����Ǵ洢����������Ҫһ����ȡ�ķ������ٴ����г����ʱ���ܿ�����һ����������ݣ�Ҳ����������
	// ����û��void ���з���ֵ�ķ�����List<Department>���Ƕ������������������ֵ������
	public List<Department> search() {
		List<Department> list = new ArrayList<Department>();
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
			// String sql = "select e.*,d.name as dName,dep_count from Department as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from Department ";
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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

	public List<Department> search(int begin, int size) {
		List<Department> list = new ArrayList<Department>();
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
			// String sql = "select e.*,d.name as dName,dep_count from Department as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from Department limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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
			// ��ѯDepartment���е�����
			String sql = "select count(*) from Department ";
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
	public int searchCount(Department condition) {
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
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			if (condition.getEmpCount()!=-1) {
				where += " and ifnull(emp_count,0)=" + condition.getEmpCount() ;
			}
		

			
			// ��ѯDepartment���е�����
			String sql = "select count(*) from Department "+where ;
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

	public boolean add(Department dep) {
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
			String sql = "insert into Department(name)values(?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
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

	public boolean update(Department dep) {
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
			// int rs = stat.executeUpdate("update Department set name=('hh')where
			// '"+dep.getName()+"'=name");

			String sql = "update Department set name=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			pstat.setInt(2, dep.getId());
			// Department����û�о���Ĳ��ţ�ֻ��d_id
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
	public Department search(int id) {
		Department dep = new Department();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.����statement sql���ִ����
			stat = conn.createStatement();
			// 5.ִ��sql��䲢�õ����
			// ��������ǲ�ѯ,������Ľ��������ResultSet���͵ģ����α�һ��
			String sql = "select *from Department where id =" + id;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			if (rs.next()) {
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

			}

			// 7.�ر�
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dep;
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
			// int rs = stat.executeUpdate("update department set name=('hh')where
			// '"+dep.getName()+"'=name");
			conn.setAutoCommit(false);
			// ���������Ū��һ������
			String sql = "delete from department where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			pstat.close();
			// ɾ�����ŵ�ʱ�򣬰�Ա������������ŵ��˵�d_id��Ϊnull
			sql = "update employee set d_id=null where d_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			pstat.close();
			// ɾ�����ŵ�ʱ�򣬰�Ա������������ŵ��˵�d_id��Ϊnull
			sql = "delete from r_dep_pro where d_id=?";
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

	


	public List<Department> search(Department condition, int begin, int size) {
		List<Department> list = new ArrayList<Department>();
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
			//�������������жϣ�����һ�δ򿪽����ʱ�򲻴�name��������null���򿪺�����������ǿ��ַ���
			//��EmpCount���ã���Ϊ����servlet�о������ж�
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			//ifnull���������Ϊnull�Ļ����Ը�һ��ֵ�������Ϊnull�������Լ���
			//������Ϊ�����ݿ����еĲ��ŵ�emp_countΪnull���������0,���ڲ���
			if (condition.getEmpCount()!=-1) {
				where += " and ifnull(emp_count,0)=" + condition.getEmpCount() ;
			}
			//ע��ֺ���Χ�ӿո�
			String sql = " select * from Department " + where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.�Խ�������д���
			// .nest()���ж���û����һ�����еĻ��ͷ���true��û�оͷ���false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_Count"));
				// Department dep = new Department();
				// dep.setId(rs.getInt("d_id"));
				// dep.setName(rs.getString("dName"));
				// dep.setdepCount(rs.getInt("dep_count"));
				// dep.setDep(dep);
				list.add(dep);
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
