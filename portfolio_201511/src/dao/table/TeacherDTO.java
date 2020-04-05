package dao.table;

import java.util.*;
import java.util.regex.*;

public class TeacherDTO 
{
	// 값을 검사하고 넘기기 위해 필요한 변수
	private TeacherDAO teacherDAO;
	private Teacher teacher;
	
	// TeacherDAO만 받는 생성자
	public TeacherDTO(TeacherDAO teacherDAO)
	{
		this.teacherDAO = teacherDAO;
		teacher = new Teacher();
	}
	
	// TeacherDAO와 Teacher 객체를 모두 받는 생성자
	public TeacherDTO(TeacherDAO teacherDAO, Teacher teacher)
	{
		this.teacherDAO = teacherDAO;
		this.teacher = teacher;
	}

	// 강사 번호를 넣어주는 메소드
	public boolean checkT_num(String language)
	{
		boolean result = false;		// 강사번호가 잘 들어갔을 경우 true를 반환할 변수
		
		switch(language)
		{
		case "영어":
			teacher.setT_num(teacherDAO.createT_num(100));		// 영어의 경우 1000번대로 강사번호를 생성한 뒤
			result = true;												// 결과 값을 true로 설정한다.
			break;
			
		case "일본어":
			teacher.setT_num(teacherDAO.createT_num(200));		// 일본어의 경우 2000번대로 강사번호를 생성한 뒤
			result = true;												// 결과 값을 true로 설정한다.
			break;
			
		case "중국어":
			teacher.setT_num(teacherDAO.createT_num(300));		// 중국어의 경우 3000번대로 강사번호를 생성한 뒤
			result = true;												// 결과 값을 true로 설정한다.
			break;
		}
		
		return result;		// 결과 값 반환
	}

	// 이름이 정확히 입력됐나 확인하는 메소드
	public boolean checkName(String name)
	{
		boolean result = false;		// 이름이 잘 입력됐을 경우 true를 반환할 boolean 변수
		name = name.trim();		// 이름의 앞, 뒤 공백을 제거한다.
		
		if (name.length() >= 2 && name.length() <= 5)	// 이름은 2자 이상 5자 이하로 입력받는다.
		{
			teacher.setName(name);		// 조건에 맞게 입력됐을 시 Teacher객체에 이름을 넣는다.
			result = true;					// 결과 값을 true로 바꾼다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 패스워드가 정확히 입력됐나 확인하는 메소드
	public boolean checkPassword(String password)
	{
		boolean result = false;			// 패스워드가 잘 입력됐을 경우 true를 반환할 boolean 변수
		password = password.trim();	// 패스워드의 앞, 뒤 공백을 제거한다.
		
		if (password.length() >= 8 && password.length() <= 20)	// 비밀번호는 8자리 이상 20자리 이하로 입력한다.
		{
			teacher.setPassword(password);		// 조건에 맞게 입력됐을 시 Teacher 객체에 비밀번호를 넣는다.
			result = true;							// 결과 값을 true로 바꾼다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 성별 확인하는 메소드
	public boolean checkGender(int gender)
	{
		boolean result = false;		// 성별이 잘 입력됐을 시 true를 반환할 boolean 변수
		
		if (gender == 1)			// 남자인 경우
		{
			teacher.setGender(1);	// Teacher 객체에 성별 값을 넣는다.
			result = true;			// 결과 값을  true로 바꾼다.
		}
		else if (gender == 0)		// 여자인 경우
		{
			teacher.setGender(0);
			result = true;
		}
				
		return result;		// 결과 값 반환
	}
	
	// 이메일 형식 확인하는 메소드
	public boolean checkEmail(String email)
	{
		boolean result = false;		// e-mail이 잘 입력됐을 시 true를 반환할 boolean 변수 
		email = email.trim();			// e-mail의 앞, 뒤 공백을 제거한다.
		
		// Pattern 클래스를 통해 패턴을 만들어 정확한 이메일인지 확인한다.
		// 패턴 방식은 Student와 동일하므로 StudentDTO 클래스 참고
		String pattern = "^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$";
		Pattern emailPattern = Pattern.compile(pattern);
		
		if ((emailPattern.matcher(email)).matches())
		{
			result = true;
			teacher.setEmail(email);
		}
		
		return result;		// 결과 값 반환
	}
	
	// 전화번호 확인하는 메소드
	public boolean checkPhone(String phone)
	{
		boolean result = false;		// 전화번호가 잘 입력됐을 시 true를 반환할 boolean 변수
		phone = phone.trim();		// 전화번호의 앞, 뒤 공백을 제거한다.
		
		// Pattern 클래스를 통해 패턴을 만들어 정확한 전화번호인지 확인한다.
		// 패턴 방식은 Student와 동일하므로 StudentDTO 클래스 참고
		String pattern = "(0\\d{2})-(\\d{3,4})-(\\d{4})";
		Pattern phonePattern = Pattern.compile(pattern);
		
		if (phonePattern.matcher(phone).matches())
		{
			result = true;			
		}
		
		return result;		// 결과 값 반환
	}
	
	// 전화번호 중복 확인 메소드
	public boolean overlapPhone(String phone)
	{
		ArrayList<String> list;		// 전화번호를 String으로 담아올 ArrayList
		boolean result = true;		// 중복이 아닐 경우 true를 반환할 boolean 변수
		
		list = teacherDAO.selectPhone();		// teacherDAO를 통해 저장된 전화번호 리스트를 받아온다.
		
		for (int i = 0; i < list.size(); i++)
		{
			if (phone.equals(list.get(i)))		// 중복되는 전화번호가 있는 경우
			{
				result = false;					// 결과 값을 false로 바꾼다.
				break;								// 반복문 종료
			}
		}
		
		if (result)		// 중복되는 전화번호가 없는 경우
		{
			teacher.setPhone(phone);		// Teacher 객체에 전화번호를 넣는다.
		}
		
		return result;		// 결과 값 반환
	}
	
	// 정보를 다 담은 teacher 객체를 반환하는 메소드
	public Teacher getTeacher()
	{
		return teacher;
	}
}
