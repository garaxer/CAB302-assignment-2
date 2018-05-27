package GUI;

import java.io.BufferedReader;
import java.io.File;
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
	

	
	/**
	 * Populates the contents of the file into an ArrayList
	 * @param fileName the file to read and populate the array list with
	 * @return
	 * @throws IOException if something is wrong with the file
	 * @throws CSVFormatException 
	 */
	@SuppressWarnings("resource")
	public ArrayList<String[]> processInventory(File file) throws IOException, CSVFormatException {
		ArrayList<String[]> itemList = null;
		FileReader reader = new FileReader(file.getAbsolutePath());
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		if (line == null) {
			System.out.println("The file was empty");
			throw new CSVFormatException("The file was empty, please select another");
		} else {
			itemList = new ArrayList<String[]>();
			itemList.add(line.split(",")); // create inventory, add to stock instead so we can throw error if it doesn't work

			while((line = bufferedReader.readLine())!= null) {
				itemList.add(line.split(",")); // create inventory, add to stock
				//return false if lists length is less that 5
			}
		}
		bufferedReader.close();
		return itemList;
	}

	/**
	 * Adds the contents of the sales log into list and returns true if successfully did so.
	 * TODO: change to stock
	 * @param absolutePath
	 * @return true if the file was working
	 * @throws IOException if the file isn't readable
	 * @throws CSVFormatException 
	 */
	public  HashMap<String, Integer> processSales(File file) throws IOException, CSVFormatException {
		HashMap<String, Integer> salesLog;
		FileReader reader = new FileReader(file.getAbsolutePath());
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		if (line == null) {
			System.out.println("The file was empty");
			bufferedReader.close();
			throw new CSVFormatException("The file was empty, please select another");
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
		return salesLog;
	}
	
	/**
	 * read in the manifest file and 
	 * @param selectedFile
	 * @return manifest - a manifest filled with Truck's stock
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
			Stock stock = new Stock();
			while((line = bufferedReader.readLine())!= null) {

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
						System.out.println(stock.getTotalQuantity());
						if (cold) {
							truck = new RefrigeratedTruck(stock);
							truckList.add(truck);
							stock = new Stock();
						}
						else {
							truck = new OrdinaryTruck(stock);
							truckList.add(truck);
							stock = new Stock();
						}
					}
					if (line.equals(">Refrigerated")) {
						cold = true;
					} else if (line.equals(">Ordinary")) {
						cold = false;
					} 

				}
			}

			Truck truck = null;
			if (stock.getTotalQuantity() > 0) {
				System.out.println(stock.getTotalQuantity());
				if (cold) {
					truck = new RefrigeratedTruck(stock);
					truckList.add(truck);
					stock = new Stock();
				}
				else {
					truck = new OrdinaryTruck(stock);
					truckList.add(truck);
					stock = new Stock();
				}
			}

			manifest = new Manifest(truckList);
			System.out.println(manifest.getStockString());
		}
		bufferedReader.close();
		return manifest;
	}

}
