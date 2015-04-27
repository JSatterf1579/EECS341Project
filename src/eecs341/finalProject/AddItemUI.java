package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class AddItemUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	private int storeID;
	
	private String[] itemTypes = {"None", "Home Item", "Medicine", "Food"};
	private JComboBox<String> dropDown = new JComboBox<String>(itemTypes);
	private ArrayList<JComponent> temporaryUIElements = new ArrayList<>();
	
	// Common fields
	JTextField nameField = null;
	JTextField supplierIDField = null;
	JTextField priceField = null;
	JTextField stockField = null;
	
	// Home Item fields
	JTextField brandField = null;
	
	// Medicine fields
	JTextField scientificNameField = null;
	JTextField manufacturerField = null;
	JTextField strengthField = null;
	
	// Food fields
	// duplicate brandField;
	JTextField expirationField = null;
	
	public AddItemUI(JFrame parent, SQLConnection db, int storeID) {
		this.parent = parent;
		this.db = db;
		this.storeID = storeID;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Item");
		
		JTextArea nameLabel = new JTextArea("Name:");
		nameField = new JTextField();
		JTextArea supplierIDLabel = new JTextArea("Supplier ID:");
		supplierIDField = new JTextField();
		JTextArea priceLabel = new JTextArea("Price:");
		priceField = new JTextField();
		JTextArea stockLabel = new JTextArea("Amount Stocked:");
		stockField = new JTextField();

		JButton back = new JButton("Back");
		JButton add = new JButton("Add");

		
		dropDown.setBounds(50, 20, 200, 20);
		nameLabel.setBounds(20, 100, 120, 20);
		nameField.setBounds(160, 100, 120, 20);
		supplierIDLabel.setBounds(20, 140, 120, 20);
		supplierIDField.setBounds(160, 140, 120, 20);
		priceLabel.setBounds(20, 180, 120, 20);
		priceField.setBounds(160, 180, 120, 20);
		stockLabel.setBounds(20, 350, 120, 20);
		stockField.setBounds(160, 350, 120, 20);
		
		add.setBounds(200, 390, 90, 40);
		back.setBounds(10, 390, 90, 40);
		
		nameLabel.setEditable(false);
		supplierIDLabel.setEditable(false);
		priceLabel.setEditable(false);
		
		frame.add(dropDown);
		frame.add(nameLabel);
		frame.add(nameField);
		frame.add(supplierIDLabel);
		frame.add(supplierIDField);
		frame.add(priceLabel);
		frame.add(priceField);
		frame.add(stockLabel);
		frame.add(stockField);
		frame.add(add);
		frame.add(back);

		frame.setSize(300, 500);
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
			public void actionPerformed(ActionEvent event) {
				String name = nameField.getText();
				int supplierID;
				double currentPrice;
				int itemID;
				try {
					supplierID = Integer.parseInt(supplierIDField.getText());
				} catch (NumberFormatException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				}
				try {
					currentPrice = Double.parseDouble(priceField.getText());
				} catch (NumberFormatException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				}
				try {
					db.runUpdateString("INSERT INTO Items ( name, supplierID, currentPrice )"
							         + "VALUES ( '" + name + "', " + supplierID + ", " + currentPrice + " )");
					ResultSet rs = db.runQueryString("SELECT LAST_INSERT_ID()");
					if (rs.next()) {
						itemID = Integer.parseInt(rs.getString(1));
					} else {
						throw new SQLException("The item added successfully, but there was a problem getting the automatically assigned ID");
					}
					String brand;
					String scientificName;
					String manufacturer;
					String strength;
					String expirationDate;
					switch ((String)dropDown.getSelectedItem()) {
					case "Home Item":
						brand = brandField.getText();
						db.runUpdateString("INSERT INTO HomeItems ( itemID, brand )"
				                         + "VALUES ( " + itemID + ", '" + brand + "' )");
						break;
					case "Medicine":
						scientificName = scientificNameField.getText();
						manufacturer = manufacturerField.getText();
						strength = strengthField.getText();
						db.runUpdateString("INSERT INTO Medicine ( itemID, scientificName, manufacturer, strength )"
		                                 + "VALUES ( " + itemID + ", '" + scientificName + "', '" + manufacturer + "', '" + strength + "' )");
						break;
					case "Food":
						brand = brandField.getText();
						expirationDate = expirationField.getText();
						db.runUpdateString("INSERT INTO Food ( itemID, brand, expirationDate )"
                                         + "VALUES ( " + itemID + ", '" + brand + "', '" + expirationDate + "' )");
						break;
					default:
						break;
					}
					
					Integer quantityStocked = Integer.parseInt(stockField.getText());
					String stockUpdate = "Insert into AmountStocked VALUES (?, ?, ?)";
					PreparedStatement p1 = db.getActiveConnection().prepareStatement(stockUpdate);
					p1.setInt(1, itemID);
					p1.setInt(2, storeID);
					p1.setInt(3, quantityStocked);
					p1.executeUpdate();
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
					e.printStackTrace();
					return;
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				}
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

// TODO STORE
