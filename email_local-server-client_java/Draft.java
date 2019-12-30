import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Draft {

	private JFrame Draftf;
	private JTextField tf_from;
	private JTextField tf_subject;
	private JTextArea ta_body;
	private int pk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Draft window = new Draft();
					window.Draftf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Draft() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Draftf = new JFrame();
		Draftf.getContentPane().setEnabled(false);
		Draftf.setTitle("Draft");
		Draftf.setBounds(100, 100, 1080, 801);
		//Inboxf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Draftf.getContentPane().setLayout(null);
		Draftf.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 60, 1026, 638);
		Draftf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_to = new JLabel("To");
		lbl_to.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_to.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_to.setBounds(88, 22, 56, 31);
		panel.add(lbl_to);
		
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
		
		JButton btnReply = new JButton("Send");
		btnReply.setBounds(898, 13, 125, 36);
		Draftf.getContentPane().add(btnReply);
		btnReply.setForeground(new Color(34, 139, 34));
		btnReply.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JButton btn_back = new JButton("BACK");
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 17));
		btn_back.setBounds(53, 705, 125, 36);
		Draftf.getContentPane().add(btn_back);
		
		
		JScrollPane sp_body = new JScrollPane();
		sp_body.setBounds(173, 121, 808, 478);
		panel.add(sp_body);
		
		ta_body = new JTextArea();
		ta_body.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ta_body.setEditable(false);
		sp_body.setViewportView(ta_body);
	}
	
	
	//------Method--------
	
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
			Draftf.setVisible(true);
		if (b==false)
			Draftf.setVisible(false);
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}
}