package dao.table;

import java.util.*;
import java.util.regex.*;

public class TeacherDTO 
{
	// ���� �˻��ϰ� �ѱ�� ���� �ʿ��� ����
	private TeacherDAO teacherDAO;
	private Teacher teacher;
	
	// TeacherDAO�� �޴� ������
	public TeacherDTO(TeacherDAO teacherDAO)
	{
		this.teacherDAO = teacherDAO;
		teacher = new Teacher();
	}
	
	// TeacherDAO�� Teacher ��ü�� ��� �޴� ������
	public TeacherDTO(TeacherDAO teacherDAO, Teacher teacher)
	{
		this.teacherDAO = teacherDAO;
		this.teacher = teacher;
	}

	// ���� ��ȣ�� �־��ִ� �޼ҵ�
	public boolean checkT_num(String language)
	{
		boolean result = false;		// �����ȣ�� �� ���� ��� true�� ��ȯ�� ����
		
		switch(language)
		{
		case "����":
			teacher.setT_num(teacherDAO.createT_num(100));		// ������ ��� 1000����� �����ȣ�� ������ ��
			result = true;												// ��� ���� true�� �����Ѵ�.
			break;
			
		case "�Ϻ���":
			teacher.setT_num(teacherDAO.createT_num(200));		// �Ϻ����� ��� 2000����� �����ȣ�� ������ ��
			result = true;												// ��� ���� true�� �����Ѵ�.
			break;
			
		case "�߱���":
			teacher.setT_num(teacherDAO.createT_num(300));		// �߱����� ��� 3000����� �����ȣ�� ������ ��
			result = true;												// ��� ���� true�� �����Ѵ�.
			break;
		}
		
		return result;		// ��� �� ��ȯ
	}

	// �̸��� ��Ȯ�� �ԷµƳ� Ȯ���ϴ� �޼ҵ�
	public boolean checkName(String name)
	{
		boolean result = false;		// �̸��� �� �Էµ��� ��� true�� ��ȯ�� boolean ����
		name = name.trim();		// �̸��� ��, �� ������ �����Ѵ�.
		
		if (name.length() >= 2 && name.length() <= 5)	// �̸��� 2�� �̻� 5�� ���Ϸ� �Է¹޴´�.
		{
			teacher.setName(name);		// ���ǿ� �°� �Էµ��� �� Teacher��ü�� �̸��� �ִ´�.
			result = true;					// ��� ���� true�� �ٲ۴�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// �н����尡 ��Ȯ�� �ԷµƳ� Ȯ���ϴ� �޼ҵ�
	public boolean checkPassword(String password)
	{
		boolean result = false;			// �н����尡 �� �Էµ��� ��� true�� ��ȯ�� boolean ����
		password = password.trim();	// �н������� ��, �� ������ �����Ѵ�.
		
		if (password.length() >= 8 && password.length() <= 20)	// ��й�ȣ�� 8�ڸ� �̻� 20�ڸ� ���Ϸ� �Է��Ѵ�.
		{
			teacher.setPassword(password);		// ���ǿ� �°� �Էµ��� �� Teacher ��ü�� ��й�ȣ�� �ִ´�.
			result = true;							// ��� ���� true�� �ٲ۴�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ���� Ȯ���ϴ� �޼ҵ�
	public boolean checkGender(int gender)
	{
		boolean result = false;		// ������ �� �Էµ��� �� true�� ��ȯ�� boolean ����
		
		if (gender == 1)			// ������ ���
		{
			teacher.setGender(1);	// Teacher ��ü�� ���� ���� �ִ´�.
			result = true;			// ��� ����  true�� �ٲ۴�.
		}
		else if (gender == 0)		// ������ ���
		{
			teacher.setGender(0);
			result = true;
		}
				
		return result;		// ��� �� ��ȯ
	}
	
	// �̸��� ���� Ȯ���ϴ� �޼ҵ�
	public boolean checkEmail(String email)
	{
		boolean result = false;		// e-mail�� �� �Էµ��� �� true�� ��ȯ�� boolean ���� 
		email = email.trim();			// e-mail�� ��, �� ������ �����Ѵ�.
		
		// Pattern Ŭ������ ���� ������ ����� ��Ȯ�� �̸������� Ȯ���Ѵ�.
		// ���� ����� Student�� �����ϹǷ� StudentDTO Ŭ���� ����
		String pattern = "^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$";
		Pattern emailPattern = Pattern.compile(pattern);
		
		if ((emailPattern.matcher(email)).matches())
		{
			result = true;
			teacher.setEmail(email);
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ��ȭ��ȣ Ȯ���ϴ� �޼ҵ�
	public boolean checkPhone(String phone)
	{
		boolean result = false;		// ��ȭ��ȣ�� �� �Էµ��� �� true�� ��ȯ�� boolean ����
		phone = phone.trim();		// ��ȭ��ȣ�� ��, �� ������ �����Ѵ�.
		
		// Pattern Ŭ������ ���� ������ ����� ��Ȯ�� ��ȭ��ȣ���� Ȯ���Ѵ�.
		// ���� ����� Student�� �����ϹǷ� StudentDTO Ŭ���� ����
		String pattern = "(0\\d{2})-(\\d{3,4})-(\\d{4})";
		Pattern phonePattern = Pattern.compile(pattern);
		
		if (phonePattern.matcher(phone).matches())
		{
			result = true;			
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ��ȭ��ȣ �ߺ� Ȯ�� �޼ҵ�
	public boolean overlapPhone(String phone)
	{
		ArrayList<String> list;		// ��ȭ��ȣ�� String���� ��ƿ� ArrayList
		boolean result = true;		// �ߺ��� �ƴ� ��� true�� ��ȯ�� boolean ����
		
		list = teacherDAO.selectPhone();		// teacherDAO�� ���� ����� ��ȭ��ȣ ����Ʈ�� �޾ƿ´�.
		
		for (int i = 0; i < list.size(); i++)
		{
			if (phone.equals(list.get(i)))		// �ߺ��Ǵ� ��ȭ��ȣ�� �ִ� ���
			{
				result = false;					// ��� ���� false�� �ٲ۴�.
				break;								// �ݺ��� ����
			}
		}
		
		if (result)		// �ߺ��Ǵ� ��ȭ��ȣ�� ���� ���
		{
			teacher.setPhone(phone);		// Teacher ��ü�� ��ȭ��ȣ�� �ִ´�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ������ �� ���� teacher ��ü�� ��ȯ�ϴ� �޼ҵ�
	public Teacher getTeacher()
	{
		return teacher;
	}
}
