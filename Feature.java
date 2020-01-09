class Feature extends GameObject{
	
	// Objects and features of rooms that can't be moved or added to the player's inventory.


	public Feature()
	{
		super();
	}


	public Feature(String name, Location loc, Runnable r1, Runnable r2, Runnable r3, Action act1, Action act2, Action act3)
	{
		super(name, loc, r1, r2, r3, act1, act2, act3);
	}

	


}
