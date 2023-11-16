import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

class GameInfo{
    Vector<String> team1 = new Vector<String>();
    Vector<String> team2 = new Vector<String>();
    Vector<String> stadium = new Vector<String>();
    Vector<String> daytime = new Vector<String>();
    ArrayList<Integer> gameNum =  new ArrayList<Integer>();
   
    public void insertGame(String team1, String team2, String stadium, String daytime, int gameNum) {
    	 this.team1.add(team1);
         this.team2.add(team2);
         this.stadium.add(stadium);
         this.daytime.add(daytime);
         this.gameNum.add(gameNum);
    }
}

public class Administrator_game extends JFrame{
    GameInfo gi;
    JPanel northP;
    JPanel southP;
    
    private JTable table = null;
    private DefaultTableModel model = null;
    JScrollPane scroll;
    
    private JButton btnAdd = null;
    private JButton btnDel = null;
    private JButton btnBack = null;
    
    private JTextField tf_team1 = null;
    private JTextField tf_team2 = null;
    private JTextField tf_stadium = null;
    private JTextField tf_daytime = null;
    private JTextField tf_gameNum = null;
    
    JLabel title;
    private JLabel la_team1 = null;
    private JLabel la_team2 = null;
    private JLabel la_stadium = null;
    private JLabel la_daytime = null;
    private JLabel la_gameNum = null;
    
    private String team1 = null;
    private String team2 = null;
    private String stadium = null;
    private String daytime = null;
    private int gameNum;
    private String[] arr = null;
 
    String[] colName = {"TEAM1", "TEAM2", "STADIUM", "DAYTIME", "GAME_NUMBER"}; 
    DB_connect DB = new DB_connect();
    
	public Administrator_game(){
		setTitle("경기일정");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Container c = getContentPane();
        c.setLayout(new BorderLayout());
		
		String[][] row = new String[0][5];
		model = new DefaultTableModel(row, colName);
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			private String team1 = null;
		    private String team2 = null;
		    private String stadium = null;
		    private String daytime = null;
		    private String gameNum = null;
			private String[] arr = null;
            public void mouseClicked(MouseEvent e) {            	 
            	String[] info = new String[5];     // 추출한 정보를 담을 배열              
            	int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기            
            	
            	// 선택한 행에 대한 전체 데이터 추출                 
            	for (int i = 0; i < table.getColumnCount(); i++) {                    
            		info[i] = (String) table.getValueAt(row, i);                 
            	}

                // 인스턴스 변수에 정보 전달                 
            	this.arr = info;               
                team1 = info[0];              
                team2 = info[1];
                stadium = info[2];
                daytime = info[3];
                gameNum = info[4];
               
                System.out.println(team1);
                System.out.println(team2);
                System.out.println(stadium);
                System.out.println(daytime);
                System.out.println(gameNum);
                
                tf_team1.setText(team1);
                tf_team2.setText(team2);
                tf_stadium.setText(stadium);
                tf_daytime.setText(daytime);
                tf_gameNum.setText(gameNum);
            }
        });
	        
	    scroll = new JScrollPane(table);
	    c.add(scroll,BorderLayout.CENTER);
		
		gi = DB.selectAll();
		for (int i = 0; i < gi.team1.size(); i++) {
            String[] data = {gi.team1.get(i), gi.team2.get(i), gi.stadium.get(i), gi.daytime.get(i), String.valueOf(gi.gameNum.get(i))};
            model.addRow(data);
        }
		
		//위
		title = new JLabel("경기일정");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        title.setForeground(Color.WHITE);
      
        northP = new JPanel();
        northP.setBackground(new Color(45,45,45));
        northP.add(title);
        c.add(northP,BorderLayout.NORTH);
             
        //아래
        southP = new JPanel();
        southP.setBackground(new Color(45,45,45));
        
        la_team1 = new JLabel("TEAM1");
        la_team2 = new JLabel("TEAM2");
        la_stadium = new JLabel("STADIUM");
        la_daytime = new JLabel("DAYTIME");
        la_gameNum = new JLabel("GAME_NUMBER");
        la_team1.setForeground(Color.WHITE);
        la_team2.setForeground(Color.WHITE);
        la_stadium.setForeground(Color.WHITE);
        la_daytime.setForeground(Color.WHITE);
        la_gameNum.setForeground(Color.WHITE);
        
        tf_team1 = new JTextField(10);
        tf_team2 = new JTextField(10);
        tf_stadium = new JTextField(18);
        tf_daytime = new JTextField(15);
        tf_gameNum = new JTextField(10);
        
        southP.add(la_team1);
        southP.add(tf_team1);
        southP.add(la_team2);
        southP.add(tf_team2);
        southP.add(la_stadium);
        southP.add(tf_stadium);
        southP.add(la_daytime);
        southP.add(tf_daytime);
        southP.add(la_gameNum);
        southP.add(tf_gameNum);
        
        btnAdd = new JButton("추가");
        btnAdd.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String team1 =tf_team1.getText();
                String team2 =tf_team2.getText();
                String stadium =tf_stadium.getText();
                String daytime =tf_daytime.getText();
                int gameNum =Integer.valueOf(tf_gameNum.getText());
                
                DB.insert(team1,team2,stadium,daytime,gameNum);
            }
        });
        southP.add(btnAdd);
        
        btnDel = new JButton("삭제");
        btnDel.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btnDel.setForeground(Color.WHITE);
        btnDel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 String team1 =tf_team1.getText();
                 String team2 =tf_team2.getText();
                 String stadium =tf_stadium.getText();
                 String daytime =tf_daytime.getText();
                 int gameNum =Integer.valueOf(tf_gameNum.getText());
                 
                 DB.delete(team1,team2,stadium,daytime,gameNum);
            }
        });
        southP.add(btnDel);
        
        btnBack = new JButton("뒤로가기");
        btnBack.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Administrator();
            	Administrator_game.this.dispose();
            }
        });
        southP.add(btnBack);
        
        c.add(southP,BorderLayout.SOUTH);
		
		setResizable(false); //창 크기 조절 못하게 하는
	    setSize(1200,600);
	    setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
	    setVisible(true);
	}
}
