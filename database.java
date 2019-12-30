import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

public class database {
	public static Connection myConn;
	public static LinkedList<String> ListOfUsers = new LinkedList<String>();
	//public static LinkedList<email> ListOfEmails = new LinkedList<email>();
	private static User currentUser;
	private String nextUserInTable;
	//Gets connection to a database with the name email
	final static String url = "jdbc:mysql://:3306/email?useSSL=false";
	private static PreparedStatement delete, stmt, update;
	private static ResultSet rs;
	//WHAT DO YOU THINK OF THE FOLLOWING IDEA GUYS! I THINK IT MIGHT HELP SPEED UP
	//MAY CONSIDER STORING THE database.connect() IN A VARAIBLE AND THEN CLOSING THAT VARIABLE
	//DOING database.Connect().close MIGHT CONNECT AGAIN (2nd time) FOR NO REASON JUST TO CLOSE IT
	
	public static Connection Connect(){
		/*all mySQL url for java require the format jdbc:mysql://
		followed by the host(IP assigned to database):port(3306)/database name*/
		
		try {
				
			//connect to database
			myConn = DriverManager.getConnection(url, "root", "root");
			return myConn;
		}
		catch (SQLException ex) {
			throw new RuntimeException("Couldn't connect to database");
		}

	}
	//just to test if I connected
	public static LinkedList<String> getAllUser() {
		String sql = "SELECT * FROM email.users";
		try {
			stmt = database.Connect().prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				//Adds the users to a list, just so we can see all the users(for testing)
				ListOfUsers.add(rs.getString("username") + rs.getString("password"));;
			}
			return ListOfUsers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				database.Connect().close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void insertUser(User s) {
		try {
			String insert = "Insert into email.users " +
					"VALUES(?,?)";
			PreparedStatement stmt = database.Connect().prepareStatement(insert);
			
			//filling in question marks
			stmt.setString(1, s.getUserName());
			stmt.setString(2, s.getPassword());
			
			//int numberUpdated =stmt.executeUpdate();
			stmt.executeUpdate();
			
			
			//System.out.print("User entered into DB");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				database.Connect().close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void fillBox(User u, DefaultTableModel model_box, String locationCHAR) {
//		Homepage.clearBox(model_box);
		/*model_box can be model_inbox, model_sent, model_draft
		 * I for model_inbox
		 * S for model_sent 
		 * D for model_draft
		*/
		//System.out.print(u.getUserName());
		//Dan's Temporary Class Method of handling the client side display
		if (locationCHAR.equals("I")) {
			String sql = "SELECT * FROM email.`" + u.getUserName() + "`" +
					" WHERE location = '" + locationCHAR +
					"' ORDER BY emailNum ASC";
			String fromEmail, subject, timestamp, body;        //*******KARL********
			int primeKey;
			
			try {
				stmt = database.Connect().prepareStatement(sql);
				rs = stmt.executeQuery();
				
				while(rs.next()){
					//Retrieve the 4 values in the current row of the table, then move to the next row
					fromEmail = rs.getString("From");
					subject = rs.getString("subject");
					timestamp = rs.getString("time");
					primeKey = rs.getInt("emailNum");
					body = rs.getString("body");                //*******KARL********
					
					//System.out.println("\n HOMEPAGE.ADD_ROW BEING RUN Inbox"+fromEmail);
					Homepage.add_row(model_box, fromEmail, subject, timestamp, primeKey, body); //*******KARL********
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					database.Connect().close();
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		else if (locationCHAR.equals("S") || locationCHAR.equals("D")){
			String sql = "SELECT * FROM email.`" + u.getUserName() + "`" +
					" WHERE location = '" + locationCHAR +
					"' ORDER BY emailNum ASC";
			String toEmail, subject, timestamp, body;                 //******KARL**********
			int primeKey;
			
			try {
				stmt = database.Connect().prepareStatement(sql);
				rs = stmt.executeQuery();
				
				while(rs.next()){
					//Retrieve the 4 values in the current row of the table, then move to the next row
					toEmail = rs.getString("to");
					subject = rs.getString("subject");
					timestamp = rs.getString("time");
					primeKey = rs.getInt("emailNum");
					body = rs.getString("body");	                  //******KARL**********
					
					//System.out.println("\n HOMEPAGE.ADD_ROW BEING RUN notinbox"+toEmail);
					Homepage.add_row(model_box, toEmail , subject, timestamp, primeKey, body); //****KARL****
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					database.Connect().close();
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		/*for (int i = 0 ; i < 10 ; i++ ) {
			
			if (locationCHAR.equals("I")) {
				//Homepage.add_row(model_box, "fromEmail"+i+"@cq.edu", "subject"+i, "timestamp"+i, i);
			}
			else if (locationCHAR.equals("S") || locationCHAR.equals("D")){	
				//Homepage.add_row(model_box, "toEmail"+i+"@cq.edu" , "subject"+i, "timestamp"+i, i);
			}		
		}*///End of For Loop
		//System.out.println("Emails uploaded from: "+locationCHAR);

	}//End of fillBox
	
	public static void deleteEmail(int pk, String from){
		String del = "DELETE FROM email.`" + from + "`" +
				" WHERE emailNum = " + pk;
	try {
		delete = database.Connect().prepareStatement(del);
		delete.executeUpdate();
		
	} catch (SQLException ex) {
		ex.printStackTrace();
	}
	finally {
		try {
			database.Connect().close();
			delete.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		//System.out.println("EMAIL DELETED: "+pk);
	}
	
	public static void addEmail(String to, String from, String subject, String body, String location, String needTimeStamp) {
		
		//System.out.println("EmailTo:"+to);
		//System.out.println("EmailFrom:"+from);
		//System.out.println("EmailSubject:"+subject);
		//System.out.println("EmailBody:"+body);
		//System.out.println("EmailLocation:"+location);
		
		try {
			String insert = "Insert into email." +"`" + from +
							"`" + " (`from`, `to`, subject, body, location, timestamp, time)" + 
					"VALUES(?,?,?,?,?,?, CURRENT_TIME)";                             //******KARL**** add current time
			stmt = database.Connect().prepareStatement(insert);
			
			//filling in question marks
			stmt.setString(1, from);
			stmt.setString(2, to);
			stmt.setString(3, subject);
			stmt.setString(4, body);
			stmt.setString(5, location);
			stmt.setString(6, needTimeStamp);
			
			//execute statement
			stmt.executeUpdate();
			
			
			
			//System.out.print("Message added to DB");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				database.Connect().close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}//No PK Parameter, then it is a new email and it will need one for the DB
	
	
	public static void addEmail(int pk, String to, String from, String subject, String body, String location, String needTimeStamp) {
		
		//Update email with primary key = pk with the following....
		
		//System.out.println("EmailTo:"+to);
		//System.out.println("EmailFrom:"+from);
		//System.out.println("EmailSubject:"+subject);
		//System.out.println("EmailBody:"+body);
		//System.out.println("EmailLocation:"+location);
		
		String up = "UPDATE email.`" + from + "`" +
					" SET `From` = '" + from + "'," +
					   " `to` = '" + to + "'," +
					   " subject = '" + subject + "'," +
					   " body = '" + body + "'," +
					   " location = '" + location + "'," +
					   " timestamp = '" + needTimeStamp + "'," +
					   " time = CURRENT_TIMESTAMP" + 
				" WHERE emailNum = " + pk;
	try {
		update = database.Connect().prepareStatement(up);
		update.executeUpdate();
		
		
	} catch (SQLException ex) {
		ex.printStackTrace();
	}
	finally {
		try {
			database.Connect().close();
			update.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
		
		
	}//PK is provided, update the email
	
	//INSERTS THE RECEIEVED MESSAGE INTO THE DB OF RECEIVER
	public static void addEmailToSent(String to, String from, String subject, String body, String location, String needTimeStamp) {
		try {
			String insert = "Insert into email." +"`" + to +
							"`" + " (`from`, `to`, subject, body, location, timestamp, time)" + 
					"VALUES(?,?,?,?,?,?, CURRENT_TIMESTAMP)";
			stmt = database.Connect().prepareStatement(insert);
			
			//filling in question marks
			stmt.setString(1, from);
			stmt.setString(2, to);
			stmt.setString(3, subject);
			stmt.setString(4, body);
			stmt.setString(5, location);
			stmt.setString(6, needTimeStamp);
			
			//execute statement
			stmt.executeUpdate();
			
			
			
			//System.out.print("Message added to DB");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				database.Connect().close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
	public static String getFrom(int pk, String TableName) {
		String getFrom = "SELECT * FROM email.`" + TableName + "`" +
				" WHERE emailNum =" + pk;
		String from;		
		try {
			stmt = database.Connect().prepareStatement(getFrom);
			rs = stmt.executeQuery();
				
			from = rs.getString("From");
			
			database.Connect().close();
			stmt.close();
			rs.close();
			
			return from;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
		
	}
	
	public static String getTo(int pk, String TableName) {
		String getTo = "SELECT * FROM email.`" + TableName + "`" +
				" WHERE emailNum = " + pk;
		String to;		
		try {
			stmt = database.Connect().prepareStatement(getTo);
			rs = stmt.executeQuery();
			
			to = rs.getString("to");
			
			database.Connect().close();
			stmt.close();
			rs.close();
			
			return to;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getSubject(int pk, String TableName) {
		String getSub = "SELECT * FROM email.`" + TableName + "`" +
				" WHERE emailNum = " + pk;
		String sub;		
		try {
			stmt = database.Connect().prepareStatement(getSub);
			rs = stmt.executeQuery();
			
		    sub = rs.getString("subject");
			
			database.Connect().close();
			stmt.close();
			rs.close();
			
			return sub;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDateTime(int pk, String TableName) {
		String getTime = "SELECT * FROM email.`" + TableName + "`" +
				" WHERE emailNum = " + pk;
		String DateTime;		
		try {
			stmt = database.Connect().prepareStatement(getTime);
			rs = stmt.executeQuery();
			
			//if(rs.next())
			DateTime = rs.getString("time");
			
			database.Connect().close();
			stmt.close();
			rs.close();
			
			return DateTime;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}



	