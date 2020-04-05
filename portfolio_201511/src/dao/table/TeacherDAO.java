package dao.table;

import java.sql.*;
import java.util.*;

public class TeacherDAO
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
	private static TeacherDAO teacher;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private TeacherDAO()
	{
		try 
		{
			Class.forName(JDBC_DRIVER);		// 드라이버 로드
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
	
	// TeacherDAO 인스턴스를 돌려주는 메소드
	public static TeacherDAO getInstance()
	{
		if (teacher == null)			// 생성된 인스턴스가 없는 경우 인스턴스를 생성해 돌려줌
		{
			teacher = new TeacherDAO();
		}
		
		return teacher;		// 생성된 인스턴스가 있는 경우 이를 반환해 모든 클래스가 한개의 인스턴스만 사용하게 함
	}
	
	// 강사수를 검색해 강사번호 돌려주는 메소드
	public int createT_num (int language)
	{
		int t_num = 0;		// 강사번호를 받아올 변수
		
		try
		{
			sql = "select max(t_num) from teacher where t_num between " + language 
					+ " and " + (language + 99);
			stmt = conn.createStatement();	// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);		// sql문 실행
			
			while(rs.next())
			{
				t_num = rs.getInt(1) + 1;	// 가장 큰 강사번호를 받아온 뒤 1을 더해 돌려준다.
			}
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
		
		return t_num;		// 만들어진 강사번호를 반환한다.
	}
	
	// 전체 강사를 ArrayList로 불러오는 메소드
	public ArrayList<Vector> selectTeacher()
	{
		ArrayList<Vector> teachers = null;		// Vector를 넣을 ArrayList
		Vector tempList = null;						// 데이터베이스에서 받아온 값을 넣을 Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher order by name asc";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())		// 데이터베이스에서 받아올 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector에 담은 뒤,
				
				teachers.add(tempList);					// 그 Vector를 ArrayList에 담는다.
			}
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
		
		return teachers;
	}
	
	// 강사번호로 검색한 결과를 ArrayList로 불러주는 메소드
	public ArrayList<Vector> selectTeacherByT_num(int t_num)
	{
		ArrayList<Vector> teachers = null;		// Vector를 담을 ArrayList
		Vector tempList = null;						// 데이터베이스에서 가져온 값을 넣을 Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher where t_num = " + t_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 생성
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())			// 데이터베이스에 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector에 넣은 뒤
				
				teachers.add(tempList);					// 그 Vector를 ArrayList에 넣는다.
			}
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
		
		return teachers;
	}
	
	// 강사 이름으로 검색한 결과를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> selectTeacherByName(String name)
	{
		ArrayList<Vector> teachers = null;	// Vector를 담을 ArrayList
		Vector tempList = null;					// 데이터베이스에서 받아온 값을 넣을 Vector
		
		try
		{
			sql = "select t_num, name, gender, phone, email from teacher where name like '%" + name +"%'"; 
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			teachers = new ArrayList<Vector>();
			
			while (rs.next())		// 데이터베이스에 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("t_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));		// 값을 Vector에 넣은 뒤
				
				teachers.add(tempList);						// 그 Vector를 ArrayList에 넣는다.
			}
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
		
		return teachers;
	}
		
	// 전체 강사번호와 이름을 String 형태로 저장해 ArrayList로 불러오는 메소드
	public ArrayList<String> selectT_numAndName()
	{
		ArrayList<String> teacher_nn = null;		// 강사번호와 이름이 담긴 String을 받을 ArrayList
		String t_numAndName = null;				// 강사번호와 이름을 하나의 String에 담기 위한 변수
		
		try
		{
			sql = "select t_num, name from teacher order by name asc";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			teacher_nn = new ArrayList<String>();
			
			while (rs.next())
			{
				t_numAndName = rs.getInt("t_num") + " ";		// [강사번호] [강사명] 형태의 한 문자열로 만든다.
				t_numAndName += rs.getString("name");		// ex) 100 유승민
				
				teacher_nn.add(t_numAndName);	// 만든 String을 ArrayList에 넣는다.
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)			// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teacher_nn;
	}
	
	// 전체 강사 전화번호 정보를 ArrayList로 돌려주는 메소드
	public ArrayList<String> selectPhone()
	{
		ArrayList<String> teacher_phone = null;		// 전화번호를 String으로 받을 ArrayList 생성
		String phone = null;								// 전화번호를 받아올 변수
		
		try
		{
			sql = "select phone from teacher order by name asc";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			teacher_phone = new ArrayList<String>();
			
			while (rs.next())		// 데이터베이스에 값이 있다면
			{
				phone = rs.getString("phone");		// String으로 받아와	
				teacher_phone.add(phone);			// ArrayList에 넣는다.
			}
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
		
		return teacher_phone;
	}

	// 강사번호로 검색한 강사 정보를 Teacher 클래스로 돌려주는 메소드
	public Teacher createTeacher(int t_num)
	{
		Teacher teacher = new Teacher();		// 검색된 강사 정보를 담을 Teacher 인스턴스 생성
		
		try
		{
			sql = "select * from teacher where t_num = " + t_num;
			stmt = conn.createStatement();	// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);		// sql문 실행
			
			while (rs.next())		// 데이터베이스에 값이 있다면
			{
				teacher.setT_num(rs.getInt("t_num"));			// Teacher 인스턴스에 값을 담는다.
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
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return teacher;		// 만들어진 Teacher 객체 반환
	}
	
	// 새로운 강사를 등록하는 메소드 (성공 시 1, 실패 시 0)
	public int insertTeacher(Teacher teacher)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try
		{
			sql = "insert into teacher values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			
			pstmt.setInt(1, teacher.getT_num());		// sql문에 넣을 값 설정
			pstmt.setString(2, teacher.getName());
			pstmt.setString(3, teacher.getPassword());
			pstmt.setInt(4, teacher.getGender());
			pstmt.setString(5, teacher.getEmail());
			pstmt.setString(6, teacher.getPhone());
			
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
	
	// 강사정보를 수정하는 메소드 (성공 시 1, 실패 시 0)
	public int updateTeacher(Teacher teacher)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try
		{
			sql = "update teacher set password = ?, phone = ?, email = ? where t_num = ?";
			pstmt = conn.prepareStatement(sql);			// pstmt 인스턴스 생성
			
			pstmt.setString(1, teacher.getPassword());	// sql문에 넣을 값 설정
			pstmt.setString(2, teacher.getPhone());
			pstmt.setString(3, teacher.getEmail());
			pstmt.setInt(4, teacher.getT_num());
			
			result = pstmt.executeUpdate();				// sql문 실행
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
	
	// 강사번호로 검색한 강사를 삭제하는 메소드 (성공 시 1, 실패시 0)
	public int deleteTeacher(int t_num)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try 
		{
			sql = "delete from teacher where t_num = " + t_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			result = stmt.executeUpdate(sql);		// sql문 실행
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
		
		return result;
	}
}
