package dao.table;

import java.sql.*;
import java.util.*;

public class TeacherDAO
{
	// DB ������ ���� �ʿ��� ��� ���� ����
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Connection conn;
    
    // DB �̸� �� ����
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB URL �� ����̹�
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// Ŀ�ؼ��� �ϳ��� ������ ���ҽ� ���� ���̱� ���� �̱��������� �����Ѵ�.
	// �ڱ� �ڽ� Ÿ������ ���� ����
	private static TeacherDAO teacher;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private TeacherDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);		// ����̹� �ε�
			conn = DriverManager.getConnection(URL, DB_USER, DB_PW);		// �����ͺ��̽� ����
		} 
		catch (ClassNotFoundException e) {
			System.out.println("�����ͺ��̽� ����̹��� ã�� �� �����ϴ�.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
			e.printStackTrace();
		}
	}
	
	// TeacherDAO �ν��Ͻ��� �����ִ� �޼ҵ�
	public static TeacherDAO getInstance()
	{
		if (teacher == null)			// ������ �ν��Ͻ��� ���� ��� �ν��Ͻ��� ������ ������
		{
			teacher = new TeacherDAO();
		}
		
		return teacher;		// ������ �ν��Ͻ��� �ִ� ��� �̸� ��ȯ�� ��� Ŭ������ �Ѱ��� �ν��Ͻ��� ����ϰ� ��
	}
	
	// ������� �˻��� �����ȣ �����ִ� �޼ҵ�
	public int createT_num (int language)
	{
		int t_num = 0;		// �����ȣ�� �޾ƿ� ����
		
		try
		{
			sql = "select max(t_num) from teacher where t_num between " + language 
					+ " and " + (language + 99);
			stmt = conn.createStatement();	// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);		// sql�� ����
			
			while(rs.next())
			{
				t_num = rs.getInt(1) + 1;	// ���� ū �����ȣ�� �޾ƿ� �� 1�� ���� �����ش�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return t_num;		// ������� �����ȣ�� ��ȯ�Ѵ�.
	}
	
	// ��ü ���縦 ArrayList�� �ҷ����� �޼ҵ�
	public ArrayList<Vector> selectTeacher()
	{
		ArrayList<Vector> teachers = null;		// Vector�� ���� ArrayList
		Vector tempList = null;						// �����ͺ��̽����� �޾ƿ� ���� ���� Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher order by name asc";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())		// �����ͺ��̽����� �޾ƿ� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector�� ���� ��,
				
				teachers.add(tempList);					// �� Vector�� ArrayList�� ��´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teachers;
	}
	
	// �����ȣ�� �˻��� ����� ArrayList�� �ҷ��ִ� �޼ҵ�
	public ArrayList<Vector> selectTeacherByT_num(int t_num)
	{
		ArrayList<Vector> teachers = null;		// Vector�� ���� ArrayList
		Vector tempList = null;						// �����ͺ��̽����� ������ ���� ���� Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher where t_num = " + t_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())			// �����ͺ��̽��� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector�� ���� ��
				
				teachers.add(tempList);					// �� Vector�� ArrayList�� �ִ´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teachers;
	}
	
	// ���� �̸����� �˻��� ����� ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> selectTeacherByName(String name)
	{
		ArrayList<Vector> teachers = null;	// Vector�� ���� ArrayList
		Vector tempList = null;					// �����ͺ��̽����� �޾ƿ� ���� ���� Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher where name like '%" + name +"%'"; 
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())		// �����ͺ��̽��� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));		// ���� Vector�� ���� ��
				
				teachers.add(tempList);						// �� Vector�� ArrayList�� �ִ´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teachers;
	}
		
	// ��ü �����ȣ�� �̸��� String ���·� ������ ArrayList�� �ҷ����� �޼ҵ�
	public ArrayList<String> selectT_numAndName()
	{
		ArrayList<String> teacher_nn = null;		// �����ȣ�� �̸��� ��� String�� ���� ArrayList
		String t_numAndName = null;				// �����ȣ�� �̸��� �ϳ��� String�� ��� ���� ����
		
		try
		{
			sql = "select t_num, name from teacher order by name asc";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			teacher_nn = new ArrayList<String>();
			
			while (rs.next())
			{
				t_numAndName = rs.getInt("t_num") + " ";		// [�����ȣ] [�����] ������ �� ���ڿ��� �����.
				t_numAndName += rs.getString("name");		// ex) 100 ���¹�
				
				teacher_nn.add(t_numAndName);	// ���� String�� ArrayList�� �ִ´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)			// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teacher_nn;
	}
	
	// ��ü ���� ��ȭ��ȣ ������ ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<String> selectPhone()
	{
		ArrayList<String> teacher_phone = null;		// ��ȭ��ȣ�� String���� ���� ArrayList ����
		String phone = null;								// ��ȭ��ȣ�� �޾ƿ� ����
		
		try
		{
			sql = "select phone from teacher order by name asc";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			teacher_phone = new ArrayList<String>();
			
			while (rs.next())		// �����ͺ��̽��� ���� �ִٸ�
			{
				phone = rs.getString("phone");		// String���� �޾ƿ�	
				teacher_phone.add(phone);			// ArrayList�� �ִ´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teacher_phone;
	}

	// �����ȣ�� �˻��� ���� ������ Teacher Ŭ������ �����ִ� �޼ҵ�
	public Teacher createTeacher(int t_num)
	{
		Teacher teacher = new Teacher();		// �˻��� ���� ������ ���� Teacher �ν��Ͻ� ����
		
		try
		{
			sql = "select * from teacher where t_num = " + t_num;
			stmt = conn.createStatement();	// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);		// sql�� ����
			
			while (rs.next())		// �����ͺ��̽��� ���� �ִٸ�
			{
				teacher.setT_num(rs.getInt("t_num"));			// Teacher �ν��Ͻ��� ���� ��´�.
				teacher.setName(rs.getString("name"));
				teacher.setPassword(rs.getString("password"));
				teacher.setEmail(rs.getString("email"));
				teacher.setPhone(rs.getString("phone"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teacher;		// ������� Teacher ��ü ��ȯ
	}
	
	// ���ο� ���縦 ����ϴ� �޼ҵ� (���� �� 1, ���� �� 0)
	public int insertTeacher(Teacher teacher)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try
		{
			sql = "insert into teacher values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			
			pstmt.setInt(1, teacher.getT_num());		// sql���� ���� �� ����
			pstmt.setString(2, teacher.getName());
			pstmt.setString(3, teacher.getPassword());
			pstmt.setInt(4, teacher.getGender());
			pstmt.setString(5, teacher.getEmail());
			pstmt.setString(6, teacher.getPhone());
			
			result = pstmt.executeUpdate();			// sql�� ����
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)		// ������ �����ϱ� ���� pstmt�� �ݴ´�.
			{
				try { pstmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// ���������� �����ϴ� �޼ҵ� (���� �� 1, ���� �� 0)
	public int updateTeacher(Teacher teacher)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try
		{
			sql = "update teacher set password = ?, phone = ?, email = ? where t_num = ?";
			pstmt = conn.prepareStatement(sql);			// pstmt �ν��Ͻ� ����
			
			pstmt.setString(1, teacher.getPassword());	// sql���� ���� �� ����
			pstmt.setString(2, teacher.getPhone());
			pstmt.setString(3, teacher.getEmail());
			pstmt.setInt(4, teacher.getT_num());
			
			result = pstmt.executeUpdate();				// sql�� ����
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)
			{
				try { pstmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// �����ȣ�� �˻��� ���縦 �����ϴ� �޼ҵ� (���� �� 1, ���н� 0)
	public int deleteTeacher(int t_num)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try 
		{
			sql = "delete from teacher where t_num = " + t_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			result = stmt.executeUpdate(sql);		// sql�� ����
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
}
