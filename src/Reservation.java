import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// 표 테이블에 들어갈 표 정보
class Table {
    ArrayList<String> Team_1 = new ArrayList<String>();
    ArrayList<String> Team_2 = new ArrayList<String>();
    ArrayList<String> Stadium = new ArrayList<String>();
    ArrayList<String> Daytime = new ArrayList<String>();

    public void insertTable(String Team_1, String Team_2, String Stadium, String Daytime) {
        this.Team_1.add(Team_1);
        this.Team_2.add(Team_2);
        this.Stadium.add(Stadium);
        this.Daytime.add(Daytime);
    } 
    
}

// 표 테이블의 가운데 부분
class TableCenter extends JPanel implements MouseListener {
    JTable table;
    DefaultTableModel model;
    JScrollPane scroll;
    private String Team_1 = "TEAM1";  // 팀1
    private String Team_2 = "TEAM2";  // 팀2
    private String Stadium = "경기장";
    private String Daytime = "경기일정";
    private String[] arr = null;   // 선택한 행의 데이터를 담을 배열
    
    int len;

    // 표 테이블 생성
    public TableCenter() {
        String[] title = {"TEAM1","TEAM2","STADIUM","DAYTIME"}; //컬럼 네임 설정
        String[][] row = new String[0][4];
        model = new DefaultTableModel(row,title);

        table = new JTable(model); //표 테이블
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 행만 선택 가능

        scroll = new JScrollPane(table); //스크롤 팬 추가
        scroll.setPreferredSize(new Dimension(780,300));
        add(scroll,BorderLayout.CENTER);

        setVisible(true);
    }

    //표 테이블에 데이터 추가
    public void showTable(Table t, String Team_1) {
        //중복 제거
        if (this.Team_1.equals(Team_1) && this.Team_2.equals(Team_2) && this.Stadium.equals(Stadium) && this.Daytime.equals(Daytime)) return;

        //중복 출력을 방지하기 위해 이름 기억
        this.Team_1 = Team_1;
        
        //이미 한 번 조회한 적이 있을 경우 테이블 리셋
        if (this.Team_1 != "TEAM1") {
            for (int i = len-1; i >= 0; i--) {
                this.model.removeRow(i);
            }
        }

        //표 테이블에 티켓 정보 삽입
        len = t.Team_1.size();
        for (int i = 0; i < len; i++) {
            String[] data = {t.Team_1.get(i), t.Team_2.get(i), t.Stadium.get(i), t.Daytime.get(i)};//String.valueOf(t.seats.get(i))
            model.addRow(data);
        }

        // 선택한 표에 대한 정보 가져오기
        table.addMouseListener(this);

        setVisible(true);
    }

    // 테이블에서 행 선택 시 이벤트 처리
    @Override
    public void mouseClicked(MouseEvent e) {
        String[] info = new String[4];     // 추출한 정보를 담을 배열
        int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기

        //선택한 행에 대한 전체 데이터 추출
        for (int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) table.getValueAt(row, i);
        }

        //인스턴스 변수에 정보 전달
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

    // 팀1 반환
    public String getTeam_1() {
        return this.Team_1;
    }
    
    //팀2 반환
    public String getTeam_2() {
        return this.Team_2;
    }
    
    //경기장 반환
    public String getStadium() {
        return this.Stadium;
    }

    //경기일정 반환
    public String getDaytime() {
        return this.Daytime;
    }

    //선택한 행 정보 반환
    public String[] getArr() {
        return this.arr;
    }

    //선택한 행 정보 리셋
    public void setArr() {
        this.arr = null;
    }    
}

//예약하기 화면의 가운데 패널
class ReservationCenter extends JPanel implements ItemListener{//implements ItemListener
    Reservation frame; //없어도 될듯
    static JComboBox<String> team = new JComboBox<String>();   //팀 콤보박스
    DB_connect DB = new DB_connect();
    
    private JRadioButton one = new JRadioButton("1");
	private JRadioButton two = new JRadioButton("2");
	private JRadioButton three = new JRadioButton("3");
	private JRadioButton four = new JRadioButton("4");
	private JRadioButton five = new JRadioButton("5");
	
	int cost=0;
	int person=0;
 
	public int getCost() {	    	
		return this.cost;		
	}
	
	public void setCost(int cost) {	
		this.cost = cost;		
	}
	
	public int getPerson() {		
		return this.person;		
	}
	
	public void setPerson(int person) {		
		this.person = person;		
	}
	
