class Feature {
	
	// Objects and features of rooms that can't be moved or added to the player's inventory.

	protected final String name;	
	protected final Location location;
	protected final Runnable method1;

	public void activate(Runnable actMethod)
	{
		actMethod.run();
	}

	public Feature()
	{
		this.name = "";
		this.location = Location.NULL_LOCATION;
		this.method1 = Feature::dummyMethod;

	}


	public Feature(String name, Location loc, Runnable r1)
	{
		this.name = name;
		this.location = loc;
		this.method1 = r1;
	}

	public static void dummyMethod()
	{
		Game.output("Dummy method");
	}


	public static void RingBell()
	{
		Game.output("Ding dong ding dong!");
	}



}
