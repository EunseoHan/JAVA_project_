import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

class MenuCenter extends JPanel{
	Menu frame; //없어도 될듯
	JButton b1= new JButton("예매하기");
	JButton b2= new JButton("예매내역확인");
	JButton b3= new JButton("로그아웃");
	
	public MenuCenter(Menu frame,String id) {
		setLayout(null);
		setBackground(new Color(0,0,133));
		
        //맨위 패널
        JPanel pa= new JPanel();
        pa.setBackground(Color.BLACK);
        pa.setLayout(null);
        pa.setBounds(0,0,800,50);
        add(pa);
        JLabel la1 = new JLabel("MENU");
        la1.setBounds(365,0,100,50);
        la1.setForeground(Color.WHITE);
        la1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        pa.add(la1);
        
        JPanel pa1= new JPanel();
        pa1.setBackground(new Color(0XFF895A));
        pa1.setBounds(0,50,800,10);
        add(pa1);
        
        //예매하기 버튼
        b1.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        b1.setForeground(Color.WHITE);
        b1.setBounds(210,80,150,80);
    	b1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	b1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Reservation(id);
                frame.dispose();
            }
        });
    	add(b1);
    	
    	//예매내역확인 버튼
    	b2.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        b2.setForeground(Color.WHITE);
    	b2.setBounds(440,80,150,80);
    	b2.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        
        b2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new ReservationCheck(id);
                frame.dispose();
            }
        });
        add(b2);
        
        //로그아웃 버튼
        b3.setBackground(new Color(0,0,133)); //주황색 new Color(0XFF895A)
        b3.setForeground(Color.WHITE);
    	b3.setBounds(680,12,88,30);
    	b3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        
        b3.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {
                 int answer = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","로그아웃",JOptionPane.YES_NO_OPTION);
                 if (answer == JOptionPane.YES_OPTION) {
                     new Login_();
                     frame.dispose(); 
                 }
             }
         });
        pa.add(b3);
    	
        //맨 아래 패널
        JPanel pa2= new JPanel();
        pa2.setBackground(Color.BLACK);
        pa2.setBounds(0,570,800,50);
        add(pa2);
    	JLabel la= new JLabel("Thank you for using our service.");
    	la.setForeground(Color.WHITE);
    	la.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	pa2.add(la);
    	
	}
}

public class Menu extends JFrame{
	MyPanel panel = new MyPanel();	
	
	public Menu(String id) {
	MenuCenter mc= new MenuCenter(this,id);
	setTitle("Menu");
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	
	Container c = getContentPane();
    c.setLayout(new BorderLayout());
	
	c.add(mc,BorderLayout.CENTER);
	
	setResizable(false); //창 크기 조절 못하게 하는
	setSize(800,650);
    setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
    setVisible(true);
    
    panel.setBounds(0,162,800,410);
    panel.setBackground(new Color(0,0,133));
	mc.add(panel);
	}
	
	class MyPanel extends JPanel{		
		ImageIcon icon = new ImageIcon("images/전체_.jpeg");
		Image img = icon.getImage();
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //JPanel의 paintComponent() 호출 - 이전에 그려진 잔상을 지우기 위해
			g.drawImage(img,0,0,800,410,this);
		}
	}
}