    public ReservationCenter(Reservation frame, String id) {
        setLayout(null);
        setBackground(new Color(45,45,45)); //이 패널의 배경색
              
        //맨위 패널
        JPanel pa= new JPanel();
        pa.setBackground(Color.BLACK);
        pa.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pa.setBounds(0,0,900,50);
        add(pa);
        JLabel la = new JLabel("EPL Ticket Reservation");
        la.setForeground(Color.WHITE);
        la.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        pa.add(la);
        
        JPanel pa1= new JPanel();
        pa1.setBackground(new Color(0XFF895A));
        pa1.setBounds(0,50,900,10);
        add(pa1);
        
        //콤보박스를 뒤 네모 박스 생성
        JPanel square = new JPanel();
        square.setBackground(Color.BLACK);
        square.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 16));
        square.setBounds(100,80,350,60);//square.setBounds(100,130,350,60);
        add(square);
        
        // '팀' 글자
        JLabel teamSelect = new JLabel("TEAM");
        teamSelect.setForeground(Color.WHITE);
        teamSelect.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        square.add(teamSelect);
        
        //team:콤보박스, teamList:db에서 가져온 팀 리스트 배열, Team_list:db에서 팀 리스트 정리한 거
        String[] teamList = new String[100];
        teamList=DB.Team_list();
        // 팀 콤보박스에 리스트 삽입
        team.setPreferredSize(new Dimension(220,30));
        for (int i=0; i<teamList.length; i++){
            team.addItem(teamList[i]);
        }
        square.add(team);
        
        // 표 테이블
        JPanel ticketTable = new JPanel();
        ticketTable.setBounds(60,160,780,300);
        
        // 표 구현
        TableCenter tow = new TableCenter();
        ticketTable.add(tow);
        add(ticketTable);
        
        // 조회 버튼
        JButton lookUp = new JButton("조회");
        lookUp.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
        lookUp.setForeground(Color.WHITE);
        lookUp.setBounds(500, 90, 200, 40);
        lookUp.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(lookUp);
        
        // 조회 버튼 클릭시
        lookUp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tow.setArr();
                String t1 = team.getSelectedItem().toString();//팀 콤보박스로부터 팀1에 대한 정보를 얻어와서 t1에 저장한다

                // DB로 팀
                Table t = DB.table_list(t1);
                tow.showTable(t, t1);
            }
        });
        
        
        //라디오버튼 인원수 선택
        JPanel pn =new JPanel();
        pn.setBackground(Color.BLACK);
        pn.setBounds(60,480,780,50);
        pn.setLayout(new FlowLayout(FlowLayout.CENTER,25,10));
        add(pn);
        
        JLabel peopleNumber = new JLabel("인원선택");
        peopleNumber.setForeground(Color.WHITE);
        peopleNumber.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        pn.add(peopleNumber);
        
        ButtonGroup group = new ButtonGroup();
    	
    	group.add(one);
    	group.add(two);
    	group.add(three);
    	group.add(four);
    	group.add(five);
    	
    	one.setBackground(Color.BLACK);
    	one.setForeground(Color.WHITE);
    	one.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	two.setBackground(Color.BLACK);
    	two.setForeground(Color.WHITE);
    	two.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	three.setBackground(Color.BLACK);
    	three.setForeground(Color.WHITE);
    	three.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	four.setBackground(Color.BLACK);
    	four.setForeground(Color.WHITE);
    	four.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	five.setBackground(Color.BLACK);
    	five.setForeground(Color.WHITE);
    	five.setFont(new Font("맑은 고딕", Font.BOLD, 18));
    	
    	one.addItemListener(this);
		two.addItemListener(this);
		three.addItemListener(this);
		four.addItemListener(this);
		five.addItemListener(this);

    	pn.add(one);
    	pn.add(two);
    	pn.add(three);
    	pn.add(four);
    	pn.add(five);
        
    	//뒤로가기 버튼
    	JButton back = new JButton("뒤로가기");
		back.setBounds(225,550,150,40); 
		back.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		back.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
	    back.setForeground(Color.WHITE);
	    add(back);
	    back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Menu(id);
                frame.dispose();
            }
        });
	    
	    // 좌석선택 버튼
	    JButton seats = new JButton("좌석선택");      
	    seats.setBounds(525,550,150,40);
	    seats.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
	    seats.setForeground(Color.WHITE);
	    seats.setFont(new Font("맑은 고딕", Font.BOLD, 18));
	    add(seats);
        

        // 좌석선택 버튼 클릭시
        seats.addMouseListener(new MouseAdapter() {
            String Team_1 = null;
            String Team_2 = null;
            String Stadium = null;
            String Daytime = null;
            String[] info = null;
            public void mouseClicked(MouseEvent e) {
                this.info = tow.getArr();                
                
                if (this.info == null) {
                    JOptionPane.showMessageDialog(null, "예매할 경기를 선택하세요.");
                }  
                else if(person == 0) {
                	JOptionPane.showMessageDialog(null, "예매 인원수를 선택하세요.");
                }
                else {
                	this.Team_1 = info[0];
                	this.Team_2 = info[1];
                	this.Stadium = info[2];
                	this.Daytime = info[3];
                    new SeatSelect(id,Team_1,Team_2,Stadium,Daytime,person,cost);//new SeatSelect(frame,id,Team_1,Team_2,Stadium,Daytime,person,cost);//new SeatSelect(frame, id, st, ed, dt, info);
                    frame.dispose();
                }              
            }
        });    
    }
    
    public void itemStateChanged(ItemEvent e) {
		if(one.isSelected()) {
			cost= 100000;
			person=1;
		}
		else if(two.isSelected()) {
			cost= 200000;
			person=2;
		}
		else if(three.isSelected()) {
			cost= 300000;
			person=3;
		}
		else if(four.isSelected()) {
			cost= 400000;
			person=4;
		}
		else if(five.isSelected()) {
			cost= 500000;
			person=5;
		}
		setPerson(person);
		setCost(cost);
	}
}

// '예약하기' 화면의 메인 부분
public class Reservation extends JFrame {

    public Reservation(String id) {
        setTitle("Reservation");
        setSize(900,650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        c.add(new ReservationCenter(this, id), BorderLayout.CENTER);
        
        setVisible(true);
    }
}