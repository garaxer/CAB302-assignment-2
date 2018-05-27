package stock;

/**
 * Item holds the item properties of the various items sold at the store
 * @author Alex Koppon
 *
 */

public class Item {
	
	/**
	 * Create the variables to hold the item's properties
	 */
	private String name;
	private int manufactureCost;
	private int sellPrice;
	private int reorderPoint;
	private int reorderAmount;
	private int temperature;
	
	/**
	 * Constructs an item using all the item properties
	 * @param name
	 * @param man
	 * @param sell
	 * @param reordp
	 * @param reorder
	 * @param temp
	 */
	public Item(String name, int man, int sell, int reordp, int reorder, int temp) {
		this.name = name;
		this.manufactureCost = man;
		this.sellPrice = sell;
		this.reorderPoint = reordp;
		this.reorderAmount = reorder;
		this.temperature = temp;
	}	
	
	/**
	 * Constructs an item without a temperature. To differentiate items which need to be 
	 * stored in a Refrigerated truck, the arbitrary number 1000 was used to denote the temperature
	 * of unrefrigerated items, as no item should accidentally have this temperature requirement. 
	 * @param name
	 * @param man
	 * @param sell
	 * @param reordp
	 * @param reorder
	 */
	public Item(String name, int man, int sell, int reordp, int reorder) {
		this.name = name;
		this.manufactureCost = man;
		this.sellPrice = sell;
		this.reorderPoint = reordp;
		this.reorderAmount = reorder;
		this.temperature = 1000;
	}

	/**
	 * @return the name of the item as a string
	 */
	public String getItemName() {
		return name;
	}
	
	/**
	 * @return the cost of the item as an int
	 */
	public int getItemCost() {
		return manufactureCost;
	}
	
	/**
	 * @return the price of the item as an int
	 */
	public int getItemPrice() {
		return sellPrice;
	}
	
	/**
	 * @return as an int the quantity of the item to reorder when it drops below the reorder point
	 */
	public int getItemReorderAmount() {
		return reorderAmount;
	}
	
	/**
	 * @return as an int the threshold below which the item needs to be reordered
	 */
	public int getItemReorderPoint() {
		return reorderPoint;
	}
	
	/**
	 * @return the temperature the item must be transported at. 
	 * In the case where an item can be transported at any temperature, the returned int is 1000
	 */
	public int getItemTemperature() {
		return temperature;
	}
}
