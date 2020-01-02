class Room {
	
	protected final String name;
	protected final String description;
	protected final Location roomLoc;

	protected boolean firstVisit;


	public Door northExit;
	public Door southExit;
	public Door eastExit;
	public Door westExit;


	public Room()
	{
		this.name = "";
		this.description = "";
		this.roomLoc = null;
		this.northExit = null;
		this.southExit = null;
		this.eastExit = null;
		this.westExit = null;
		this.firstVisit = true;

	}

	public Room(String name, String desc, Location loc, Door north, Door south, Door east, Door west)
	{
		this.name = name;
		this.description = desc;
		this.roomLoc = loc;
		this.northExit = north;
		this.southExit = south;
		this.eastExit = east;
		this.westExit = west;
		this.firstVisit = true;
	}


	public boolean exit(GameState state, Action act)
	{
		Door d = null;
		boolean result = false;
		Location dest = Location.NULL_LOCATION;
		String failString = StringList.CANTGO;


		switch(act)
		{
			case EXIT_NORTH:
			{
				d = northExit;
			} break;

			case EXIT_SOUTH:
			{
				d = southExit;
			} break;

			case EXIT_EAST:
			{
				d = eastExit;
			} break;

			case EXIT_WEST:
			{
				d = westExit;
			} break;

			default:
			{

			} break;
		}

		if (d == null)
		{
			result = false;
			Game.output(failString);
		}

		else
		{
			if (d.locationA == this.roomLoc)
			{
				dest = d.locationB;
			}
			else
			{
				dest = d.locationA;
			}

			state.setPreviousLocation(state.getCurrentLocation());
			state.setCurrentLocation(dest);
			result = true;
		}

		return result;
	}

}