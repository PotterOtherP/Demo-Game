class Actor extends Feature {
	
	public Location startingLocation;
	public Location currentLocation;
	public Location previousLocation;

	public boolean activated;
	
	public void move()
	{
		int number = (int)(Math.random()*4);
		// Game.output("The " + name + "'s random number is " + number);
	}

	public Actor()
	{
		super();
		activated = false;
	}

	public Actor(String name, Location loc)
	{
		super(name, loc);
		activated = false;
	}



}