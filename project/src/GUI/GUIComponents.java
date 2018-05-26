package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import stock.StockException;

@SuppressWarnings("serial")
public class GUIComponents extends JPanel implements ActionListener{

	//change to panel with the button, see slide37 of lecture 9
	public Store store;
	
	private JLabel costLabel;
	private JTable table;
     
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
		costLabel = new JLabel("Store Capital: "+store.getCapitalString());
		topPanel.add(costLabel);
		
		topPanel.add(Box.createHorizontalGlue());
		JButton blueButton = new JButton("Load in sales logs");
		blueButton.setBackground(Color.BLUE);
		blueButton.setForeground(Color.WHITE);
		blueButton.addActionListener(this);
		topPanel.add(blueButton);
	
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));


		String data[][] = store.getInventoryArray();
		String column[]={"Name","Quantity","Cost","Sell Price","Reorder Point","Reorder Amount","Temperature"};         
		table = new JTable(data,column);     
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
			try {
				store.loadSales(csv.loadSalesLog(this));
				JOptionPane.showMessageDialog(null, "sales log loaded successfully");
				updateGUI();
			} catch (StockException e1) {
				JOptionPane.showMessageDialog(null, "Can't remove more Stock than exists, pleaes reload new file or generate Manifest. Error:"+e1);
			}
	    } 
		else if (buttonString.equals("Generate manifest")) {
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File("manifest.csv"));
			chooser.setCurrentDirectory(new File(".\\"));
			chooser.showSaveDialog(this);
		    try {
		    	WriteManifest(store.generateManifest(), chooser.getSelectedFile());
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1+"The file couldn't be found");
			}
		    updateGUI();
	     }
	}

	private void updateGUI() {
		costLabel.setText("Store Capital: "+store.getCapitalString());
		String data[][] = store.getInventoryArray();
		String column[]={"Name","Quantity","Cost","Sell Price","Reorder Point","Reorder Amount","Temperature"};       
		TableModel dataModel = new DefaultTableModel(data,column);
		table.setModel(dataModel);
	}
	
	public static void WriteManifest(String manifest, File file) throws IOException
	{
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		writer.write(manifest);
		writer.close();
		JOptionPane.showMessageDialog(null, file.getName()+" has been saved successfully");
	}
}
