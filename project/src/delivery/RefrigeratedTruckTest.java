package delivery;
import stock.Item;
import stock.Stock;

import static org.junit.Assert.*;

/**
 * author = Alexandra Koppon
 */

import org.junit.Test;


/**
 * 
 * @author Alex Koppon
 *
 */

public class RefrigeratedTruckTest {

	// 1: Test if a RefrigeratedTruck object can be constructed
	@Test
	public void checkCreation() {
		Truck refrigerated = new RefrigeratedTruck();
	}
	
	// 2: Test of Stock can be added
	@Test
	public void checkAddingStock() {
		Truck refrigerated = new RefrigeratedTruck();
		Stock stock = new Stock();
		refrigerated.addStock(stock);		
	}
	
	// 3: Test 
	@Test
	public void checkStock() {
		Stock stock = new Stock();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(stock, refrigerated.getStock());
	}
	
	@Test (expected = DeliveryException.class)
	public void tooMuchStock()  throws DeliveryException{
		Truck refrigerated = new RefrigeratedTruck();
		Item item = new Item("rice", 2, 3, 225, 300);		
		Stock stock = new Stock();
		stock.addItems(item, 1200);
	}
	
	@Test
	public void checkCost(){
		//900 + 200×0.7T/5 -- formula to calculate cost
		Stock stock = new Stock();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		//assertEquals();
	}
	
	
	
	@Test
	public void checkCapacity() {
		Item item = new Item("rice", 2, 3, 225, 300);		 
		Stock stock = new Stock();
		stock.addItems(item, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(300, refrigerated.getRemainingCapacity());
	}
	
	@Test
	public void testGetManifest() throws DeliveryException{
		String list = ">Ordinary\n" + 
				"cookies,10\n" + 
				"biscuits,50\n" 
		Item cookies = new Item("cookies",50,100,5,25);
		Item biscuits = new Item("biscuits",50,100,5,25);
		Stock stock = new Stock();
		stock.addItems(cookies,10);
		stock.addItems(biscuits,50);
		Truck ordinaryTruck = new OrdinaryTruck(stock);			
		assertEquals(list, ordinaryTruck.getManifest());
	}
	
}
