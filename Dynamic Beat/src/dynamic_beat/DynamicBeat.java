package dynamic_beat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame{
	
	private Image screenImage;
	private Graphics screenGraphic;
	
	private ImageIcon exitButtonEnteredImage= new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage= new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private Image introBackground = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	
	private int mouseX, mouseY;
	
	public DynamicBeat() {
		super("Dynamic Beat");
		setUndecorated(true);	//실행했을 때 기본적으로 존재하는 메뉴바가 보이지 않는다.
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0)); //페인트 컴포넌트를 했을 때 배경이 회색이 아닌 전부 하얀색으로 바뀌게 된다.
		setLayout(null);	//버튼이나 라벨같은 것을  넣었을 때 그 위치 그대로 꽂히게 된다?
		
		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);	//마우스가 올라갔을 때 이미지 바뀌도록
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));	//마우스가 손가락 모양으로 바뀌도록
				Music ButtonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);	//false는 반복되지 않도록, 한 번만 실행 되도록.
				ButtonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);	//마우스가 벗어났을 때 이미지
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));	//원래 상태로
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music ButtonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				ButtonPressedMusic.start();
				try {	//음악이 나오자마자 바로 종료가 되기 때문에 1 초 뒤에 종료될 수 있도록 Thread sleep을 걸어준다.
					Thread.sleep(1500);
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);	//시스템이 종료되도록 함
			}
		});
		add(exitButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override//드래그 이벤트가 발생했을때의 내용
			public void mouseDragged(MouseEvent e) {
				int x= e.getXOnScreen();
				int y= e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);	//메뉴바를 마우스로 잡고 그 위치로 이동할 수 있게 함.
			}
		});
		add(menuBar);
		
		
		
		Music introMusic = new Music("introMusic.mp3", true);
		introMusic.start();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(introBackground, 0, 0, null);	//배경 이미지를 그릴 때 사용
		paintComponents(g);	//메뉴바를 그려주기 위해 사용하는 도구.(동적이지 않고 항상 있어야 하는 메뉴바 같은 경우를 그릴때 사용)
		this.repaint();
	}
}
