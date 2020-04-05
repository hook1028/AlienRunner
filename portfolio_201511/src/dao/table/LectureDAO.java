package dao.table;

import java.sql.*;

// ���� ���̺��� ���� ��, ����ϴ� Ŭ����
public class LectureDAO
{
	// DB ������ ���� �ʿ��� ��� ���� ����
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;
    
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
	private static LectureDAO lectureDAO;
	
	// �����ڸ� private Ÿ������ ����� ��ü ������ �����Ѵ�.
	// �����ڿ����� ������ ���̽� ��������� �Ѵ�
	private LectureDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);												// ����̹� �ε�
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
	
	// LectureDAO �ν��Ͻ��� �����ִ� Ŭ����
	public static LectureDAO getInstance()
	{
		if (lectureDAO == null)	// LectureDAO �ν��Ͻ��� ���� ��� �����ؼ� �����ش�.
		{
			lectureDAO = new LectureDAO();
		}
		
		return lectureDAO;		// �̹� �ִ� ��� �ִ� ��ü�� ��ȯ�� ��� Ŭ�������� �ϳ��� �ν��Ͻ��� ����ϰ� ��
	}
	
	// �� ���� ���� �˻��� ���ǹ�ȣ�� ������ ��ȯ�ϴ� �޼ҵ�
	public int createL_num(int language)		// ��� ��ȣ�� õ ������ �Է� (����: 1000, �Ϻ���: 2000, �߱���: 3000)
	{
		int l_num = 0;		// ������ ���ǹ�ȣ�� ���� ����
		
		try
		{
			sql = "select max(l_num) from lecture where l_num between " + language 
					+ " and " + (language + 999);
			stmt = conn.createStatement();		// stmt ��ü ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			while(rs.next())
			{
				l_num = rs.getInt(1) + 1;		// ���̺��� ��ȸ�� ���� ū ��ȣ�� 1�� ���Ѵ�. auto_increment�� ���� ����
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally			// ������ �����ϱ� ���� stmt�� �ݴ´�.
		{
			if (stmt != null)
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return l_num;
	}
	
	// ���� ��ȣ�� �˻��� ���� ������ �޾� Lecture Ŭ������ �����ִ� �޼ҵ�
	public Lecture createLecture (int l_num)
	{
		Lecture lecture = new Lecture();		// ���� ������ ���� Lecture �ν��Ͻ� ����
		
		try
		{
			sql = "select * from lecture where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			rs = stmt.executeQuery(sql);			// sql�� ����
			
			while(rs.next())
			{
				lecture.setL_num(rs.getInt(1));				// ���ǹ�ȣ
				lecture.setTitle(rs.getString(2));				// ����
				lecture.setT_num(rs.getInt(3));				// ������ ��ȣ
				lecture.setDay(rs.getInt(4));					// ����
				lecture.setTime(rs.getString(5));			// �ð�
				lecture.setClass_n(rs.getInt(6));				// ���ǽ�
				lecture.setCost(rs.getInt(7));				// ���Ƿ�
				lecture.setAll_people(rs.getInt(8));			// �� �ο�
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally			// ������ �����ϱ� ���� stmt�� �ݴ´�.
		{
			if (stmt != null)
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture;		// ������ ���� Lecture �ν��Ͻ� ��ȯ
	}
	
	// ���Ǹ� ����ϴ� �޼ҵ� (������ 1, ���� 0)
	public int insertLecture (Lecture lecture)
	{
		int result = 0;	// ������Ʈ �� �� ���� ���� ���� ����
		
		try 
		{
			sql = "insert into lecture values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			
			// sql�� ���� ���� �����Ѵ�.
			pstmt.setInt(1, lecture.getL_num());
			pstmt.setString(2, lecture.getTitle());
			pstmt.setInt(3, lecture.getT_num());
			pstmt.setInt(4, lecture.getDay());
			pstmt.setString(5, lecture.getTime());
			pstmt.setInt(6, lecture.getClass_n());
			pstmt.setInt(7, lecture.getCost());
			pstmt.setInt(8, lecture.getAll_people());
			
			result = pstmt.executeUpdate();		// sql�� ����	
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// ���� ������ ���� pstmt�� �ݴ´�.
		{
			if (pstmt != null)
			{
			    try { pstmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// ���Ǹ� �����ϴ� �޼ҵ� (������ 1, ���� 0)
	public int updateLecture(Lecture lecture)
	{
		int result = 0;		// ������Ʈ �� �� ������ ���� ����
		
		try 
		{
			sql = "update lecture set class = ?, all_people = ? where l_num = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt �ν��Ͻ� ����
			
			// sql���� ���� �� ����
			pstmt.setInt(1, lecture.getClass_n());
			pstmt.setInt(2, lecture.getAll_people());
			pstmt.setInt(3, lecture.getL_num());
			
			result = pstmt.executeUpdate();		// sql�� ����	
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)		// ���� ������ ���� pstmt�� �ݴ´�.
			{
			    try { pstmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// ���ǹ�ȣ�� �˻��� ���Ǹ� �����ϴ� �޼ҵ�
	public int deleteLecture(int l_num)
	{
		int result = 0;		// ������Ʈ �� �� ������ ���� ����
		
		try 
		{
			sql = "delete from lecture where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt �ν��Ͻ� ����
			result = stmt.executeUpdate(sql);		// sql�� ����
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
		
		return result;
	}
}
