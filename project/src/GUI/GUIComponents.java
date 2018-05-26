package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIComponents extends JPanel implements ActionListener{

	//change to panel with the button, see slide37 of lecture 9
	public Store store;
     
     public GUIComponents(Store store){
    	 this.store = store;
    	// Initialize the GUI Components
 		initialiseComponents();	 
     }
	
 	private void initialiseComponents()	{
 		//Choose a box layout for the main frame
 		
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

 		JPanel topPanel = new JPanel();
 		topPanel.setBackground(Color.LIGHT_GRAY);
 		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		JButton redButton = new JButton("Generate manifest");
		redButton.setBackground(Color.RED);
		redButton.addActionListener(this);
		topPanel.add(redButton);
		
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(new JLabel("Store Capital: "+store.getCapitalString()));//
		
		topPanel.add(Box.createHorizontalGlue());
		JButton blueButton = new JButton("Load in sales logs");
		blueButton.setBackground(Color.BLUE);
		blueButton.setForeground(Color.WHITE);
		blueButton.addActionListener(this);
		topPanel.add(blueButton);
		//topPanel.add(Box.createHorizontalStrut(10));
	
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		//TODO Store.getInventoryInARRAYARRAY
		//String data[][]={ {"1","2","3","4","5","6"} };   
		
		String data[][] = store.getInventoryArray(); //
		String column[]={"Name","Quantity","Cost","Sell Price","Reorder Point","Reorder Amount","Temperature"};         
		JTable table =new JTable(data,column);     
		table.setEnabled(false);
		table.setDragEnabled(false);
		JScrollPane scroll = new JScrollPane(table); 
		p2.add(scroll);
 	     
        this.add(topPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(p2);

 	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 String buttonString = e.getActionCommand();
		 if (buttonString.equals("Load in sales logs")) {
			 
			 CSV csv = new CSV();
			 store.loadSales(csv.loadSalesLog(this));
			 JOptionPane.showMessageDialog(null, "sales log loaded successfully");
	     } 
		 else if (buttonString.equals("Generate manifest")) {
			 
			 //CSV csv = new CSV();
			 //csv.exportManifest(store.generateManifest(),this)
			 
			 System.out.println(store.generateManifest());

			 JOptionPane.showMessageDialog(null, "Generating Manifest - remove me");
	     }
	}
}
