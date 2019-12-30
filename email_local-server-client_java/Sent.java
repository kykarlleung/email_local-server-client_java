import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Sent {

	private JFrame Sentf;
	private JTextField tf_to;
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
					Sent window = new Sent(userObj);
					window.Sentf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sent(User userObj) {
		setUserObj(userObj);
		initialize(userObj.getUserName());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String s) {
		Sentf = new JFrame();
		Sentf.setTitle(s+"'s Sent Message");
		Sentf.setBounds(100, 100, 1080, 801);
		//Sentf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Sentf.getContentPane().setLayout(null);
		Sentf.setResizable(false);
		
		JButton btn_back = new JButton("Back");
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_back.setBounds(73, 702, 125, 36);
		Sentf.getContentPane().add(btn_back);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 52, 993, 665);
		Sentf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_to = new JLabel("Sent To");
		lbl_to.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_to.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_to.setBounds(82, 22, 62, 29);
		panel.add(lbl_to);
		
		JLabel lbl_subject = new JLabel("Subject");
		lbl_subject.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_subject.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_subject.setBounds(51, 64, 93, 29);
		panel.add(lbl_subject);
		
		JLabel lblBody = new JLabel("Body");
		lblBody.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBody.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBody.setBounds(51, 117, 93, 29);
		panel.add(lblBody);
		
		tf_to = new JTextField();
		tf_to.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_to.setEditable(false);
		tf_to.setBounds(173, 22, 808, 31);
		panel.add(tf_to);
		tf_to.setColumns(10);
		
		tf_subject = new JTextField();
		tf_subject.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tf_subject.setEditable(false);
		tf_subject.setColumns(10);
		tf_subject.setBounds(173, 67, 808, 31);
		panel.add(tf_subject);
		
		JScrollPane sp_body = new JScrollPane();
		sp_body.setBounds(173, 121, 808, 478);
		panel.add(sp_body);
		
		ta_body = new JTextArea();
		ta_body.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ta_body.setEditable(false);
		sp_body.setViewportView(ta_body);
		
		JButton btn_forward = new JButton("Forward");
		btn_forward.setBounds(898, 13, 125, 36);
		Sentf.getContentPane().add(btn_forward);
		btn_forward.setForeground(new Color(34, 139, 34));
		btn_forward.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		
		
		//-------------------Move to HANDLER-------------------------------
		btn_forward.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Compose cp = new Compose(userObj, userObj.getUserName() + "'s Forward");
				cp.setSubject("FW: " + tf_subject.getText());
				cp.setBody(ta_body.getText());
				cp.setVisible(true);
				cp.setDraftModel(model_draft);
				cp.setSentModel(model_sent);
				Sentf.dispose();
			}
		});
		
		btn_back.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Sentf.dispose();
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
			Sentf.setVisible(true);
		if (b==false)
			Sentf.setVisible(false);
	}

	public static User getUserObj() {
		return userObj;
	}

	public static void setUserObj(User userObj) {
		Sent.userObj = userObj;
	}


}