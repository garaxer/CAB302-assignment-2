package GUI;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CSV {

	
	public static void getInventory(GUI gui) {
		
		boolean workingFile = true;
		File dir = new File("item_properties.csv");
		workingFile = processFile(dir);
		
	    while (!workingFile){ 
	    	JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".\\"));
	        chooser.showOpenDialog(gui);	
	        workingFile = processFile(chooser.getSelectedFile());
	    }
	}
	
	private static boolean processFile(File file){
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
	
	private static boolean processInventory(String fileName) throws IOException {
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
			//String data[][] = null ;
			
			ArrayList<String[]> lists = new ArrayList<String[]>();
	
			System.out.println(line);
			lists.add(line.split(",")); // create inventory, add to stock
			
			while((line = bufferedReader.readLine())!= null) {
				System.out.println(line);
				lists.add(line.split(",")); // create inventory, add to stock
			}
			
			String data[][] = new String[lists.size()][6];
			
			int i = 0;
			for (String[] alist : lists) {
				for (int j = 0; j < alist.length; j++) {
					 data[i][j] = alist[j];
				}
				i++;
			}
			
			
			System.out.println(data);
			
			/*
			 * BufferedReader in = new BufferedReader(new FileReader("path/of/text"));
				String str;
				
				List<String> list = new ArrayList<String>();
				while((str = in.readLine()) != null){
				    list.add(str);
				}
				
				String[] stringArr = list.toArray(new String[0]);
			 */
			
		}
		bufferedReader.close();
		return workingFile;
	}
	
}
