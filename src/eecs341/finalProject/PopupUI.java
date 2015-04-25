package eecs341.finalProject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PopupUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	
	public PopupUI(String errorString, String details) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay(errorString, details);
			}
		});
	}
	
	private void launchDisplay(String errorString, String details) {
		JOptionPane.showMessageDialog(frame, details, errorString, JOptionPane.ERROR_MESSAGE);
	}
}