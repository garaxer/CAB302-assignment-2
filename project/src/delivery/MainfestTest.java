package delivery;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 * @ author Alex Koppon
 * 
 */

import org.junit.Test;

import stock.Item;
import stock.Stock;

public class MainfestTest {
	
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
	
	Stock stock = new Stock();
	Truck ordinary;
	Truck refrigerated;
	
	@Before 
	public void setUp() {
		stock.addItems(biscuits, 200);
		stock.addItems(nuts, 220);
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
	
	@Test 
	public void testOneTruckCost() {
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5)));
		double foodCost = ((250*8) + (575*2));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost());
	}
	
	@Test
	public void testStockObjectOneTruck() {
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(biscuits, 575);
		orderStock.addItems(iceCream, 250);
		assertEquals(orderStock, manifest.getReorderStock());
	}
	
	@Test
	public void testStringOneTruck() {
		Manifest manifest = new Manifest(stock);
		String manifestString = ">Refrigerated\nbiscuits,575\nice cream,250\n";
		String manifestString2 = ">Refrigerated\nice cream,250\nbiscuits,575\n";
		assertTrue((manifestString.equals(manifest.getStockString())) ||(manifestString2.equals(manifest.getStockString()) ));
	}
	
	@Test 
	public void testTwoTruckRefrigandOrd() {
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5))) + (750 + 0.25*350);
		double foodCost = ((250*8) + (575*2) + (325*2));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost());
	}
	
	@Test
	public void testStockObjectTwoTruck() {
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(biscuits, 575);
		orderStock.addItems(iceCream, 250);
		orderStock.addItems(ice, 325);
		assertEquals(orderStock, manifest.getReorderStock());
	}
	
	@Test
	public void testStringTwoTruck() {
		Manifest manifest = new Manifest(stock);
		String manifestString = ">Refrigerated\nbiscuits,575\nice cream,250\nice,325\n";
		String manifestString2 = ">Refrigerated\nice cream,250\nbiscuits,575\nice,325\n";
		String manifestString3 = ">Refrigerated\nice,325\nice cream,250\nbiscuits,575\n";
		String manifestString4 = ">Refrigerated\nice cream,250\nice,325\nbiscuits,575\n";
		String manifestString5 = ">Refrigerated\nice cream,250\nbiscuits,575\n";
		String manifestString6 = ">Refrigerated\nice cream,250\nbiscuits,575\n";
		assertTrue((manifestString.equals(manifest.getStockString())() ||(manifestString2.equals(manifest.getStockString())) );
	}
	
	@Test 
	public void testTwoTruckRefrig() {
		stock.addItems(biscuits, 300);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5))) + (900.0 + 200.0*Math.pow(0.7, (-14/5)));
		double foodCost = ((250*8) + (575*10) + (325*2));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost());
	}
	
	@Test
	public void testStockObjectOneTruck() {
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(ice, 325);
		orderStock.addItems(iceCream, 250);
		orderStock.addItems(frozenMeat, 575);
		assertEquals(orderStock, manifest.getReorderStock());
	}
	
	@Test
	public void testStringOneTruck() {
		Manifest manifest = new Manifest(stock);
		String manifestString = ">Refrigerated\nfrozen meat,575\nice cream,250\nice,325\n";
		String manifestString2 = ">Refrigerated\nice cream,250\nbiscuits,575\n";
		assertTrue((manifestString.equals(manifest.getStockString())) ||(manifestString2 == manifest.getStockString()) );
	}
	


	/*
	@Test
	public void testSalesAmount() {
		stock.addItems(ice, 200);
		stock.addItems(iceCream, 200);
		stock.addItems(chocolate, 200);
		manifest = new Manifest(stock);
		double profit = (200*8) + (200*5) + (200*14);
		assertEquals(profit, manifest.getSalesAmount());
	}
	
	@Test
	public void testSalesAmount2() {
		stock.addItems(iceCream, 100);
		stock.addItems(chocolate, 1100);
		manifest = new Manifest(stock);
		double profit = (1100*8) + (100*14);
		assertEquals(profit, manifest.getSalesAmount());
		
	}
	
	@Test
	public void testSalesAmountEmptyStock() {
		manifest = new Manifest(stock);
		double profit = 0;
		assertEquals(profit, manifest.getSalesAmount());
		
	}
	*/
	
	@Test 
	public void testOrderCost() {
		stock.addItems(ice, 200);
		stock.addItems(iceCream, 200);
		stock.addItems(chocolate, 200);
		manifest = new Manifest(stock);
		double cost = (200*5) + (200*2) + (200*8);
		assertEquals(cost, manifest.getReorderCost());
	}
	
	@Test 
	public void testOrderCost2() {
		stock.addItems(iceCream, 100);
		stock.addItems(chocolate, 1100);
		manifest = new Manifest(stock);
		double cost = (1100*5) + (100*8);
		assertEquals(cost, manifest.getReorderCost());
	}
	
	@Test 
	public void testOrderCostEmptyStock() {
		manifest = new Manifest(stock);
		double cost = 0;
		assertEquals(cost, manifest.getReorderCost());
	}
	
	@Test
	public void testReorderAmount() {
		stock.addItems(ice, 200);
		stock.addItems(iceCream, 150);
		stock.addItems(chocolate, 200);
		manifest = new Manifest(stock);
		Stock newStock = new Stock();
		newStock.addItems(ice, 235);
		newStock.addItems(iceCream, 250);
		newStock.addItems(chocolate, 375);
		assertEquals(newStock, manifest.getReorderStock());
	}
	
	@Test 
	public void testReorderAmount2() {
		stock.addItems(ice, 200);
		stock.addItems(iceCream, 220);
		stock.addItems(chocolate, 200);
		manifest = new Manifest(stock);
		Stock newStock = new Stock();
		newStock.addItems(ice, 235);
		newStock.addItems(chocolate, 375);
		assertEquals(newStock, manifest.getReorderStock());		
	}
	
	@Test 
	public void testUnderReorderAmount() {
		stock.addItems(ice, 300);
		stock.addItems(iceCream, 300);
		stock.addItems(chocolate, 300);
		manifest = new Manifest(stock);
		Stock newStock = new Stock();
		assertEquals(newStock, manifest.getReorderStock());		
	}
	
	/**@Test 
	public void testSalesLogStock() {
		
	}
	
	@Test 
	public void testSalesLogStock2() {
		
	}
	**/

}
