package GUI;

import static org.junit.Assert.*;

import org.junit.Test;

import stock.Item;
import stock.Stock;

public class StoreTests {
	
	Item item = new Item("rice", 2, 3, 225, 300);
	Stock stock = new Stock();
	Store store = new Store("Weatherbee", stock, 0);
	
	@Test
	public void checkStoreName() {
		assertEquals("Weatherbee", store.getName());
	}
	
	@Test
	public void checkCaptial() {
		assertEquals(0, store.getCapital());
	}
	
	@Test
	public void checkEmptyStock() {
		assertEquals("", store.getStock());
	}
	
	@Test
	public void checknewCapital() {
		store.addCapital(500);
		assertEquals(500, store.getCapital());
	}
	
	@Test(expected = Exception.class)
	public void removeTooMuchCapital() throws Exception {
		store.removeCapital(400);
	}
	
	@Test
	public void AddAndRemoveCapital() {
		store.addCapital(200);
		store.removeCapital(50);
		assertEquals(150, store.getCapital());
	}
	
	@Test
	public void CheckNonEmptyStock() {
		stock.addItems(item);
		store.addStock(stock);
		assertEquals(item, store.getStock());
	}

	
}
