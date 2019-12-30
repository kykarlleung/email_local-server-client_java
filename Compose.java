import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Compose {

	private JFrame Composef;
	private JTextField tf_to;
	private JTextField tf_subject;
	private JTextArea ta_body;
	private Object[] row = new Object[3];
	private int pk = 0;
	private boolean check = false;
	//private static int origPk = 0; //original pk is to see if that email already exist, 
	//if that primary key exist(its been put into the table once before) thus
	//its pk will have a value thats not 0, thus its already in the table,
	//otherwise we'll use the origPk value which will be 0 thus signifying that instance of 
	//compose doesn't exist
	private boolean newEmail;
	private DefaultTableModel model_sent;
	private DefaultTableModel model_draft;
	private static User userObj;
	private static String user;
	
	//Made these global so we can always close them with the finally block
	public PreparedStatement stmt;
	public ResultSet rs;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Compose window = new Compose(userObj);
					window.Composef.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public Compose(User u) {
		setUserObj(u);
		newEmail = true;
		initialize(u.getUserName()+"'s Draft");
	}//Composing a new email, saving this email will result in creating a new primary key
	
	public Compose(User u, String s) {
		setUserObj(u);
		newEmail = true;
		initialize(s);
	}//Composition as FWD or RE, saving this email will result in creating a new primary key
	
	/**
	 * Initialize the contents of the frame.
	 * @param s 
	 */
	private void initialize(String s) {	
		Composef = new JFrame();
		Composef.setTitle(s); 			//Set a title for the compose frame
		Composef.setBounds(100, 100, 1080, 801);
		//Composef.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Composef.getContentPane().setLayout(null);
		Composef.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 60, 1026, 638);
		Composef.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("To");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(88, 22, 56, 31);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Subject");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(51, 64, 93, 29);
		panel.add(lblNewLabel_1);
		
		tf_to = new JTextField();
		tf_to.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_to.setBounds(173, 22, 808, 31);
		panel.add(tf_to);
		tf_to.setColumns(10);
		
		tf_subject = new JTextField();
		tf_subject.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_subject.setColumns(10);
		tf_subject.setBounds(173, 67, 808, 31);
		panel.add(tf_subject);
		
		JLabel lblBody = new JLabel("Body");
		lblBody.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBody.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBody.setBounds(51, 121, 93, 29);
		panel.add(lblBody);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds(889, 13, 125, 36);
		Composef.getContentPane().add(btnSend);
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JButton btn_back = new JButton("BACK");
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_back.setBounds(53, 705, 125, 36);
		Composef.getContentPane().add(btn_back);
		
		JScrollPane sp_body = new JScrollPane();
		sp_body.setBounds(173, 121, 808, 478);
		panel.add(sp_body);
		
		ta_body = new JTextArea();
		ta_body.setFont(new Font("Tahoma", Font.PLAIN, 25));
		sp_body.setViewportView(ta_body);

//---------------------------------move to handler-------------------------------------
		Composef.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	handleCloseOperations();
		    }
		});//Captures Window Exit Button and asks user if he or she wants to save before exit
		
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCloseOperations();
			}
		});//Back button has same function as Window Exit Button
				
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				stringChecks sc = new stringChecks();
						
				/*if(tf_to.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please Enter receiver's email address");
					//no need to confirm if the email is exist, but have to confirm the domain name are @cq.edu/@yq.com/@ 
				}
				else*/
				String recipientS = tf_to.getText();
				String[] recipientsA = recipientS.split(",");
				
				int i = 0;
				while(i < recipientsA.length){
					
					//System.out.println(recipientsA[i]);
					
					try {
						if(sc.initialCheck("password", recipientsA[i]) && sc.isValidEmailFormat(recipientsA[i]))
						{
							if(pk == 0) {
								
								//ADDS THE MESSAGE TO SENT (AKA IN SENT TAB) FOR THE USER WHO SENT IT
								
								//1
								System.out.println("1 EMAIL SENT OUT BY SENDER: NEW EMAIL AND KEY ADDED TO SENDERS SENT BOX");
								database.addEmail(recipientsA[i], userObj.getUserName(), tf_subject.getText(),
										ta_body.getText(), "S", "true");
								
								//THE PERSON WHO GOT THE MESSAGE SENT TO HIM HAS TO GET THAT MESSAGE IN HIS INBOX (I)
								//2
								System.out.println("2 EMAIL RECEIVED BY RECEIVER: NEW EMAIL AND KEY ADDED TO RECEIEVERS INBOX");
								database.addEmailToSent(recipientsA[i], userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "I", "true");
								
								//retrieve the primary key from the entered email
								String getPK = "SELECT * FROM email." + "`" + userObj.getUserName() + "`" +
								" ORDER BY emailNum desc";
								
								try {
									stmt = database.Connect().prepareStatement(getPK);
									
									rs = stmt.executeQuery();
									
									if(rs.next())
										pk = rs.getInt("emailNum");
								}
								catch(Exception ex) {
									sc.doMessage("An error has occured in attempting to send the email.");
								}
								finally{
									try {
										database.Connect().close();
										stmt.close();
										rs.close();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
							else {
								//UPDATE USERS EMAIL TO BE SENT
								//3
								System.out.println("3 EMAIL SENT OUT BY SENDER: DRAFT EMAIL AND OLD KEY ADDED TO SENDERS SENT BOX");
								database.addEmail(pk, recipientsA[i], userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "S", "true");
								
								//THE PERSON WHO GOT THE MESSAGE SENT TO HIM HAS TO GET THAT MESSAGE IN HIS INBOX (I)
								//4
								System.out.println("4 EMAIL RECEIVED BY RECEIEVER: DRAFT EMAIL AND NEW KEY ADDED TO RECEIVERS INBOX");
								database.addEmailToSent(recipientsA[i], userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "I", "true");
							}
			
							sc.doMessage("Message Sent");
							Composef.dispose();
						}
					}
					catch (Exception ex) {
						sc.doMessage("An error has occured in attempting to send the email.");
					}
				pk = 0; //PK RESET AFTER EACH EMAIL SENT AS SUBSEQUENT RECIPIENTS WOULD RECEIVE COPIES OF THE ORIGINAL EMAILS. THOSE COPIES WOULD REQUIRE A NEW PRIMARY KEY.
				i++;	//Grab next email in toEmail Text Field
				}//End of While
					
			}//If the initial check and email format of the Forwarding Email is valid, Send the Email
		});
	}//End of Initialize
	



	//------Method--------		
	public void setSentModel(DefaultTableModel ms)
	{
		model_sent = ms;
	}
				
	public void setDraftModel(DefaultTableModel md)
	{
		model_draft = md;
	}
			
	public void setMail(String s0)
	{
		tf_to.setText(s0);
	}
			
	public void setSubject(String s1)
	{
		tf_subject.setText(s1);
	}
				
	public void setBody(String s2)
	{
		ta_body.setText(s2);
	}
			
	public void setVisible(boolean b) {
		if (b==true)
			Composef.setVisible(true);
		if (b==false)
			Composef.setVisible(false);
	}

	public int getPk() {
		return pk;
	}


	public void setPk(int pk) {
		newEmail=false;
		this.pk = pk;
	}


	public static User getUserObj() {
		return userObj;
	}


	public static void setUserObj(User userObj) {
		Compose.userObj = userObj;
	}


	public static String getUser() {
		return user;
	}


	public static void setUser(String user) {
		Compose.user = user;
	}
	
	public void handleCloseOperations() {

		stringChecks sc = new stringChecks();
		int dialogButton = sc.doMessage("Save as Draft","Confirm Close");
		
		if (dialogButton == 0) 
	    {
		    try //maybe need try and catch for communicating with DATABASE
			{
		    	
		    	
		    	/*@JASON 
		    	 * Create or Update email with the following parameters:
		    	 * if (newEmail) pk = create new pk
		    	 * else {};//pk stays the same
		    	 * to = tf_to.getText()
		    	 * subject = tf_subject.getText()
		    	 * body = ta_body.getText()
		    	 * location = D
		    	 * timeStamp = n/a as it hasn't been sent out
		    	*/
		    	//System.out.println("Checking for email in DB");
				
				String get = "SELECT * FROM email." + "`" + userObj.getUserName() + "`";
				
				stmt = database.Connect().prepareStatement(get);
			
				rs = stmt.executeQuery();
				
				//if the table is empty, its a new email(aka if there's not a first element its empty)
				//Might be unnecessary because if the table is empty then the compose will have pk=0 so
				//its a new email 
				//(WHAT DO YOU GUYS THINK)
				if(rs.first() == false) {
					database.addEmail(tf_to.getText(), userObj.getUserName(), tf_subject.getText(),
							ta_body.getText(), "D", "false");
					//retrieve the primary key from the entered email
					String getPK = "SELECT * FROM email." + "`" + userObj.getUserName() + "`";
					
					try {
						stmt = database.Connect().prepareStatement(getPK);
						
						rs = stmt.executeQuery();
						
						pk = rs.getInt("emailNum");
					}
					
					catch(Exception ex) {
						ex.printStackTrace();
					    
						sc.doMessage("An error has occured with saving the draft to the database.");
				    }
					finally { //this is to guarantee that we always close all the connections, even if
				    	//an exception occurs, saves resources and data for us

						try {
							database.Connect().close();
							stmt.close();
							rs.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				    }
		
				}
				
				//IF THAT INSTANCE OF COMPOSE DOES NOT HAVE A PRIMARY KEY ASSIGNED TO IT THEN JUST ENTER IT
				//OTHERWISE UPDATE BECAUSE IT HAS A PK thats not 0;
				else if(pk == 0) {
					
					database.addEmail(tf_to.getText(), userObj.getUserName(), tf_subject.getText(),
							ta_body.getText(), "D", "false");
					
					//retrieve the primary key from the entered email
					String getPK = "SELECT * FROM email." + "`" + userObj.getUserName() + "`" +
					" ORDER BY emailNum desc";
					
					try {	
						stmt = database.Connect().prepareStatement(getPK);
						
						rs = stmt.executeQuery();
						
						if(rs.next())
							pk = rs.getInt("emailNum");
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					    
						sc.doMessage("An error has occured with saving the draft to the database.");
				    }
					finally { //this is to guarantee that we always close all the connections, even if
				    	//an exception occurs, saves resources and data for us

						try {
							database.Connect().close();
							stmt.close();
							rs.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				    }
				}
				
				else { //if the the primary key isnt zero that means its already in the table, thus its an update call
					
					database.addEmail(pk, tf_to.getText(), userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "D", "false");
				}
		    	//if (newEmail) database.addEmail(tf_to.getText(), userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "D", false);
		    	//THIS ELSE IS FOR UPDATING
		    	//else database.addEmail(pk, tf_to.getText(), userObj.getUserName(), tf_subject.getText(), ta_body.getText(), "D", false);
		    	
		    	//add_row(model_draft, tf_to.getText(), tf_subject.getText(), ta_body.getText()); //This line may not be necessary depending on how we implement concurrency
		    	//Homepage.updateAllBoxes();
		    	//Once the email is saved into the DB under Draft, we'll just call an update to the Homepage
		    	
		    	sc.doMessage("Message Saved in Draft");
		    		
		    	Composef.dispose();

			}
		    catch(Exception ex) 
			{
				ex.printStackTrace();
				try {
					database.Connect().close();
					stmt.close();
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sc.doMessage("An error has occured with saving the draft to the database.");
		    }
	
		    
		}
	    else if (dialogButton == 1) 
	    {
	    	
	    	sc.doMessage("Message Not saved");
	    	Composef.dispose();
	    }
	    else
	    {
	    	//Do nothing
	    }		
	}
	
}//End of Class Compose