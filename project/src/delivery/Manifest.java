package delivery;

import java.util.ArrayList;
import java.util.Collection;

//undo commit

import stock.Stock;

public class Manifest {

//	/ArrayList<Truck> trucks;
	private Stock stock;
	private String[] inventory;

	public Manifest(Stock stock, ArrayList<String[]> inventory) {
		this.stock = stock;
		System.out.println("Stock bro");
		
		for (String[] alist : inventory) {
			//for (int j = 0; j < alist.length; j++) {
			System.out.println("name= "+alist[0]+" point= "+alist[3]+" amount= "+alist[4]);
				//System.out.println(alist[0]);
			//}
		}
	}

	public ArrayList<String[]> reStock() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getCapital() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
