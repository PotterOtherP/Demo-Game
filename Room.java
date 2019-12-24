class Room {

	protected final String name;
	protected final String fullDescription;
	protected final Location roomID;
	protected final Location northExit;
	protected final Location southExit;
	protected final Location eastExit;
	protected final Location westExit;
	protected final Location upExit;
	protected final Location downExit;

	protected boolean firstVisit;
	protected boolean northExitOpen;
	protected boolean southExitOpen;
	protected boolean eastExitOpen;
	protected boolean westExitOpen;
	protected boolean upExitOpen;
	protected boolean downExitOpen;

	public Room()
	{
		name = "";
		fullDescription = "";
		roomID = Location.NULL_LOCATION;
		northExit = Location.NULL_LOCATION;
		southExit = Location.NULL_LOCATION;
		eastExit = Location.NULL_LOCATION;
		westExit = Location.NULL_LOCATION;
		upExit = Location.NULL_LOCATION;
		downExit = Location.NULL_LOCATION;
		firstVisit = true;
		this.northExitOpen = true;
		this.southExitOpen = true;
		this.eastExitOpen = true;
		this.westExitOpen = true;
		this.upExitOpen = true;
		this.downExitOpen = true;
	}

	public Room(String name, String fullDesc, Location id, Location north, Location south, Location east, Location west, Location up, Location down)
	{
		this.name = name;
		this.fullDescription = fullDesc;
		this.roomID = id;
		this.northExit = north;
		this.southExit = south;
		this.eastExit = east;
		this.westExit = west;
		this.upExit = up;
		this.downExit = down;
		this.firstVisit = true;
		this.northExitOpen = true;
		this.southExitOpen = true;
		this.eastExitOpen = true;
		this.westExitOpen = true;
		this.upExitOpen = true;
		this.downExitOpen = true;
	}


	
}