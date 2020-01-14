class Actor extends Feature {
	
	public Location startingLocation;
	public Location currentLocation;
	public Location previousLocation;

	
	public void move()
	{
		int number = (int)(Math.random()*4);
		// Game.output("The " + name + "'s random number is " + number);
	}

	public Actor()
	{
		super();
	}

	public Actor(String name, Location loc)
	{
		super(name, loc);
	}



}