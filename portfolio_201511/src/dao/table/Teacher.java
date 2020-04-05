package dao.table;

// 강사 한 명의 정보를 저장하는 클래스
public class Teacher
{	
	// 정보를 저장하는 멤버 변수
	private int t_num;
	private int gender;
	private String name;
	private String password;
	private String phone;
	private String email;
	
	// 기본 생성자
	public Teacher() {}
	
	// 정보를 받아 강사 객체를 생성하는 생성자
	public Teacher(int t_num, int gender, String name, String password,
			String phone, String email)
	{
		super();
		this.t_num = t_num;
		this.gender = gender;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	// getters and setters
	public int getT_num()
	{
		return t_num;
	}

	public void setT_num(int t_num)
	{
		this.t_num = t_num;
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
