package dao.table;

// 강의 하나의 정보를 저장하는 클래스
public class Lecture
{
	// 정보를 저장하는 멤버 변수
	private int l_num;
	private int t_num;
	private int day;
	private int class_n;
	private int cost;
	private int all_people;
	private String title;
	private String time;
	
	// 기본 생성자. 모든 멤버변수가 null인 인스턴스가 생성된다.
	public Lecture() {}
	
	// 정보를 입력받아 객체를 생성하는 생성자
	public Lecture(int l_num, int t_num, int day, int class_n, int cost, int all_people, String title, String time)
	{
		this.l_num = l_num;
		this.t_num = t_num;
		this.day = day;
		this.class_n = class_n;
		this.cost = cost;
		this.all_people = all_people;
		this.title = title;
		this.time = time;
	}

	// getters and setters
	public int getL_num()
	{
		return l_num;
	}

	public void setL_num(int l_num)
	{
		this.l_num = l_num;
	}

	public int getT_num()
	{
		return t_num;
	}

	public void setT_num(int t_num)
	{
		this.t_num = t_num;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getClass_n()
	{
		return class_n;
	}

	public void setClass_n(int class_n)
	{
		this.class_n = class_n;
	}

	public int getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}

	public int getAll_people()
	{
		return all_people;
	}

	public void setAll_people(int all_people)
	{
		this.all_people = all_people;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}
}
