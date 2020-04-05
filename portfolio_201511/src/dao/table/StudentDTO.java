package dao.table;

import java.util.*;
import java.util.regex.*;

public class StudentDTO
{	
	// ���� �˻��ϰ� �ѱ�� ���� �ʿ��� ����
	private Student student;
	private StudentDAO studentDAO;
	
	// StudentDAO�� �ް�, Student ��ü�� �����ϴ� ������
	public StudentDTO(StudentDAO studentDAO)
	{
		this.studentDAO = studentDAO;
		student = new Student();
	}
	
	// StudentDAO�� Student ��ü�� ��� �޴� ������
	public StudentDTO(StudentDAO studentDAO, Student student)
	{
		this.student = student;
		this.studentDAO = studentDAO;
	}

	// �̸��� ��Ȯ�� �ԷµƳ� Ȯ���ϴ� �޼ҵ�
	public boolean checkName(String name)
	{
		boolean result = false;		// �̸��� �� �Էµ��� �� true�� ��ȯ�ϴ� boolean ����
		name = name.trim();		// ���� �̸��� ��, �� ������ �����Ѵ�.
		
		if (name.length() >= 2 && name.length() <= 5)	// �̸��� 2�� �̻� 5�� ���Ϸ� �Է¹ް� �Ѵ�.
		{
			student.setName(name);		// �ش� ���ǿ� ���� ��, ��ü�� �̸��� �ְ�
			result = true;					// boolean ������ true�� �ٲ۴�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// �н����尡 ��Ȯ�� �ԷµƳ� Ȯ���ϴ� �޼ҵ�
	public boolean checkPassword(String password)
	{
		boolean result = false;			// �н����尡 �� �Էµ��� �� true�� ��ȯ�ϴ� boolean ����
		password = password.trim();	// �н������� ��, �� ������ �����Ѵ�.
		
		if (password.length() >= 8 && password.length() <= 20)	// ��й�ȣ�� 8�ڸ� �̻� 20�ڸ� ����
		{
			student.setPassword(password);		// �ش� ���ǿ� ���� ��, Student ��ü�� ��й�ȣ�� �ְ�
			result = true;							// boolean ������ true�� �ٲ۴�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ���� Ȯ���ϴ� �޼ҵ�
	public boolean checkGender(int gender)
	{
		boolean result = false;		// ������ �� �Էµ��� �� true�� ��ȯ�� ����
		
		if (gender == 1)		// ������ ���
		{
			student.setGender(1);	// Student ��ü�� ���� ���� �ְ�
			result = true;			// boolean ������ true�� �ٲ۴�.
		}
		else if (gender == 0)	// ������ ���
		{
			student.setGender(0);
			result = true;
		}
				
		return result;			// ��� �� ��ȯ
	}
	
	// �̸��� ���� Ȯ���ϴ� �޼ҵ�
	// ���Ͻ� ���
	public boolean checkEmail(String email)
	{
		boolean result = false;		// e-mail�� �� �Էµ��� �� true�� ��ȯ�� boolean ����
		email = email.trim();			// e-mail�� ��, �� ������ �����Ѵ�.
		
		// Pattern Ŭ������ ���� ������ ����� ��Ȯ�� �̸������� Ȯ���Ѵ�.
		// ^ : ������ ����
		// [0-9a-zA-z_-] : ����, �ҹ���, �빮��, -, _ �� ����
		// + : �տ� ����� ������ ���ڰ� 1�� �̻� �ݺ�
		// @ : �̸����� @
		// \\. :  ������ ������ ��
		// {1, 2} : �տ� ����� ������ 1�� Ȥ�� 2�� ���� (ex) .com Ȥ�� .co.kr)
		// $ : ������ ��
		String pattern = "^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$";
		Pattern emailPattern = Pattern.compile(pattern);		// ���Ͻ��� �Է��� String�� Pattern �ν��Ͻ��� ����
		
		if ((emailPattern.matcher(email)).matches())		// email ������ �Է��� ���Ͽ� �´� �� �˻��� �´ٸ� (= true)
		{
			student.setEmail(email);		// ���� Student ��ü�� �ְ�,
			result = true;				// ��� ���� true�� �ٲ�.
		}
		
		return result;			// ��� �� ��ȯ
	}
	
	// ��ȭ��ȣ Ȯ���ϴ� �޼ҵ�
	// ���Ͻ� ���
	public boolean checkPhone(String phone)
	{
		boolean result = false;		// ��ȭ��ȣ�� �� �Էµ��� �� true�� ��ȯ�� boolean ����
		phone = phone.trim();		// ��ȭ��ȣ�� ��, �� ������ �����Ѵ�.
		
		// Pattern Ŭ������ ���� ������ ����� ��Ȯ�� ��ȭ��ȣ���� Ȯ���Ѵ�.
		// 0\\d{2} : 0���� �����ϴ� �� �ڸ��� ���� (000 ~ 099)
		// \\d{3,4} : ���� 3�ڸ� Ȥ�� 4�ڸ�
		// \\d{4} : ���� 4�ڸ�
		String pattern = "(0\\d{2})-(\\d{3,4})-(\\d{4})";
		Pattern phonePattern = Pattern.compile(pattern);		// ���Ͻ��� �Է��� String�� Pattern �ν��Ͻ��� ����
		
		if (phonePattern.matcher(phone).matches())		// phone ������ �Է��� ���Ͽ� �´� �� �˻��� �´ٸ� (=true)
		{
			result = true;		// true�� ��ȯ
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ��ȭ��ȣ �ߺ� Ȯ���ϴ� �޼ҵ�
	public boolean overlapPhone(String phone)
	{
		ArrayList<String> list;			// ���� ����Ǿ� �ִ� ��ȭ��ȣ���� ���� ArrayList
		boolean result = true;			// ��ȭ��ȣ�� �ߺ����� �ʾ� ����� ��� true�� ��ȯ�� boolean ����
		
		list = studentDAO.selectPhone();	// StudentDAO���� ���� ����Ǿ� �ִ� ��ȭ��ȣ�� ��ƿ�
		
		for (int i = 0; i < list.size(); i++)
		{
			if (phone.equals(list.get(i)))		// �ߺ��Ǵ� ��ȭ��ȣ�� �ִٸ�
			{
				result = false;				// false�� ������ ��
				break;							// �ݺ��� ����
			}
		}
		
		if (result)		// �ߺ��Ǵ� ��ȭ��ȣ�� ���� ���
		{
			student.setPhone(phone);		// Student ��ü�� ��ȭ��ȣ�� �ִ´�.
		}
		
		return result;		// ��� �� ��ȯ
	}
	
	// ������ ��� �Էµ� �л� ��ü�� �����ִ� �޼ҵ�
	public Student getStudent()
	{
		return student;
	}
	
}
