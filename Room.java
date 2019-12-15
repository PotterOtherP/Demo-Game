class Room {

	protected final String roomName;
	protected final String fullDescription;
	protected final Location roomID;
	protected final Location northExit;
	protected final Location southExit;
	protected final Location eastExit;
	protected final Location westExit;



	public Room()
	{
		roomName = "";
		fullDescription = "";
		roomID = Location.NULL_LOCATION;
		northExit = Location.NULL_LOCATION;
		southExit = Location.NULL_LOCATION;
		eastExit = Location.NULL_LOCATION;
		westExit = Location.NULL_LOCATION;
	}

	public Room(String name, String fullDesc, Location id, Location north, Location south, Location east, Location west)
	{
		this.roomName = name;
		this.fullDescription = fullDesc;
		this.roomID = id;
		this.northExit = north;
		this.southExit = south;
		this.eastExit = east;
		this.westExit = west;
	}


	
}