package eecs341.finalProject;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CheckoutUI {
	
	private JFrame frame;
	private DefaultListModel<String> listModel;
	private String memberID;

	public CheckoutUI(DefaultListModel<String> listModel) {
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
		frame.setTitle("Make Purchase");
		JList<String> itemList = new JList<String>(listModel);
		JTextField member = new JTextField();
		JTextArea memberLabel = new JTextArea("Member ID: ");
		JButton addMember = new JButton("Submit");
		JButton createMember = new JButton("Create JMJ");
		JButton back = new JButton("Back");
		JButton checkout = new JButton("Checkout");
		
		memberLabel.setBounds(200, 10, 90, 20);
		member.setBounds(200, 40, 90, 20);
		addMember.setBounds(200, 70, 90, 20);
		createMember.setBounds(200, 100, 90, 20);
		itemList.setBounds(10, 10, 180, 300);
		back.setBounds(10, 320, 90, 50);
		checkout.setBounds(200, 320, 90, 50);
		
		itemList.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		memberLabel.setEditable(false);
		
		frame.add(member);
		frame.add(memberLabel);
		frame.add(addMember);
		frame.add(createMember);
		frame.add(itemList);
		frame.add(back);
		frame.add(checkout);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		addMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberID = member.getText();
			}
		});
		
		createMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Go to CreateMember page
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MakePurchaseUI(listModel);
			}
		});
	}
	
	public static void main(String[] args) {
		new CheckoutUI(new DefaultListModel<String>());
	}

}
