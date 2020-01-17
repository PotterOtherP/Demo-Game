class Actor extends Feature {
	
	public Location startingLocation;
	public Location currentLocation;
	public Location previousLocation;

	public boolean activated;

	public ActorMethod actM;
	


	public Actor()
	{
		super();
		activated = false;
		this.actM = () -> {};
	}

	public Actor(String name, Location loc)
	{
		super(name, loc);
		activated = false;
		this.actM = () -> {};
	}

	public void actorTurn()
	{
		actM.actorUpdate();
	}

}