package dao.table;

import java.io.*;
import java.sql.*;

// 관리자 정보를 데이터베이스에 입, 출력하는 클래스
public class AdminDAO
{	
	// DB 연동을 위해 필요한 멤버 변수 선언
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;
    
    // DB 이름 및 계정 관련 변수
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB 드라이버 및 URL
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// 커넥션을 하나로 제한해 리소스 낭비를 줄이기 위해 싱글턴패턴을 적용한다.
	// 자기 자신 타입으로 변수 선언
	private static AdminDAO admin_DAO;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private AdminDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);		// 드라이버 로드
			conn = DriverManager.getConnection(URL, DB_USER, DB_PW);	// 데이터베이스 연결
		} 
		catch (ClassNotFoundException e) {
			System.out.println("데이터베이스 드라이버를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결에 실패하였습니다.");
			e.printStackTrace();
		}
	}
	
	// AdminDAO 인스턴스를 돌려주는 클래스
	public static AdminDAO getInstance()
	{
		// Admin_DAO 가 생성되지 않은 경우 생성해서 돌려준다.
		if (admin_DAO == null)
		{
			admin_DAO = new AdminDAO();
		}
		// 이미 생성되어 있는 경우 생성된 객체를 돌려줌. 즉, 모든 클래스는 하나의 AdminDAO 객체를 사용하게 됨
		return admin_DAO;
	}
	
	// Admin 객체에 id와 password를 넣어주는 메소드
	public Admin createAdmin()
	{
		Admin admin = new Admin();
		
		try 
		{
			sql = "select * from admin";
			stmt = conn.createStatement();		// stmt 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
						
			while (rs.next())
			{
				admin.setId(rs.getString("id"));
				admin.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally		// 연결을 유지하기 위해 stmt만 닫는다.
		{
			if (stmt != null)
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}

		return admin;
	}
	
	// 관리자 id와 password를 테이블에 입력하는 메소드 (성공시 1, 실패시 0)
	public int insertAdmin(Admin admin)
	{
		int result = 0;	// 업데이트 된 열 개수를 받아올 변수
		
		try 
		{
			sql = "insert into Admin values(?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt 생성
			
			pstmt.setString(1, admin.getId());				// sql문에 넣을 id 값 설정
			pstmt.setString(2, admin.getPassword());		// sql문에 넣을 password 값 설정
			
			result = pstmt.executeUpdate();				// sql문 실행
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// 연결을 유지하기 위해 pstmt만 닫는다.
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
