package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import delivery.DeliveryException;
import delivery.Manifest;
import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * Used to import CSV files that are used in the store
 * @author Gary Bagnall
 *
 */
public class CSV {
	
	private ArrayList<String[]> lists; //initial list of inventory //TODO:Change to stock object
	private HashMap<String, Integer> salesLog; //sales log imported
	
	/**
	 * Opens the file item_properties.csv. 
	 * If it isn't there prompt to find a file and check if it correct.
	 * If the file wasn't use-able, it will prompt to find another file
	 * @param gui the Jframe to use to select a file with.
	 * @return an Arraylist with a String array 
	 */
	public ArrayList<String[]> getInventory(GUI gui) {
		boolean workingFile = true;
		File dir = new File("item_properties.csv");
		workingFile = processFile(dir);
		
	    while (!workingFile){ 
	    		JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".\\"));
	        chooser.showOpenDialog(gui);	
	        workingFile = processFile(chooser.getSelectedFile());
	    }
		return lists;
	}
	
	/**
	 *  Checks if the file is use-able - has the correct information and isn't corrupted, etc.
	 * @param file the file to process
	 * @return true if the file is a use-able file
	 */
	private boolean processFile(File file){
		boolean workingFile = true;
	    //import the file
		try {
			workingFile = processInventory(file.getAbsolutePath());
		}  catch (java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "item_properties.csv not found please select another file:");
			workingFile = false;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Please select working inventory file. Error: "+e1);
			workingFile = false;
		}
	    //return true if the reading works properly, and false otherwise    
		return workingFile;
	}
	
	/**
	 * Populates the contents of the file into an ArrayList
	 * @param fileName the file to read and populate the array list with
	 * @return
	 * @throws IOException if something is wrong with the file
	 */
	private boolean processInventory(String fileName) throws IOException {
		boolean workingFile = true;
		FileReader reader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		if (line == null) {
			System.out.println("The file was empty");
			JOptionPane.showMessageDialog(null, "The file was empty, please select another");
			workingFile = false;
		} else {
			lists = new ArrayList<String[]>();
			lists.add(line.split(",")); // create inventory, add to stock instead so we can throw error if it doesn't work
			
			while((line = bufferedReader.readLine())!= null) {
				lists.add(line.split(",")); // create inventory, add to stock
				//return false if lists length is less that 5
			}
		}
		bufferedReader.close();
		return workingFile;
	}

	/**
	 * @param file the file to process
	 * @return true if the file is a use-able file and false otherwise
	 */
	private boolean processFileSales(File file){
		try {
			return processSales(file.getAbsolutePath());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Please select working inventory file. Error: "+e1);
			return false;
		}
	}

	/**
	 * Adds the contents of the sales log into list and returns true if successfully did so.
	 * TODO: change to stock
	 * @param absolutePath
	 * @return true if the file was working
	 * @throws IOException if the file isn't readable
	 */
	private boolean processSales(String absolutePath) throws IOException {
		FileReader reader = new FileReader(absolutePath);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		if (line == null) {
			System.out.println("The file was empty");
			JOptionPane.showMessageDialog(null, "The file was empty, please select another");
			bufferedReader.close();
			return false;
		} else { 
			salesLog = new HashMap<String, Integer>();
			
			System.out.println(line);
			String[] lineStart = line.split(",");
			salesLog.put(lineStart[0], Integer.parseInt(lineStart[1]) ); 
			
			while((line = bufferedReader.readLine())!= null) {
				System.out.println(line);
				lineStart = line.split(",");
				salesLog.put(lineStart[0], Integer.parseInt(lineStart[1])); 
			}

		}
		bufferedReader.close();
		return true;
	}

	/**
	 * Prompts to find the saleslog file and returns the data from it.
	 * If the file wasn't use-able, it will prompt to find another file until a working one is found.
	 * @param guiComponents the gui to prompt the open file dialog
	 * @return the contents of the file in a list
	 */
	public HashMap<String, Integer> loadSalesLog(GUIComponents guiComponents) {
		boolean workingFile = false;

	    while (!workingFile){ 
	    	JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".\\"));
	        chooser.showOpenDialog(guiComponents);	
	        workingFile = processFileSales(chooser.getSelectedFile());
	    }	
	    
	    return salesLog;
	}

	/**
	 * read in the manifest file
	 * @param selectedFile
	 * @return a manifest
	 * @throws CSVFormatException 
	 * @throws IOException 
	 * @throws StockException 
	 * @throws DeliveryException 
	 */
	public Manifest processManifest(File selectedFile) throws CSVFormatException, IOException, StockException, DeliveryException {
		Store store = Store.getInstance();
		Manifest manifest = null;
		FileReader reader = new FileReader(selectedFile.getAbsolutePath());
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		if (line == null) {	
			bufferedReader.close();
			throw new CSVFormatException("The file was empty, please select another");	
		} else { 
			ArrayList<Truck> truckList = new ArrayList<Truck>();
			
			System.out.println(line);
			//String[] lineStart = line.split(",");
			//salesLog.put(lineStart[0], Integer.parseInt(lineStart[1]) ); 
			boolean cold = false;
			if (line.equals(">Refrigerated")) {
				cold = true;
			} else if (line.equals(">Ordinary")) {
				cold = false;
			} else {
				bufferedReader.close();
				throw new CSVFormatException("Refridgereated or Ordinary truck not found in first line: "+line);
			}
			
			while((line = bufferedReader.readLine())!= null) {
				System.out.println(line);
				Stock stock = new Stock();
				if (!( line.equals(">Refrigerated") || line.equals(">Ordinary"))) {
					
					String[] lineStart = line.split(",");
					Item item = store.getStock().getItem(lineStart[0]);
					if (item == null) {
						bufferedReader.close();
						throw new CSVFormatException("Item not found: "+line);
					}
					stock.addItems(item, Integer.parseInt(lineStart[1]));
				} else {
					Truck truck = null;
					if (stock.getTotalQuantity() > 0) {
						if (cold) {
							truck = new RefrigeratedTruck(stock);
							truckList.add(truck);
						}
						else {
							truck = new OrdinaryTruck(stock);
							truckList.add(truck);
						}
					}
					if (line.equals(">Refrigerated")) {
						cold = true;
					} else if (line.equals(">Ordinary")) {
						cold = false;
					} 
					
				}
			}
			manifest = new Manifest(truckList);
		}
		bufferedReader.close();
		return manifest;
	}
	
}
