package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
import stock.Stock;


/**
 * 
 * @author Gary Bagnall
 *
 */
public class OrdinaryTruckTests {
	
	/* Test 0: Declaring ordinaryTruck objects
	 */
	Truck ordinaryTruck;
	Item item;
	Stock stock;
	
	/* Test 1: Constructing a ordinaryTruck object with a stock
	 */
	@Before @Test
	public void setUpOrdinaryTruck() throws DeliveryException {
		item = new Item("Test",50,100,5,25);
		stock = new Stock();
		stock.addItems(item,500);
		ordinaryTruck = new OrdinaryTruck(stock);
	}
	

	/* Test 2: See if the Truck can return the correct Capacity. - Might not be used, added just in case
	 */
	@Test
	public void testCapacity() throws DeliveryException{
		int capacity = 1000;
		assertEquals(capacity, ordinaryTruck.getCapacity());
	}
	
	/* Test 3: See if the Truck can return it's type as a string (Can be an abstract method)
	 */
	@Test
	public void testType(){
		String type = "Ordinary";
		assertEquals(type, ordinaryTruck.getType());
	}
		
	/* Test 4: See if the Truck can return the correct Cost.
	 */
	@Test
	public void testCost() { 
		double cost = 875; //750 + 0.25*500
		assertEquals(cost, ordinaryTruck.getCost(),0);
	}
	
	/* Test 5: See if the Truck can *deliver the items* 
	 * return a Stock - a collection of items
	 */
	@Test
	public void testGetStock(){
		assertEquals(stock, ordinaryTruck.getStock());
	}
	
	/**
	 * 	Test 7: Error if going over capacity
	 * @throws DeliveryException
	 */
	@Test(expected = DeliveryException.class)
	public void setUpOverCapacityTruck() throws DeliveryException {
		stock.addItems(item,501); //In addition to the 500 in stock
		ordinaryTruck = new OrdinaryTruck(stock);
	}
	
	/**
	 * 	Test 8: Error if adding a Stock with temperature controlled good(s)
	 * @throws DeliveryException
	 */
	@Test(expected = DeliveryException.class)
	public void setColdTruck() throws DeliveryException {
		Item item2 = new Item("Test",50,100,5,25,-1);
		stock.addItems(item2,100); 
		ordinaryTruck = new OrdinaryTruck(stock);
	}

	/* Test 5: See if the Truck can return the item and quantity as a string
	 */
	@Test
	public void testGetManifest() throws DeliveryException{
		String list = ">Ordinary\n" + 
				"cookies,10\n" + 
				"biscuits,50\n";
		Item cookies = new Item("cookies",50,100,5,25);
		Item biscuits = new Item("biscuits",50,100,5,25);
		stock = new Stock();
		stock.addItems(cookies,10);
		stock.addItems(biscuits,50);
		ordinaryTruck = new OrdinaryTruck(stock);			
		assertEquals(list, ordinaryTruck.getManifest());
	}
}
