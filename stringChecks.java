import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class stringChecks {

	public stringChecks() {
	}

	//INITAL CHECK METHODS
	public boolean initialCheck(String passS, String emailS) {
		return initialCheck(emailS, passS, emailS);
	}//Takes in 2 parameters if there is no user name text field to be checked
	
	public boolean initialCheck(String userS, String passS, String emailS) {
		if(userS.equals("")) {
			System.out.println("E-mail login is empty");
			doMessage("Oops! Looks like you forgot to enter your e-mail!");
			return false;
		}//If the password wasn't entered, tell the user
			
		else if (emailS.length()>20) {
			System.out.println("E-mail length is too long: " + emailS.length());
			doMessage("Username cannot exceed 20 characters.");
			return false;
		}//If the user name is too long, tell the user
		
		else if(!isValidEmailFormat(emailS)) {
		System.out.println("Username is in bad format");
			doMessage("Invalid e0mail");
			return false;
		}//If the user name is poorly formatted, tell the user
			
		else if(passS.equals("")) {
			System.out.println("PasswordField is empty");
			doMessage("Oops! Looks like you forgot to enter a password!");
			return false;
		}//If the password wasn't entered, tell the user
		
		else if(!isValidPasswordFormat(passS)) {
			System.out.println("PasswordField is invalid. Password length: " + passS.length());
			doMessage("Passwords must be between 4 and 12 characters and cannot contain any spaces.");
			return false;
		}//If the password is too short, tell the user		
		
		return true; //return true if all fields are valid
	}
		
	public boolean isValidEmailFormat(String emailS) {

		Pattern p = Pattern.compile("^([a-zA-Z0-9._%+-]+?@cq\\.edu)$|^([a-zA-Z0-9._%+-]+?@yg\\.com)$|^([a-zA-Z0-9._%+-]+?@lnb\\.gov)$" , Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		//This pattern accepts periods, underscores, percentages, addition, and subtraction symbols for username
		
		//Pattern p = Pattern.compile("^([a-zA-Z0-9._%+-]+?@cq\\.edu)$|^([a-zA-Z0-9._%+-]+?@yg\\.com)$|^([a-zA-Z0-9._%+-]+?@lnb\\.gov)$" , Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		//This pattern only accepts the alphabet and digits
		
		/*The REGEX Pattern
		 * 	^([a-zA-Z0-9._%+-]+?@cq\\.edu)$ matches for domain names cq.edu
		 * 	^([a-zA-Z0-9._%+-]+?@yg\\.com)$ matches for domain names yg.com
		 * 	^([a-zA-Z0-9._%+-]+?@lnb\\.gov)$ matches for domain names lnb.gov
		 * */
		Matcher matcher = p.matcher(emailS);
        return matcher.find();
        //Returns true if it finds that the String emailS matches 1 of the 3 patterns.
		}
		
		public boolean isValidPasswordFormat(String passS) {

			Pattern p = Pattern.compile("^[^\\s]{4,12}$" , Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			/*The REGEX Pattern
			 * 	^[^\\s]{4,12}$ allows any string between 4-12 characters but no white space
			 * 	[^\\s] no white space
			 *	{4,12} String length must be 4 to 12
			 * */
			Matcher matcher = p.matcher(passS);
	        return matcher.find();
	        //Returns true if it finds that the String passS matches 1 of the 3 patterns.
		}
		
	//REGISTER METHODS

		public boolean isValidPassword(User u) {
			//if the user is in the DB, check if the password matches with the one in DB
			//if it does return true, else false
			if(u.getUserFromDB(u.getUserName())!= null) {
				if(u.getPassword().equals(u.getUserFromDB(u.getUserName()).getPassword()))
						return true;		
			}
			return false;
		}//Checks if the user name and password match
		
		public boolean isUserNameAvailable(User u) {
			if(database.getAllUser().isEmpty()) return true;
			
			System.out.print(database.getAllUser().size());
			
			//if the database returns null, that username wasn't found
			//else its already taken
			if(u.getUserFromDB(u.getUserName())==null)
				return true;
			else
				return false;
		}//Checks if String user name is in the database/vector of users
		
		//GENERAL PURPOSE METHODS
		public void doMessage(String s) {
			JLabel message = new JLabel(s);
			message.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			JOptionPane.showMessageDialog(null, message);
		}
		
		public int doMessage(String x, String y) {
			JLabel message = new JLabel(x);
			message.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			return JOptionPane.showConfirmDialog(null, message, y, JOptionPane.YES_NO_OPTION);
		}
		
}