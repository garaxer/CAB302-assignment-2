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
	
	/**
	 * Create some variables to test the class with
	 */
	Item chocolate = new Item("chocolate", 5, 8, 250, 375);
	Item ice = new Item("ice", 2, 5, 225, 325, -10);
	Item iceCream = new Item("ice cream", 8, 14, 175, 250, -20);

	/**
	 * Ensure that creating a truck object does not throw any errors
	 */
	@Test
	public void checkCreation() {
		Truck refrigerated = new RefrigeratedTruck();
	}
	
	/**
	 * Ensure that a Truck object can also be created with a Stock Object
	 * @throws DeliveryException
	 */
	@Test
	public void checkCreationwithStock() throws DeliveryException {
		Stock stock = new Stock();
		stock.addItems(chocolate, 10);
		Truck refrigerated = new RefrigeratedTruck(stock);
	}
	
	/**
	 * Ensure that stock can be added to the truck after creation
	 * @throws DeliveryException
	 */
	@Test
	public void checkAddingStock() throws DeliveryException {
		Truck refrigerated = new RefrigeratedTruck();
		Stock stock = new Stock();
		refrigerated.addStock(stock);		
	}
	 
	/**
	 * Check that the stock contained in the truck is the same as the stock added
	 * to the truck
	 * @throws DeliveryException
	 * @throws StockException
	 */
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
	
	/**
	 * Ensure that a Truck object does not add a Stock object with more quantity than 
	 * the truck's capacity. To ensure this, the Truck object should throw an 
	 * exception to pass the test. 
	 * @throws DeliveryException
	 */
	@Test (expected = DeliveryException.class)
	public void tooMuchStock()  throws DeliveryException{
		Truck refrigerated = new RefrigeratedTruck();	
		Stock stock = new Stock();
		stock.addItems(ice, 1200);
		refrigerated.addStock(stock);
	}
	
	/**
	 * Check that the truck returns the correct cost using the given stock
	 * with a temperature requirement -10
	 * @throws DeliveryException
	 * @throws StockException
	 */
	@Test
	public void checkCost() throws DeliveryException, StockException{
		Stock stock = new Stock();
		stock.addItems(ice);
		int temp = ice.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (double)(temp/5)));
		assertEquals(cost, refrigerated.getCost(), 0);
	}
	
	/**
	 * Check that the truck returns the correct cost for the given stock 
	 * with a temperature requirement of -20
	 * @throws StockException
	 * @throws DeliveryException
	 */
	@Test
	public void checkCost2() throws StockException, DeliveryException {
		Stock stock = new Stock();
		stock.addItems(iceCream);
		int temp = iceCream.getItemTemperature();
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		double cost = (900.0 + 200.0*Math.pow(0.7, (double)(temp/5)));
		assertEquals(cost, refrigerated.getCost(), 0);
	}
	
	/**
	 * Check that the truck returns the correct remaining capacity 
	 * after being loaded with stock
	 * @throws DeliveryException
	 */
	@Test
	public void checkCapacity() throws DeliveryException {	 
		Stock stock = new Stock();
		stock.addItems(chocolate, 700);
		Truck refrigerated = new RefrigeratedTruck();
		refrigerated.addStock(stock);
		assertEquals(100, refrigerated.getRemainingCapacity());
	}
	
	/**
	 * Check that the truck returns the correct remaining capacity after
	 * having stock added to it twice
	 * @throws DeliveryException
	 */
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
	
	/**
	 * Check that the truck produces the appropriate string
	 * in the format used by Manifest. 
	 * For one item the format will be: ">Refrigerated\nitem,quantity\n"
	 * @throws DeliveryException
	 * @throws StockException
	 */
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
