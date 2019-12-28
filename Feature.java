class Feature {
	
	// Objects and features of rooms that can't be moved or added to the player's inventory.

	protected final String name;	
	protected final Location location;

	public Feature()
	{
		this.name = "";
		this.location = Location.NULL_LOCATION;
	}


	public Feature(String name, Location loc)
	{
		this.name = name;
		this.location = loc;
	}
}