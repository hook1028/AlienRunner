package dao.table;

// 관리자 정보를 저장하는 클래스
public class Admin
{	
	// 정보를 저장하는 멤버변수
	private String id;
	private String password;
	
	// id와 password가 null 이 되는 기본 생성자
	public Admin() {}
	
	// id와 password를 받아 객체를 만드는 생성자
	public Admin(String id, String password)
	{
		this.id = id;
		this.password = password;
	}

	// getters and setters
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
