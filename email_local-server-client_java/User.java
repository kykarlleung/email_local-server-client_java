import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
public class User {
	
	private String username;
	private String password;
	
	
	public User(String s, String p){
	    setUsername(s);
	    setPassword(p);
	}//Constructor for inputs from login/register GUI

	//default constructor
	public User() {
		
	}

	public String getUserName() {
		return username;
	}

	public void setUsername(String u) {
		this.username = u;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		this.password = p;
	}
	
	public User getUserFromDB(String s) {
		try {
			//for error checking
			System.out.println("Checking for user in DB");
			
			//MUST include '' marks in the where clause because the 
			//username column is of type string, so we have to tell 
			//mySQL to look for rows with that matching string
			String get = "SELECT * FROM email.users"
					+ " WHERE username = " + "'" + s + "'";
			PreparedStatement stmt = database.Connect().prepareStatement(get);
			//WORKS UP TO HERE
			
			//error occurs here
			ResultSet rs = stmt.executeQuery();
			
			//if there is another row in the table
			if(rs.next() != false) {
				User user = new User();
				
				//set this users username and password to what is in the DB
				user.username = rs.getString("username");
				user.password = rs.getString("password");
				
				//always close connection when done
				database.Connect().close();
				stmt.close();
				return user;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		//equivalent to sql NULL value for non existent users
		return null;
	}
	
	
}