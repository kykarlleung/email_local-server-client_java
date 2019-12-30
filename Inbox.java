import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Inbox {

	private JFrame Inboxf;
	private JTextField tf_from;
	private JTextField tf_subject;
	private JTextArea ta_body;
	private Object[] row = new Object[3];
	private DefaultTableModel model_sent;
	private DefaultTableModel model_draft;
	private static User userObj;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inbox window = new Inbox(userObj);
					window.Inboxf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inbox(User userObj) {
		setUserObj(userObj);
		initialize(userObj);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User u) {
		Inboxf = new JFrame();
		Inboxf.getContentPane().setEnabled(false);
		Inboxf.setTitle(u.getUserName()+"'s Received Message");
		Inboxf.setBounds(100, 100, 1080, 801);
		//Inboxf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Inboxf.getContentPane().setLayout(null);
		Inboxf.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 60, 1026, 638);
		Inboxf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_from = new JLabel("From");
		lbl_from.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_from.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_from.setBounds(88, 22, 56, 31);
		panel.add(lbl_from);
		
		JLabel lbl_subject = new JLabel("Subject");
		lbl_subject.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_subject.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_subject.setBounds(51, 64, 93, 29);
		panel.add(lbl_subject);
		
		tf_from = new JTextField();
		tf_from.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_from.setEditable(false);
		tf_from.setBounds(173, 22, 808, 31);
		panel.add(tf_from);
		tf_from.setColumns(10);
		
		tf_subject = new JTextField();
		tf_subject.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_subject.setEditable(false);
		tf_subject.setColumns(10);
		tf_subject.setBounds(173, 67, 808, 31);
		panel.add(tf_subject);
		
		JLabel lbl_body = new JLabel("Body");
		lbl_body.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_body.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_body.setBounds(51, 121, 93, 29);
		panel.add(lbl_body);
		
		JButton btn_reply = new JButton("Reply");
		btn_reply.setBounds(869, 13, 125, 36);
		Inboxf.getContentPane().add(btn_reply);
		btn_reply.setForeground(new Color(34, 139, 34));
		btn_reply.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JButton btn_back = new JButton("BACK");
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_back.setBounds(53, 705, 125, 36);
		Inboxf.getContentPane().add(btn_back);
		
		
		JScrollPane sp_body = new JScrollPane();
		sp_body.setBounds(173, 121, 808, 478);
		panel.add(sp_body);
		
		ta_body = new JTextArea();
		ta_body.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ta_body.setEditable(false);
		sp_body.setViewportView(ta_body);
		
		JButton btn_forward = new JButton("Forward");
		btn_forward.setForeground(new Color(34, 139, 34));
		btn_forward.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_forward.setBounds(720, 13, 125, 36);
		Inboxf.getContentPane().add(btn_forward);
		

		
		
		//---------------HANDLER-------------------
		
		btn_reply.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Compose cp = new Compose(userObj, userObj.getUserName()+"'s Reply");
				cp.setMail(tf_from.getText());
				cp.setSubject("RE: " + tf_subject.getText());
				cp.setBody(ta_body.getText());
				cp.setDraftModel(model_draft);
				cp.setSentModel(model_sent);
				cp.setVisible(true);
				Inboxf.dispose();
			}
		});//Click Reply, pass on Username
		
		btn_forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Compose cp = new Compose(userObj, userObj.getUserName()+"'s Forward");
				cp.setSubject("FW: " + tf_subject.getText());
				cp.setBody(ta_body.getText());
				cp.setDraftModel(model_draft);
				cp.setSentModel(model_sent);
				cp.setVisible(true);
				Inboxf.dispose();
			}
		});//Click Forward, pass on Username
		
		btn_back.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Inboxf.dispose();
			}
		});
		
	}
	
	
	
	//------Method--------
    public void add_row(DefaultTableModel m, Object EA, Object S, Object DT)
    {
        row[0] = EA;
        row[1] = S;
        row[2] = DT;
    	m.insertRow(0, row);;
    }
    
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
		tf_from.setText(s0);
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
			Inboxf.setVisible(true);
		if (b==false)
			Inboxf.setVisible(false);
	}

	public static User getUserObj() {
		return userObj;
	}

	public static void setUserObj(User userObj) {
		Inbox.userObj = userObj;
	}


}