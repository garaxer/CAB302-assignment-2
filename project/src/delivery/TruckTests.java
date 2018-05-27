package delivery;

import static org.junit.Assert.*;
import org.junit.Test;
import stock.Stock;

/**
 * Unit test for the truck class
 * @author Gary Bagnall
 *
 */
public class TruckTests  {
	
	/**
	 * Creates a mock truck to see if truck has the required Abstract methods
	 * @throws DeliveryException
	 */
	@Test
	public void testAbstractMethods() throws DeliveryException {
		Stock mockStock = new Stock();
		MockTruck mock = new MockTruck(mockStock);
		
		assertEquals(5,mock.getCapacity());
		assertEquals(0,mock.getCost(),0);
		assertEquals(null,mock.getStock());
		assertEquals(null,mock.getType());
		assertEquals(null,mock.getManifest());
		assertEquals(0,mock.getRemainingCapacity());
	}
}
