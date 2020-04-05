package dao.table;

import java.sql.*;
import java.util.*;

public class StudentDAO
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
	private static StudentDAO student;
	
	// 생성자를 private 타입으로 만들어 객체 생성을 제한한다.
	// 생성자에서는 데이터 베이스 연결까지만 한다
	private StudentDAO()
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
	
	// StudentDAO 인스턴스를 돌려주는 클래스
	public static StudentDAO getInstance()
	{
		if (student == null)			// 생성된 인스턴스가 없을 경우 인스턴스를 생성해 반환한다.
		{
			student = new StudentDAO();
		}
		
		return student;			// 생성된 인스턴스가 있는 경우 이를 반환해 하나의 인스턴스만 사용하게 함.
	}
		
	// 전체 수강생 정보를 ArrayList로 불러오는 메소드
	public ArrayList<Vector> selectStudent()
	{
		ArrayList<Vector> students = null;		// Vector를 담을 ArrayList
		Vector tempList = null;						// 데이터베이스에서 가져온 값을 넣을 Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// 데이터베이스에서 받아온 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector에 값을 담은 후
				
				students.add(tempList);					// 그 Vector를 ArrayList에 담는다.
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
		
		return students;
	}
	
	// 특정 강의를 신청한 학생 정보를 ArrayList로 불러오는 메소드
	public ArrayList<Vector> selectStudentByLNum(int l_num)
	{
		ArrayList<Vector> students = null;		// Vector를 담을 ArrayList
		Vector tempList = null;						// 데이터베이스에서 불러온 값을 저장할 Vector
		
		try
		{
			sql = "select name, gender, phone, email from student "
					+ "where st_num in (select st_num from register where l_num = " + l_num
					+ ")order by name asc";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			students = new ArrayList<Vector>();
			
			while (rs.next())			// 데이터베이스에 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));		// Vector에 담은 뒤
				
				students.add(tempList);						// 그 Vector를 다시 ArrayList에 담는다.
			}
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
		
		return students;
	}
	
	// 학생 번호로 찾은 학생 정보를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> selectStudentBySt_num(int st_num)
	{
		ArrayList<Vector> students = null;	// Vector를 담을 ArrayList
		Vector tempList = null;					// 데이터베이스에서 가져온 값을 담을 Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// 데이터베이스에 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector에 담은 뒤
				
				students.add(tempList);					// 그 Vector를 ArrayList에 담는다.
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
		
		return students;
	}
	
	// 이름으로 찾은 학생 정보를 ArrayList로 돌려주는 메소드
	public ArrayList<Vector> selectStudentByName(String name)
	{
		ArrayList<Vector> students = null;		// Vector를 담을 ArrayList
		Vector tempList = null;						// 데이터베이스에서 가져온 값을 담을 Vector
		
		try
		{
			sql = "select st_num, name, gender, phone, email from student where name like '%" + name + "%'";
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			students = new ArrayList<Vector>();
					
			while (rs.next())		// 데이터베이스에서 가져올 값이 있다면
			{
				tempList = new Vector();
				tempList.add(rs.getInt("st_num"));
				tempList.add(rs.getString("name"));
				tempList.add((rs.getInt("gender") == 1) ? "남" : "여");
				tempList.add(rs.getString("phone"));
				tempList.add(rs.getString("email"));	// Vector에 넣은 뒤
				
				students.add(tempList);					// 그 Vector를 ArrayList에 넣는다.
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
		
		return students;
	}
	
	
	// 전체 학생 전화번호 정보를 ArrayList로 돌려주는 메소드
	public ArrayList<String> selectPhone()
	{
		ArrayList<String> students_phone = null;		// 제네릭으로 String만 받는 ArrayList 생성
		String phone = null;

		try 
		{
			sql = "select phone from student";
			stmt = conn.createStatement();	// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);		// sql문 실행

			students_phone = new ArrayList<String>();

			while (rs.next()) 		// 데이터베이스에 전화번호 값이 있다면
			{
				phone = rs.getString("phone");	// String에 넣어
				students_phone.add(phone);		// ArrayList에 넣음
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if (stmt != null) 		// 연결을 유지하기 위해 stmt만 닫는다.
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
	
	// 수강생 번호로 검색한 수강생 정보를 Student 객체로 돌려주는 메소드
	public Student createStudent(int st_num)
	{
		Student student = new Student();		// 수강생 정보를 담을 Student 객체 생성
				
		try
		{
			sql = "select * from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			rs = stmt.executeQuery(sql);			// sql문 실행
			
			while (rs.next())		// 수강생 번호로 검색된 수강생 정보를 가져와 Student 객체에 담는다.
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
			if (stmt != null)		// 연결을 유지하기 위해 stmt만 닫는다.
			{
				try { stmt.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return student;
	}
	
	// 이름과 전화번호로 수강생을 찾아 회원 번호를 보내주는 메소드
	public int findSt_num(String name, String phone)
	{
		int st_num = 0;		// 반환할 수강생 번호를 저장할 변수
				
		try
		{
			sql = "select st_num from student where name = ? and phone = ?";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			
			pstmt.setString(1, name);		// sql문에 넣을 이름 값 설정
			pstmt.setString(2, phone);		// sql문에 넣을 전화번호 값 설정
			rs = pstmt.executeQuery();		// sql문 실행
			
			while (rs.next())		// 데이터베이스에 검색 결과가 있다면
			{
				st_num = rs.getInt("st_num");		// 학생 번호를 얻어온다.
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
		
		return st_num;
	}
	
	// 새로운 수강생 정보를 입력하는 메소드 (성공 시 1, 실패 시 0)
	public int insertStudent(Student student)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try
		{
			sql = "insert into student values (null, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);		// pstmt 인스턴스 생성
			
			pstmt.setString(1, student.getName());		// sql문에 넣을 값 설정
			pstmt.setString(2, student.getPassword());
			pstmt.setInt(3, student.getGender());
			pstmt.setString(4, student.getEmail());
			pstmt.setString(5, student.getPhone());
			
			result = pstmt.executeUpdate();		// sql문 실행
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
		
		return result;		// 결과 값 반환
	}
	
	// 수강생 정보를 수정하는 메소드 (성공 시 1, 실패 시 0)
	public int updateStudent(Student student)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try
		{
			sql = "update student set password = ?, phone = ?, email = ? where st_num = ?";
			pstmt = conn.prepareStatement(sql);			// pstmt 인스턴스 생성
			
			pstmt.setString(1, student.getPassword());	// sql문에 넣을 값 설정
			pstmt.setString(2, student.getPhone());
			pstmt.setString(3, student.getEmail());
			pstmt.setInt(4, student.getSt_num());
			
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
		
		return result;		// 결과 값 반환
	}
	
	// 회원번호로 검색한 수강생을 삭제하는 메소드 (성공 시 1, 실패 시 0)
	public int deleteStudent(int st_num)
	{
		int result = 0;		// 업데이트 된 열 개수를 받아올 변수
		
		try 
		{
			sql = "delete from student where st_num = " + st_num;
			stmt = conn.createStatement();		// stmt 인스턴스 생성
			result = stmt.executeUpdate(sql);		// slq문 실행
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)		//  연결을 유지하기 위해 stmt만 닫는다.
			{
			    try { stmt.close(); } 
			    catch (SQLException e) { e.printStackTrace(); }
			}
		}
		
		return result;		// 결과 값 반환
	}	
}
