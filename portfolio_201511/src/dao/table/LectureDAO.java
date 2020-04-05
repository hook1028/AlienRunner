package dao.table;

import java.sql.*;

// 강의 테이블의 값을 입, 출력하는 클래스
public class LectureDAO
{
	// DB 연동을 위해 필요한 멤버 변수 선언
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;
    
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
	private static LectureDAO lectureDAO;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private LectureDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);												// 드라이버 로드
			conn = DriverManager.getConnection(URL, DB_USER, DB_PW);		// 데이터베이스 연결
		} 
		catch (ClassNotFoundException e) {
			System.out.println("데이터베이스 드라이버를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결에 실패하였습니다.");
			e.printStackTrace();
		}
	}
	
	// LectureDAO 인스턴스를 돌려주는 클래스
	public static LectureDAO getInstance()
	{
		if (lectureDAO == null)	// LectureDAO 인스턴스가 없을 경우 생성해서 돌려준다.
		{
			lectureDAO = new LectureDAO();
		}
		
		return lectureDAO;		// 이미 있는 경우 있는 객체를 반환해 모든 클래스에서 하나의 인스턴스만 사용하게 함
	}
	
	// 언어별 강의 수를 검색해 강의번호를 생성해 반환하는 메소드
	public int createL_num(int language)		// 언어 번호는 천 단위로 입력 (영어: 1000, 일본어: 2000, 중국어: 3000)
	{
		int l_num = 0;		// 생성할 강의번호를 담을 변수
		
		try
		{
			sql = "select max(l_num) from lecture where l_num between " + language 
					+ " and " + (language + 999);
			stmt = conn.createStatement();		// stmt 객체 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			while(rs.next())
			{
				l_num = rs.getInt(1) + 1;		// 테이블에서 조회된 가장 큰 번호에 1을 더한다. auto_increment와 같은 원리
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally			// 연결을 유지하기 위해 stmt만 닫는다.
		{
			if (stmt != null)
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return l_num;
	}
	
	// 강의 번호로 검색한 강의 정보를 받아 Lecture 클래스로 돌려주는 메소드
	public Lecture createLecture (int l_num)
	{
		Lecture lecture = new Lecture();		// 강의 정보를 담을 Lecture 인스턴스 생성
		
		try
		{
			sql = "select * from lecture where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			while(rs.next())
			{
				lecture.setL_num(rs.getInt(1));				// 강의번호
				lecture.setTitle(rs.getString(2));				// 제목
				lecture.setT_num(rs.getInt(3));				// 선생님 번호
				lecture.setDay(rs.getInt(4));					// 요일
				lecture.setTime(rs.getString(5));			// 시간
				lecture.setClass_n(rs.getInt(6));				// 강의실
				lecture.setCost(rs.getInt(7));				// 강의료
				lecture.setAll_people(rs.getInt(8));			// 총 인원
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally			// 연결을 유지하기 위해 stmt만 닫는다.
		{
			if (stmt != null)
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture;		// 정보를 담은 Lecture 인스턴스 반환
	}
	
	// 강의를 등록하는 메소드 (성공시 1, 실패 0)
	public int insertLecture (Lecture lecture)
	{
		int result = 0;	// 업데이트 된 열 개수 값을 받을 변수
		
		try 
		{
			sql = "insert into lecture values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			
			// sql에 넣을 값을 설정한다.
			pstmt.setInt(1, lecture.getL_num());
			pstmt.setString(2, lecture.getTitle());
			pstmt.setInt(3, lecture.getT_num());
			pstmt.setInt(4, lecture.getDay());
			pstmt.setString(5, lecture.getTime());
			pstmt.setInt(6, lecture.getClass_n());
			pstmt.setInt(7, lecture.getCost());
			pstmt.setInt(8, lecture.getAll_people());
			
			result = pstmt.executeUpdate();		// sql문 실행	
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally		// 연결 유지를 위해 pstmt만 닫는다.
		{
			if (pstmt != null)
			{
			    try { pstmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// 강의를 수정하는 메소드 (성공시 1, 실패 0)
	public int updateLecture(Lecture lecture)
	{
		int result = 0;		// 업데이트 된 열 개수를 받을 변수
		
		try 
		{
			sql = "update lecture set class = ?, all_people = ? where l_num = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			
			// sql문에 넣을 값 설정
			pstmt.setInt(1, lecture.getClass_n());
			pstmt.setInt(2, lecture.getAll_people());
			pstmt.setInt(3, lecture.getL_num());
			
			result = pstmt.executeUpdate();		// sql문 실행	
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pstmt != null)		// 연결 유지를 위해 pstmt만 닫는다.
			{
			    try { pstmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
	
	// 강의번호로 검색한 강의를 삭제하는 메소드
	public int deleteLecture(int l_num)
	{
		int result = 0;		// 업데이트 된 열 개수를 받을 변수
		
		try 
		{
			sql = "delete from lecture where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			result = stmt.executeUpdate(sql);		// sql문 실행
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// 연결 유지를 위해 stmt만 닫는다.
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;
	}
}
