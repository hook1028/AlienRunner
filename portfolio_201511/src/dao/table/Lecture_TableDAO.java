package dao.table;

import java.sql.*;
import java.util.*;

// lecture_table ���� �����͸� �������� Ŭ����
public class Lecture_TableDAO
{	
	// DB ������ ���� �ʿ��� ��� ���� ����
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Connection conn;
    
    // DB �̸� �� ������ �����ϴ� ����
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB ����̹� �� URL
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// Ŀ�ؼ��� �ϳ��� ������ ���ҽ� ���� ���̱� ���� �̱��������� �����Ѵ�.
	// �ڱ� �ڽ� Ÿ������ ���� ����
	private static Lecture_TableDAO Lecture_TableDAO;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private Lecture_TableDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);											// ����̹� �ε�
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

	// Lecture_TableDAO �ν��Ͻ��� �����ִ� �޼ҵ�
	public static Lecture_TableDAO getInstance() 
	{
		if (Lecture_TableDAO == null)
		{
			Lecture_TableDAO = new Lecture_TableDAO();
		}
		
		return Lecture_TableDAO;
	}
		
	// ��� ���� ����� ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> selectLecture_table()
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList ����
		Vector tempList = null;							// ���̺��� �ҷ��� ������ ������ Vector ����

		try
		{
			sql = "select * from lecture_table order by title asc";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			makeList(lecture_table, tempList, rs);		// ������ ���Ϳ� ���� �� �� ���͸� �ٽ� ArrayList�� ��� �޼ҵ�
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// ������ �����ϱ� ���� stmt�� �ݴ´�.
		{
			if (stmt != null)
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture_table;
	}
	
	// ���Ǹ����� �˻��� ����� ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> searchLecture_tableByTitle(String title)
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList
		Vector tempList = null;							// ���̺��� ������ ������ ������ Vector

		try
		{
			// where �÷��� like '% �˻��� %' : �˻�� ���Ե� ���� ������
			sql = "select * from lecture_table where title like '%" + title + "%'";
			stmt = conn.createStatement();		// stmt ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// ������ ���� ���� Vector�� ArrayList�� �ִ� �޼ҵ�
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// ������ �����ϱ� ���� stmt�� �ݴ´�.
		{
			if (stmt != null)
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture_table;
	}
	
	// ��������� �˻��� ����� ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> searchLecture_tableByTeacher(String name)
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList
		Vector tempList = null;							// ���̺��� ������ ���� ���� Vector

		try
		{
			sql = "select * from lecture_table where name like '%" + name + "%'";
			stmt = conn.createStatement();		// stmt ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// ���̺��� ������ ���� ���� Vector�� ArrayList�� �ִ� �޼ҵ�
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
		
		return lecture_table;
	}
	
	// �������� ������û�� ���Ǹ� ArrayList�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<Vector> searchLecture_tableBySt_Num(int st_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList
		Vector tempList = null;							// ���̺��� ������ ���� ���� Vector

		try
		{
			sql = "select * from lecture_table where l_num in (select l_num from register where st_num =" + st_num + ")";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// ���̺��� ������ ���� ���� Vector�� ArrayList�� �ִ� �޼ҵ�
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
		return lecture_table;
	}
	
	// ���ǹ�ȣ�� �˻��� ����� ArrayList�� �����ִ� �޼ҵ�
	public ArrayList<Vector> searchLecture_tableByL_num(int l_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList
		Vector tempList = null;							// ���̺��� ������ ���� ���� Vector

		try
		{
			sql = "select * from lecture_table where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// ���̺��� ������ ���� ���� Vector�� ArrayList�� ��� �޼ҵ�
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
		
		return lecture_table;
	}

	// �����ȣ�� �˻��� ����� Arraylist�� �����ִ� �޼ҵ�
	public ArrayList<Vector> searchLecture_tableByT_num(int t_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector�� ���� ArrayList
		Vector tempList = null;							// ���̺��� ������ ���� ���� Vector

		try
		{
			sql = "select * from lecture_table where name in (select name from teacher where t_num = " + t_num + ")";
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// ���̺��� ������ ���� ���� Vector�� ArrayList�� ��� �޼ҵ�
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
		
		return lecture_table;
	}
	
	// ���̺��� ������ ������ Vector�� ���� �� �װ��� �ٽ� ArrayList�� �־��ִ� �޼ҵ�
	private void makeList(ArrayList<Vector> lecture_table, Vector tempList, ResultSet rs) 
			throws SQLException 
	{
		while (rs.next())	// ResultSet�� ���� ���� ������
		{
			tempList = new Vector();

			// ���ǹ�ȣ�� ���� ���� ���ǹ�ȣ�� �����´�.
			int l_num = rs.getInt(1);
			String language = null;
			switch (l_num / 1000) {
			case 1:		// 1000������ ��� ����
				language = "����";
				break;
			case 2:		// 2000������ ��� �Ϻ���
				language = "�Ϻ���";
				break;
			case 3:		// 3000������ ��� �߱���
				language = "�߱���";
				break;
			}

			tempList.add(l_num); 								// ���ǹ�ȣ
			tempList.add(language); 							// ���Ǿ��
			tempList.add(rs.getString(2)); 						// ���Ǹ�
			tempList.add(rs.getString(3)); 						// �����
			tempList.add(rs.getString(4)); 						// ���ǿ���
			tempList.add(rs.getString(5)); 						// ���ǽð�
			tempList.add(rs.getInt(6) + " ���ǽ�"); 			// ���ǽ�
			tempList.add(rs.getInt(7)); 							// ���Ǻ�
			tempList.add(rs.getInt(9) + "/" + rs.getInt(8));	 // ��û��Ȳ
			lecture_table.add(tempList);		// ���� ��� ���� Vector�� ArrayList�� �ִ´�.
		}
	}
}
