class Door {
	
	private boolean locked;
	private boolean open;

	protected final String name;
	protected final Location locationA;
	protected final Location locationB;
	protected final Item keyItem;

	public Door()
	{
		this.name = "null";
		this.locked = false;
		this.open = true;
		this.locationA = Location.NULL_LOCATION;
		this.locationB = Location.NULL_LOCATION;
		this.keyItem = new Item();
	}

	public Door(String name, Location locA, Location locB, Item it)
	{
		this.name = name;
		this.locked = false;
		this.open = true;
		this.locationA = locA;
		this.locationB = locB;
		this.keyItem = it;
	}

	public void lock()
	{
		this.locked = true;
	}

	public void lock(Item it)
	{
		if (it == keyItem)
		{
			Game.output("You lock the door with the " + it.name + ".");
			locked = true;
		}
		else
		{
			Game.output("You can't use that to lock the door.");
		}
	}

	public void unlock()
	{
		this.locked = false;
	}

	public void unlock(Item it)
	{
		if (it == keyItem)
		{
			Game.output("You unlock the door with the " + it.name + ".");
			locked = false;
		}
		else
		{
			Game.output("You can't use that to unlock the door.");
		}
	}

	public boolean isLocked()
	{
		return locked;
	}

	public void open()
	{
		this.open = true;
	}

	public void close()
	{
		this.open = false;
	}

	public boolean isOpen()
	{
		return open;
	}




}