package dao.table;

import java.sql.*;

public class RegisterDAO
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
	private static RegisterDAO register;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private RegisterDAO()
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
	
	// RegisterDAO �ν��Ͻ��� �����ִ� Ŭ����
	public static RegisterDAO getInstance()
	{
		if (register == null)			// Register ��ü�� ���� ��� �����ؼ� ��ȯ�Ѵ�.
		{
			register = new RegisterDAO();
		}
		
		return register;				// �̹� �ִ� ��� �ִ� ��ü�� ��ȯ, ��� Ŭ������ �ϳ��� �ν��Ͻ��� ����ϰ� ��.
	}
	
	// ���� ��û�� ���������� �˷��ִ� �޼ҵ�
	public boolean searchRegister(int l_num, int st_num)
	{
		boolean result = false;		// ������û ���ɺҰ����� �˷��� ����
		
		try
		{
			sql = "select * from register where l_num = ? and st_num = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			pstmt.setInt(1, l_num);						// sql���� ���� ���ǹ�ȣ ����
			pstmt.setInt(2, st_num);						// sql���� ���� �л���ȣ ����
			
			rs = pstmt.executeQuery();					// sql�� ����
			
			if(!(rs.next()))	// ��� ���� ������ true, �� �˻������ ���� ��� �����ϸ� ��û ������ ���ϴ� true�� ��ȯ�Ѵ�.
			{
				result = true;
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
		
		return result;
	}

	// ���� ����ϴ� �޼ҵ�
	public int insertRegister(int l_num, int st_num)
	{
		int result = 0;		// ������Ʈ �� �� ������ ��ȯ�� ����
		
		try
		{
			sql = "insert into register values (?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			pstmt.setInt(1, l_num);						// sql���� ���� �� ����
			pstmt.setInt(2, st_num);
			
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
	
	// ������û�� ����ϴ� �޼ҵ�
	public int deleteRegister(int l_num, int st_num)
	{
		int result = 0;		// ������Ʈ �� ������ �ޱ� ���� ���� 
		sql = "delete from register where l_num = ? and st_num = ?";
		
		try
		{
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			pstmt.setInt(1, l_num);						// sql���� ���� �� ����
			pstmt.setInt(2, st_num);
			
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
}
