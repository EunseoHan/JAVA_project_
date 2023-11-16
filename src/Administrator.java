import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

class AdministratorCenter extends JPanel{
	Administrator frame;//없어도 될듯
	JButton b1= new JButton("회원정보");
	JButton b2= new JButton("경기일정");
	JButton b3= new JButton("나가기");
	
	public AdministratorCenter(Administrator frame) {
		setLayout(null);
        setBackground(new Color(45,45,45)); //이 패널의 배경색
        
        //맨위 패널
        JPanel pa= new JPanel();
        pa.setBackground(Color.BLACK);
        pa.setLayout(null);
        pa.setBounds(0,0,800,50);
        add(pa);
        JLabel la1 = new JLabel("ADMINISTRATOR");
        la1.setBounds(320,0,200,50);
        la1.setForeground(Color.WHITE);
        la1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        pa.add(la1);
        
        JPanel pa1= new JPanel();
        pa1.setBackground(new Color(0XFF895A));
        pa1.setBounds(0,50,800,10);
        add(pa1);
        
        //회원정보 버튼
        b1.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        b1.setForeground(Color.WHITE);
        b1.setBounds(150,80,150,80);
    	b1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	b1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Administrator_customer();
                frame.dispose();
            }
        });
    	add(b1);
    	
    	//경기일정 버튼
    	b2.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        b2.setForeground(Color.WHITE);
    	b2.setBounds(500,80,150,80);
    	b2.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        
        b2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Administrator_game();//경기일정 화면 new 해야함
                frame.dispose();
            }
        });
        add(b2);
    	
        //나가기 버튼
        b3.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        b3.setForeground(Color.WHITE);
    	b3.setBounds(680,12,88,30);
    	b3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        
        b3.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {
                     new Login_();
                     frame.dispose();
             }
         });
        pa.add(b3);
        
        //맨 아래 패널
        JPanel pa2= new JPanel();
        pa2.setBackground(Color.BLACK);
        pa2.setBounds(0,570,800,50);
        add(pa2);
    	JLabel la= new JLabel("오늘도 화이팅입니다!");
    	la.setForeground(Color.WHITE);
    	la.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	pa2.add(la);
    	
	}
}

public class Administrator extends JFrame{
	AdministratorCenter ac= new AdministratorCenter(this);
	MyPanel panel = new MyPanel();	
	
	public Administrator() {
		setTitle("Administrator");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Container c = getContentPane();
	    c.setLayout(new BorderLayout());
		
		c.add(ac,BorderLayout.CENTER);
		
		setResizable(false);
	    setSize(800,650);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    panel.setBounds(0,60,800,530);
	    panel.setBackground(new Color(0,0,133));
		ac.add(panel);
		}
	
	class MyPanel extends JPanel{		
		ImageIcon icon = new ImageIcon("images/son.jpg");
		Image img = icon.getImage();
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //JPanel의 paintComponent() 호출 - 이전에 그려진 잔상을 지우기 위해
			g.drawImage(img,0,0,800,530,this);
		}
	}
}
	
