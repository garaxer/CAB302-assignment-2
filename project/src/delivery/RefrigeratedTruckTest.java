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
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkCost(){
		int temp = -20;
		Stock stock = new Stock();
		Truck refrigerated = new RefrigeratedTruck(temp);
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost());
	}
	
	@SuppressWarnings("deprecation")
	public void checkCost2() {
		int temp = -10;
		Stock stock = new Stock();
		Truck refrigerated = new RefrigeratedTruck(-10);
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost());
	}
	
	
	
	@Test
	public void checkCapacity() {
		Item item = new Item("rice", 2, 3, 225, 300);		 
		Stock stock = new Stock();
		stock.addItems(item, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(100, refrigerated.getRemainingCapacity());
	}
	
	
	@Test
	public void checkCapacity2() {
		Item item = new Item("rice", 2, 3, 225, 300);		 
		Stock stock = new Stock();
		stock.addItems(item, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		Stock stock2 = new Stock();
		stock2.addItems(item, 300);
		refrigerated.removeStock(stock2);
		assertEquals(400, refrigerated.getRemainingCapacity());
	}
	
	
}
