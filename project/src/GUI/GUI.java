package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import stock.StockException;

@SuppressWarnings("serial")
/**
 * The GUI for the Store. Sets the title and adds the components.
 * @author Gary Bagnall
 *
 */
public class GUI extends JFrame {

	public GUI(Store store) throws StockException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle(store.getName());
		
		getContentPane().add(new GUIComponents());
		// Resize the frame to fit its components
		setPreferredSize(new Dimension(800, 500));
		pack();
	}
	
}

