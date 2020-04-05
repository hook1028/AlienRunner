package dao.table;

import java.sql.*;
import java.util.*;

// lecture_table 뷰의 데이터를 가져오는 클래스
public class Lecture_TableDAO
{	
	// DB 연동을 위해 필요한 멤버 변수 선언
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Connection conn;
    
    // DB 이름 및 계정을 저장하는 변수
	final static private String DB_NAME = "project";
	final static private String DB_USER = "root";
	final static private String DB_PW = "123456";
	
	// DB 드라이버 및 URL
	final static private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static private String URL = "jdbc:mysql://localhost/" + DB_NAME;
	private String sql = null;
	
	// 커넥션을 하나로 제한해 리소스 낭비를 줄이기 위해 싱글턴패턴을 적용한다.
	// 자기 자신 타입으로 변수 선언
	private static Lecture_TableDAO Lecture_TableDAO;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private Lecture_TableDAO()
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

	// Lecture_TableDAO 인스턴스를 돌려주는 메소드
	public static Lecture_TableDAO getInstance() 
	{
		if (Lecture_TableDAO == null)
		{
			Lecture_TableDAO = new Lecture_TableDAO();
		}
		
		return Lecture_TableDAO;
	}
		
	// 모든 강의 결과를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> selectLecture_table()
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList 선언
		Vector tempList = null;							// 테이블에서 불러온 정보를 저장할 Vector 선언

		try
		{
			sql = "select * from lecture_table order by title asc";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			makeList(lecture_table, tempList, rs);		// 정보를 벡터에 담은 뒤 그 벡터를 다시 ArrayList에 담는 메소드
		}
		catch (SQLException e)
		{
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
		
		return lecture_table;
	}
	
	// 강의명으로 검색한 결과를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> searchLecture_tableByTitle(String title)
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList
		Vector tempList = null;							// 테이블에서 가져온 정보를 저장할 Vector

		try
		{
			// where 컬럼명 like '% 검색어 %' : 검색어가 포함된 값을 가져옴
			sql = "select * from lecture_table where title like '%" + title + "%'";
			stmt = conn.createStatement();		// stmt 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// 가져온 값을 담은 Vector를 ArrayList에 넣는 메소드
		}
		catch (SQLException e)
		{
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
		
		return lecture_table;
	}
	
	// 강사명으로 검색한 결과를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> searchLecture_tableByTeacher(String name)
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList
		Vector tempList = null;							// 테이블에서 가져온 값을 넣을 Vector

		try
		{
			sql = "select * from lecture_table where name like '%" + name + "%'";
			stmt = conn.createStatement();		// stmt 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// 테이블에서 가져온 값을 넣은 Vector를 ArrayList에 넣는 메소드
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture_table;
	}
	
	// 수강생이 수강신청한 강의를 ArrayList로 반환하는 메소드
	public ArrayList<Vector> searchLecture_tableBySt_Num(int st_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList
		Vector tempList = null;							// 테이블에서 가져온 값을 넣을 Vector

		try
		{
			sql = "select * from lecture_table where l_num in (select l_num from register where st_num =" + st_num + ")";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// 테이블에서 가져온 값을 넣은 Vector를 ArrayList에 넣는 메소드
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return lecture_table;
	}
	
	// 강의번호로 검색한 결과를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> searchLecture_tableByL_num(int l_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList
		Vector tempList = null;							// 테이블에서 가져온 값을 넣을 Vector

		try
		{
			sql = "select * from lecture_table where l_num = " + l_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// 테이블에서 가져온 값을 넣은 Vector를 ArrayList에 담는 메소드
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture_table;
	}

	// 강사번호로 검색한 결과를 Arraylist로 돌려주는 메소드
	public ArrayList<Vector> searchLecture_tableByT_num(int t_num)
	{
		ArrayList<Vector> lecture_table = null;		// Vector를 담을 ArrayList
		Vector tempList = null;							// 테이블에서 가져온 값을 담을 Vector

		try
		{
			sql = "select * from lecture_table where name in (select name from teacher where t_num = " + t_num + ")";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			lecture_table = new ArrayList<Vector>();
			
			makeList(lecture_table, tempList, rs);		// 테이블에서 가져온 값을 넣은 Vector를 ArrayList에 담는 메소드
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return lecture_table;
	}
	
	// 테이블에서 가져온 정보를 Vector에 넣은 뒤 그것을 다시 ArrayList에 넣어주는 메소드
	private void makeList(ArrayList<Vector> lecture_table, Vector tempList, ResultSet rs) 
			throws SQLException 
	{
		while (rs.next())	// ResultSet에 값이 있을 때까지
		{
			tempList = new Vector();

			// 강의번호를 통해 언어와 강의번호를 가져온다.
			int l_num = rs.getInt(1);
			String language = null;
			switch (l_num / 1000) {
			case 1:		// 1000번대일 경우 영어
				language = "영어";
				break;
			case 2:		// 2000번대일 경우 일본어
				language = "일본어";
				break;
			case 3:		// 3000번대일 경우 중국어
				language = "중국어";
				break;
			}

			tempList.add(l_num); 								// 강의번호
			tempList.add(language); 							// 강의언어
			tempList.add(rs.getString(2)); 						// 강의명
			tempList.add(rs.getString(3)); 						// 강사명
			tempList.add(rs.getString(4)); 						// 강의요일
			tempList.add(rs.getString(5)); 						// 강의시간
			tempList.add(rs.getInt(6) + " 강의실"); 			// 강의실
			tempList.add(rs.getInt(7)); 							// 강의비
			tempList.add(rs.getInt(9) + "/" + rs.getInt(8));	 // 신청현황
			lecture_table.add(tempList);		// 값을 모두 넣은 Vector를 ArrayList에 넣는다.
		}
	}
}
