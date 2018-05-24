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
	
	Item chocolate = new Item("chocolate", 5, 8, 250, 375);
	Item ice = new Item("ice", 2, 5, 225, 325, -10);
	Item iceCream = new Item("ice cream", 8, 14, 175, 250, -20);

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
		stock.addItems(ice);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(stock, refrigerated.getStock());
	}
	
	@Test (expected = DeliveryException.class)
	public void tooMuchStock()  throws DeliveryException{
		Truck refrigerated = new RefrigeratedTruck();	
		Stock stock = new Stock();
		stock.addItems(ice, 1200);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkCost(){
		Stock stock = new Stock();
		stock.addItems(ice);
		int temp = ice.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost());
	}
	
	@SuppressWarnings("deprecation")
	public void checkCost2() {
		Stock stock = new Stock();
		stock.addItems(iceCream);
		int temp = iceCream.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost());
	}
	
	
	@Test
	public void checkCapacity() {	 
		Stock stock = new Stock();
		stock.addItems(chocolate, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(100, refrigerated.getRemainingCapacity());
	}
	
	
	@Test
	public void checkCapacity2() {	 
		Stock stock = new Stock();
		stock.addItems(chocolate, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		Stock stock2 = new Stock();
		stock2.addItems(chocolate, 300);
		refrigerated.removeStock(stock2);
		assertEquals(400, refrigerated.getRemainingCapacity());
	}
	
	@Test
	public void checkManifest() {
		String dummyManifest = ">Refrigerated\nchocolate,50\nice,400\n";
		String dummyManifest2 = ">Refrigerated\nice,400\nchocolate,50\n";
		Stock stock = new Stock();
		stock.addItems(chocolate, 50);
		stock.addItems(ice, 500);
		Truck truck = new RefrigeratedTruck();
		truck.addStock(stock);
		assertTrue((dummyManifest == truck.getManifest() || dummyManifest2 == truck.getManifest()));
		
	}
	
	
}
