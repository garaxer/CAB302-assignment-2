package GUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
import stock.Stock;
import stock.StockException;

public class StoreTests {
	
	Item rice = new Item("rice", 2, 3, 225, 300);
	Item yoghurt = new Item("yoghurt",	10,	12,	200,	325,	3);
	Stock stock = new Stock();
	Store store = Store.getInstance();
	
	@Before 
	public void setUpStore() {
		store.setName("Weatherbee");
		store.inventory = stock;
		store.capital = 0.00;
	}
	
	@Test
	public void checkStoreName() {
		assertEquals("Weatherbee", store.getName());
	}
	
	@Test
	public void checkCaptial() {
		assertEquals(0.00, store.getCapital(),0);
	}
	
	@Test
	public void checkEmptyStock() {
		assertTrue("".equals(store.getStock().getList()));
	}
	
	@Test
	public void checknewCapital() {
		store.addCapital(500);
		assertEquals(500.00, store.getCapital(),0);
	}
	
	@Test
	public void AddAndRemoveCapital() {
		store.addCapital(200);
		store.removeCapital(50);
		assertEquals(150.00, store.getCapital(),0);
	}
	
	@Test
	public void CheckNonEmptyStock() throws StockException {
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 300);
		store.addStock(toAdd);
		Stock newStock = new Stock();
		newStock.addItems(rice, 50);
		newStock.addItems(yoghurt, 300);
		Stock expectedStock = store.getStock();
		for (Item item : expectedStock.toSet()) {
			if (newStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	@Test
	public void testStockManifest() {
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		store.generateManifest();
		Stock expectedStock = new Stock();
		expectedStock.addItems(rice, rice.getItemReorderAmount()+50);
		expectedStock.addItems(yoghurt, yoghurt.getItemReorderAmount()+150);
		for (Item item : expectedStock.toSet()) {
			if (store.getStock().getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}		
	}
	
	@Test 
	public void testCapitalManifest() {
		Stock toAdd = new Stock();
		//toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		store.capital = 100000.00;
		store.generateManifest();
		double estimatedCapital = store.getCapital();
		double Capital = yoghurt.getItemReorderAmount()*yoghurt.getItemCost(); //+ rice.getItemReorderAmount()*rice.getItemCost();
		double exponent = (double)(yoghurt.getItemTemperature()/5);
		double Capital2 = (900 + (200 * Math.pow(0.7, exponent))) + Capital;
		double newCapital = 100000.00 - Capital2;
		assertEquals(newCapital, estimatedCapital, 0);
	}
	

	
}
