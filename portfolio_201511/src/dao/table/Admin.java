package dao.table;

// ������ ������ �����ϴ� Ŭ����
public class Admin
{	
	// ������ �����ϴ� �������
	private String id;
	private String password;
	
	// id�� password�� null �� �Ǵ� �⺻ ������
	public Admin() {}
	
	// id�� password�� �޾� ��ü�� ����� ������
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
