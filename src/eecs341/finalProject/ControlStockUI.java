package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ControlStockUI {
	
	private JFrame frame;

	public ControlStockUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Control Stock");
		JButton button1 = new JButton("Add item");
		JButton button2 = new JButton("Lookup item");
		JButton button3 = new JButton("Back");
		
		button1.setBounds(50, 75, 200, 50);
		button2.setBounds(50, 150, 200, 50);
		button3.setBounds(50, 225, 200, 50);
		
		frame.add(button1);
		frame.add(button2);
		frame.add(button3);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new DatabaseUI();
			}
		});
	}
	
	public static void main(String[] args) {
		new ControlStockUI();
	}

}
