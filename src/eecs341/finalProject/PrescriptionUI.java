package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PrescriptionUI {
	private JFrame frame;
	private MakePurchaseUI parent;

	public PrescriptionUI(MakePurchaseUI parent) {
		this.parent = parent;
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
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		JTextField itemID = new JTextField();
		JTextArea prescriberNameLabel = new JTextArea("Prescriber Name:");
		JTextField prescriberName = new JTextField();
		JTextArea amountLabel = new JTextArea("Amount:");
		JTextField amount = new JTextField();
		JTextArea unitLabel = new JTextArea("Unit:");
		JTextField unit = new JTextField();
		JTextArea frequencyLabel = new JTextArea("Frequency:");
		JTextField frequency = new JTextField();
		JButton add = new JButton("Add");

		itemIDLabel.setBounds(20, 20, 120, 20);
		itemID.setBounds(160, 20, 120, 20);
		prescriberNameLabel.setBounds(20, 60, 120, 20);
		prescriberName.setBounds(160, 60, 120, 20);
		amountLabel.setBounds(20, 100, 120, 20);
		amount.setBounds(160, 100, 120, 20);
		unitLabel.setBounds(20, 140, 120, 20);
		unit.setBounds(160, 140, 120, 20);
		frequencyLabel.setBounds(20, 180, 120, 20);
		frequency.setBounds(160, 180, 120, 20);
		add.setBounds(105, 300, 90, 50);


		itemIDLabel.setEditable(false);
		prescriberNameLabel.setEditable(false);
		amountLabel.setEditable(false);
		unitLabel.setEditable(false);
		frequencyLabel.setEditable(false);
		
		frame.add(itemIDLabel);
		frame.add(itemID);
		frame.add(prescriberNameLabel);
		frame.add(prescriberName);
		frame.add(amountLabel);
		frame.add(amount);
		frame.add(unitLabel);
		frame.add(unit);
		frame.add(frequencyLabel);
		frame.add(frequency);
		frame.add(add);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: SQL ADD THE PRESCRIPTION(?)
				frame.dispose();
				//parent.addPerscription(perscriptionID);
			}
		});
	}
}
