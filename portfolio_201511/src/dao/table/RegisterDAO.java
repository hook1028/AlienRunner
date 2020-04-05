package dao.table;

import java.sql.*;

public class RegisterDAO
{
	// DB 연동을 위해 필요한 멤버 변수 선언
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Connection conn;
    
    // DB 이름 및 계정
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB URL 및 드라이버
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// 커넥션을 하나로 제한해 리소스 낭비를 줄이기 위해 싱글턴패턴을 적용한다.
	// 자기 자신 타입으로 변수 선언
	private static RegisterDAO register;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private RegisterDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);											// 드라이버 로드
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
	
	// RegisterDAO 인스턴스를 돌려주는 클래스
	public static RegisterDAO getInstance()
	{
		if (register == null)			// Register 객체가 없을 경우 생성해서 반환한다.
		{
			register = new RegisterDAO();
		}
		
		return register;				// 이미 있는 경우 있는 객체를 반환, 모든 클래스가 하나의 인스턴스를 사용하게 함.
	}
	
	// 수강 신청이 가능한지를 알려주는 메소드
	public boolean searchRegister(int l_num, int st_num)
	{
		boolean result = false;		// 수강신청 가능불가능을 알려줄 변수
		
		try
		{
			sql = "select * from register where l_num = ? and st_num = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			pstmt.setInt(1, l_num);						// sql문에 넣을 강의번호 설정
			pstmt.setInt(2, st_num);						// sql문에 넣을 학생번호 설정
			
			rs = pstmt.executeQuery();					// sql문 실행
			
			if(!(rs.next()))	// 결과 값이 없으면 true, 즉 검색결과가 없어 등록 가능하면 신청 가능을 뜻하는 true를 반환한다.
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
			if (pstmt != null)		// 연결을 유지하기 위해 pstmt만 닫는다.
			{
				try { pstmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}

	// 수강 등록하는 메소드
	public int insertRegister(int l_num, int st_num)
	{
		int result = 0;		// 업데이트 된 열 개수를 반환할 변수
		
		try
		{
			sql = "insert into register values (?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			pstmt.setInt(1, l_num);						// sql문에 넣을 값 설정
			pstmt.setInt(2, st_num);
			
			result = pstmt.executeUpdate();			// sql문 실행
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)		// 연결을 유지하기 위해 pstmt만 닫는다.
			{
				try { pstmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// 수강신청을 취소하는 메소드
	public int deleteRegister(int l_num, int st_num)
	{
		int result = 0;		// 업데이트 열 개수를 받기 위한 변수 
		sql = "delete from register where l_num = ? and st_num = ?";
		
		try
		{
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			pstmt.setInt(1, l_num);						// sql문에 넣을 값 설정
			pstmt.setInt(2, st_num);
			
			result = pstmt.executeUpdate();			// sql문 실행
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)		// 연결을 유지하기 위해 pstmt만 닫는다.
			{
				try { pstmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
}
