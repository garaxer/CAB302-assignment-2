package delivery;

import static org.junit.Assert.*;


import org.junit.Test;

import stock.Item;
import stock.Stock;

/**
 * 
 * @author Gary Bagnall
 *
 */
public class TruckTests  {
	
	Item item;
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAbstractMethods() throws DeliveryException {
		Stock mockStock = new Stock();
		MockTruck mock = new MockTruck(mockStock);
		
		assertEquals(0,mock.getCapacity());
		assertEquals(0,mock.getCost(),0);
		assertEquals(null,mock.getStock());
		assertEquals(null,mock.getType());
		assertEquals(null,mock.getManifest());
	}
}
