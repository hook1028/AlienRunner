package dao.table;

// �������̺� ���� �ֱ� ���� �ùٸ� ���� ���Դ��� Ȯ���ϴ� Ŭ����
public class LectureDTO
{
	// ���� Ȯ���ϰ� �ѱ�� ���� �ʿ��� ����
	private LectureDAO lectureDAO;
	private Lecture lecture;
	
	// LectureDAO�� �ʿ�� �ϴ� ������
	public LectureDTO(LectureDAO lectureDAO)
	{
		this.lectureDAO = lectureDAO;
		lecture = new Lecture();
	}
	
	// LectureDAO�� Lecture�� ��� �ʿ�� �ϴ� ������
	public LectureDTO(LectureDAO lectureDAO, Lecture lecture)
	{
		this.lectureDAO = lectureDAO;
		this.lecture = lecture;
	}
	
	// ���� ��ȣ�� �������ִ� �޼ҵ�
	public boolean checkL_num(String language)
	{
		boolean result = false;		// ���ǹ�ȣ �������θ� ��ȯ�� ����
		
		switch (language)
		{
		case "����":
			lecture.setL_num(lectureDAO.createL_num(1000));	// 1. ����ȣ�� �Ѱ� ���� ��ȣ�� �����Ѵ�.
			result = true;											// 2. ������ ���ǹ�ȣ�� ���� ��ü�� �ִ´�.
			break;														// 3. �������θ� true�� �ٲ۴�.
			
		case "�Ϻ���":
			lecture.setL_num(lectureDAO.createL_num(2000));
			result = true;
			break;
			
		case "�߱���":
			lecture.setL_num(lectureDAO.createL_num(3000));
			result = true;
			break;	
		}
		
		return result;
	}
	
	// ���Ǹ��� �˻��� �� �־��ִ� �޼ҵ�
	public boolean checkTitle(String title)
	{
		boolean result = false;		// ���Ǹ��� �ùٸ��� �Էµƴ����� �˷��� ����
		title = title.trim();				// ���ڿ� ��, ���� ������ �����Ѵ�.
		// ���Ǹ��� 5���� �̻� 25�� ���Ϸ� �Ѵ�. ���ǿ� ������ ���� �ְ� true, �ƴ� �� false
		if (title.length() >= 5 && title.length() <= 25)
		{
			lecture.setTitle(title);		// �ش� ������ ���� �� lecture ��ü�� �ִ´�.
			result = true;			// �Է� ���θ� true�� �ٲ۴�.
		}
		
		return result;
	}
	
	// ���� ������ �˻��� �� �־��ִ� �޼ҵ�
	public boolean checkDay(String day)
	{
		boolean result = false;		// ���� ������ �� �����ƴ��� ���θ� �˷��� ����
		
		// ���� ���Ͽ� ���� lecture�� ���� ���� �ִ´�.
		// 1 = ��, ��, ��, 2 = ȭ, ��, ��, 3 = ��, ��
		switch(day)
		{
		case "��, ��, ��" :
			lecture.setDay(1);		// Lecture ��ü�� ���� ���� �ִ´�.
			result = true;			// ���� ���θ� true�� �ٲ۴�.
			break;
			
		case "ȭ, ��, ��" :
			lecture.setDay(2);
			result = true;
			break;
			
		case "��, ��" :
			lecture.setDay(3);
			result = true;
			break;
		}
		
		return result;
	}
	
	// �����ȣ�� �־��ִ� �޼ҵ�
	public boolean checkT_num(int t_num)
	{
		boolean result = false;		// ���ǹ�ȣ�� �� �Էµƴ��� �˷��� ����
		
		lecture.setT_num(t_num);	// Lecture ��ü�� �����ȣ�� �ִ´�.
		result = true;				// �Է¿��θ� true�� �ٲ۴�.
		
		return result;
	}
	
	// ���� �ð��� �־��ִ� �޼ҵ�
	public boolean checkTime(String startTime, String endTime)
	{
		boolean result = false;		// ���� �ð��� �� �Էµƴ��� �˷��� ����
		String time = "";
		
		// ���� �ð��� �ϳ��� ��Ʈ�� �������� ���� �� true�� ��ȯ
		time = startTime + "-" + endTime;
		lecture.setTime(time);		// Lecture ��ü�� ���� �ð��� �ִ´�.
		result = true;				// �Է¿��θ� true�� �ٲ۴�.
		
		return result;
	}
	
	// ���ǽ� ���� �־��ִ� �޼ҵ�
	public boolean checkClass(int class_n)
	{
		boolean result = false;		// ���ǽ� ���� �� �Էµƴ��� �˷��� ����
		
		// 1���� 5 ������ ���� �� ���Դ��� Ȯ�� ��, lecture�� �ְ� true ��ȯ
		if (class_n >= 1 && class_n <= 5)
		{
			lecture.setClass_n(class_n);		// Lecture ��ü�� ���ǽ� ���� �ִ´�.
			result = true;					// �Է¿��θ� true�� �ٲ۴�.
		}
		
		return result;
	}
	
	// ���ǷḦ �־��ִ� �޼ҵ�
	public boolean checkCost(int cost)
	{
		boolean result = false;		// ���Ƿ� ���� �� �Էµƴ��� �˷��� ����
		
		// ���Ƿ�� 300000 �̳��� �Ѵ�. ���� Ȯ���� �� ����� �������� lecture�� �ְ� true ��ȯ
		if (cost >= 0 && cost <= 300000)
		{
			lecture.setCost(cost);	// Lecture ��ü�� ���Ƿ� ���� �ִ´�.
			result = true;			// �Է� ���θ� true�� �ٲ۴�.
		}
		
		return result;
	}
	
	// �� �ο��� �־��ִ� �޼ҵ�
	public boolean checkAll_people(int all_people)
	{
		boolean result = false;		// �� �ο� ���� �� �Էµƴ��� �˷��� ����
		
		// 10 ~ 50 ������ �������� Ȯ�� �� ����� �������� lecture�� �ְ� true ��ȯ
		if (all_people >= 10 && all_people <= 50)
		{
			lecture.setAll_people(all_people);		// Lecture ��ü�� �� �ο� ���� �ִ´�.
			result = true;							// �Է� ���θ� true�� �ٲ۴�.
		}
		
		return result;
	}
	
	// ���� ��� ��ϵ� lecture�� ��ü�� ��ȯ
	public Lecture getLecture()
	{
		return lecture;
	}

}
