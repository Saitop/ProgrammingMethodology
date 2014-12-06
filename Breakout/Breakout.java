/*
* File: Breakout.java
* -------------------
* This file will eventually implement the game of Breakout.
* �˳���ʵ�ֲ�ǽ��Ϸ
*/

import acm.graphics.*;
import acm.gui.IntField;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Breakout extends GraphicsProgram {
	
	/** ���峤�� */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	
	/** С���򳤿� */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** ��Ϸ���泤��*/
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** ������ײ����� */
	private static final int PADDLE_Y_OFFSET = 30;
	
	/** ÿ��ש����������һ�أ�  */
	private static final int NBRICKS_PER_ROW = 10;

	/** ש����������һ�أ�  */
	private static final int NBRICK_ROWS = 10;

	/** ש��������һ�أ� */
	private static final int BRICK_SEP = 4;
	
	/** ש����� */
	private static final int BRICK_WIDTH =
			(WIDTH - (NBRICKS_PER_ROW + 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	
	/** ש��߶� */
	private static final int BRICK_HEIGHT = 8;

	/** С��ֱ�� */
	private static final int BALL_DIA = 10;

	/** ש���붥������*/
	private static final int BRICK_Y_OFFSET = 70;
	
	/** �ڼ�����Ϸ */
	private static final int NTURNS = 3;
	
	/** ÿһ����Ϸ��ʼǰ��ʱ */
	private static final int DELAY = 10;
	

	public void run() {
		instructions();
		currentTimes=NTURNS;
		
		createController();  //������������������ť
		setup();                         //������Ϸ����
		
		while(!gameOver()){
			moveBall();						 //�ƶ�С��
			checkForCollisions();            //�����ײ
			pause(DELAY);                    //ÿһ��ѭ��֮����ӳ�
		}
	}
	
	private void instructions(){
		box = new GCompound();
		GRect outline = new GRect(240, 240);
		GLabel label = new GLabel("�����Ļ������start����ť��ʼ��Ϸ");
		label.setFont("Courier 32");
		box.add(outline, -240 / 2, -240 / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, getWidth() / 2, getHeight() / 2);
		box.pause(3000);
		remove(box);
		
	}
	private void setup(){
		
		GImage image = new GImage("sheep.jpg");
		//image.scale(0.5);
		image.sendBackward();
		add(image);
		
	    gameboard = new GRect(WIDTH ,HEIGHT);
		add(gameboard);

		//���ݲ�ͬ�ؿ��趨ש��
		if(levels==1){
			setupBricks1();
		}else if(levels==2){
			setupBricks2();
		}else if(levels==3){
			setupBricks3();
		}
		setupPaddle();		//�趨����
		
		addMouseListeners();
		addActionListeners();
		pause(1000);
	}
	
	
	//	������������������ť
	private void createController(){
		add(new JLabel("Lives:"), SOUTH);
		livesField=new IntField(currentTimes);
		
	
		add(livesField,SOUTH);
		livesField.addActionListener(this);
		
		startButton = new JButton("Start");
		add(startButton,SOUTH);
		
		restartButton = new JButton("Restart");
		add(restartButton,SOUTH);
	}
	
	//�Բ������Ͱ�ť�ķ�Ӧ
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		if (source == startButton&&ball == null){
			remove(box);
			pause(1000);
			int ct = currentTimes-1;
			livesField.setValue(ct);
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;
			vy = 3.0;
			ball = new GOval(WIDTH/2.5,HEIGHT/2,BALL_DIA,BALL_DIA);
			ball.setFilled(true);
			add(ball);
		}else if(source ==restartButton && ball==null){

			currentTimes=NTURNS;
			int ct=currentTimes;
			livesField.setValue(ct);
			removeAll();
			setup();
	
		}
	}
	

	private void setupBricks1(){
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=0;j<NBRICKS_PER_ROW;j++){
				int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
				int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if(i<2){
					brick.setColor(Color.red);
				}else if(i<4){
					brick.setColor(Color.orange);
				}else if (i<6){
					brick.setColor(Color.yellow);
				}else if (i<8){
					brick.setColor(Color.green);
				}else if (i<10){
					brick.setColor(Color.cyan);
				}
				add(brick,x, y);
				number_of_bricks++;
			}
		}
	}
	
	private void setupBricks2(){
		setupBricks2A();
		setupBricks2B();
		setupBricks2C();
	}
	
	private void setupBricks2A(){
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=3;j>i;j--){
				int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
				int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if(i<3){
					brick.setColor(Color.red);
                                        add(brick,x, y);
				        number_of_bricks++; 

				}				
			}
	 }
	}
	 private void setupBricks2B(){
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=6;j>i;j--){
				int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
				int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
	                  if(i<6){				
					  brick.setColor(Color.orange);
	                  }
	                  add(brick,x, y);
	  				  number_of_bricks++;
	  			}
		}
	}
	private void setupBricks2C(){
		for(int i=0;i<NBRICK_ROWS;i++){
			for(int j=9;j>i;j--){
				int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
				int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if (i<10){	
					brick.setColor(Color.yellow);
				}
				add(brick,x, y);
				number_of_bricks++;
			}
		}
	}
	private void setupBricks3(){
		setupBricks3A();
		setupBricks3B();
		setupBricks3C();
		setupBricks3D();
	}



	private void setupBricks3A(){
			 int i=0;
			while(i<NBRICK_ROWS){
			i=i+2;{
				for(int j=9;j>i;j--){
					int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
					int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
					brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
					brick.setFilled(true);
					if(i<2){
						brick.setColor(Color.red);
	                                        add(brick,x, y);
					        number_of_bricks++; 

						}				
					}
				}
			}
		}
		 private void setupBricks3B(){
			 int i=0;
				while(i<NBRICK_ROWS){
				i=i+2;{
				for(int j=9;j>i;j--){
					int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
					int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
					brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
					brick.setFilled(true);
		                  if(i<6&i>=2){				
						  brick.setColor(Color.yellow);
		                  }
		                  add(brick,x, y);
		  				  number_of_bricks++;
		  				}
					}
				}
		 }

		 private void setupBricks3C(){
			 int i=0;
				while(i<NBRICK_ROWS){
				i=i+2;{
				for(int j=9;j>i;j--){
					int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
					int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
					brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
					brick.setFilled(true);
					if (i>=6&i<10){	
						brick.setColor(Color.cyan);
					}
					add(brick,x, y);
					number_of_bricks++;
					}
				}
				}
		 }
		
		private void setupBricks3D(){
				int i=0;
				while(i<NBRICK_ROWS){
				i=i+2;{
					for(int j=0;j<i;j++){
						int x = BRICK_WIDTH*j+BRICK_SEP*(j+1);
						int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT+BRICK_SEP);
						brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
						brick.setFilled(true);
						if(i<10){
							brick.setColor(Color.red);
		                    add(brick,x, y);
						    number_of_bricks++; 
						}				
					}
			 }
			}
		 }
	
	private void setupPaddle(){
		paddle = new GRect((WIDTH- PADDLE_WIDTH)/2,HEIGHT-(PADDLE_Y_OFFSET+PADDLE_HEIGHT+10),
				PADDLE_WIDTH,PADDLE_HEIGHT );
		paddle.setFilled(true);
		add(paddle);
	}
	
	// ��¼��갴��ʱ��λ��
	public void mousePressed(MouseEvent e) {
		// GPoint has X and Y coordinate
		last = new GPoint(e.getPoint());
		gobj = getElementAt(last);
	}
	
	// ��Ӧ����϶����� 
	public void mouseDragged(MouseEvent e) {
		if (gobj == paddle) {
			gobj.move(e.getX() - last.getX(), e.getY() - e.getY());
			last = new GPoint(e.getPoint());
			if (e.getX()<0 ){
				paddle.setLocation(0,paddle.getY());
			}if(e.getX()+PADDLE_WIDTH>WIDTH){
				paddle.setLocation(WIDTH-PADDLE_WIDTH,paddle.getY());
			}

		}
	}
	
	//��������������С��ʼ��Ϸ
	public void mouseClicked(MouseEvent e) {
		remove(box);
		pause(900);
		if (ball== null) {
			
			int ct = currentTimes-1;
			livesField.setValue(ct);
			
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;
			vy = 3.0;
			ball = new GOval(WIDTH/2.5,HEIGHT/2,BALL_DIA,BALL_DIA);
			ball.setFilled(true);
			add(ball);

		}
	}
	
	//�ƶ�С��
	private void moveBall(){
		if (ball != null) {
			ball.move(vx,vy);
		}
	}
	
	//�����ײ
	private void checkForCollisions(){
		checkWallCollisions();           //���С���Ƿ���ǽ����ײ
		
		GObject collider = getCollidingObject();  //�����С��Ӵ�������
		if(collider!=gameboard){
			if(collider==paddle){
				bounceOffPaddle();
				

			}else if(collider != null){
				remove(collider);
				//
				vy=-vy;
				number_of_bricks--;
			
			}
		}
	}
	
