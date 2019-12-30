import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

class loginregHandler implements ActionListener{
	
	private JFrame theGUI;
	

	public loginregHandler(JFrame g) {
		this.theGUI = g;
	}

	public void actionPerformed(ActionEvent event) {
		
//VARIABLES
		String userS = loginregisterGUI.getUsernameTextField().getText().toLowerCase(); //User Names are not case sensitive
		char[] passC = loginregisterGUI.getPasswordField().getPassword();
		String passS = new String(passC);
		String domainS = loginregisterGUI.getDnList().getSelectedItem().toString();
		
		String emailS = userS + "@" + domainS; //combine user name and domain name
		
		User thisUser = new User(emailS, passS);
		//Create User object
		
		stringChecks sc = new stringChecks();
		
		/* Whenever an action is performed,
		 * the text inputs from the usernameTextField and passwordField 
		 * are taken from the loginregisterGUI
		 * and stored in String variables userS and passS
		 * (passC is converted to passS since passwordFields can only store arrays of Chars)
		*/
		
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,35))); 
		//Makes button larger in JOption

//INITIAL CHECK
		if (!sc.initialCheck(userS, passS, emailS))
			return;
		//First check if the userS and passS are in the right format

//LOGIN
		if (event.getSource()==loginregisterGUI.loginButton || event.getSource()==loginregisterGUI.getUsernameTextField()
				|| event.getSource()==loginregisterGUI.getPasswordField()) {
		//User has clicked the login Button or hit enter key inside the TextField or PasswordField

			System.out.println("User has clicked the loginButton or entered a username/password"); //More debugging messages
			
			if (sc.isValidPassword(thisUser)) {
				System.out.println("E-mail login and or password is valid");
				System.out.println("d) If ok, user logs in to account (for now can be blank GUI)");
				
				
				Homepage hp = new Homepage(thisUser);//Temporary Action for Successful Log In
				hp.setVisible(true);
				theGUI.dispose();
				
				//Create Karl's Homepage with all it's functions
			}//If the user name and password is valid, a new window is created and the login screen is gone
			
			else {
				
				System.out.println("Email login and or password is invalid");
				System.out.println("c) Test if user logs in using invalid account or password");
				sc.doMessage("The e-mail login or password is invalid");		
				return;
			}//If the user name and password combination is incorrect, tell the user
				
		}

//REGISTER
		else if (event.getSource()==loginregisterGUI.registerButton) {
		//User clicks on the register Button
			
			System.out.println("Register button is clicked");
			
			if (sc.isUserNameAvailable(thisUser)){
				
				System.out.println("b) Test if account already exists");
				System.out.println("Username is available");

				database.insertUser(thisUser);
				//Add user to server.txt and vector of Users
				
				//CREATE A TABLE TO STORE THE EMAILS OF THE CURRENT USER, AKA EMAIL TABLE FOR USER
				String s = "CREATE TABLE IF NOT EXISTS " + "`" + 
						    emailS + "`" +
							" (emailNum int(11) NOT NULL AUTO_INCREMENT, " +
							" `From` varchar(30) NOT NULL, " +
							" `to` varchar(100) NOT NULL, " +
							" subject varchar(100) DEFAULT NULL, " +
							" body varchar(1000) DEFAULT NULL, " +
							" location varchar(5) NOT NULL, " +
							" timestamp varchar(20) DEFAULT NULL, " +
							" time TIMESTAMP," + 							//******KARL**** add a Time and Date variable
							" PRIMARY KEY (emailNum))";                          
				try {
					PreparedStatement stmt = database.Connect().prepareStatement(s);
					stmt.executeUpdate();
					stmt.close();//have to use this for data modification queries
				    } catch (SQLException e) {
				    	System.out.println("Error creating Table");
						e.printStackTrace();
					}			
								
				
				System.out.println("a) Create new account");
				
				sc.doMessage("The account with the e-mail login: "+emailS+" has just been created!");
			}//Tell user that account has been made
			
			else {
				System.out.println("b) Test if account already exists");
				System.out.println("Username is unavailable");
				
				sc.doMessage("The e-mail login " + emailS + " is already in use.");
				return;
			}//Tell user that user name is not available
			
		}//If the user clicked the log in button 
		
	}//End of actionPerformed
}