package GUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial") // We don't care
public class GUI extends JFrame {

	public GUI(Store store) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle(store.getName());
		
		//load inventory into store. TODO make the below return a stock to give to store
		CSV.getInventory(this);
		
		getContentPane().add(new GUIComponents(store));
		
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(800, 1000));
		pack();
	}
}

