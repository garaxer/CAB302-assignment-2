package GUI;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import delivery.Manifest;
import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * Tests the class Store.java
 * @author Alex Koppon
 *
 */

public class StoreTests {
	
	// Set up some variables to test the store with
	Item rice = new Item("rice", 2, 3, 225, 300);
	Item yoghurt = new Item("yoghurt",	10,	12,	200,	325,	3);
	Stock stock = new Stock();
	Store store = Store.getInstance();
	
	/**
	 * Before each test set up the store with its name, $0.00 in capital and an empty Stock
	 * As this is a singleton class, this 'reset' helps test the class, as changes otherwise 
	 * persist through tests
	 */
	@Before 
	public void setUpStore() {
		store.setName("Weatherbee");
		store.inventory = stock;
		store.capital = 0.00;
	}
	
	/**
	 * Check that the store returns the correct name
	 */
	@Test
	public void checkStoreName() {
		assertEquals("Weatherbee", store.getName());
	}
	
	/**
	 * Check that the store returns the correct capital
	 */
	@Test
	public void checkCaptial() {
		assertEquals(0.00, store.getCapital(),0);
	}
	
	/**
	 * check that the stock is initially empty
	 */
	@Test
	public void checkEmptyStock() {
		assertTrue("".equals(store.getStock().getList()));
	}
	
	/**
	 * Check that the name is changed after setting a new name
	 */
	@Test
	public void testNameChange() {
		store.setName("Hello World");
		assertEquals("Hello World", store.getName());
	}
	
	/**
	 * Check that adding capital changes the store's capital
	 * by the correct amount
	 */
	@Test
	public void checknewCapital() {
		store.addCapital(500);
		assertEquals(500.00, store.getCapital(),0);
	}
	
	/**
	 * Check that the String of the capital returns correctly 
	 * after some capital is added
	 */
	@Test
	public void checknewCapitalString() {
		store.addCapital(500);
		assertEquals("$500.00", store.getCapitalString());
	}
	
	/**
	 * Check that the store returns the correct capital after both adding
	 * and removing capital from the store
	 */
	@Test
	public void AddAndRemoveCapital() {
		store.addCapital(200);
		store.removeCapital(50);
		assertEquals(150.00, store.getCapital(),0);
	}
	
	/**
	 * Check that the store's stock matches the items and quantities
	 * of the stock added to it
	 */
	@Test
	public void CheckNonEmptyStock() {
		// Create stock item to add to the store
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 300);
		// Add stock to the store
		store.addStock(toAdd);
		Stock expectedStock = store.getStock();
		// Compare stock items
		for (Item item : expectedStock.toSet()) {
			if (toAdd.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	/**
	 * Check that generating a manifest produces the correct stock String
	 */
	@Test
	public void testStockManifest() {
		// Create stock to add
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		Manifest expected = new Manifest(toAdd);
		assertTrue(expected.getStockString().equals(store.generateManifest()));
	}
	
	/**
	 * Check that importing a manifest correctly updates the store's capital
	 */
	@Test 
	public void testCapitalManifest() {
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 50);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		store.capital = 100000.00;
		Manifest expected = new Manifest(toAdd);
		store.importManifest(expected);
		double Capital = yoghurt.getItemReorderAmount()*yoghurt.getItemCost() + rice.getItemReorderAmount()*rice.getItemCost();
		double exponent = (double)yoghurt.getItemTemperature()/5;
		double Capital2 = (900 + (200 * Math.pow(0.7, exponent))) + Capital;
		double newCapital = 100000.00 - Capital2;
		assertEquals(newCapital, store.getCapital(), 0);
	}
	
	/**
	 * Check that loading in sales correctly increases the capital
	 * of the store
	 */
	@Test 
	public void testLoadSalesCost() throws StockException {
		// Set up the store with stock
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 112);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		// Create a HashMap object or organise sales
		HashMap<String, Integer> salesLog = new HashMap<String, Integer>();
		salesLog.put("rice", 50);
		salesLog.put("yoghurt", 12);
		store.loadSales(salesLog);
		double capitalIncrease = yoghurt.getItemPrice()*12 + rice.getItemPrice()*50;
		assertEquals(capitalIncrease, store.getCapital(), 0);		
	}
	
	/**
	 * Check that the correct amount of stock is in the store after
	 * loading in sales
	 * @throws StockException
	 */
	@Test
	public void testLoadSalesStock() throws StockException {
		// Set up the store with stock
		Stock toAdd = new Stock();
		toAdd.addItems(rice, 112);
		toAdd.addItems(yoghurt, 150);
		store.addStock(toAdd);
		// Create a HashMap object or organise sales
		HashMap<String, Integer> salesLog = new HashMap<String, Integer>();
		salesLog.put("rice", 50);
		salesLog.put("yoghurt", 12);
		Stock expectedStock = new Stock();
		expectedStock.addItems(rice, 62);
		expectedStock.addItems(yoghurt, 138);
		store.loadSales(salesLog);
		for (Item item : toAdd.toSet()) {
			if (store.getStock().getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}	
		
	}
	
	/**
	 * Check that the stock is correct after loading in a manifest
	 * @throws StockException
	 */
	@Test
	public void testImportInventory() throws StockException {
		ArrayList<String[]> inventory = new ArrayList<String[]>();
		String[] item = {"yoghurt", "10", "12", "200", "325", "3"};
		inventory.add(item);
		Stock expectedStock = new Stock();
		expectedStock.addItems(yoghurt);
		store.loadInventory(inventory);
		for (Item stockItem : store.getStock().toSet()) {
			if (store.getStock().getQuantity(stockItem) != expectedStock.getQuantity(stockItem) ) {
				fail();
			}
		}
	}
	
	

	
}
