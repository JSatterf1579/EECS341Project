package eecs341.finalProject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PopupUI {
	
	public PopupUI(String errorString, String details) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay(errorString, details);
			}
		});
	}
	
	private void launchDisplay(String errorString, String details) {
		JOptionPane.showMessageDialog(new JFrame(), details, errorString, JOptionPane.ERROR_MESSAGE);
	}
}