class Feature {

	/* A feature is an object that exists in a room. It can have up to three activation methods
	   with corresponding activation actions.


	*/
	
	public final String name;
	public FeatureMethod ft;


	public Location location;

	public void setLocation(Location loc) { location = loc; }
	public Location getLocation() { return location; }


	// default constructor
	public Feature()
	{
		this.name = "null";
		this.location = Location.NULL_LOCATION;
		this.ft = (act) -> {};
	}

	// Constructor with no unique methods
	public Feature(String name, Location loc)
	{
		this.name = name;
		this.location = loc;
		this.ft = (act) -> {};
	}

	public Feature(String name, Location loc, FeatureMethod fm)
	{
		this.name = name;
		this.location = loc;
		this.ft = fm;
	}


	public void activate(Action act)
	{
		ft.outputMessage(act);
	}

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

