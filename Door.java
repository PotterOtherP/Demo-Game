class Door extends GameObject {
	
	public boolean locked;
	public boolean open;


	public Location locationA;
	public Location locationB;
	public Item keyItem;

	public Door()
	{
		super();
		this.keyItem = new Item();
		this.locked = true;
		this.open = false;
	}

	public Door(String name, Location locA, Location locB, Item it)
	{
		super(name, locA);
		this.locationA = locA;
		this.locationB = locB;
		this.keyItem = it;
		this.locked = false;
		this.open = true;
	}

	public void lock()
	{
		this.locked = true;
	}

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

	public void unlock()
	{
		this.locked = false;
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