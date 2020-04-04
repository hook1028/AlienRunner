package com.lastviolet.alienrunner;

import android.graphics.Bitmap;
import android.util.Log;

public class Player
{
	// ��Ȳ�� �´� �÷��̾� �̹����� ������ ���
	static final int HIT = 0;
	static final int JUMP_01 = 1;
	static final int JUMP_02 = 2;
	static final int WALK_01 = 3;
	static final int WALK_02 = 4;
	static final int[] HP = { 2, 3, 3, 3, 4 };
	
	int width, height;			// �÷��̾� ĳ������ �ʺ�, ����
	int x, y, dx, dy, y0, x0;		// �÷��̾� ĳ������ �߽���, �̵��ӵ�, ���� y��ġ
	int w, h, w0, h0;	
	int loop = 0;				// ������ �ӵ��� ����ϴ� ����
	int jumpCount = 0;			// ���� ī��Ʈ�� ���� ����
	int doubleJumpCount = 0;	// ���� ���� ī��Ʈ�� ���� ����
	int collisionCount = 8;		// �浹 �� ���� ���¸� ī��Ʈ �ϴ� ����
	int kind;					// ĳ������ ����
	int move = 0;				// ĳ������ �ȴ� ����� �����ϴ� ����
	int size = 0;				// ĳ���Ͱ� Ŀ�� �� ����� �����ϴ� ����
	
	// ���� �� dy�� ����ϴµ� ���Ǵ� ����
	float[] jump = { 1.0f, 0.6f, 0.3f, 0.1f, 0, 0, -0.1f, -0.3f, -0.6f, -1.0f };	// 10
	float[] doubleJump = { 1.0f, 0.6f, 0.3f, 0.1f, 0, 0, -0.2f, -0.6f, -1.2f, -2.0f };	// 10
	
	// ���¸� Ȯ���ϴ� ����
	boolean isBig = false;
	boolean isJump = false;
	boolean isDoubleJump = false;
	boolean isDrop = false;
	boolean isCollision = false;
	boolean isInvincible = false;	// ���� ���¸� Ȯ���ϴ� ����
	
	// �̹���
	Bitmap[][] imgs;
	Bitmap[][] imgsBig;
	Bitmap img;
	
	// ������ (ĳ������ �� �̹���, ĳ������ �ʺ�, ĳ������ ����, ��ü ����, ���� ����, ĳ���� ����) 
	public Player(Bitmap[][]imgs, Bitmap[][]imgsBig, int pWidth, int pHeight, int fHeight, int rHeight, int kind) 
	{
		this.imgs = imgs;
		this.imgsBig = imgsBig;
		this.kind = kind;
		img = imgs[kind][WALK_01];	// �ȴ� ù ������� �ʱ�ȭ
		
		width = pWidth;
		height = pHeight;
		
		w = width / 2;
		h = height / 2;
		
		x = rHeight + w;			// ���� ���� + w�� �߽��� ����
		y = fHeight - rHeight - h;	// ��ü ���� - ���� ���� - h�� �߽��� ����
		y0 = y;
		x0 = x;
		w0 = w;
		h0 = h;
	}
	
	public void move(boolean isFast)
	{
		loop++;
		if (loop % (isFast ? 2 : 3) == 0)
		{	
			if (isBig)
			{
				if (size < imgsBig[kind].length - 2)
				{
					img = imgsBig[kind][size];
					size++;
					y = y0 + h - img.getHeight();
					x = x - w + img.getWidth() / 2;
					h = img.getHeight() / 2;
					w = img.getWidth() / 2;
				}
				else
				{
					size = (size == 3) ? 4 : 3;
					img = imgsBig[kind][size];
				}
			}
					
			if (!isJump && !isDrop && !isCollision && !isBig)	// �Ȱ� ���� ��
			{
				move = 1 - move;
				img = imgs[kind][WALK_01 + move];	
			}
			else if (isDrop)		// ������ ��
			{
				drop();
				return;
			}
		}
		
		if (isJump && !isBig)
			jump();
		if (isDoubleJump && !isBig)
			doubleJump();
		if (isCollision)
			collision();	
	} // end of jump
	
	private void jump()
	{
		if(isDoubleJump)			// �������� ���� �� ���� �޼ҵ�� ȣ����� �ʵ��� �Ѵ�.
		{
			isJump = false;
			return;
		}
		
		img = imgs[kind][JUMP_01];	// �ٴ� �̹��� ����

		dy = (int)(h * jump[jumpCount]);
		y -= dy;
		jumpCount++;
			
		if (jumpCount == 10)		// ���� ������ ��� ������ �� ���·� ������.
		{	
			if (y >= y0)			// ���� �� dy�� ���� ������ ĳ���Ͱ� �Ʒ��� ������ ��
				y = y0;				// �� ���� �������´�.
			
			jumpCount = 0;			// ���� ī��Ʈ �ʱ�ȭ
			isJump = false;			// ���� ����
			if (jumpCount == 10)	// �ȴ� �̹����� ����
				img = imgs[kind][WALK_01];	
		}					
	}
	
	private void doubleJump()
	{
		img = imgs[kind][(doubleJumpCount > 7) ? JUMP_01 : JUMP_02];	// �̹��� �� ���� ǥ��
		
		if(jumpCount < 4)	// �������� �ٴ� ������ ������ �ʾ��� �� ���� �ٰ� 2�� ���� ����
		{
			y -= (int)(h * jump[jumpCount]);
			jumpCount++;
		}
		
		dy = (int)(h * doubleJump[doubleJumpCount]);
		y -= dy;
		doubleJumpCount++;
				
		if (doubleJumpCount == 10)		// ���� ���� ������ ��� ������ �� ���·� ������.
		{
			if (y >= y0)				// ���� ���� �� float���� ���� ������ ����
				y = y0;
			
			jumpCount = 0;				// ���� ī���� �� ���� ���� ī���� �ʱ�ȭ
			doubleJumpCount = 0;
			isDoubleJump = false;		// ���� ���� �Ϸ�
			img = imgs[kind][WALK_01];	// �ȴ� �̹����� ����
		}
	} // end of doubleJump
	
	private void collision()
	{
		img = imgs[kind][HIT];
		isInvincible = true;
		collisionCount--;
	
		// �浹�� �÷��̾��� ü���� -1 �Ѵ�.
		
		// �浹 �� 8Ÿ�ӵ����� �������·� ������ش�. (�̹��� ����)
		if (collisionCount == 0)
		{
			img = imgs[kind][WALK_01];
			isCollision = false;
			isInvincible = false;
			collisionCount = 8;
		}
	}
	
	public void returnSize()
	{
		img = imgs[kind][WALK_01];
		y = y0;
		x = x0;
		h = img.getHeight() / 2;
		w = img.getWidth() / 2;
		size = 0;
	}
	public void drop()
	{
		img = imgs[kind][HIT];		// �������� �̹����� �ٲ�
		
		dx = (w / 3) * 2;			// ��� ���� �̵��ϱ� ���� dx ����
		dy = h;
		
		x -= dx;
		y += dy;
	}
}
