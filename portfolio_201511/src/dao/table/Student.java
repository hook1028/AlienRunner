package dao.table;

// 수강생 한 명의 정보를 저장하는 클래스
public class Student
{	
	// 회원 정보 멤버 변수
	private int st_num;
	private int gender;
	private String name;
	private String password;
	private String email;
	private String phone;
	
	// 기본 생성자
	public Student() {}
	
	// 정보를 입력받아 수강생 객체를 생성한다.
	public Student(int s_num, int gender, String name, String password, String email, String phone)
	{
		this.st_num = s_num;
		this.gender = gender;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}
	
	// 수강생 정보를 가져오고 설정하는 getter and setters
	public int getSt_num()
	{
		return st_num;
	}

	public void setSt_num(int st_num)
	{
		this.st_num = st_num;
	}

	public int getGender()
	{
		return gender;
	}

	public void setGender(int gender)
	{
		this.gender = gender;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
