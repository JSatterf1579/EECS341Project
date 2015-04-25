package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddItemUI {
	private JFrame frame;
	
	private String[] itemTypes = {"None", "Home Item", "Medicine", "Food"};
	private JComboBox<String> dropDown = new JComboBox<String>(itemTypes);
	
	public AddItemUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Item");
		
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		JTextField itemID = new JTextField();
		JTextArea nameLabel = new JTextArea("Name:");
		JTextField name = new JTextField();
		JTextArea supplierIDLabel = new JTextArea("Supplier ID:");
		JTextField supplierID = new JTextField();
		JTextArea priceLabel = new JTextArea("Price:");
		JTextField price = new JTextField();

		JButton back = new JButton("Back");
		JButton add = new JButton("Add");

		
		dropDown.setBounds(50, 20, 200, 20);
		itemIDLabel.setBounds(20, 60, 120, 20);
		itemID.setBounds(160, 60, 120, 20);
		nameLabel.setBounds(20, 100, 120, 20);
		name.setBounds(160, 100, 120, 20);
		supplierIDLabel.setBounds(20, 140, 120, 20);
		supplierID.setBounds(160, 140, 120, 20);
		priceLabel.setBounds(20, 180, 120, 20);
		price.setBounds(160, 180, 120, 20);
		add.setBounds(200, 330, 90, 40);
		back.setBounds(10, 330, 90, 40);
		
		itemIDLabel.setEditable(false);
		nameLabel.setEditable(false);
		supplierIDLabel.setEditable(false);
		priceLabel.setEditable(false);
		
		frame.add(dropDown);
		frame.add(itemIDLabel);
		frame.add(itemID);
		frame.add(nameLabel);
		frame.add(name);
		frame.add(supplierIDLabel);
		frame.add(supplierID);
		frame.add(priceLabel);
		frame.add(price);
		frame.add(add);
		frame.add(back);

		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		dropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch ((String)dropDown.getSelectedItem()) {
				case "Home Item":
					homeItemInput();
					break;
				case "Medicine":
					medicineInput();
					break;
				case "Book": // For some reason I thought we had books. Do we have books?
					bookInput();
					break;
				case "Food":
					foodInput();
					break;
				default:
					break;
				}
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: SQL ADD ITEM TO DATABASE
				frame.dispose();
			}
		});
	}
	
	private void homeItemInput() {
		frame.setVisible(false);
		frame.remove(dropDown);
		
		JTextField itemType = new JTextField("Home Item");
		JTextArea brandLabel = new JTextArea("Brand:");
		JTextField brand = new JTextField();
		
		itemType.setBounds(50, 20, 200, 20);
		brandLabel.setBounds(20, 220, 120, 20);
		brand.setBounds(160, 220, 120, 20);
		
		itemType.setEditable(false);
		itemType.setHorizontalAlignment(JTextField.CENTER);
		itemType.setBorder(null);
		brandLabel.setEditable(false);
		
		frame.add(itemType);
		frame.add(brandLabel);
		frame.add(brand);
		frame.setVisible(true);
	}
	
	private void medicineInput() {
		frame.setVisible(false);
		frame.remove(dropDown);
		
		JTextField itemType = new JTextField("Medicine");
		JTextArea scientificNameLabel = new JTextArea("Scientific Name:");
		JTextField scientificName = new JTextField();
		JTextArea manufacturerLabel = new JTextArea("Manufacturer:");
		JTextField manufacturer = new JTextField();
		JTextArea strengthLabel = new JTextArea("Strength:");
		JTextField strength = new JTextField();
		
		itemType.setBounds(50, 20, 200, 20);
		scientificNameLabel.setBounds(20, 220, 120, 20);
		scientificName.setBounds(160, 220, 120, 20);
		manufacturerLabel.setBounds(20, 260, 120, 20);
		manufacturer.setBounds(160, 260, 120, 20);
		strengthLabel.setBounds(20, 300, 120, 20);
		strength.setBounds(160, 300, 120, 20);
		
		itemType.setEditable(false);
		scientificNameLabel.setEditable(false);
		manufacturerLabel.setEditable(false);
		strengthLabel.setEditable(false);
		itemType.setHorizontalAlignment(JTextField.CENTER);
		itemType.setBorder(null);
		
		frame.add(itemType);
		frame.add(scientificNameLabel);
		frame.add(scientificName);
		frame.add(manufacturerLabel);
		frame.add(manufacturer);
		frame.add(strengthLabel);
		frame.add(strength);
		
		frame.setVisible(true);
	}
	
	private void bookInput() {
		
	}
	
	private void foodInput() {
		frame.setVisible(false);
		frame.remove(dropDown);
		
		JTextField itemType = new JTextField("Food");
		JTextArea brandLabel = new JTextArea("Brand:");
		JTextField brand = new JTextField();
		JTextArea expirationLabel = new JTextArea("Expiration Date:");
		JTextField expiration =  new JTextField();
		
		itemType.setBounds(50, 20, 200, 20);
		brandLabel.setBounds(20, 220, 120, 20);
		brand.setBounds(160, 220, 120, 20);
		expirationLabel.setBounds(20, 260, 120, 20);
		expiration.setBounds(160, 260, 120, 20);
		
		itemType.setEditable(false);
		itemType.setHorizontalAlignment(JTextField.CENTER);
		itemType.setBorder(null);
		brandLabel.setEditable(false);
		expirationLabel.setEditable(false);
		
		frame.add(itemType);
		frame.add(brandLabel);
		frame.add(brand);
		frame.add(expirationLabel);
		frame.add(expiration);
		frame.setVisible(true);
	}
}
