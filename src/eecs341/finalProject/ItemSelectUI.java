package eecs341.finalProject;

import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ItemSelectUI extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	private PreparedStatement ps;
	
	public ItemSelectUI(JFrame parent, SQLConnection db, PreparedStatement ps) {
		this.parent = parent;
		this.db = db;
		this.ps = ps;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}

	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Item Select");
		
		
		JButton back = new JButton("Back");
		JButton execute = new JButton("Execute");
		back.setBounds(20, 330, 120, 40);
		execute.setBounds(160, 330, 120, 40);
		frame.add(back);
		frame.add(execute);
		
	}
	
	
}
