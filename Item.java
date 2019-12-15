class Item {

	protected final String itemName;
	
	private Location itemLocation;
	private boolean inInventory;

	public Item(String name, Location loc)
	{
		this.itemName = name;
		this.itemLocation = loc;
		this.inInventory = false;
	}



	public void setLocation(Location loc) { itemLocation = loc; }
	public Location getLocation() { return itemLocation; }
	public void addToInventory() { inInventory = true; }
	public void removeFromInventory() { inInventory = false; }
	public boolean isInInventory() { return inInventory; }


}