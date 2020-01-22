// Method for an actor's "turn"
interface ActorMethod {

	public void actorUpdate();
}

class Actor extends GameObject {
	
	public Location startingLocation;
	public Location currentLocation;
	public Location previousLocation;

	public boolean activated;

	public ActorMethod actorMethod;
	


	public Actor()
	{
		super();
		activated = false;
		this.actorMethod = () -> {};
	}

	public Actor(String name)
	{
		super(name);
		activated = false;
		this.actorMethod = () -> {};
	}

	public Actor(String name, Location loc)
	{
		super(name, loc);
		activated = false;
		this.actorMethod = () -> {};
	}

	public Actor(String name, Location loc, ActivateMethod am)
	{
		super(name, loc, am);
		activated = false;
		this.actorMethod = () -> {};
	}



	public void actorTurn()
	{
		actorMethod.actorUpdate();
	}

}