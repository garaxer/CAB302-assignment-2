package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;


@SuppressWarnings("serial") // We don't care
public class GUI extends JFrame {

	public GUI(String name) {
		setTitle(name);
		getContentPane().add(new GUIComponents());
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(400, 200));
		pack();
	}
}

