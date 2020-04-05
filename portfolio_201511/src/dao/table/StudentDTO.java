package dao.table;

import java.util.*;
import java.util.regex.*;

public class StudentDTO
{	
	// 값을 검사하고 넘기기 위해 필요한 변수
	private Student student;
	private StudentDAO studentDAO;
	
	// StudentDAO만 받고, Student 객체는 생성하는 생성자
	public StudentDTO(StudentDAO studentDAO)
	{
		this.studentDAO = studentDAO;
		student = new Student();
	}
	
	// StudentDAO와 Student 객체를 모두 받는 생성자
	public StudentDTO(StudentDAO studentDAO, Student student)
	{
		this.student = student;
		this.studentDAO = studentDAO;
	}

	// 이름이 정확히 입력됐나 확인하는 메소드
	public boolean checkName(String name)
	{
		boolean result = false;		// 이름이 잘 입력됐을 시 true를 반환하는 boolean 변수
		name = name.trim();		// 받은 이름의 앞, 뒤 공백을 제거한다.
		
		if (name.length() >= 2 && name.length() <= 5)	// 이름은 2자 이상 5자 이하로 입력받게 한다.
		{
			student.setName(name);		// 해당 조건에 맞을 시, 객체에 이름을 넣고
			result = true;					// boolean 변수를 true로 바꾼다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 패스워드가 정확히 입력됐나 확인하는 메소드
	public boolean checkPassword(String password)
	{
		boolean result = false;			// 패스워드가 잘 입력됐을 시 true를 반환하는 boolean 변수
		password = password.trim();	// 패스워드의 앞, 뒤 공백을 제거한다.
		
		if (password.length() >= 8 && password.length() <= 20)	// 비밀번호는 8자리 이상 20자리 이하
		{
			student.setPassword(password);		// 해당 조건에 맞을 시, Student 객체에 비밀번호를 넣고
			result = true;							// boolean 변수를 true로 바꾼다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 성별 확인하는 메소드
	public boolean checkGender(int gender)
	{
		boolean result = false;		// 성별이 잘 입력됐을 시 true를 반환할 변수
		
		if (gender == 1)		// 남자일 경우
		{
			student.setGender(1);	// Student 객체에 성별 값을 넣고
			result = true;			// boolean 변수를 true로 바꾼다.
		}
		else if (gender == 0)	// 여자일 경우
		{
			student.setGender(0);
			result = true;
		}
				
		return result;			// 결과 값 반환
	}
	
	// 이메일 형식 확인하는 메소드
	// 패턴식 사용
	public boolean checkEmail(String email)
	{
		boolean result = false;		// e-mail이 잘 입력됐을 시 true를 반환할 boolean 변수
		email = email.trim();			// e-mail의 앞, 뒤 공백을 제거한다.
		
		// Pattern 클래스를 통해 패턴을 만들어 정확한 이메일인지 확인한다.
		// ^ : 형식의 시작
		// [0-9a-zA-z_-] : 숫자, 소문자, 대문자, -, _ 만 허용됨
		// + : 앞에 써놓은 형식의 문자가 1개 이상 반복
		// @ : 이메일의 @
		// \\. :  도메인 사이의 점
		// {1, 2} : 앞에 써놓은 형식이 1개 혹은 2개 존재 (ex) .com 혹은 .co.kr)
		// $ : 형식의 끝
		String pattern = "^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$";
		Pattern emailPattern = Pattern.compile(pattern);		// 패턴식을 입력한 String을 Pattern 인스턴스에 넣음
		
		if ((emailPattern.matcher(email)).matches())		// email 변수가 입력한 패턴에 맞는 지 검사해 맞다면 (= true)
		{
			student.setEmail(email);		// 값을 Student 객체에 넣고,
			result = true;				// 결과 값을 true로 바꿈.
		}
		
		return result;			// 결과 값 반환
	}
	
	// 전화번호 확인하는 메소드
	// 패턴식 사용
	public boolean checkPhone(String phone)
	{
		boolean result = false;		// 전화번호가 잘 입력됐을 시 true를 반환할 boolean 변수
		phone = phone.trim();		// 전화번호의 앞, 뒤 공백을 제거한다.
		
		// Pattern 클래스를 통해 패턴을 만들어 정확한 전화번호인지 확인한다.
		// 0\\d{2} : 0으로 시작하는 세 자리의 숫자 (000 ~ 099)
		// \\d{3,4} : 숫자 3자리 혹은 4자리
		// \\d{4} : 숫자 4자리
		String pattern = "(0\\d{2})-(\\d{3,4})-(\\d{4})";
		Pattern phonePattern = Pattern.compile(pattern);		// 패턴식을 입력한 String을 Pattern 인스턴스에 넣음
		
		if (phonePattern.matcher(phone).matches())		// phone 변수가 입력한 패턴에 맞는 지 검사해 맞다면 (=true)
		{
			result = true;		// true를 반환
		}
		
		return result;		// 결과 값 반환
	}
	
	// 전화번호 중복 확인하는 메소드
	public boolean overlapPhone(String phone)
	{
		ArrayList<String> list;			// 현재 저장되어 있는 전화번호들을 담을 ArrayList
		boolean result = true;			// 전화번호가 중복되지 않아 저장될 경우 true를 반환할 boolean 변수
		
		list = studentDAO.selectPhone();	// StudentDAO에서 현재 저장되어 있는 전화번호를 담아옴
		
		for (int i = 0; i < list.size(); i++)
		{
			if (phone.equals(list.get(i)))		// 중복되는 전화번호가 있다면
			{
				result = false;				// false로 설정한 후
				break;							// 반복문 종료
			}
		}
		
		if (result)		// 중복되는 전화번호가 없는 경우
		{
			student.setPhone(phone);		// Student 객체에 전화번호를 넣는다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 정보가 모두 입력된 학생 객체를 돌려주는 메소드
	public Student getStudent()
	{
		return student;
	}
	
}
