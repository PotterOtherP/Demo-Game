class Door {
	

	public final String name;
	public final String lockFail;
	public final String closedFail;
	public final Location locationA;
	public final Location locationB;

	private boolean open;
	private boolean locked;

	// Constructors
	public Door()
	{
		this.name = "null";
		this.lockFail = GameStrings.CANT_GO;
		this.closedFail = GameStrings.CANT_GO;
		this.locationA = Location.NULL_LOCATION;
		this.locationB = Location.NULL_LOCATION;
		this.locked = true;
		this.open = false;
	}

	public Door(String name, String exit, String closed, Location locA, Location locB)
	{
		this.name = name;
		this.lockFail = exit;
		this.closedFail = closed;
		this.locationA = locA;
		this.locationB = locB;
		this.locked = false;
		this.open = true;
	}

	
	public void unlock() { locked = false; }
	public void lock() { locked = true; }

	public void open() { open = true; }
	public void close() { open = false; }

	public boolean isLocked() { return locked; }
	public boolean isOpen() { return open; }




}