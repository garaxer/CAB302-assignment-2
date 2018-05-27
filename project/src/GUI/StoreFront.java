package GUI;

import javax.swing.JFrame;
import stock.StockException;

/**
 * This class contains the main program for a shop.
 * 
 * @author Gary and Alex
 */
public class StoreFront {
	
	/** 
	 * Program entry point.
	 * Creates the GUI for the user to start managing inventory
	 * @throws StockException 
	 */
	public static void main(String[] args) throws StockException {
		// Create a store
		Store store = Store.getInstance();
		store.setName("Shop store the inventory");
		//Create the GUI
		GUI gui = new GUI(store);
		// Terminate if the user closes
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Make the GUI visible
		gui.setVisible(true);
	}
}
