import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


class Member{ //회원 가입 db연결 클래스
    String name;
    String id;
    String pw;
    String email;
    String phoneNumber;
    
    public Member(String id) {
        this.name="name";
        this.id=id;
        this.pw="pw";
        this.email="email";
        this.phoneNumber="phoneNumber";
    }
    public Member(String name, String id, String pw, String email, String phoneNumber){
        this.name=name;
    	this.id=id;
        this.pw=pw;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }
    
    public String get_name(){
        return name;
    }
    public String get_id(){ 
    	return id; 
    }
    public String get_pw(){
        return pw;
    }
    public String get_email(){
        return email;
    }
    public String get_phoneNumber(){
        return phoneNumber;
    }

}

public class Join extends JFrame {
    JTextField namet = new JTextField();
    JTextField idt = new JTextField();
    JPasswordField pwt = new JPasswordField();
    JPasswordField pwct = new JPasswordField();
    JTextField emailt = new JTextField();
    JTextField phonet = new JTextField();

    JFrame fr = new JFrame();
    
    String name = namet.getText();
    String id = idt.getText();
    String pw = pwt.getText();
    String pwc = pwct.getText();
    String email = emailt.getText();
    String phoneNumber = phonet.getText();
    Member new_member = new Member(name, id, pw, email, phoneNumber);
    DB_connect DB = new DB_connect();

    public Join() {
        fr.setTitle("Join");
        fr.setResizable(false); //창 크기 조절 못하게 하는
        fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        fr.getContentPane().setBackground(new Color(45,45,45));
        
        fr.setSize(400, 500);
        fr.setLocationRelativeTo(null);  //프레임을 화면 정중앙에 뜨도록 설정
        fr.setLayout(null);

        JLabel title = new JLabel("회원 가입");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(145, 5, 100, 30);
        fr.add(title);
        
        Font f = new Font("맑은 고딕", Font.BOLD, 15);
        JLabel name = new JLabel("이름");
        name.setForeground(Color.WHITE);;
        name.setFont(f);
        name.setBounds(50, 60, 100, 30); //null 이니 컴포넌트 위치, 크기 정해야 하는데 setBounds가 동시에 위치 크기 정하는 컴포넌트임
        fr.add(name);
        namet.setBounds(200, 60, 150, 30);
        fr.add(namet);

        JLabel id = new JLabel("아이디");
        id.setBounds(50, 105, 50, 30);
        id.setForeground(Color.WHITE);
        id.setFont(f);
        fr.add(id);
        idt.setBounds(200, 110, 150, 30);
        fr.add(idt);
        
        //아이디 중복확인
        JButton idCheck = new JButton("중복확인");
        idCheck.setBounds(105, 110, 80, 28);
        idCheck.setBackground(new Color(0XFF895A));
        idCheck.setForeground(Color.WHITE);
        idCheck.setFont(new Font("맑은 고딕", Font.BOLD, 11));
        fr.add(idCheck);
        idCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idt.getText();
                Member idckmember = new Member(id);
                int check = DB.idCheck(idckmember);
                //id = idt.getText();
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
                } else if (check == 0) {
                    JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
                } else if (check == 1){
                    JOptionPane.showMessageDialog(null, "사용 중인 아이디입니다.");
                }
                else {
                	JOptionPane.showMessageDialog(null, "오류");
                }
            }
        });

        JLabel pw = new JLabel("비밀번호");
        pw.setBounds(50, 160, 100, 30);
        pw.setForeground(Color.WHITE);
        pw.setFont(f);
        fr.add(pw);
        pwt.setBounds(200, 160, 150, 30);
        fr.add(pwt);

        JLabel pwc = new JLabel("비밀번호 확인");
        pwc.setBounds(50, 210, 100, 30);
        pwc.setForeground(Color.WHITE);
        pwc.setFont(f);
        fr.add(pwc);
        pwct.setBounds(200, 210, 150, 30);
        fr.add(pwct);

        JLabel email = new JLabel("이메일");
        email.setBounds(50, 260, 100, 30);
        email.setForeground(Color.WHITE);
        email.setFont(f);
        fr.add(email);
        emailt.setBounds(200, 260, 150, 30);
        fr.add(emailt);

        JLabel phone = new JLabel("전화번호");
        phone.setBounds(50, 310, 100, 30);
        phone.setForeground(Color.WHITE);
        phone.setFont(f);
        fr.add(phone);
        phonet.setBounds(200, 310, 150, 30);
        fr.add(phonet);

        //등록
        JButton btn1 = new JButton("등록");
        btn1.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btn1.setForeground(Color.WHITE);
        btn1.setFont(f);
        btn1.setBounds(75, 380, 80, 30);
        fr.add(btn1);

        //닫기
        JButton btn2 = new JButton("닫기");
        btn2.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btn2.setForeground(Color.WHITE);
        btn2.setFont(f);
        btn2.setBounds(225, 380, 80, 30);
        fr.add(btn2);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                
                String name = namet.getText();
                String id = idt.getText();
                String pw = pwt.getText();
                String pwc = pwct.getText();
                String email = emailt.getText();
                String phoneNumber = phonet.getText();

                Member idckmember = new Member(id);
                int check = DB.idCheck(idckmember);
                
                Member new_member = new Member(name, id, pw, email, phoneNumber);
                DB_connect DB = new DB_connect();
                //등록 이벤트 처리
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "이름을 입력하세요.");
                } else if (id.equals("")) { 	
                    JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
                } else if (pw.equals("")) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
                } else if (pwc.equals("")) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
                } else if (email.equals("")) {
                    JOptionPane.showMessageDialog(null, "이메일을 입력하세요.");
                } else if (phoneNumber.equals("")) {
                    JOptionPane.showMessageDialog(null, "전화번호를 입력하세요.");
                } else if (!(pw.equals(pwc))) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
                } else if (check == 1){
                    JOptionPane.showMessageDialog(null, "사용 중인 아이디입니다. 아이디 중복 확인을 해주세요.");
                } else {
                	if(JOptionPane.showConfirmDialog(null,"월정액을 내시는 것에 동의하십니까?")==0) {
                    DB.customer_db(new_member);
                    JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
                    new Login_();
                    fr.dispose();
                	} else {}
                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login_();  // 회원가입 창 닫고 로그인 창 띄우기
                fr.dispose();
            }
        });

        fr.setVisible(true);

    }
}