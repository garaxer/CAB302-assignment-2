package delivery;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;

/**
 * @ author Alex Koppon
 * 
 */

import org.junit.Test;

import stock.Item;
import stock.Stock;
import stock.StockException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 
 * @author Alex Koppon
 * Class to test Manifest.java
 *
 */
public class ManifestTest {
	
	// Create Items to mimic a real inventory
	Item biscuits = new Item("biscuits",	2,	5,	450,	575);	
	Item nuts = new Item("nuts",	5,	9,	125,	250);	
	Item chips = new Item("chips",	2,	4,	125,	200);	
	Item chocolate = new Item("chocolate",	5,	8,	250,	375);	
	Item bread = new Item("bread",	2,	3,	125,	200);	
	Item mushrooms = new Item("mushrooms",	2,	4,	200,	325,	10);
	Item tomatoes = new Item("tomatoes",	1,	2,	325,	400,	10);
	Item lettuce = new Item("lettuce",	1,	2,	250,	350,	10);
	Item grapes = new Item("grapes",	4,	6,	125,	225,	9);
	Item asparagus = new Item("asparagus",	2,	4,	175, 275,	8);
	Item celery = new Item("celery",	2,	3,	225,	350,	8);
	Item chicken = new Item("chicken",	10,	14,	325,	425,	4);
	Item beef = new Item("beef",	12,	17,	425,	550,	3);
	Item fish = new Item("fish",	13,	16,	375,	475,	2);
	Item yoghurt = new Item("yoghurt",	10,	12,	200,	325,	3);
	Item milk = new Item("milk",	2,	3,	300,	425,	3);
	Item cheese = new Item("cheese",	4,	7,	375,	450,	3);
	Item iceCream = new Item("ice cream",	8,	14,	175,	250,	-20);
	Item ice = new Item("ice",	2,	5,	225,	325,	-10);
	Item frozenMeat = new Item("frozen meat",	10,	14,	450,	575,	-14);
	Item frozenVeg = new Item("frozen vegetable mix",	5,	8,	225,	325,	-12);
	
	// Create objects used by tests
	Stock stock = new Stock();
	Truck ordinary;
	Truck refrigerated;
	
	/**
	 * Before each test set up the stock containing a base amount of each item
	 */
	@Before 
	public void setUp() {
		stock.addItems(biscuits, 500);
		stock.addItems(nuts, 100);
		stock.addItems(chips, 200);
		stock.addItems(chocolate, 300);
		stock.addItems(bread, 180);
		stock.addItems(mushrooms, 210);
		stock.addItems(tomatoes, 360);
		stock.addItems(lettuce, 260);
		stock.addItems(grapes, 170);
		stock.addItems(asparagus, 190);
		stock.addItems(celery, 240);
		stock.addItems(chicken, 440);
		stock.addItems(beef, 440);
		stock.addItems(fish, 400);
		stock.addItems(yoghurt, 210);
		stock.addItems(milk, 330);
		stock.addItems(cheese, 390);
		stock.addItems(iceCream, 150);
		stock.addItems(ice, 280);
		stock.addItems(frozenMeat, 490);
		stock.addItems(frozenVeg, 230);
	}
	
