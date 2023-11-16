import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// 예매 정보 불러오기
class CheckUp {
    ArrayList<String> Team_1 = new ArrayList<String>();
    ArrayList<String> Team_2 = new ArrayList<String>();
    ArrayList<String> Stadium = new ArrayList<String>();
    ArrayList<String> Daytime = new ArrayList<String>();
    ArrayList<String> SeatNum = new ArrayList<String>();
    
    public void insertData(String Team_1, String Team_2, String Stadium, String Daytime, String seatNum) {
    	 this.Team_1.add(Team_1);
         this.Team_2.add(Team_2);
         this.Stadium.add(Stadium);
         this.Daytime.add(Daytime);
         this.SeatNum.add(seatNum);
    }
}

public class ReservationCheck extends JFrame implements MouseListener {
    String[] arr;
    JPanel northP;
    JPanel southP;
    //JButton bt_receipt; //예매 내역 출력
    JButton bt_del; //예매 취소
    JButton bt_back; // 뒤로 가기
    JLabel title;   // 테이블 타이틀
    JTable table;   // 표 내용을 담을 테이블
    JScrollPane scroll;
    DefaultTableModel model;
    String[] colName = {"TEAM1", "TEAM2", "경기장", "경기일정", "좌석번호"};  // 테이블 열 이름
    CheckUp cu;
    DB_connect DB = new DB_connect();

    public ReservationCheck(String id) {
        setTitle("Ticket Check");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        String[][] row = new String[0][5];
        model = new DefaultTableModel(row, colName);

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 행만 선택 가능

        scroll = new JScrollPane(table);
        c.add(scroll,BorderLayout.CENTER);
       
        cu = DB.loadReservationCheck(id); 
        for (int i = 0; i < cu.Team_1.size(); i++) {
            String[] data = {cu.Team_1.get(i), cu.Team_2.get(i), cu.Stadium.get(i), cu.Daytime.get(i), cu.SeatNum.get(i)};
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
        
        /*bt_receipt = new JButton("예매 내역 출력");
        bt_receipt.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        bt_receipt.setForeground(Color.WHITE);
        bt_receipt.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        southP.add(bt_receipt);*/ 
        
        bt_del = new JButton("예매 취소");
        bt_del.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        bt_del.setForeground(Color.WHITE);
        bt_del.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        southP.add(bt_del);
        
        bt_back = new JButton("뒤로 가기");
        bt_back.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        bt_back.setForeground(Color.WHITE);
        bt_back.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        southP.add(bt_back);
        c.add(southP,BorderLayout.SOUTH);

        // 테이블 마우스 이벤트
        table.addMouseListener(this);

        // 예매취소 누르면 DB에서 삭제
        bt_del.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "취소 하시겠습니까?","예매취소",JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                	String[] info = new String[5];     // 추출한 정보를 담을 배열
                    int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기

                    // 선택한 행에 대한 전체 데이터 추출
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        info[i] = (String) table.getValueAt(row, i);
                    }
                    
                	//GAME_NUMBER 가져오기
                	int gameNumber = DB.gameN_up(info[0],info[1],info[2],info[3]);
                	String [] seatNum = info[4].split("/");
                	for(int i=0 ; i<seatNum.length ; i++)
                    {
                		System.out.println(gameNumber);
                		System.out.println(seatNum[i]);
                		DB.del_seat(gameNumber,seatNum[i]);
                    }
                	
                	DB.del_ticket(info[0],info[1],info[2],info[3],info[4]);
                }
            }
        });

        bt_back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	new Menu(id);
                ReservationCheck.this.dispose();
            }
        });

        setSize(700,500);
        setResizable(false);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setVisible(true);
    }
    

    // 테이블에서 행 선택 시 이벤트 처리
    @Override
    public void mouseClicked(MouseEvent e) {
        String[] info = new String[5];     // 추출한 정보를 담을 배열
        int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기

        // 선택한 행에 대한 전체 데이터 추출
        for (int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) table.getValueAt(row, i);
        }

        // 인스턴스 변수에 정보 전달
        this.arr = info;
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}