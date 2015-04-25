package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MakeMemberUI {
	private JFrame frame;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();

	public MakeMemberUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	public MakeMemberUI(DefaultListModel<String> listModel) {
		this.listModel = listModel;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Prescription");
		JTextArea nameLabel = new JTextArea("Name:");
		JTextField name = new JTextField();
		JTextArea addressLabel = new JTextArea("Address:");
		JTextField address = new JTextField();
		JTextArea phoneLabel = new JTextArea("Phone:");
		JTextField phone = new JTextField();
		JButton add = new JButton("Add");

		
		nameLabel.setBounds(20, 20, 120, 20);
		name.setBounds(160, 20, 120, 20);
		addressLabel.setBounds(20, 60, 120, 20);
		address.setBounds(160, 60, 120, 20);
		phoneLabel.setBounds(20, 100, 120, 20);
		phone.setBounds(160, 100, 120, 20);
		add.setBounds(105, 300, 90, 50);


		nameLabel.setEditable(false);
		addressLabel.setEditable(false);
		phoneLabel.setEditable(false);
		
		frame.add(nameLabel);
		frame.add(name);
		frame.add(addressLabel);
		frame.add(address);
		frame.add(phoneLabel);
		frame.add(phone);
		frame.add(add);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: SQL ADD THE MEMBER - CREATE A MEMBER ID
				frame.dispose();
				if (listModel.isEmpty()) {
					new DatabaseUI();
				} else {
					CheckoutUI checkout = new CheckoutUI(listModel);
					checkout.setMemberID("MEMBERID");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new MakeMemberUI();
	}
}
