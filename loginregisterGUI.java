import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JLabel;

	public class loginregisterGUI{

//VARIABLES
	//SCREEN VARIABLES
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x0 = (int)(screenSize.getWidth()/2);	//Center X coordinate of screen 
		int y0 = (int)(screenSize.getHeight()/2); //Center Y coordinate of screen
		Vector<User> usersV = new Vector<User>();

	//GENERAL VARIABLES	
		JFrame lrWindow = new JFrame();

		private JPanel userPane; 
		private JLabel usernameLabel;
		private JLabel atSymbol;
		private static JTextField usernameTextField;
		private String[] domainNames = {"cq.edu","yg.com","lnb.gov"};
		private static JComboBox<String> dnList;
		//userPane used to contain usernameLabel, usernameTextField, and dnList
				
		private JPanel passPane;
		private JLabel passwordLabel;
		private static JPasswordField passwordField;
		//passPane used to contain the passwordLabel and the passwordField
		
		private JPanel buttonPane;
		static JButton loginButton;
		static JButton registerButton;
		//buttonPane used to contain the loginButton and the registerButton

//GUI CONSTRUCTOR
		public loginregisterGUI() {
	
	//SET UP WINDOW TITLE
			lrWindow.setTitle("Login/Register GUI");
			
	//SET UP JFRAME PANES
			lrWindow.setLayout(new BorderLayout());
			
		//LOGIN/USERNAME PANE	
			userPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 10));
			//just increased the sizes of the labels, textfields and buttons
			
			usernameLabel = new JLabel("Username: ");
			usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			usernameTextField = new JTextField(10);
			usernameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 40));

			atSymbol = new JLabel("@");
			atSymbol.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			dnList = new JComboBox<String>(domainNames);
			dnList.setFont(new Font("Times New Roman", Font.PLAIN, 40));

			userPane.add(usernameLabel);
			userPane.add(usernameTextField);
			userPane.add(atSymbol);
			userPane.add(dnList);
			lrWindow.add(userPane, BorderLayout.NORTH);
			/*Initialize the userPane, usernameLabel, and the usernameTextField
			 * FlowLayout.CENTER puts the userPane in the center with a horizontal and vertical gap of 5 and 10 pixels
			 * Add the usernameLabel, usernameTextField, and the drop list into the userPane
			 * Finally, add the userPane into the GUI's pane in NORTH
			*/
			
		//PASSWORD PANE
			passPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

			passwordLabel = new JLabel("Password: ");
			passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			passwordField = new JPasswordField(10);
			passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			passPane.add(passwordLabel);
			passPane.add(passwordField);
			lrWindow.add(passPane, BorderLayout.CENTER);
			/*Initialize the passPane, passwordLabel, and the passwordField
 			 * FlowLayout.CENTER puts the passPane in the center with a horizontal and vertical gap of 5 and 5 pixels
			 * Add the passwordLabel and passwordField into the passPane
			 * Finally, add the passPane into the GUI's pane in CENTER
			*/
  
		//BUTTON PANE	
			buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
			loginButton = new JButton("Log in");
			registerButton = new JButton("Register");
			
			loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
			
			buttonPane.add(loginButton);
			buttonPane.add(registerButton);
			lrWindow.add(buttonPane, BorderLayout.SOUTH);
			/*Initialize the buttonPane, loginButton, and the registerButton
 			 * FlowLayout.CENTER puts the buttonPane in the center with a horizontal and vertical gap of 5 and 10 pixels
			 * Add the loginButton and registerButton into the buttonPane
			 * Finally, add the buttonPane into the GUI's pane in SOUTH
			*/
			
		//LOGIN HANDLER
			loginregHandler handler = new loginregHandler(lrWindow);
			loginButton.addActionListener(handler);
			registerButton.addActionListener(handler);
			usernameTextField.addActionListener(handler);
			passwordField.addActionListener(handler);
			//Initialize the loginregHandler to handle the buttons the textfields in the GUI

			lrWindow.pack();//Sets to most optimal size with no white space
			lrWindow.setMinimumSize(new Dimension(lrWindow.getWidth(),lrWindow.getHeight()));
			lrWindow.setSize(lrWindow.getWidth()+20 , lrWindow.getHeight()+20);
			lrWindow.setLocation(x0-lrWindow.getWidth()/2,y0-lrWindow.getHeight()/2);		
			lrWindow.setVisible(true);
			lrWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}//End of loginregisterGUI constructor
		
		
//METHODS
		public static JTextField getUsernameTextField() {
			return usernameTextField;
		}

		public static void setUsernameTextField(JTextField usernameTextField) {
			loginregisterGUI.usernameTextField = usernameTextField;
		}
		
		public static JComboBox<String> getDnList() {
			return dnList;
		}

		public static JPasswordField getPasswordField() {
			return passwordField;
		}

		public void setPasswordField(JPasswordField passwordField) {
			loginregisterGUI.passwordField = passwordField;
		}//Thoughts on this one guys? Would this method act as a back door for password theft?
		
		public Vector<User> getUsersV() {
			return usersV;
		}
}