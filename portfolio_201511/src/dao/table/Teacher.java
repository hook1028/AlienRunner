package dao.table;

// ���� �� ���� ������ �����ϴ� Ŭ����
public class Teacher
{	
	// ������ �����ϴ� ��� ����
	private int t_num;
	private int gender;
	private String name;
	private String password;
	private String phone;
	private String email;
	
	// �⺻ ������
	public Teacher() {}
	
	// ������ �޾� ���� ��ü�� �����ϴ� ������
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
