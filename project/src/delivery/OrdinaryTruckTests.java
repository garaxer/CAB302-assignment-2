package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Gary Bagnall
 *
 */
public class OrdinaryTruckTests {
	
	/* Test 0: Declaring ordinaryTruck objects
	 */
	Truck ordinaryTruck;
	
	/* Test 1: Constructing a ordinaryTruck object
	 */
	@Before
	public void setUpOrdinaryTruck() {
		ordinaryTruck = new OrdinaryTruck();
	}
	

	/* Test 2: See if the Truck can return the correct Capacity.
	 */
	@Test
	public void testCapacity() {
		ordinaryTruck = new OrdinaryTruck();
		int capacity = 1000;
		assertEquals(capacity, ordinaryTruck.getCapacity());
	}
	
	/* Test 4: See if the Truck can add stock
	 */
	@Test
	public void testAddStock() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck();
		Item item = new item("Test",50,100,5,25);
		ordinaryTruck.addItems(item,100);
		assertEquals(cost, ordinaryTruck.getCost());
	}
	
	/* Test 4: See if the Truck can return the correct Cost.
	 */
	@Test
	public void testCost() {
		ordinaryTruck = new OrdinaryTruck();
		Item item = new item("Test",50,100,5,25);
		Stock stock = new Stock();
		stock.addItem(item);
		ordinaryTruck.addStock(stock);
		int cost = 775; //750 + 0.25*100
		assertEquals(cost, ordinaryTruck.getCost());
	}
	
	/* Test 5: See if the Truck can *deliver the items* 
	 * return a Stock - a collection of items
	 */
	
	
	/**
	 * 	Test 7: Error if going over capacity
	 * @throws DeliveryException
	 */
	
	/**
	 * 	Test 8: Error if adding a Stock with temperature controlled good
	 * @throws DeliveryException
	 */
	@Test(expected = DeliveryException.class)
	public void testTemparatureGoods() throws DeliveryException {
		ordinaryTruck = new OrdinaryTruck();
		//Item item = new item("Test",50,100,5,25,0);
		New stock
		ordinaryTruck.addStock(item,1);
	}
	

}
