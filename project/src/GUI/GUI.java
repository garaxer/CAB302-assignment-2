package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import stock.StockException;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public GUI(Store store) throws StockException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle(store.getName());
		
		//Load inventory into store. TODO make the below return a stock to give to store, surround with try catch
		CSV csv = new CSV();
		store.loadInventory(csv.getInventory(this));

		getContentPane().add(new GUIComponents());
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(800, 500));
		pack();
	}
	
}

