import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;



public class Homepage {
			
	private JFrame Homepagef;
	private int pk;
	private String s0, s1, s2, body;
	private Object timeStamp;
	
	//----Move to Handler?----
	private static DefaultTableModel model_inbox;
	private static DefaultTableModel model_sent;
	private static DefaultTableModel model_draft;

		//DefaultTableModels hold the row's string data of emails
    private static Object[] row = new Object[5];   //**KARL**
    private JTable table_inbox;
    private JTable table_sent;
    private JTable table_draft;

    private static User userObj;

    //------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage window = new Homepage(userObj);
					window.Homepagef.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Homepage(User u) {
		setUserObj(u);
		initialize(u.getUserName());
	}

	/**
	 * Create the application without the User parameter for GUI debugging.
	 */ /*
	public Homepage() {
		initialize("Best E-mail");
	}
*/
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String username) {
		Homepagef = new JFrame();
		Homepagef.setTitle("Welcome "+username);
		Homepagef.setBounds(100, 100, 1137, 766);
		Homepagef.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLayeredPane panel = new JLayeredPane();
		panel.setBounds(12, 186, 161, 520);
		panel.setLayout(null);
		
		JLabel lbl_inbox = new JLabel("Inbox");
		lbl_inbox.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_inbox.setFont(new Font("Traditional Arabic", Font.BOLD, 20));
		lbl_inbox.setBounds(0, 88, 149, 32);
		panel.add(lbl_inbox);
		
		JLabel lbl_sent = new JLabel("Sent");
		lbl_sent.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_sent.setFont(new Font("Traditional Arabic", Font.BOLD, 20));
		lbl_sent.setBounds(0, 133, 149, 32);
		panel.add(lbl_sent);
		
		JLabel lbl_draft = new JLabel("Draft");
		lbl_draft.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_draft.setFont(new Font("Traditional Arabic", Font.BOLD, 20));
		lbl_draft.setBounds(0, 178, 149, 32);
		panel.add(lbl_draft);
		
		JButton btn_compose = new JButton("Compose");
		btn_compose.setForeground(new Color(0, 100, 0));
		btn_compose.setFont(new Font("Tahoma", Font.BOLD, 25));
		btn_compose.setBackground(Color.WHITE);
		btn_compose.setBounds(0, 0, 161, 58);
		panel.add(btn_compose);
		
		JButton btn_delete = new JButton("Delete Message");
		btn_delete.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_delete.setBounds(840, 91, 250, 25);

//JTabbedPane		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(185, 91, 905, 615);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
  //table_inbox
		JScrollPane sp_inbox = new JScrollPane();
		sp_inbox.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Inbox", null, sp_inbox, null);

		table_inbox = new JTable();
		table_inbox.setFont(new Font("Tahoma", Font.PLAIN, 30));
		table_inbox.setUpdateSelectionOnSort(true);
		sp_inbox.setViewportView(table_inbox);
		tabbedPane.setEnabledAt(0, true);

  //table_sent
		JScrollPane sp_sent = new JScrollPane();
		sp_sent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Sent", null, sp_sent, null);
		
		table_sent = new JTable();
		table_sent.setFont(new Font("Tahoma", Font.PLAIN, 30));
		table_sent.setUpdateSelectionOnSort(true);
		sp_sent.setViewportView(table_sent);
		tabbedPane.setEnabledAt(1, true);
		
  //table_draft
		JScrollPane sp_draft = new JScrollPane();
		sp_draft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Draft", null, sp_draft, null);
		
		table_draft = new JTable();
		table_draft.setFont(new Font("Tahoma", Font.PLAIN, 30));
		table_draft.setUpdateSelectionOnSort(true);
		sp_draft.setViewportView(table_draft);
		tabbedPane.setEnabledAt(1, true);

		
//TESTING
	    ChangeListener changeListener = new ChangeListener() {
	        public void stateChanged(ChangeEvent changeEvent) {
	          JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
	          int index = sourceTabbedPane.getSelectedIndex();
	          if (sourceTabbedPane.getTitleAt(index).equals("Inbox")) {
	              updateBox(model_inbox, "I");	        	  
	          }
	          else if (sourceTabbedPane.getTitleAt(index).equals("Sent")) {
	              updateBox(model_sent, "S");
	          }
	          else if (sourceTabbedPane.getTitleAt(index).equals("Draft")) {
	              updateBox(model_draft, "D");
	          }
	          
	        }
	      };
	      tabbedPane.addChangeListener(changeListener);
//TESTING
		
        Object[] columns_from = {"Email Address (From)", "Subject", "Date/Time", "Primary Key", "Body"}; //****KARL*****
        Object[] columns_to = {"Email Address (To)", "Subject", "Date/Time", "Primary Key", "Body"}; //***KARL****
        //Make JTable non-editable
        model_inbox = new DefaultTableModel()
        {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
        			false, false, false, false, false //****KARL*****
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
    	};
        model_sent = new DefaultTableModel()
        {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
        			false, false, false, false, false //****KARL*****
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
    	};
        model_draft = new DefaultTableModel()
        {

			private static final long serialVersionUID = 7988234664678098875L;
			boolean[] columnEditables = new boolean[] {
        			false, false, false, false, false //****KARL*****
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
    	};
    	
    	table_inbox.setRowHeight(30);
    	table_sent.setRowHeight(30);
    	table_draft.setRowHeight(30);
   	
        model_inbox.setColumnIdentifiers(columns_from); 	//***KARL****
        model_sent.setColumnIdentifiers(columns_to);		//***KARL****
        model_draft.setColumnIdentifiers(columns_to);    	//***KARL****

        table_inbox.setModel(model_inbox);
        table_sent.setModel(model_sent);
        table_draft.setModel(model_draft);
        Homepagef.getContentPane().setLayout(null);
        Homepagef.getContentPane().add(panel);
        Homepagef.getContentPane().add(btn_delete);
        
        JButton btn_logout = new JButton("Logout");
        btn_logout.setBounds(954, 18, 140, 41);
        
        btn_logout.setFont(new Font("Tahoma", Font.ITALIC, 30));
        btn_logout.setForeground(Color.RED);
        Homepagef.getContentPane().add(btn_logout);
        		
        hideAllPrimaryKeyColumns();
        
//Logout + Logout confirm
   		btn_logout.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent e) 
        	{
   				stringChecks sc = new stringChecks();
        		int choice = sc.doMessage("Are you sure to Logout?","Confirm Logout");
        			
        		if (choice==0) // 0 is YES, 1 is NO
        		{
        		 	Homepagef.dispose();
        			loginregisterGUI newWindow = new loginregisterGUI();
        		}
        		else
        		{
        		//Chose "No" so nothing happen
        		}
        	}
       });
   		
