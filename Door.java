class Door {
	
	private boolean locked;
	private boolean open;

	protected final Location locationA;
	protected final Location locationB;
	protected final Item keyItem;

	public Door()
	{
		this.locked = false;
		this.open = true;
		this.locationA = Location.NULL_LOCATION;
		this.locationB = Location.NULL_LOCATION;
		this.keyItem = null;
	}

	public Door(Location locA, Location locB, Item it)
	{
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

	public void unlock()
	{
		this.locked = false;
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