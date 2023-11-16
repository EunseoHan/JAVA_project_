import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.Vector;
import java.util.ArrayList;

public class SeatSelect extends JFrame {
	SeatSelect frame;
	
	private ArrayList<String> reservedSeat = new ArrayList<String>();//예약된 좌석 저장하는 
	
	JCheckBox[] seatsN = new JCheckBox[20]; //북쪽 좌석
	JCheckBox[] seatsS = new JCheckBox[20]; //남쪽 좌석
	JCheckBox[] seatsE = new JCheckBox[11]; //동쪽 좌석
	JCheckBox[] seatsW = new JCheckBox[11]; //서쪽 좌석
	Vector<String> seatsNumber = new Vector<String>(); //체크한 좌석 저장
	
	int personCheck = 0;

    DB_connect DB = new DB_connect();  // DB
    
	MyPanel panel = new MyPanel();
	JButton ticketing = new JButton("예매하기");
	JButton back = new JButton("뒤로가기");
	//JButton reticketing = new JButton("다시선택");
	
	JLabel n = new JLabel("N");
	JLabel s = new JLabel("S");
	JLabel e = new JLabel("E");
	JLabel w = new JLabel("W");
	
	public SeatSelect(String id, String Team_1, String Team_2, String Stadium, String Daytime, int person, int cost) {//저장 중임 왜냐면 Reservation에서 넘어올거니까
		setTitle("좌석선택");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(null);
		
		panel.setBackground(new Color(45,45,45));
		c.setBackground(new Color(45,45,45));
		
		//아이디에 맞는 이름 가져오기(일단 가져오고 보자!)
		String name = DB.name_up(id);
        System.out.println(name);
        
        //Team_1, Team_2, Stadium, Daytime에 맞는 game의 GAME_NUMBER 가져오기
        int gameNumber = DB.gameN_up(Team_1, Team_2, Stadium, Daytime);
        System.out.println(gameNumber);
        
		//맨위 패널
		JPanel pa= new JPanel();
        pa.setBackground(Color.BLACK);
        pa.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pa.setBounds(0,0,1000,43);//pa.setBounds(0,0,1200,50);
        c.add(pa);
        JLabel la = new JLabel("EPL Ticket Reservation");
        la.setForeground(Color.WHITE);
        la.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        pa.add(la);
		
        JPanel pa1= new JPanel();
        pa1.setBackground(new Color(0XFF895A));
        pa1.setBounds(0,43,1000,8);//pa1.setBounds(0,50,1200,10);
        c.add(pa1);
        
        //맨아래 패널
        JPanel pa2= new JPanel();
        pa2.setBackground(Color.BLACK);
        pa2.setBounds(0,607,1000,43);//pa2.setBounds(0,870,1200,50);
        add(pa2);
        JLabel la2 = new JLabel("원하시는 좌석을 선택해주세요. 경기장 좌석을 깨끗하게 이용하여 주세요. 감사합니다.");
        la2.setForeground(Color.WHITE);
        la2.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        pa2.add(la2);
        
        JPanel pa3= new JPanel();
        pa3.setBackground(new Color(0XFF895A));
        pa3.setBounds(0,599,1000,8);//pa3.setBounds(0,860,1200,10);
        c.add(pa3);
		
		//구장 모양
        panel.setBounds(-90,60,900,530);//panel.setBounds(0,70,1200,720);
		c.add(panel);
		
		//좌석(체크박스) 
		for(int i = 0; i<20; i++)
		{	
		    seatsN[i]=new JCheckBox();
		    seatsN[i].setBackground(new Color(0,0,133));
			seatsN[i].setBounds(300+(i*20),120,20,20);
			
			char input = (char) (78); //input="N"을 의미함
		    seatsN[i].setText(input + "," + Integer.toString(i + 1));
			seatsN[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1) {
						// 좌석 번호 뽑아내기.
						JCheckBox tempCheckbox = (JCheckBox) e.getSource();
						String test = tempCheckbox.getText().trim();//trim():공백 없애는
						String[] testArr = test.split(",", 2); 

						String row = testArr[0];
						String col = testArr[1];
						
						// 행
						System.out.println(row);
						System.out.println(col);

						seatsNumber.add(row +"-"+ col); // 좌석 번호 vector에 넣기.
						seatsNumber.set(personCheck, row +"-"+ col);
						personCheck++;
						if (person == personCheck) {
							System.out.println("모든 좌석 check 완료.");
							//reticketing.setEnabled(true);
							ticketing.setEnabled(true);
							for(int i = 0; i<20; i++)
							{
								seatsN[i].setEnabled(false);
								seatsS[i].setEnabled(false);
							}
							for(int i = 0; i<11; i++)
							{
								seatsW[i].setEnabled(false);
								seatsE[i].setEnabled(false);
							}
							
						}
					} else {
						// check 지워질때
						if (personCheck != 0) {
							personCheck--;
						}
					}
				}
			});
			panel.add(seatsN[i]);
			
			seatsS[i]=new JCheckBox();
		    seatsS[i].setBackground(new Color(0,0,133));
			seatsS[i].setBounds(300+(i*20),390,20,20);
			
			char inputS = (char) (83); //input="S"을 의미함
		    seatsS[i].setText(inputS + "," + Integer.toString(i + 1));
			seatsS[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1) {
						// 좌석 번호 뽑아내기.
						JCheckBox tempCheckbox = (JCheckBox) e.getSource();
						String test = tempCheckbox.getText().trim();//trim():공백 없애는
						String[] testArr = test.split(",", 2);

						String row = testArr[0];
						String col = testArr[1];
						
						// 행
						System.out.println(row);
						System.out.println(col);

						seatsNumber.add(row +"-"+ col); // 좌석 번호 vector에 넣기.
						seatsNumber.set(personCheck, row +"-"+ col);
						personCheck++;
						if (person == personCheck) {
							System.out.println("모든 좌석 check 완료.");
							//reticketing.setEnabled(true);
							ticketing.setEnabled(true);
							for(int i = 0; i<20; i++)
							{
								seatsN[i].setEnabled(false);
								seatsS[i].setEnabled(false);
							}
							for(int i = 0; i<11; i++)
							{
								seatsW[i].setEnabled(false);
								seatsE[i].setEnabled(false);
							}
							
						}
					} else {
						// check 지워질때
						if (personCheck != 0) {
							personCheck--;
						}
					}
				}
			});
			
			panel.add(seatsS[i]);
		}
		
		for(int i = 0; i<11; i++)
		{	
		    seatsE[i]=new JCheckBox();
		    seatsE[i].setBackground(new Color(0,0,133));
			seatsE[i].setBounds(710,155+(i*20),20,20);
			
			char inputE = (char) (69); //input="E"을 의미함
		    seatsE[i].setText(inputE + "," + Integer.toString(i + 1));
			seatsE[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1) {
						// 좌석 번호 뽑아내기.
						JCheckBox tempCheckbox = (JCheckBox) e.getSource();
						String test = tempCheckbox.getText().trim();//trim():공백 없애는
						String[] testArr = test.split(",", 2); //split(",", 0):안 나누겠다는건데..?

						String row = testArr[0];
						String col = testArr[1];
						
						// 행
						System.out.println(row);
						System.out.println(col);

						seatsNumber.add(row +"-"+ col); // 좌석 번호 vector에 넣기.
						seatsNumber.set(personCheck, row +"-"+ col);
						personCheck++;
						if (person == personCheck) {
							System.out.println("모든 좌석 check 완료.");
							//reticketing.setEnabled(true);
							ticketing.setEnabled(true);
							for(int i = 0; i<20; i++)
							{
								seatsN[i].setEnabled(false);
								seatsS[i].setEnabled(false);
							}
							for(int i = 0; i<11; i++)
							{
								seatsW[i].setEnabled(false);
								seatsE[i].setEnabled(false);
							}
							
						}
					} else {
						// check 지워질때
						if (personCheck != 0) {
							personCheck--;
						}
					}
				}
			});
			panel.add(seatsE[i]);
			
			seatsW[i]=new JCheckBox();
		    seatsW[i].setBackground(new Color(0,0,133));
			seatsW[i].setBounds(270,155+(i*20),20,20);
			
			char inputW = (char) (87); //input="W"을 의미함
		    seatsW[i].setText(inputW + "," + Integer.toString(i + 1));
			seatsW[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1) {
						// 좌석 번호 뽑아내기.
						JCheckBox tempCheckbox = (JCheckBox) e.getSource();
						String test = tempCheckbox.getText().trim();//trim():공백 없애는
						String[] testArr = test.split(",", 2);

						String row = testArr[0];
						String col = testArr[1];
						
						// 행
						System.out.println(row);
						System.out.println(col);

						seatsNumber.add(row +"-"+ col); // 좌석 번호 vector에 넣기.
						seatsNumber.set(personCheck, row +"-"+ col);
						personCheck++;
						if (person == personCheck) {
							System.out.println("모든 좌석 check 완료.");
							//reticketing.setEnabled(true);
							ticketing.setEnabled(true);
							for(int i = 0; i<20; i++)
							{
								seatsN[i].setEnabled(false);
								seatsS[i].setEnabled(false);
							}
							for(int i = 0; i<11; i++)
							{
								seatsW[i].setEnabled(false);
								seatsE[i].setEnabled(false);
							}
							
						}
					} else {
						// check 지워질때
						if (personCheck != 0) {
							personCheck--;
						}
					}
				}
			});
			panel.add(seatsW[i]);
		}
		
		//label 붙이기(N,S,E,W)
		n.setBounds(475,25,50,50);
		n.setForeground(Color.RED);
		n.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		panel.add(n);
		
		s.setBounds(485,450,50,50);
		s.setForeground(Color.BLUE);
		s.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		panel.add(s);
		
		e.setBounds(792,240,50,50);
		e.setForeground(new Color(0,180,30));
		e.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		panel.add(e);
		
		w.setBounds(188,240,60,50);
		w.setForeground(Color.YELLOW);
		w.setFont(new Font("맑은 고딕", Font.BOLD, 45));
		panel.add(w);
		
		//이미 예약한 좌석 선택 못하게 하기
        reservedSeat = DB.getSeatList(gameNumber);
        
		for (int i = 0; i < reservedSeat.size(); i++) {
			String text = reservedSeat.get(i);
			String[] testArr = text.split("-", 2);

			String row = testArr[0];
			String col = testArr[1];
			
			int num =0; 
			num=Integer.parseInt(col)-1;
			
			if(row.equals("N")) {
				seatsN[num].setEnabled(false);
			}else if(row.equals("S")) {
				seatsS[num].setEnabled(false);
			}else if(row.equals("E")) {
				seatsE[num].setEnabled(false);
			}else if(row.equals("W")) {
				seatsW[num].setEnabled(false);
			}else {
				System.out.println("아무것도 아님");			
				System.out.println(row);
			}
		}
		
		//예매버튼
		ticketing.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
		ticketing.setForeground(Color.WHITE);
		ticketing.setBounds(850,70,120,40);
		ticketing.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		c.add(ticketing);
		
		ticketing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seatNumberCheck = "";
				for (int i = 0; i < person; i++) {					 				
					//seat(db)의 SEATNUMBER 만들기
					String seatNumber = seatsNumber.get(i); // seatNumber가 seat(db)에 넣을 좌석번호, seatsNumber가 체크한 좌석번호 넣어놓는 벡터
					
					//seat(db)의 RESERVED에 예약되었는 표시하기
					String reserved = "O";
					
					//seat(db)에 정보저장
					DB.seat(gameNumber,seatNumber,reserved);
					
					//ticket(db)의 SEATNUMBER 만들기
					if((person-1) == i ) {
						seatNumberCheck += seatsNumber.get(i);
					} else {
						seatNumberCheck += (seatsNumber.get(i)+"/");
					}
				}
				
				//(ticket(db)의 SEATNUMBER)=seatNumberCheck; 
				String ticketSeatNumber = seatNumberCheck; 
				DB.ticket(name,id,Team_1,Team_2,Stadium,Daytime,person,cost,ticketSeatNumber);
				JOptionPane.showMessageDialog(null, "예매가 완료되었습니다.");
				new Menu(id);
				dispose();
			}
			
		});
		
		//뒤로가기 버튼 
		back.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
		back.setForeground(Color.WHITE);
		back.setBounds(850,150,120,40);
		back.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		c.add(back);
		
	    back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Reservation(id);
				dispose();
			}			
		});
		
		//다시선택 버튼
		/*reticketing.setBackground(new Color(0XFF895A)); //주황색 new Color(0XFF895A)
		reticketing.setForeground(Color.WHITE);
		reticketing.setBounds(850,150,120,40);
		reticketing.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		c.add(reticketing);*/

		System.out.println("나 왔는데 보이지!!!");
		System.out.println(id);
		System.out.println(Team_1);
		System.out.println(Team_2);
		System.out.println(Stadium);
		System.out.println(Daytime);
		System.out.println(person);
		System.out.println(cost);
		
		setSize(1000,680);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	class MyPanel extends JPanel{
		
		public MyPanel(){
			setLayout(null);	
		}
		
		public void paintComponent(Graphics g){		
			super.paintComponent(g);
			//북
			g.setColor(Color.LIGHT_GRAY);
			int []x1 = {260,740,784,700,300,216};
			int []y1 = {0,0,60,140,140,60};
			g.fillPolygon(x1,y1,6);
				
			//남
			g.setColor(Color.LIGHT_GRAY);
			int []x2 = {300,700,784,740,260,216};
			int []y2 = {390,390,470,530,530,470};
			g.fillPolygon(x2,y2,6);
			
			//동
			g.setColor(Color.LIGHT_GRAY);
			int []x3 = {710,797,860,860,797,710};
			int []y3 = {150,75,105,425,455,380};
			g.fillPolygon(x3,y3,6);
			
			//서
			g.setColor(Color.LIGHT_GRAY);
			int []x4 = {140,203,290,290,203,140};
			int []y4 = {105,75,150,380,455,425};
			g.fillPolygon(x4,y4,6);
			
			ImageIcon stadium = new ImageIcon("images/STADIUM.jpg");
			Image img = stadium.getImage();
			g.drawImage(img,300,150,400,230,this);//x,y,너비,높이
		}	
	}
}