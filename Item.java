class Item {

	// Items that can be picked up and moved

	protected final String itemName;
	
	private Location itemLocation;

	public Item()
	{
		this.itemName = "";
		this.itemLocation = Location.NULL_LOCATION;

	}

	public Item(String name, Location loc)
	{
		this.itemName = name;
		this.itemLocation = loc;
	}



	public void setLocation(Location loc) { itemLocation = loc; }
	public Location getLocation() { return itemLocation; }

	public boolean vowelStart()
	{
		// Exceptions can go here
		if (false)
		{
			return true;
		}

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