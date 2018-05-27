package GUI;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

import delivery.DeliveryException;
import stock.StockException;

@SuppressWarnings("serial")
/**
 * Used to hold the GUI components and their methods.
 * @author Gary Bagnall
 *
 */
public class GUIComponents extends JPanel implements ActionListener{

	public Store store; //public to be accessed by CSV
	private JLabel costLabel; //the current capital
	private JTable table; // the table of items
	private CSV csv = new CSV();

	/**
	 * The Constructor for the GUI Components
	 * Gets a current instance of the store
	 * @param store
	 */
	public GUIComponents(){
		this.store = Store.getInstance();
		//Load inventory into store. TODO make the below return a stock to give to store, surround with try catch
		getInventory();
		// Initialize the GUI Components
		initialiseComponents();	 
	}
	
	public void getInventory() {
		ArrayList<String[]> lists = null;
		boolean workingFile = true;
		File dir = new File("item_properties.csv");
		try {
			lists = csv.processInventory(dir);
			store.loadInventory(lists); 
			workingFile = true;
		} catch (CSVFormatException e1) {
			JOptionPane.showMessageDialog(null, "Manifest CSV file is incorrect. "+e1);
			//e1.printStackTrace();
			workingFile = false;
		}  catch (java.io.FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Manifest File not found. Please Select one.... "+e1);
			workingFile = false;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "IO error "+e1);
			//e1.printStackTrace();
			workingFile = false;
		} catch (StockException e) {
			JOptionPane.showMessageDialog(null, "Issue with Manifest CSV "+e);
			//e1.printStackTrace();
			workingFile = false;
		}

		while (!workingFile){ 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".\\"));
			chooser.showOpenDialog(this);	
			try {
				lists = csv.processInventory(chooser.getSelectedFile());
				workingFile = true;
				store.loadInventory(lists); 
			} catch (CSVFormatException e1) {
				JOptionPane.showMessageDialog(null, "Manifest CSV file is incorrect. "+e1);
				//e1.printStackTrace();
				workingFile = false;
			}  catch (java.io.FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Manifest File not found. Please Select one....  "+e1);
				workingFile = false;
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "IO error "+e1);
				//e1.printStackTrace();
				workingFile = false;
			} catch (StockException e) {
				JOptionPane.showMessageDialog(null, "Issue with Manifest CSV "+e);
				//e1.printStackTrace();
				workingFile = false;
			}
		}
	}

	/**
	 * Adds components to the GUI
	 */
	private void initialiseComponents()	{
		//Choose a box layout for the main frame
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JButton redButton = new JButton("Generate manifest");
		redButton.setBackground(Color.YELLOW);
		redButton.addActionListener(this);
		topPanel.add(redButton);
		
		JButton greenButton = new JButton("Import manifest");
		greenButton.setBackground(Color.GREEN);
		greenButton.addActionListener(this);
		topPanel.add(greenButton);

		topPanel.add(Box.createHorizontalGlue());
		costLabel = new JLabel("Store Capital: "+store.getCapitalString());
		topPanel.add(costLabel);

		topPanel.add(Box.createHorizontalGlue());
		JButton blueButton = new JButton("Load in sales logs");
		blueButton.setBackground(Color.WHITE);
		blueButton.addActionListener(this);
		topPanel.add(blueButton);

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		String data[][] = store.getInventoryArray();
		String column[]={"Name","Quantity","Cost ($)","Price ($)","Reorder Point","Reorder Amount","Temperature C"};         
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
	/**
	 * The actions the button's perform
	 * SalesButton: store calls the CSV to load the sales
	 * Manifestbutton: A manifest is generated from the contents received from store
	 */
	public void actionPerformed(ActionEvent e) {

		String buttonString = e.getActionCommand();
		try {
			if (buttonString.equals("Load in sales logs")) {
				JFileChooser choosers = new JFileChooser();
				choosers.setCurrentDirectory(new File(".\\"));
				choosers.showOpenDialog(this);	
				store.loadSales(csv.processSales(choosers.getSelectedFile()));
				updateGUI();
				JOptionPane.showMessageDialog(null, "sales log loaded successfully");
			} 
			else if (buttonString.equals("Generate manifest")) {
				JFileChooser chooser = new JFileChooser();
				chooser.setSelectedFile(new File("manifest.csv"));
				chooser.setCurrentDirectory(new File(".\\"));
				chooser.showSaveDialog(this);
				WriteManifest(store.generateManifest(), chooser.getSelectedFile());
				updateGUI();
			} else if (buttonString.equals("Import manifest")) { 
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(".\\"));
				chooser.setSelectedFile(new File("manifest.csv"));
				chooser.showOpenDialog(this);	
				store.importManifest(csv.processManifest(chooser.getSelectedFile()));
				JOptionPane.showMessageDialog(null, "manifest loaded successfully");
				updateGUI();
			}
		} catch (StockException e1) {
			JOptionPane.showMessageDialog(null, "Error ProcessingStock:  "+e1);
		} catch (CSVFormatException e1) {
			JOptionPane.showMessageDialog(null, "CSV file is incorrect. "+e1);
			//e1.printStackTrace();
		}  catch (java.io.FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File not found. "+e1);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "IO error "+e1);
			//e1.printStackTrace();
		} catch (DeliveryException e1) {
			JOptionPane.showMessageDialog(null, "Delivery Error "+e1);
			//e1.printStackTrace();
		}
	}

	/**
	 * Refreshes the gui by re-adding the components that have changed
	 */
	private void updateGUI() {
		costLabel.setText("Store Capital: "+store.getCapitalString());
		String data[][] = store.getInventoryArray();
		String column[]={"Name","Quantity","Cost ($)","Price ($)","Reorder Point","Reorder Amount","Temperature C"};        
		TableModel dataModel = new DefaultTableModel(data,column);
		table.setModel(dataModel);
	}

	/**
	 * Writes the manfiest to the chosen file
	 * @param manifest The content to be written to the file
	 * @param file The file that will be written to
	 * @throws IOException
	 */
	private static void WriteManifest(String manifest, File file) throws IOException
	{
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		writer.write(manifest);
		writer.close();
		JOptionPane.showMessageDialog(null, file.getName()+" has been saved successfully");
	}
}
