class Door extends GameObject {
	
	private boolean locked;
	private boolean open;


	public final Location locationA;
	public final Location locationB;
	public final Item keyItem;


	// Constructors
	public Door()
	{
		super();
		this.locationA = Location.NULL_LOCATION;
		this.locationB = Location.NULL_LOCATION;
		this.keyItem = new Item();
		this.locked = true;
		this.open = false;
	}

	public Door(String name, Location locA, Location locB, Item it)
	{
		super(name, Location.NULL_LOCATION);
		this.locationA = locA;
		this.locationB = locB;
		this.keyItem = it;
		this.locked = false;
		this.open = true;
	}

	public Door(String name, Location locA, Location locB, Item it, ActivateMethod am)
	{
		super(name, Location.NULL_LOCATION, am);
		this.locationA = locA;
		this.locationB = locB;
		this.keyItem = it;
		this.locked = false;
		this.open = true;
	}

	
	// These methods are used by the player when they try to open the door.

	public boolean lock(Item it)
	{
		boolean result = false;

		if (it == keyItem)
		{
			Game.output("You lock the door with the " + it.name + ".");
			locked = true;
			result = true;
		}
		else
		{
			Game.output("You can't use that to lock the door.");
		}

		return result;
	}

	public boolean unlock(Item it)
	{

		boolean result = false;
		if (it == keyItem)
		{
			Game.output("You unlock the door with the " + it.name + ".");
			locked = false;
			result = true;
		}
		else
		{
			Game.output("You can't use that to unlock the door.");
		}

		return result;
	}


	public void unlock() { locked = false; }
	public void lock() { locked = true; }

	public void open() { open = true; }
	public void close() { open = false; }

	public boolean isLocked() { return locked; }
	public boolean isOpen() { return open; }




}