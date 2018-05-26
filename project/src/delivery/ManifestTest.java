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

public class ManifestTest {
	
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
	
	@Test 
	public void testOneTruckCost() {
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5)));
		double foodCost = (iceCream.getItemCost()*iceCream.getItemReorderAmount()) + (nuts.getItemCost()*nuts.getItemReorderAmount());
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	@Test
	public void testStockObjectOneTruck() {
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(nuts, nuts.getItemReorderAmount());
		orderStock.addItems(iceCream, iceCream.getItemReorderAmount());
		Stock expectedStock = manifest.getReorderStock();
		for (Item item : expectedStock.toSet()) {
			if (orderStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	@Test
	public void testStringOneTruck() {
		Manifest manifest = new Manifest(stock);
		String manifestString = ">Refrigerated\nnuts,250\nice cream,250\n";
		String manifestString2 = ">Refrigerated\nice cream,250\nnuts,250\n";
		assertTrue((manifestString.equals(manifest.getStockString())) ||(manifestString2.equals(manifest.getStockString()) ));
	}
	
	@Test 
	public void testTwoTruckRefrigandOrd() throws StockException {
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5))) + (750 + 0.25*25);
		double foodCost = ((iceCream.getItemReorderAmount()*iceCream.getItemCost()) + (nuts.getItemReorderAmount()*nuts.getItemCost()) + (ice.getItemReorderAmount()*ice.getItemCost()));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	@Test
	public void testStockObjectTwoTruck() throws StockException {
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
		Stock orderStock = new Stock();
		orderStock.addItems(nuts, nuts.getItemReorderAmount());
		orderStock.addItems(iceCream, iceCream.getItemReorderAmount());
		orderStock.addItems(ice, ice.getItemReorderAmount());
		Stock expectedStock = manifest.getReorderStock();
		for (Item item : expectedStock.toSet()) {
			if (orderStock.getQuantity(item) != expectedStock.getQuantity(item) ) {
				fail();
			}
		}
	}
	
	@Test
	public void testStringTwoTruck() throws StockException {
		stock.removeItems(ice, 200);
		Manifest manifest = new Manifest(stock);
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
	
	@Test 
	public void testTwoTruckRefrig() throws StockException {
		stock.addItems(nuts, 100);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
		double TruckCost = (900.0 + 200.0*Math.pow(0.7, (-20/5))) + (900.0 + 200.0*Math.pow(0.7, (-14/5)));
		double foodCost = ((250*8) + (575*10) + (325*2));
		double totalCost = TruckCost + foodCost;	
		assertEquals(totalCost, manifest.getTotalCost(), 0);
	}
	
	@Test
	public void testStockObjectTwoRefrigeratedTrucks() throws StockException {
		stock.addItems(nuts, 100);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
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
	
	@Test
	public void testStringTwoRefrigeratedTrucks() throws StockException {
		stock.addItems(nuts, 100);
		stock.removeItems(ice, 200);
		stock.removeItems(frozenMeat, 200);
		Manifest manifest = new Manifest(stock);
		String manifestString = ">Refrigerated\nfrozen meat,550\nice cream,250\n>Refrigerated\nice,325\nfrozen meat,25\n";
		assertTrue((manifestString.equals(manifest.getStockString())));
	}
	

}
