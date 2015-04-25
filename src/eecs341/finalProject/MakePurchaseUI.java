package eecs341.finalProject;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MakePurchaseUI {
	
	private JFrame frame;
	private DefaultListModel<String> itemListModel;

	public MakePurchaseUI() {
		itemListModel = new DefaultListModel<String>();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	public MakePurchaseUI(DefaultListModel<String> listModel) {
		itemListModel = listModel;
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
		JList<String> itemList = new JList<String>(itemListModel);
		JTextField item = new JTextField();
		JTextArea itemlabel = new JTextArea("Item ID: ");
		JButton addItem = new JButton("Add Item");
		JButton removeItem = new JButton("Remove Item");
		JButton addPrescription = new JButton("Prescription");
		JButton back = new JButton("Back");
		JButton checkout = new JButton("Checkout...");
		
		itemlabel.setBounds(200, 10, 90, 20);
		item.setBounds(200, 40, 90, 20);
		addItem.setBounds(200, 70, 90, 20);
		removeItem.setBounds(200, 100, 90, 20);
		itemList.setBounds(10, 10, 180, 300);
		addPrescription.setBounds(105, 320, 90, 50);
		back.setBounds(10, 320, 90, 50);
		checkout.setBounds(200, 320, 90, 50);
		
		itemList.setFont(new Font("monospaced", Font.PLAIN, 12));
		itemList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		itemlabel.setEditable(false);
		
		frame.add(item);
		frame.add(itemlabel);
		frame.add(addItem);
		frame.add(removeItem);
		frame.add(itemList);
		frame.add(addPrescription);
		frame.add(back);
		frame.add(checkout);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// NEEDS TO PULL FROM DATABASE
				itemListModel.addElement(String.format("%5s  %10s  $%.2f",item.getText(), "Item Name", 0.00));
				item.setText("");
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemListModel.remove(itemList.getSelectedIndex());
			}
		});
		
		addPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PrescriptionUI(itemListModel);
				frame.dispose();
			}
		});
		
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new CheckoutUI(itemListModel);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new DatabaseUI();
			}
		});
	}
	
	public static void main(String[] args) {
		new MakePurchaseUI();
	}

}