//	���С���Ƿ���ǽ����ײ
	private void checkWallCollisions(){	
		if (ball!=null&&((ball.getY()<0))) {
			vy=-vy;		
		}if (ball!=null&&(ball.getY() > HEIGHT )){
			remove(ball);
			ball=null;
			currentTimes--;
		}if(ball!=null&&((ball.getX()<0)||((ball.getX()+BALL_DIA)>WIDTH))){
			vx=-vx;
		}
	}
	

	//��鲢�����С��Ӵ�������
	private GObject getCollidingObject(){
		GObject obj =null;
	    if(ball!=null){
			double x = ball.getX();
			double y = ball.getY();
				if((obj=getElementAt(x, y))!=null){
					obj=getElementAt(x, y);
				}else if((obj=getElementAt(x+BALL_DIA, y))!=null){
					obj=getElementAt(x+BALL_DIA, y);
				}else if((obj=getElementAt(x, y+BALL_DIA))!=null){
					obj=getElementAt(x, y+BALL_DIA);
				}else {
					obj=getElementAt(x+BALL_DIA, y+BALL_DIA);
				}
	    }
	    return obj;
		
	}

	//��������С����
    private void bounceOffPaddle()
    {
        double dy = (ball.getY() + BALL_DIA) - paddle.getY();
        ball.move(0,-dy);
        
        vy = -vy;
    }
    
    
    //�ж���Ϸ�Ƿ����
	private boolean gameOver(){
		if(number_of_bricks<=0){levels--;}
		if(number_of_bricks<=0&&levels==3){
			box = new GCompound();
			GRect outline = new GRect(120, 50);
			GLabel label = new GLabel("You win!");
			box.add(outline, -120 / 2, -50 / 2);
			box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
			add(box, getWidth() / 2, getHeight() / 2);
		}
		if(currentTimes<=0){
			box = new GCompound();
			GRect outline = new GRect(120, 50);
			GLabel label = new GLabel("You lose!");
			label.setFont("Courier 24");
			box.add(outline, -120 / 2, -50 / 2);
			box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
			add(box, getWidth() / 2, getHeight() / 2);
		}
		return ((number_of_bricks<=0&&levels==3)||currentTimes<=0);
	}
	
	

	
	private GObject gobj;          /* ������������ */
	private GPoint last;           /* ����������λ�� */
	private GRect paddle;          /*�ײ��ƶ�����*/
	private GOval ball;            /*��ǽ��С��*/
	private GRect brick;           /*ש�飨���ǽ�壩*/
	private GRect gameboard;       /*��Ϸ����*/
	
	private double vx,vy;          /*С���ٶ�*/
	private RandomGenerator rgen = RandomGenerator.getInstance();   //�����������
	private int currentTimes;                                       //����������ÿ��������Ϸ���ᣩ
	private int number_of_bricks;

	private JButton startButton;
	private IntField livesField;
	private JButton restartButton;
	private GCompound box;
	
	private int levels=1;            /*��Ϸ�ؿ�*/
	
}