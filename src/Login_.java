import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class LoginCenter extends JPanel{
	Login_ frame;
    JTextField id = new JTextField();
    JPasswordField pw = new JPasswordField();
    JLabel la = new JLabel("EPL VIP Member"); 
    public JPanel panel = new JPanel();
    
	public LoginCenter(Login_ frame) {
    	setLayout(null);
        setBackground(new Color(45,45,45)); //이 패널의 배경색
        
        //글자와 로고
        panel.setBackground(Color.BLACK);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBounds(0,10,500,50);
        add(panel);
        
        la.setForeground(Color.WHITE);
        //la.setBounds(120,0,280,100);
        la.setFont(new Font("Lucida Fax", Font.PLAIN, 30));
        panel.add(la);
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0XFF895A));
        panel1.setBounds(0,0,500,10);
        add(panel1);
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(0XFF895A));
        panel2.setBounds(0,303,500,10);
        add(panel2);
        
        //아이디
        id.setBounds(110,100,180,25);
        add(id);
        
        //비밀번호
        pw.setBounds(110,150,180,25);
        add(pw);
        
        //로그인
        JButton login_btn = new JButton("로그인");
        login_btn.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        login_btn.setForeground(Color.WHITE);
        login_btn.setBounds(320,100,70,75);
        login_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        add(login_btn);
        
        login_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String idt = id.getText();
                String pwt = pw.getText();
                DB_connect DB = new DB_connect();
                    if (idt.equals("") && pwt.equals("")) {
                        JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
                    }
                    else if (idt.equals("")) {
                        JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
                    }
                    else if (pwt.equals("")) {
                        JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
                    }
                    else {
                        int value = DB.login_tf(idt, pwt);
                        System.out.println(value);
                        if(value == 1){                       
                        	JOptionPane.showMessageDialog(null, "관리자 로그인 되었습니다.");                               
                        	new Administrator();            
                        	frame.dispose();
                        }
                        else if(value == 2){
                            JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
                            new Menu(idt);
                            frame.dispose();
                        }
                        else if(value == -1){
                        	JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 확인하세요.");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "시스템 오류");
                        }
                    }
                }
        });  
        
        //회원가입
        JButton signinPage = new JButton("회원가입");
        signinPage.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        signinPage.setForeground(Color.WHITE);
        signinPage.setBounds(190,205,100,40);
        signinPage.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        add(signinPage);
        
        signinPage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Join();
                frame.dispose();
            }
        });
    }
}

public class Login_ extends JFrame {
	LoginCenter lc = new LoginCenter(this);
	public Login_() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
   
        c.add(lc,BorderLayout.CENTER);
        
        setResizable(false); //창 크기 조절 못하게 하는
        setSize(500,350);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setVisible(true);
    }

}