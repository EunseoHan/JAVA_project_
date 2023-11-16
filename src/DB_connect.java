import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class DB_connect {
	
	public static Connection makeConnection() {
		String url="jdbc:mysql://localhost/soccer_db?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		String id="root";
		String password="hes95**hes95**";
		Connection con=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loading success!");
			con=DriverManager.getConnection(url, id, password);
			System.out.println("database connecting success!");
		} catch(ClassNotFoundException e) {
			System.out.println("cannot find driver!");
		} catch(SQLException e) {
			System.out.println("connection failed!");
		}
		
		return con;
	}
	
	//id 중복확인
	public int idCheck(Member new_member) {
		Connection con = makeConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String id = new_member.get_id();

        try {
            String select = "select ID from customer where ID = ?";
            pstmt = con.prepareStatement(select);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	if(rs.getString(1).equals(id)) {
            		return 1;
            	}        	
            }
            return 0;

        }catch(SQLException e ) {			
			 System.out.println("connection failed! idCheck");		        		 
        }    
        if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌    
        if(con != null) try{ con.close();} catch(SQLException e){};
        return -1;
    }
	
	//customer에 대한 db (즉, 회원가입)
	 public void customer_db(Member new_member) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 
		 try {
		 String insert = "insert into customer(NAME,ID,PASSWORD,EMAIL,PHONENUMBER)";
         insert+= "values (?,?,?,?,?)";
         pstmt = con.prepareStatement(insert);
         
         String name=new_member.get_name();
         String id=new_member.get_id();
         String password=new_member.get_pw();
         String email=new_member.get_email();
         String phoneNumber=new_member.get_phoneNumber();
         
         pstmt.setString(1, name);
         pstmt.setString(2, id);
         pstmt.setString(3, password);
         pstmt.setString(4, email);
         pstmt.setString(5, phoneNumber);
         
         int i=pstmt.executeUpdate(); 
         
         if(i==1) 
				System.out.println("record add success!!"); 
			else
				System.out.println("record add failed!!");
         
		 }catch(SQLException e ) {
			System.out.println("connection failed! in customer_db");
		}
		 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
		 
	 }
	 
	 //로그인 하기 위한 db
	 public int login_tf(String idt, String pwt){
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
	     ResultSet rs = null;
	     try {
		     String select = "SELECT * FROM customer WHERE ID=? AND PASSWORD=?";
		     pstmt = con.prepareStatement(select);
		     //비번 아이디 확인 둘다 하기
		     pstmt.setString(1, idt); //ID=idt(즉, 받은 아이디를 ID로 세팅 (이게 받은 아이디와 ID와 일치 하는지는 밑에서 확인))
		     pstmt.setString(2, pwt); //PASSWORD=pwt
		     
		     rs = pstmt.executeQuery();   
		     
		     while (rs.next()) {          
		    	 if (rs.getString(2).equals(idt)&&rs.getString(3).equals(pwt)) {                
		    		 if(idt.equals("AD")){
		    			 return 1; // 관리자 로그인              
		    		 }                  
		    		 else 
		    		 {                        
		    			 return 2; // 로그인 성공          
		    		 }
		    	 }                  
		     }	
		     return -1; // 아이디/비번 없음 		     
			 }catch(SQLException e ) {			
				 System.out.println("connection failed! in login_tf");		        
			 } 
			 return -2; // DB 오류	     
	    }
	 
	 //이름 가져오려고(예매 내역, 티켓에 넣으려고)
	 public String name_up(String id) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
	     ResultSet rs = null;
	     try {
	    	 String []add=new String[100];// String []add=new String[1];이어도 됨 혹시 몰라서 100으로 해놓음
		     String select = "SELECT NAME FROM customer WHERE ID=?";
		     pstmt = con.prepareStatement(select);
		     pstmt.setString(1, id);
		     
		     rs = pstmt.executeQuery();
		     while (rs.next()) {         
	        	 add[0]=rs.getString(1);	            
	         }
		     return add[0];
			 }catch(SQLException e ) {			
				 System.out.println("connection failed! in name_up");		        
			 } 
	     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	
	     return null;
	 }
	 
	 //GAME_NUMBER 가져오려고 만듦(seatSelect에서 예매하기 버튼 누를 때 필요함)
	 public int gameN_up(String Team_1, String Team_2, String Stadium, String Daytime) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
	     ResultSet rs = null;
	     try {
	    	 int []add = new int[100];// String []add=new String[1];이어도 됨 혹시 몰라서 100으로 해놓음
		     String select = "SELECT GAME_NUMBER FROM game WHERE TEAM_1=? AND TEAM_2=? AND STADIUM=? AND DAYTIME=?";
		     pstmt = con.prepareStatement(select);
		     pstmt.setString(1, Team_1);
		     pstmt.setString(2, Team_2);
		     pstmt.setString(3, Stadium);
		     pstmt.setString(4, Daytime);
		     
		     rs = pstmt.executeQuery();
		     while (rs.next()) {         
	        	 add[0]=rs.getInt(1);	            
	         }
		     return add[0];
			 }catch(SQLException e ) {			
				 System.out.println("connection failed! in gameN_up");		        
			 } 
	     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	
	     return 0;
	 }
	 
	 //seat(db)에 insert 하는
	 public void seat(int Gamenumber, String Seatnumber, String Reserved) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		
		 try {				
			 String insert = "insert into seat values(?,?,?)";				
			 pstmt = con.prepareStatement(insert);
		     pstmt.setInt(1, Gamenumber);
			 pstmt.setString(2, Seatnumber);
		     pstmt.setString(3,Reserved);
			 pstmt.executeUpdate();			 
		 } catch(SQLException e ) {			
			 System.out.println("connection failed! in gameN_up");		        	 
		 } 
     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
     if(con != null) try{ con.close();} catch(SQLException e){};
		
	 }
	 
	 //ticket(db)에 insert 하는
	 public void ticket(String name, String id, String Team_1, String Team_2, String Stadium, String Daytime, int person, int cost, String Seatnumber) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		
		 try {				
			 String insert = "insert into ticket values(?,?,?,?,?,?,?,?,?)";				
			 pstmt = con.prepareStatement(insert);
		     pstmt.setString(1, name);
		     pstmt.setString(2, id);
			 pstmt.setString(3, Team_1);
		     pstmt.setString(4,Team_2);
		     pstmt.setString(5, Stadium);
			 pstmt.setString(6, Daytime);
		     pstmt.setInt(7,person);
		     pstmt.setInt(8,cost);
		     pstmt.setString(9, Seatnumber);
			 pstmt.executeUpdate();			 
		 } catch(SQLException e ) {			
			 System.out.println("connection failed! in ticket");		        	 
		 } 
     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
     if(con != null) try{ con.close();} catch(SQLException e){};
		
	 }	
	 
	//ticket(db)에 delete 하는
	public void del_ticket(String team1, String team2, String stadium, String daytime, String seatNum) {
		 Connection con = makeConnection(); 
		 PreparedStatement pstmt=null;
		
		 try {				
			 String delete = "delete from ticket where TEAM_1=? and TEAM_2=? and STADIUM=? and DAYTIME=? and SEATNUMBER=?";				
			 pstmt = con.prepareStatement(delete);
		     pstmt.setString(1, team1);
		     pstmt.setString(2, team2);
			 pstmt.setString(3, stadium);
		     pstmt.setString(4, daytime);
		     pstmt.setString(5, seatNum);
			 pstmt.executeUpdate();			 
		 } catch(SQLException e ) {			
			 System.out.println("connection failed! in del_ticket");		        	 
		 } 
     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
     if(con != null) try{ con.close();} catch(SQLException e){};
		
	 }	
	
	 //seats(db)에 delete 하는
		public void del_seat(int gameNum, String seatNum) {
			 Connection con = makeConnection(); 
			 PreparedStatement pstmt=null;
			
			 try {				
				 String delete = "delete from seat where GAME_NUMBER=? and SEATNUMBER=?";				
				 pstmt = con.prepareStatement(delete);
			     pstmt.setInt(1, gameNum);
			     pstmt.setString(2, seatNum);
				 pstmt.executeUpdate();			 
			 } catch(SQLException e ) {			
				 System.out.println("connection failed! in del_seat");		        	 
			 } 
	     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
			
		 }	
	 
	 //이미 찬 좌석에 대한 db
	 public ArrayList<String> getSeatList(int Gamenumber) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 ArrayList<String> list = new ArrayList<>();
		
		 try {
			 String select = "select SEATNUMBER from seat where GAME_NUMBER=?";
				
			 pstmt = con.prepareStatement(select);
			 pstmt.setInt(1, Gamenumber);
			 rs = pstmt.executeQuery();
			 while (rs.next()) {					
				 String seatNumber = rs.getString(1);					
				 list.add(seatNumber);			
			 }		
		 } catch(SQLException e ) {			
				 System.out.println("connection failed! in getSeatList");		        	 			 
		 } 
	     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	     
	     return list;
		}
	 
	 //콤보박스 위한 db
	 public String[] Team_list() {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 
		 try {
			 String []add = new String[100];
			 String select = "select distinct TEAM_1 from game";     
			 pstmt = con.prepareStatement(select);
	         rs = pstmt.executeQuery();
	         int i =0;
	         while (rs.next()) {         
	        	 add[i]=rs.getString(1);
	             i++;	            
	         }	
	         return add;
		 }catch(SQLException e ) {			
			 System.out.println("connection failed! in Team_list");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	 
	     return null;
	 }
	 
	 public Table table_list(String Team_1) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 Table t = new Table();
		 
		 try {
			 String select = "SELECT TEAM_1, TEAM_2, STADIUM, DAYTIME FROM game WHERE TEAM_1 =?";       
			 pstmt = con.prepareStatement(select);
			 pstmt.setString(1, Team_1);
	         rs = pstmt.executeQuery();
			 
	         while (rs.next()) {
	                String gTeam1 = rs.getString(1);
	                String gTeam2 = rs.getString(2);
	                String gStadium = rs.getString(3);
	                String gDaytime = rs.getString(4);

	                t.insertTable(gTeam1, gTeam2, gStadium, gDaytime);
	            }
		 }catch(SQLException e ) {			
			 System.out.println("connection failed! in table_list");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	 
	     return t;
	 }
	 
	 //예매자와 에매한 내용 저장하는 
	 public void saveReservationTicket(String name, String id, String Team_1, String Team_2, String Stadium, String Daytime, int Person, int Cost,String SeatNumber) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 
		 try {
			 String insert = "insert into ticket values(?,?,?,?,?,?,?,?,?)";      
			 pstmt = con.prepareStatement(insert);
			 pstmt.setString(1, name);
			 pstmt.setString(2, id);
			 pstmt.setString(3, Team_1);
			 pstmt.setString(4, Team_2);
			 pstmt.setString(5, Stadium);
			 pstmt.setString(6, Daytime);
			 pstmt.setInt(7, Person);
			 pstmt.setInt(8, Cost);
			 pstmt.setString(9, SeatNumber);
	         
			 
		 }catch(SQLException e) {			
			 System.out.println("connection failed! in saveReseavationTicket");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	 }
	 
	 //예매 내역 불러오는 -> ReservationCheck에서 사용
	 public CheckUp loadReservationCheck(String id) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 CheckUp cu = new CheckUp();
		 
		 try {
			 String select = "SELECT TEAM_1, TEAM_2, STADIUM, DAYTIME, SEATNUMBER FROM ticket WHERE CUSTOMERID=?"; //t1과 t2에 있는 팀에 맞는(where절 써야되는 거 같은데..) TEAM_1, TEAM_2, STADIUM, DAYTIME을 가져와야 할 듯      
			 pstmt = con.prepareStatement(select);
			 pstmt.setString(1, id);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	                String Team_1 = rs.getString(1);
	                String Team_2 = rs.getString(2);
	                String Stadium = rs.getString(3);
	                String Daytime = rs.getString(4);
	                String SeatNum = rs.getString(5);
	     
	                cu.insertData(Team_1, Team_2, Stadium, Daytime, SeatNum);
	            }
			 
		 }catch(SQLException e) {			
			 System.out.println("connection failed! in loadReseavationTicket");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	     
	     return cu;
	 }
	 
	//예매 내역 불러오는 -> Receipt에서 사용
	 public CheckUp loadTicket(String id) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 //CheckUp cu = new CheckUp();
		 
		 try {
			 String select = "SELECT CUSTOMERNAME, TEAM_1, TEAM_2, STADIUM, DAYTIME, PERSON, COST ,SEATNUMBER FROM ticket WHERE CUSTOMERID=?";   
			 pstmt = con.prepareStatement(select);
			 pstmt.setString(1, id);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	        	 String name = rs.getString(1);
	        	 String Team_1 = rs.getString(3);
	             String Team_2 = rs.getString(4);
	             String Stadium = rs.getString(5);
	             String Daytime = rs.getString(6);
	             int person = rs.getInt(7);
	             int cost = rs.getInt(8);
	             String SeatNum = rs.getString(9);
	     
	             //cu.insertData(Team_1, Team_2, Stadium, Daytime, SeatNum);
	            }
			 
		 }catch(SQLException e) {			
			 System.out.println("connection failed! in loadTicket");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	     
	     return null;
	 }
	 
	 //Administrator_customer에서 사용
	 public CustomerInfo loadCustomer() {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 CustomerInfo ci = new CustomerInfo();
		 
		 try {
			 String select = "SELECT NAME, ID, EMAIL, PHONENUMBER FROM customer";   
			 pstmt = con.prepareStatement(select);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	                String name = rs.getString(1);
	                String id = rs.getString(2);
	                String email = rs.getString(3);
	                String number = rs.getString(4);
	   
	                ci.insertCustomer(name,id,email,number);
	            }
			 
		 }catch(SQLException e) {			
			 System.out.println("connection failed! in loadCustomer");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	     
	     return ci;
	 }
	 
	 //Administrator_game에서 사용
	 public GameInfo selectAll() {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		 ResultSet rs = null;
		 GameInfo gi = new GameInfo();
		 
		 try {
			 String select = "SELECT * FROM game";   
			 pstmt = con.prepareStatement(select);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	                String team1 = rs.getString(1);
	                String team2 = rs.getString(2);
	                String stadium = rs.getString(3);
	                String daytime = rs.getString(4);
	                int gameNum = rs.getInt(5);
	   
	                gi.insertGame(team1,team2,stadium,daytime,gameNum);
	            }
			 
		 }catch(SQLException e) {			
			 System.out.println("connection failed! in seletAll");		        
		 } 
		 if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};
	     
	     return gi;
	 }
	 
	 //Administrator_game에서 add할 때
	 public void insert(String team1, String team2, String stadium, String daytime, int gameNum) {
		 Connection con = makeConnection();
		 PreparedStatement pstmt=null;
		
		 try {			
			 System.out.println(team1);
             System.out.println(team2);
             System.out.println(stadium);
             System.out.println(daytime);
             System.out.println(gameNum);
			 String insert = "insert into game values(?,?,?,?,?)";				
			 pstmt = con.prepareStatement(insert);
		     pstmt.setString(1, team1);
			 pstmt.setString(2, team2);
		     pstmt.setString(3, stadium);
		     pstmt.setString(4, daytime);
		     pstmt.setInt(5, gameNum);
			 pstmt.executeUpdate();			 
		 } catch(SQLException e ) {			
			 System.out.println("connection failed! in insert");		        	 
		 } 
     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
     if(con != null) try{ con.close();} catch(SQLException e){};	
	 }
	 
	//Administrator_game에서 delete할 때
		 public void delete(String team1, String team2, String stadium, String daytime, int gameNum) {
			 Connection con = makeConnection();
			 PreparedStatement pstmt=null;
			
			 try {	
				 System.out.println(team1);
	             System.out.println(team2);
	             System.out.println(stadium);
	             System.out.println(daytime);
	             System.out.println(gameNum);
				 String delete = "delete from game where TEAM_1=? and TEAM_2=? and STADIUM=? and DAYTIME=? and GAME_NUMBER=?";				
				 pstmt = con.prepareStatement(delete);
			     pstmt.setString(1, team1);
				 pstmt.setString(2, team2);
			     pstmt.setString(3, stadium);
			     pstmt.setString(4, daytime);
			     pstmt.setInt(5, gameNum);
				 pstmt.executeUpdate();			 
			 } catch(SQLException e ) {			
				 System.out.println("connection failed! in delete");		        	 
			 } 
	     if(pstmt != null) try{ pstmt.close();} catch(SQLException e){}; //마지막에 pstmt와 con까지 끊어줌
	     if(con != null) try{ con.close();} catch(SQLException e){};	
		 }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con = makeConnection();	
		PreparedStatement pstmt=null;
	}

}
