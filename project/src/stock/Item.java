package stock;

public class Item {
	
	private String name;
	private int manufactureCost;
	private int sellPrice;
	private int reorderPoint;
	private int reorderAmount;
	private int temperature;
	
	public Item(String name, int man, int sell, int reordp, int reorder, int temp) {
		this.name = name;
		this.manufactureCost = man;
		this.sellPrice = sell;
		this.reorderPoint = reordp;
		this.reorderAmount = reorder;
		this.temperature = temp;
	}	
	
	public Item(String name, int man, int sell, int reordp, int reorder) {
		this.name = name;
		this.manufactureCost = man;
		this.sellPrice = sell;
		this.reorderPoint = reordp;
		this.reorderAmount = reorder;
		this.temperature = 1000;
	}

	public String getItemName() {
		return name;
	}
	
	public int getItemCost() {
		return manufactureCost;
	}
	
	public int getItemPrice() {
		return sellPrice;
	}
	
	public int getItemReorderAmount() {
		return reorderAmount;
	}
	
	public int getItemReorderPoint() {
		return reorderPoint;
	}
	
	public int getItemTemperature() {
		return temperature;
	}
}
