package GUI;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class contains the main program for a shop.
 * 
 * @author Gary and Alex
 */
public class StoreFront {
	
	/** 
	 * Program entry point.
	 * Creates the GUI for the user to start managing inventory
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		//Read in the stock
		//Stock inventory = CSV.readInventory("item_properties.csv");
		// Create a store
		Store store = Store.getInstance();
		store.name = "Shop store the inventory";
		
		//Create the GUI
		GUI gui = new GUI(store);
		// Terminate if the user closes
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Make the GUI visible
		gui.setVisible(true);
	}
}
