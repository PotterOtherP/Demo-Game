import java.util.ArrayList;

class Room {
	
	protected final String name;
	protected final String description;
	protected final Location roomLoc;

	protected boolean firstVisit;


	public Door northExit;
	public Door southExit;
	public Door eastExit;
	public Door westExit;

	public ArrayList<Door> exits;


	public Room()
	{
		this.name = "";
		this.description = "";
		this.roomLoc = Location.NULL_LOCATION;
		this.northExit = null;
		this.southExit = null;
		this.eastExit = null;
		this.westExit = null;
		this.firstVisit = true;
		Door d = new Door();
		this.setExits(d, d, d, d);


	}

	public Room(String name, String desc, Location loc, Door north, Door south, Door east, Door west)
	{
		this.name = name;
		this.description = desc;
		this.roomLoc = loc;
		this.northExit = north;
		this.southExit = south;
		this.eastExit = east;
		this.westExit = west;
		this.firstVisit = true;
		this.setExits(north, south, east, west);

	}


	private void setExits(Door n, Door s, Door e, Door w)
	{
		this.exits = new ArrayList<Door>();
		exits.add(n);
		exits.add(s);
		exits.add(e);
		exits.add(w);

	}


	public boolean exit(GameState state, Action act)
	{
		Door d = null;
		boolean result = false;
		Location dest = Location.NULL_LOCATION;
		String failString = StringList.CANTGO;


		// Identify which direction the player is trying to go.
		switch(act)
		{
			case EXIT_NORTH: { d = northExit; } break;
			case EXIT_SOUTH: { d = southExit; } break;
			case EXIT_EAST:  { d = eastExit;  } break;
			case EXIT_WEST:  { d = westExit;  } break;
			default: {} break;
		}


		// If there's no exit in that direction, print the room's particular message (for that direction.)
		if (d == null)
		{
			result = false;
			Game.output(failString);
		}


		else
		{
			// Figure out which side of the door the player is on.
			if (d.locationA == this.roomLoc) { dest = d.locationB; }
			else { dest = d.locationA; }

			// If the door is open... success
			if (d.isOpen())
			{
				state.setPreviousLocation(state.getCurrentLocation());
				state.setCurrentLocation(dest);
				result = true;
			}

			else
			{
				// If the door is locked, print the door's locked message.
				if (d.isLocked())
				{
					Game.output("The door is locked.");
				}

				// If the door is closed, but not locked.
				else
				{
					Game.output("The door is closed.");
				}
			}
			
		}

		return result;
	}

}