package eecs341.finalProject;

public class PurchaseListEntry {
	
	public int itemID;
	public String name;
	public double price;
	public int quantityBought;
	
	public PurchaseListEntry(int id, String name, double price, int quantity) {
		itemID = id;
		this.name = name;
		this.price = price;
		quantityBought = quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof PurchaseListEntry ? ((PurchaseListEntry)o).itemID == this.itemID : false;
	}
	

}
