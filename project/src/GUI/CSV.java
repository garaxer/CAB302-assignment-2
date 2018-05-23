package GUI;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CSV {

	
	//private String[][] data; //change to stock
	private ArrayList<String[]> lists; // initial list of inventory 
	private HashMap<String, Integer> salesLog;
	
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
	
	private boolean processFile(File file){
		boolean workingFile = true;
	    //import the file
		try {
			workingFile = processInventory(file.getAbsolutePath());
		}  catch (java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found please select another file. Error:"+e);
			workingFile = false;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Please select working inventory file. Error: "+e1);
			workingFile = false;
		}
	    //return true if the reading works properly, and false otherwise    
		return workingFile;
	}
	
	
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
			//TODO: Create ITEM and add to stock		
			lists = new ArrayList<String[]>();
	
			System.out.println(line);
			lists.add(line.split(",")); // create inventory, add to stock
			
			while((line = bufferedReader.readLine())!= null) {
				System.out.println(line);
				lists.add(line.split(",")); // create inventory, add to stock
				//return false if lists length is less that 5
			}
		}
		bufferedReader.close();
		return workingFile;
	}

	
	
	
	//Sales log
	
	//TODO combine with the above
	private boolean processFileSales(File file){
		try {
			return processSales(file.getAbsolutePath());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Please select working inventory file. Error: "+e1);
			return false;
		}
	}
	//TODO combine with the above
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
	
}