	/**
	 * Test the cost of ordering new goods and transporting them in 
	 * one refrigerated truck. 
	 */
	@Test 
	public void testOneTruckCost() {
		Manifest manifest = new Manifest(stock);
		// calculate the expected cost
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5)));
		double foodCost = (iceCream.getItemCost()*iceCream.getItemReorderAmount()) + (nuts.getItemCost()*nuts.getItemReorderAmount());
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	/**
	 * Check that the stock object being transported by the truck 
	 * contains the correct items and item quantities. 
	 */
	@Test
	public void testStockObjectOneTruck() {
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(nuts, nuts.getItemReorderAmount());
		orderStock.addItems(iceCream, iceCream.getItemReorderAmount());
		Stock expectedStock = manifest.getReorderStock();
		// Compare the item quantities contained in the two Stock objects
		for (Item item : expectedStock.toSet()) {
			if (orderStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	/**
	 * Check that the String returned by manifest is in the correct format
	 */
	@Test
	public void testStringOneTruck() {
		Manifest manifest = new Manifest(stock);
		// Define the various appropriate String outputs
		String manifestString = ">Refrigerated\nnuts,250\nice cream,250\n";
		String manifestString2 = ">Refrigerated\nice cream,250\nnuts,250\n";
		assertTrue((manifestString.equals(manifest.getStockString())) ||(manifestString2.equals(manifest.getStockString()) ));
	}
	
	/**
	 * Test that the cost is calculated correctly using a different set of items
	 * and the two different types of trucks
	 * @throws StockException
	 */
	@Test 
	public void testTwoTruckRefrigandOrd() throws StockException {
		// change the quantity of ice so it falls below the reorder point
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		// calculate the expected cost
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5))) + (750 + 0.25*25);
		double foodCost = ((iceCream.getItemReorderAmount()*iceCream.getItemCost()) + (nuts.getItemReorderAmount()*nuts.getItemCost()) + (ice.getItemReorderAmount()*ice.getItemCost()));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	/**
	 * Test that the stock object returned matches the expected stock object
	 * @throws StockException
	 */
	@Test
	public void testStockObjectTwoTruck() throws StockException {
		// change the quantity of ice so it falls below the reorder point
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		// create the Stock object to test against
		Stock orderStock = new Stock();
		orderStock.addItems(nuts, nuts.getItemReorderAmount());
		orderStock.addItems(iceCream, iceCream.getItemReorderAmount());
		orderStock.addItems(ice, ice.getItemReorderAmount());
		Stock expectedStock = manifest.getReorderStock();
		// Compare the quantities of items in the two Stock objects
		for (Item item : expectedStock.toSet()) {
			if (orderStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	/**
	 * Test that the String produced by manifest matches the correct format and contains the correct
	 * items and quantities
	 * @throws StockException
	 */
	@Test
	public void testStringTwoTruck() throws StockException {
		// change the quantity of ice so it falls below the reorder point
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		// Create the variations of strings which fit the appropriate format
		String manifestString = ">Refrigerated\nnuts,225\nice,325\nice cream,250\n>Ordinary\nnuts,25\n";
		String manifestString2 = ">Refrigerated\nnuts,225\nice cream,250\nice,325\n>Ordinary\nnuts,25\n";
		String manifestString3 = ">Refrigerated\nice cream,250\nice,325\nnuts,250\n>Ordinary\nnuts,25\n";
		String manifestString4 = ">Refrigerated\nice cream,250\nnuts,225\nice,325\n>Ordinary\nnuts,25\n";
		String manifestString5 = ">Refrigerated\nice,325\nnuts,225\nice cream,250\n>Ordinary\nnuts,25\n";
		String manifestString6 = ">Refrigerated\nice,325\nice cream,250\nnuts,225\n>Ordinary\nnuts,25\n";
		assertTrue((manifestString.equals(manifest.getStockString())) ||(manifestString2.equals(manifest.getStockString())) ||
				(manifestString3.equals(manifest.getStockString())) || (manifestString4.equals(manifest.getStockString())) ||
				(manifestString5.equals(manifest.getStockString())) ||(manifestString6.equals(manifest.getStockString())));
	}
	
	/**
	 * Test that manifest returns the correct cost when the stock requires two
	 * refrigerated trucks to transport. 
	 * @throws StockException
	 */
	@Test 
	public void testTwoTruckRefrig() throws StockException {
		// change the quantities of items to test
		stock.addItems(nuts, 100);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
		// calculate the expected cost
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, ((double)-20/5))) + (900.0 + 200.0*Math.pow(0.7, ((double)-14/5)));
		double foodCost = ((250*8) + (575*10) + (325*2));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	/**
	 * Test that the Stock object returned matches the expected stock
	 * @throws StockException
	 */
	@Test
	public void testStockObjectTwoRefrigeratedTrucks() throws StockException {
		// change the quantities of items to test
		stock.addItems(nuts, 100);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
		// create a Stock object with the expected items and quantities
		Stock orderStock = new Stock();
		orderStock.addItems(ice, 325);
		orderStock.addItems(iceCream, 250);
		orderStock.addItems(frozenMeat, 575);
		Stock expectedStock = manifest.getReorderStock();
		for (Item item : expectedStock.toSet()) {
			if (orderStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	

}
