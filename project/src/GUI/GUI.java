package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;


@SuppressWarnings("serial") // We don't care
public class GUI extends JFrame {

	public GUI(Store store) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle(store.getName());
		getContentPane().add(new GUIComponents(store));
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(800, 1000));
		pack();
	}
}

