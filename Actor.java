class Actor {
	
	private final Location startingLocation;
	private Location currentLocation;
	private Location previousLocation;

	protected final String name;

	public void move()
	{
		int number = (int)(Math.random()*4);
		// Game.output("The " + name + "'s random number is " + number);
	}

	public Actor()
	{
		this.currentLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.startingLocation = Location.NULL_LOCATION;
		this.name = "";
	}

	public Actor(Location loc, String name)
	{
		this.startingLocation = loc;
		this.name = name;
	}



}