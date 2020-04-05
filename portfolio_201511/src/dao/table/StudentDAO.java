package dao.table;

import java.sql.*;
import java.util.*;

public class StudentDAO
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
	private static StudentDAO student;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private StudentDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);		// ����̹� �ε�
			conn = DriverManager.getConnection(URL, DB_USER, DB_PW);	// �����ͺ��̽� ����
		} 
		catch (ClassNotFoundException e) {
			System.out.println("�����ͺ��̽� ����̹��� ã�� �� �����ϴ�.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
			e.printStackTrace();
		}
	}
	
	// StudentDAO �ν��Ͻ��� �����ִ� Ŭ����
	public static StudentDAO getInstance()
	{
		if (student == null)			// ������ �ν��Ͻ��� ���� ��� �ν��Ͻ��� ������ ��ȯ�Ѵ�.
		{
			student = new StudentDAO();
		}
		
		return student;			// ������ �ν��Ͻ��� �ִ� ��� �̸� ��ȯ�� �ϳ��� �ν��Ͻ��� ����ϰ� ��.
	}
		
	// ��ü ������ ������ ArrayList�� �ҷ����� �޼ҵ�
	public ArrayList<Vector> selectStudent()
	{
		ArrayList<Vector> students = null;		// Vector�� ���� ArrayList
		Vector tempList = null;						// �����ͺ��̽����� ������ ���� ���� Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// �����ͺ��̽����� �޾ƿ� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector�� ���� ���� ��
				
				students.add(tempList);					// �� Vector�� ArrayList�� ��´�.
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
		
		return students;
	}
	
	// Ư�� ���Ǹ� ��û�� �л� ������ ArrayList�� �ҷ����� �޼ҵ�
	public ArrayList<Vector> selectStudentByLNum(int l_num)
	{
		ArrayList<Vector> students = null;		// Vector�� ���� ArrayList
		Vector tempList = null;						// �����ͺ��̽����� �ҷ��� ���� ������ Vector
		
		try
		{
			sql = "select name, gender, phone, email from student "
					+ "where st_num in (select st_num from register where l_num = " + l_num
					+ ")order by name asc";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			students = new ArrayList<Vector>();
			
			while (rs.next())			// �����ͺ��̽��� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));		// Vector�� ���� ��
				
				students.add(tempList);						// �� Vector�� �ٽ� ArrayList�� ��´�.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// ���� ������ ���� stmt�� �ݴ´�.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return students;
	}
	
	// �л� ��ȣ�� ã�� �л� ������ ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> selectStudentBySt_num(int st_num)
	{
		ArrayList<Vector> students = null;	// Vector�� ���� ArrayList
		Vector tempList = null;					// �����ͺ��̽����� ������ ���� ���� Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// �����ͺ��̽��� ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector�� ���� ��
				
				students.add(tempList);					// �� Vector�� ArrayList�� ��´�.
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
		
		return students;
	}
	
	// �̸����� ã�� �л� ������ ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> selectStudentByName(String name)
	{
		ArrayList<Vector> students = null;		// Vector�� ���� ArrayList
		Vector tempList = null;						// �����ͺ��̽����� ������ ���� ���� Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student where name like '%" + name + "%'";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// �����ͺ��̽����� ������ ���� �ִٸ�
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "��" : "��");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector�� ���� ��
				
				students.add(tempList);					// �� Vector�� ArrayList�� �ִ´�.
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
		
		return students;
	}
	
	
	// ��ü �л� ��ȭ��ȣ ������ ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<String> selectPhone()
	{
		ArrayList<String> students_phone = null;		// ���׸����� String�� �޴� ArrayList ����
		String phone = null;

		try 
		{
			sql = "select phone from student";
			stmt = conn.createStatement();	// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);		// sql�� ����

			students_phone = new ArrayList<String>();

			while (rs.next()) 		// �����ͺ��̽��� ��ȭ��ȣ ���� �ִٸ�
			{
				phone = rs.getString("phone");	// String�� �־�
				students_phone.add(phone);		// ArrayList�� ����
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if (stmt != null) 		// ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
				try 
				{
					stmt.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return students_phone;
	}
	
	// ������ ��ȣ�� �˻��� ������ ������ Student ��ü�� �����ִ� �޼ҵ�
	public Student createStudent(int st_num)
	{
		Student student = new Student();		// ������ ������ ���� Student ��ü ����
				
		try
		{
			sql = "select * from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			while (rs.next())		// ������ ��ȣ�� �˻��� ������ ������ ������ Student ��ü�� ��´�.
			{
				student.setSt_num(rs.getInt("st_num"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				student.setGender(rs.getInt("gender"));
				student.setEmail(rs.getString("email"));
				student.setPhone(rs.getString("phone"));
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
		
		return student;
	}
	
	// �̸��� ��ȭ��ȣ�� �������� ã�� ȸ�� ��ȣ�� �����ִ� �޼ҵ�
	public int findSt_num(String name, String phone)
	{
		int st_num = 0;		// ��ȯ�� ������ ��ȣ�� ������ ����
				
		try
		{
			sql = "select st_num from student where name = ? and phone = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			
			pstmt.setString(1, name);		// sql���� ���� �̸� �� ����
			pstmt.setString(2, phone);		// sql���� ���� ��ȭ��ȣ �� ����
			rs = pstmt.executeQuery();		// sql�� ����
			
			while (rs.next())		// �����ͺ��̽��� �˻� ����� �ִٸ�
			{
				st_num = rs.getInt("st_num");		// �л� ��ȣ�� ���´�.
			}
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
		
		return st_num;
	}
	
	// ���ο� ������ ������ �Է��ϴ� �޼ҵ� (���� �� 1, ���� �� 0)
	public int insertStudent(Student student)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try
		{
			sql = "insert into student values (null, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			
			pstmt.setString(1, student.getName());		// sql���� ���� �� ����
			pstmt.setString(2, student.getPassword());
			pstmt.setInt(3, student.getGender());
			pstmt.setString(4, student.getEmail());
			pstmt.setString(5, student.getPhone());
			
			result = pstmt.executeUpdate();		// sql�� ����
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
		
		return result;		// ��� �� ��ȯ
	}
	
	// ������ ������ �����ϴ� �޼ҵ� (���� �� 1, ���� �� 0)
	public int updateStudent(Student student)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try
		{
			sql = "update student set password = ?, phone = ?, email = ? where st_num = ?";
			pstmt = conn.prepareStatement(sql);			// pstmt �ν��Ͻ� ����
			
			pstmt.setString(1, student.getPassword());	// sql���� ���� �� ����
			pstmt.setString(2, student.getPhone());
			pstmt.setString(3, student.getEmail());
			pstmt.setInt(4, student.getSt_num());
			
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
		
		return result;		// ��� �� ��ȯ
	}
	
	// ȸ����ȣ�� �˻��� �������� �����ϴ� �޼ҵ� (���� �� 1, ���� �� 0)
	public int deleteStudent(int st_num)
	{
		int result = 0;		// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try 
		{
			sql = "delete from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			result = stmt.executeUpdate(sql);		// slq�� ����
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		//  ������ �����ϱ� ���� stmt�� �ݴ´�.
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;		// ��� �� ��ȯ
	}	
}
