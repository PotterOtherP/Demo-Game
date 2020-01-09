class Item {

	// Items that can be picked up and moved

	protected final String name;
	
	private Location location;

	protected final Runnable method1;

	protected final Action keyAction;

	public void activate(Runnable actMethod, Action act)
	{
		if (act == keyAction)
		{
			actMethod.run();
			
		}
		else
		{
			Game.output("That's not something you can do to the " + name + ".");
		}
	}

	public Item()
	{
		this.name = "null";
		this.location = Location.NULL_LOCATION;
		this.method1 = GameState::nullMethod;
		this.keyAction = Action.NULL_ACTION;

	}

	public Item(String name, Location loc)
	{
		this.name = name;
		this.location = loc;
		this.method1 = GameState::nullMethod;
		this.keyAction = Action.NULL_ACTION;
	}

	public Item(String name, Location loc, Runnable r1, Action act)
	{
		this.name = name;
		this.location = loc;
		this.method1 = r1;
		this.keyAction = act;
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