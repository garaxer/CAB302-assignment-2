package GUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import stock.StockException;

@SuppressWarnings("serial") // We don't care
public class GUI extends JFrame {

	public GUI(Store store) throws StockException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle(store.getName());
		
		//load inventory into store. TODO make the below return a stock to give to store
		CSV csv = new CSV();
		store.loadInventory(csv.getInventory(this));

		getContentPane().add(new GUIComponents(store));
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(800, 500));
		pack();
	}
}

