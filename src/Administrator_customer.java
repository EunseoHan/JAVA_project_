import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;

class CustomerInfo{
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> email = new ArrayList<String>();
    ArrayList<String> number = new ArrayList<String>();
   
    public void insertCustomer(String name, String id, String email, String number) {
    	 this.name.add(name);
         this.id.add(id);
         this.email.add(email);
         this.number.add(number);
    }
}

public class Administrator_customer extends JFrame{
	String[] arr;
    JPanel northP;
    JPanel southP;
    JButton btn;
    JLabel title;
    JTable table;
    JScrollPane scroll;
    DefaultTableModel model;
    String[] colName = {"회원이름", "ID", "email", "전화번호"};   // 테이블 열 이름
    CustomerInfo ci;
    DB_connect DB = new DB_connect();
    
    public Administrator_customer() {
    	setTitle("Customer Check");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        
        String[][] row = new String[0][4];
        model = new DefaultTableModel(row, colName);
        
        table = new JTable(model);
        
        scroll = new JScrollPane(table);
        c.add(scroll,BorderLayout.CENTER);
        
        ci = DB.loadCustomer();   // 데이터 저장   
        for (int i = 0; i < ci.id.size(); i++) {
            String[] data = {ci.name.get(i), ci.id.get(i), ci.email.get(i), ci.number.get(i)};
            model.addRow(data);
        }
        
        title = new JLabel("예매현황");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        title.setForeground(Color.WHITE);
        
        northP = new JPanel();
        northP.setBackground(new Color(45,45,45));
        northP.add(title);
        c.add(northP,BorderLayout.NORTH);
        
        southP = new JPanel();
        southP.setBackground(new Color(45,45,45));
        
        btn = new JButton("닫기");
        btn.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        southP.add(btn);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	new Administrator();
                Administrator_customer.this.dispose();
            }
        });
        
        c.add(southP,BorderLayout.SOUTH);
        
        setSize(700,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
