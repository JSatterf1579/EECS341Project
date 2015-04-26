package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class AddItemUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	
	private String[] itemTypes = {"None", "Home Item", "Medicine", "Food"};
	private JComboBox<String> dropDown = new JComboBox<String>(itemTypes);
	private ArrayList<JComponent> temporaryUIElements = new ArrayList<>();
	
	// Common fields
	JTextField itemIDField = null;
	JTextField nameField = null;
	JTextField supplierIDField = null;
	JTextField priceField = null;
	
	// Home Item fields
	JTextField brandField = null;
	
	// Medicine fields
	JTextField scientificNameField = null;
	JTextField manufacturerField = null;
	JTextField strengthField = null;
	
	// Food fields
	// duplicate brandField;
	JTextField expirationField = null;
	
	public AddItemUI(JFrame parent, SQLConnection db) {
		this.parent = parent;
		this.db = db;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Item");
		
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		itemIDField = new JTextField();
		JTextArea nameLabel = new JTextArea("Name:");
		nameField = new JTextField();
		JTextArea supplierIDLabel = new JTextArea("Supplier ID:");
		supplierIDField = new JTextField();
		JTextArea priceLabel = new JTextArea("Price:");
		priceField = new JTextField();

		JButton back = new JButton("Back");
		JButton add = new JButton("Add");

		
		dropDown.setBounds(50, 20, 200, 20);
		itemIDLabel.setBounds(20, 60, 120, 20);
		itemIDField.setBounds(160, 60, 120, 20);
		nameLabel.setBounds(20, 100, 120, 20);
		nameField.setBounds(160, 100, 120, 20);
		supplierIDLabel.setBounds(20, 140, 120, 20);
		supplierIDField.setBounds(160, 140, 120, 20);
		priceLabel.setBounds(20, 180, 120, 20);
		priceField.setBounds(160, 180, 120, 20);
		add.setBounds(200, 330, 90, 40);
		back.setBounds(10, 330, 90, 40);
		
		itemIDLabel.setEditable(false);
		nameLabel.setEditable(false);
		supplierIDLabel.setEditable(false);
		priceLabel.setEditable(false);
		
		frame.add(dropDown);
		frame.add(itemIDLabel);
		frame.add(itemIDField);
		frame.add(nameLabel);
		frame.add(nameField);
		frame.add(supplierIDLabel);
		frame.add(supplierIDField);
		frame.add(priceLabel);
		frame.add(priceField);
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
		JTextArea brandLabel = new JTextArea("Brand:");
		brandField = new JTextField();
		
		brandLabel.setBounds(20, 220, 120, 20);
		brandField.setBounds(160, 220, 120, 20);
		
		brandLabel.setEditable(false);
		
		clearTemporaryUI();
		
		frame.add(brandLabel);
		frame.add(brandField);
		
		frame.revalidate();
		frame.repaint();
		
		temporaryUIElements.add(brandLabel);
		temporaryUIElements.add(brandField);
	}
	
	private void medicineInput() {
		JTextArea scientificNameLabel = new JTextArea("Scientific Name:");
		scientificNameField = new JTextField();
		JTextArea manufacturerLabel = new JTextArea("Manufacturer:");
		manufacturerField = new JTextField();
		JTextArea strengthLabel = new JTextArea("Strength:");
		strengthField = new JTextField();
		
		scientificNameLabel.setBounds(20, 220, 120, 20);
		scientificNameField.setBounds(160, 220, 120, 20);
		manufacturerLabel.setBounds(20, 260, 120, 20);
		manufacturerField.setBounds(160, 260, 120, 20);
		strengthLabel.setBounds(20, 300, 120, 20);
		strengthField.setBounds(160, 300, 120, 20);
		
		scientificNameLabel.setEditable(false);
		manufacturerLabel.setEditable(false);
		strengthLabel.setEditable(false);

		clearTemporaryUI();
		
		frame.add(scientificNameLabel);
		frame.add(scientificNameField);
		frame.add(manufacturerLabel);
		frame.add(manufacturerField);
		frame.add(strengthLabel);
		frame.add(strengthField);

		frame.revalidate();
		frame.repaint();
		
		temporaryUIElements.add(scientificNameLabel);
		temporaryUIElements.add(scientificNameField);
		temporaryUIElements.add(manufacturerLabel);
		temporaryUIElements.add(manufacturerField);
		temporaryUIElements.add(strengthLabel);
		temporaryUIElements.add(strengthField);
	}
	
	private void foodInput() {
		JTextArea brandLabel = new JTextArea("Brand:");
		brandField = new JTextField();
		JTextArea expirationLabel = new JTextArea("Expiration Date:");
		expirationField =  new JTextField();
		
		brandLabel.setBounds(20, 220, 120, 20);
		brandField.setBounds(160, 220, 120, 20);
		expirationLabel.setBounds(20, 260, 120, 20);
		expirationField.setBounds(160, 260, 120, 20);
		
		brandLabel.setEditable(false);
		expirationLabel.setEditable(false);
		
		clearTemporaryUI();
		
		frame.add(brandLabel);
		frame.add(brandField);
		frame.add(expirationLabel);
		frame.add(expirationField);
		
		frame.revalidate();
		frame.repaint();
		
		temporaryUIElements.add(brandLabel);
		temporaryUIElements.add(brandField);
		temporaryUIElements.add(expirationLabel);
		temporaryUIElements.add(expirationField);
	}
	
	private void clearTemporaryUI() {
		for (JComponent UIElement : temporaryUIElements) {
			frame.remove(UIElement);
		}
		temporaryUIElements = new ArrayList<JComponent>();
	}
}
