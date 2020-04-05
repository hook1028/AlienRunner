package dao.table;

import java.io.*;
import java.sql.*;

// ������ ������ �����ͺ��̽��� ��, ����ϴ� Ŭ����
public class AdminDAO
{	
	// DB ������ ���� �ʿ��� ��� ���� ����
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;
    
    // DB �̸� �� ���� ���� ����
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB ����̹� �� URL
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// Ŀ�ؼ��� �ϳ��� ������ ���ҽ� ���� ���̱� ���� �̱��������� �����Ѵ�.
	// �ڱ� �ڽ� Ÿ������ ���� ����
	private static AdminDAO admin_DAO;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private AdminDAO()
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
	
	// AdminDAO �ν��Ͻ��� �����ִ� Ŭ����
	public static AdminDAO getInstance()
	{
		// Admin_DAO �� �������� ���� ��� �����ؼ� �����ش�.
		if (admin_DAO == null)
		{
			admin_DAO = new AdminDAO();
		}
		// �̹� �����Ǿ� �ִ� ��� ������ ��ü�� ������. ��, ��� Ŭ������ �ϳ��� AdminDAO ��ü�� ����ϰ� ��
		return admin_DAO;
	}
	
	// Admin ��ü�� id�� password�� �־��ִ� �޼ҵ�
	public Admin createAdmin()
	{
		Admin admin = new Admin();
		
		try 
		{
			sql = "select * from admin";
			stmt = conn.createStatement();		// stmt ����
			rs = stmt.executeQuery(sql);			// sql�� ����
						
			while (rs.next())
			{
				admin.setId(rs.getString("id"));
				admin.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
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

		return admin;
	}
	
	// ������ id�� password�� ���̺� �Է��ϴ� �޼ҵ� (������ 1, ���н� 0)
	public int insertAdmin(Admin admin)
	{
		int result = 0;	// ������Ʈ �� �� ������ �޾ƿ� ����
		
		try 
		{
			sql = "insert into Admin values(?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt ����
			
			pstmt.setString(1, admin.getId());				// sql���� ���� id �� ����
			pstmt.setString(2, admin.getPassword());		// sql���� ���� password �� ����
			
			result = pstmt.executeUpdate();				// sql�� ����
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// ������ �����ϱ� ���� pstmt�� �ݴ´�.
		{
			if (pstmt != null)
			{
			    try { pstmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
}
