// Method for an actor's "turn"
interface ActorMethod {

	public void actorUpdate();
}

class Actor extends GameObject {
	
	public final Location startLocation;
	public Location currentLocation;
	public Location previousLocation;


	public final ActorMethod actorMethod;
	
	private boolean alive;


	// Constructors. An actor must be either empty or have a location, activation method
	// and its own actor method.
	public Actor()
	{
		super();
		alive = false;
		this.startLocation = Location.NULL_LOCATION;
		this.currentLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.actorMethod = () -> {};
	}


	public Actor(String name, Location loc, ActivateMethod am, ActorMethod actm)
	{
		super(name, loc, am);
		alive = false;
		this.startLocation = super.location;
		this.currentLocation = super.location;
		this.previousLocation = super.location;
		this.actorMethod = actm;
	}


	public void actorTurn()
	{
		actorMethod.actorUpdate();
	}



	public void setAlive(boolean b) { alive = b; }
	public boolean isAlive() { return alive; }


}