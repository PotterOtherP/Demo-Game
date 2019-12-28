class Item {

	// Items that can be picked up and moved

	protected final String name;
	
	private Location location;

	public Item()
	{
		this.name = "";
		this.location = Location.NULL_LOCATION;

	}

	public Item(String name, Location loc)
	{
		this.name = name;
		this.location = loc;
	}



	public void setLocation(Location loc) { location = loc; }
	public Location getLocation() { return location; }

	public boolean vowelStart()
	{
		// Exceptions can go here
		if (false)
		{
			return true;
		}

		boolean result = false;

		String str = this.name.toLowerCase();
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