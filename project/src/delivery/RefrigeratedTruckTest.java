package delivery;
import stock.Item;
import stock.Stock;
import stock.StockException;

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
	
	// 
	@Test
	public void checkCreationwithStock() throws DeliveryException {
		Stock stock = new Stock();
		stock.addItems(chocolate, 10);
		Truck refrigerated = new RefrigeratedTruck(stock);
	}
	
	// 
	@Test
	public void checkAddingStock() throws DeliveryException {
		Truck refrigerated = new RefrigeratedTruck();
		Stock stock = new Stock();
		refrigerated.addStock(stock);		
	}
	 
	@Test
	public void checkStock() throws DeliveryException, StockException {
		Stock stock = new Stock();
		stock.addItems(ice);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		Stock expectedStock = refrigerated.getStock();
		for (Item item : expectedStock.toSet()) {
			if (stock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	@Test (expected = DeliveryException.class)
	public void tooMuchStock()  throws DeliveryException{
		Truck refrigerated = new RefrigeratedTruck();	
		Stock stock = new Stock();
		stock.addItems(ice, 1200);
		refrigerated.addStock(stock);
	}
	
	@Test
	public void checkCost() throws DeliveryException, StockException{
		Stock stock = new Stock();
		stock.addItems(ice);
		int temp = ice.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost(), 0);
	}
	
	@Test
	public void checkCost2() throws StockException, DeliveryException {
		Stock stock = new Stock();
		stock.addItems(iceCream);
		int temp = iceCream.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (temp/5)));
		assertEquals(cost, refrigerated.getCost(), 0);
	}
	
	
	@Test
	public void checkCapacity() throws DeliveryException {	 
		Stock stock = new Stock();
		stock.addItems(chocolate, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(100, refrigerated.getRemainingCapacity());
	}
	
	
	@Test
	public void checkCapacity2() throws DeliveryException {	 
		Stock stock = new Stock();
		stock.addItems(chocolate, 100);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		Stock stock2 = new Stock();
		stock2.addItems(chocolate, 300);
		refrigerated.addStock(stock2);
		assertEquals(400, refrigerated.getRemainingCapacity());
	}
	
	@Test
	public void checkManifest() throws DeliveryException, StockException {
		String dummyManifest = ">Refrigerated\nchocolate,50\nice,500\n";
		String dummyManifest2 = ">Refrigerated\nice,500\nchocolate,50\n";
		Stock stock = new Stock();
		stock.addItems(chocolate, 50);
		stock.addItems(ice, 500);
		Truck truck = new RefrigeratedTruck();
		truck.addStock(stock);
		assertTrue((dummyManifest.equals(truck.getManifest()) || dummyManifest2.equals(truck.getManifest())));
		
	}
	
	
}
