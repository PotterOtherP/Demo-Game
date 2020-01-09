import java.util.HashMap;

class GameState {

	// gameplay information
	private int turns;
	protected static boolean bellRung;


	// player attributes
	private Location currentLocation;
	private Location previousLocation;
	private int hitPoints;
	private int agility;


	// player action
	protected String playerInput;
	protected String first;
	protected String second;
	protected String third;
	protected String fourth;
	protected String fifth;
	protected String sixth;
	protected String seventh;
	protected String eighth;
	protected String ninth;
	protected String tenth;
	protected int numInputWords;

	protected String addInputOne;
	protected String addInputTwo;


	private Action currentAction;
	private ActionType currentActionType;
	private String actionObjectName;
	private Item actionItem;
	private Feature actionFeature;

	private Item dummyItem;
	private Feature dummyFeature;


	// lists of game objects
	public static HashMap<Location, Room> worldMap;
	public static HashMap<String, Item> itemList;
	public static HashMap<String, Feature> featureList;
	public static HashMap<String, Actor> actorList;


	// constructor
	public GameState()
	{
		this.dummyItem = new Item();
		this.dummyFeature = new Feature();
		this.resetInput();
		this.turns = 0;
		this.currentLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.bellRung = false;
		worldMap = new HashMap<Location, Room>();
		itemList = new HashMap<String, Item>();
		featureList = new HashMap<String, Feature>();
		actorList = new HashMap<String, Actor>();
	}



	public void setCurrentLocation(Location loc) { currentLocation = loc; }
	public Location getCurrentLocation() { return currentLocation; }

	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }

	public void setCurrentAction(Action act) { currentAction = act; }
	public Action getCurrentAction() { return currentAction; }

	public void setCurrentActionType(ActionType type) { currentActionType = type; }
	public ActionType getCurrentActionType() { return currentActionType; }

	public void setPlayerInput(String str)
	{ 
		playerInput = str;
		String[] inputWords = str.split(" ");
		this.numInputWords = inputWords.length;

		try { this.first = inputWords[0]; } catch (Exception e) {}
		try { this.second = inputWords[1]; } catch (Exception e) {}
		try { this.third = inputWords[2]; } catch (Exception e) {}
		try { this.fourth = inputWords[3]; } catch (Exception e) {}
		try { this.fifth = inputWords[4]; } catch (Exception e) {}
		try { this.sixth = inputWords[5]; } catch (Exception e) {}
		try { this.seventh = inputWords[6]; } catch (Exception e) {}
		try { this.eighth = inputWords[7]; } catch (Exception e) {}
		try { this.ninth = inputWords[8]; } catch (Exception e) {}
		try { this.tenth = inputWords[9]; } catch (Exception e) {}

	}

	public void resetInput()
	{
		this.playerInput = "";
		this.first = "";
		this.second = "";
		this.third = "";
		this.fourth = "";
		this.fifth = "";
		this.sixth = "";
		this.seventh = "";
		this.eighth = "";
		this.ninth = "";
		this.tenth = "";
		this.addInputOne = "";
		this.addInputTwo = "";
		this.currentAction = Action.NULL_ACTION;
		this.currentActionType = ActionType.BLANK;
		this.actionItem = dummyItem;
		this.actionFeature = dummyFeature;
		this.actionObjectName = "";
		this.numInputWords = 0;
	}


	public void setActionItem(Item it) { actionItem = it; }
	public Item getActionItem() { return actionItem; }

	public void setActionFeature(Feature ft) { actionFeature = ft; }
	public Feature getActionFeature() { return actionFeature; }

	public void setActionObjectName(String str) { actionObjectName = str; }
	public String getActionObjectName() { return actionObjectName; }

	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }
	public int getTurns() { return turns; }

	// Methods used by unique objects

	public static void nullMethod() {}

	public static void ringBell()
	{

		Game.output("Ding dong ding dong!");

		if (!bellRung)
		{
			bellRung = true;
			Game.output("A piece of paper falls out of the bell!");
			itemList.get("note").setLocation(Location.BLACK_ROOM);
			
		}

	}

	public static void kickBell()
	{
		Game.output("BWOOONG!! Ow!");
	}

	public static void playPiano()
	{
		Game.output("Da-da Da-da Da Da-da-da DUN...");
	}

	public static void readNote()
	{
		Game.output(StringList.NOTE_TEXT);
	}

}