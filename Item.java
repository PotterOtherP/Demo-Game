class Item {

	// Items that can be picked up and moved

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

	public boolean vowelStart()
	{
		boolean result = false;

		String str = itemName.toLowerCase();
		char c  = str.charAt(0);

		switch(c)
		{
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
			{
				result = true;
			} break;

			default:
			{
				result = false;
			} break;
		}

		return result;
	}


}