class Feature {
	
	// Objects and features of rooms that can't be moved or added to the player's inventory.

	protected final String name;	
	protected final Location location;
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

	public Feature()
	{
		this.name = "null";
		this.location = Location.NULL_LOCATION;
		this.method1 = Feature::nullMethod;
		this.keyAction = Action.NULL_ACTION;

	}


	public Feature(String name, Location loc, Runnable r1, Action act)
	{
		this.name = name;
		this.location = loc;
		this.method1 = r1;
		this.keyAction = act;
	}

	public static void nullMethod() {}


	public static void ringBell()
	{
		Game.output("Ding dong ding dong!");
	}

	public static void playPiano()
	{
		Game.output("Da-da Da-da Da Da-da-da DUN...");
	}


}