//Add tabbedPane to Homepage JFrame
       Homepagef.getContentPane().add(tabbedPane);
        
//Images
        ImageIcon image1 = new ImageIcon (getClass().getResource("email_icon.png"));
        JLabel lbl_image1 = new JLabel(image1);
        lbl_image1.setBounds(12, 13, 161, 160);
        Homepagef.getContentPane().add(lbl_image1);
        
        ImageIcon image2 = new ImageIcon (getClass().getResource("background2.jpg"));
        JLabel lbl_image2 = new JLabel(image2);
        lbl_image2.setBounds(165, 13, 954, 160);
        Homepagef.getContentPane().add(lbl_image2);
		
/*Populate Rows for Every Email
 *  add_row(model_draft, "draftEA"+ i, "draftSub"+ i, "draftDT"+i);
 *  I can't find where Karl's tabs get uploaded so I'm going to dump the codes here to fill all the tables on load....
 * */   

        updateAllBoxes();
        
  /*KARL'S TEMPORARY STRINGs OF DISPLAY FOR TABLES      
        //Add Row example, so we can try DELETE BUTTON
        for(int i=1; i<=50; i++)
        {
        	add_row(model_inbox, "inbox EA"+ i, "inboxSub"+ i, "inboxDT"+i);
        	add_row(model_sent, "sentEA"+ i, "sentSub"+ i, "sentDT"+i);
        	add_row(model_draft, "draftEA"+ i, "draftSub"+ i, "draftDT"+i);
        	//here is just for testing
        	//get the Email address, Subject, Time from database and ADD to the table
        	//or is there any way we can directly drag in the database and convert into table and put into our GUI?
        }
	*/	
		
		//-------move to handler-----
        
		//Click Compose
		btn_compose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Compose cp = new Compose(userObj);
				cp.setDraftModel(model_draft);
				cp.setSentModel(model_sent);
				cp.setVisible(true);
			}
		});
		//New Email being Composed
		
		//Click INBOX label
		lbl_inbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//		        (model_inbox, "I");
				tabbedPane.setSelectedIndex(0);
			}

		});
		//Click SENT label
		lbl_sent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//		        updateBox(model_sent, "S");
		        tabbedPane.setSelectedIndex(1);
			}
		});
		
		//Click DRAFT label
		lbl_draft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//		        updateBox(model_draft, "D");
				tabbedPane.setSelectedIndex(2);
			}
		});
        
        
		//Double click action in Table
		table_inbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					Inbox ib = new Inbox(userObj);
					ib.setVisible(true);
					getTableData(table_inbox, true);
					ib.setMail(s0);
					ib.setSubject(s1);
					ib.setBody(body);  //******KARL**********
					ib.setDraftModel(model_draft);
					ib.setSentModel(model_sent);
				}
			}
		});
		
		table_sent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					Sent sn = new Sent(userObj);
					sn.setVisible(true);
					getTableData(table_sent, false);
					sn.setMail(s0);
					sn.setSubject(s1);
					sn.setBody(body);    //******KARL**********
					sn.setDraftModel(model_draft);
					sn.setSentModel(model_sent);
				}
			}
		});
		
		table_draft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					Compose cp = new Compose(userObj);
					cp.setVisible(true);
					getTableData(table_draft, false);
					cp.setMail(s0);
					cp.setSubject(s1);
					cp.setBody(body);    //******KARL**********
					cp.setDraftModel(model_draft);
					cp.setSentModel(model_sent);
					cp.setPk(pk);
					//Load Primary Key to Update Email
				}
			}
		});
		
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_inbox.isShowing() && table_inbox.getSelectedRow()>=0) {	//Bug: Without the && table_inbox.isShowing(), it is possible to select 1 item from each tab and delete up to 3 emails at once
					delete_row(table_inbox, model_inbox, "I");
				}
				if(table_sent.isShowing() && table_sent.getSelectedRow()>=0) {
					delete_row(table_sent, model_sent, "S");
				}
				if(table_draft.isShowing() && table_draft.getSelectedRow()>=0) {
					delete_row(table_draft, model_draft, "D");
				}
			}
		});
		
	}
		
	public static void updateAllBoxes() {
        updateBox(model_inbox, "I");
        updateBox(model_sent, "S");
        updateBox(model_draft, "D");		
	}

	private void hideAllPrimaryKeyColumns() {
        TableColumnModel tcm = table_inbox.getColumnModel();
        tcm.removeColumn( tcm.getColumn(4) ); //****KARL*****
        tcm.removeColumn( tcm.getColumn(3) );
        
        tcm = table_sent.getColumnModel();
        tcm.removeColumn( tcm.getColumn(4) ); //****KARL*****
        tcm.removeColumn( tcm.getColumn(3) );
        
        tcm = table_draft.getColumnModel();
        tcm.removeColumn( tcm.getColumn(4) ); //****KARL*****
        tcm.removeColumn( tcm.getColumn(3) );
        
	}//Hides the values of the primary keys from user but the GUI can still access the pk's and feed them to DB

	public static void updateBox(DefaultTableModel model_box, String locationCHAR) {
		clearBox(model_box);
		database.fillBox(userObj, model_box, locationCHAR);
	}

	public static void clearBox(DefaultTableModel model_box) {
		if (model_box.getRowCount() > 0) {
		    for (int i = model_box.getRowCount() - 1; i > -1; i--) {
		    	model_box.removeRow(i);
		    }
		}		
	}

	//----Methods----
	//Add Row
    public static void add_row(DefaultTableModel m, Object EA, Object S, Object DT, int PK, Object body)   //****KARL****
    {
        row[0] = EA;
        row[1] = S;
        row[2] = DT;
        row[3] = PK;
        row[4] = body;               //****KARL****
    	m.insertRow(0, row);
    }
    

    //Delete Row
    void delete_row(JTable t, DefaultTableModel m, String locationCHAR)
    {
        int i = t.getSelectedRow();
        stringChecks sc = new stringChecks();
        
        if(i >= 0)
        {
			int choice = sc.doMessage("Are you sure you want to delete this email?","Confirm Delete");
			if (choice==0) {
				
				//Update s0, s1, s2 with Selected Table's Data of the Selected Row
				if (locationCHAR.equals("I"))getTableData(t,true);	//If we're updating inbox, update table with "from email"
				else getTableData(t,false);	//Else, update table with "to email"
				
				System.out.println(s0); //Email Sender
				System.out.println(s1);	//Email Subject
				System.out.println(s2); //Date Time
				System.out.println(pk); //Primary Key
								
				//m.removeRow(i);
				database.deleteEmail(pk, userObj.getUserName());
				updateBox(m, locationCHAR);
			}
			else
			{
			}
        }
        else{
        	sc.doMessage("Delete Error");
        }
    }
    
    
    void update_row()
    {
    	//For update row, maybe useful in the future
    }
    
    //not using for now, for passing user email address into this class
    
	public void setVisible(boolean b) {
		if (b==true)
			Homepagef.setVisible(true);
		if (b==false)
			Homepagef.setVisible(false);
	}
	
	public void getTableData(JTable t, Boolean isInbox)
	{
		DefaultTableModel model = (DefaultTableModel)t.getModel();
		int selectedRowIndex = t.getSelectedRow();
		
		/*Ignore This
		pk = Integer.parseInt( model.getValueAt(selectedRowIndex, 3).toString() );
		
		if (isInbox) {
			s0 = database.getFrom(pk);
		}
		else {
			s0=database.getTo(pk);
		}
		s1 = database.getSubject(pk);
		s2 = database.getDateTime(pk);
		*/
		
		s0 = model.getValueAt(selectedRowIndex, 0).toString();
		s1 = model.getValueAt(selectedRowIndex, 1).toString();
		s2 = model.getValueAt(selectedRowIndex, 2).toString();
		pk = Integer.parseInt( model.getValueAt(selectedRowIndex, 3).toString() );
		body = model.getValueAt(selectedRowIndex, 4).toString();
		
	}

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User u) {
		Homepage.userObj = u;
	}
}