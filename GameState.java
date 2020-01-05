import java.util.HashMap;

class GameState {

	// All of the data members of the game state should be private.
	private int turns;


	// player attributes
	private Location currentLocation;
	private Location previousLocation;
	private int hitPoints;
	private int agility;


	// player action
	private String playerInput;
	protected String[] inputWords;
	private String addInputOne;
	private String addInputTwo;

	private Action currentAction;
	private String actionObjectName;
	private Item actionItem;
	private Feature actionFeature;

	public HashMap<Location, Room> worldMap = new HashMap<Location, Room>();
	public HashMap<String, Item> itemList = new HashMap<String, Item>();
	public HashMap<String, Feature> featureList = new HashMap<String, Feature>();


	// constructor
	public GameState()
	{
		this.turns = 0;
		this.currentLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.playerInput = "";
		this.addInputOne = "";
		this.addInputTwo = "";
		this.currentAction = Action.NULL_ACTION;
		this.actionItem = new Item();
		this.actionFeature = new Feature();
	}



	public void setCurrentLocation(Location loc) { currentLocation = loc; }
	public Location getCurrentLocation() { return currentLocation; }

	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }

	public void setCurrentAction(Action act) { currentAction = act; }
	public Action getCurrentAction() { return currentAction; }

	public void setPlayerInput(String str)
	{ 
		playerInput = str;
		inputWords = str.split(" ");

	}
	public String getPlayerInput() { return playerInput; }

	public void setAddInputOne(String str) { addInputOne = str; }
	public String getAddInputOne() { return addInputOne; }

	public void setAddInputTwo(String str) { addInputTwo = str; }
	public String getAddInputTwo() { return addInputTwo; }


	public void setActionItem(Item it) { actionItem = it; }
	public Item getActionItem() { return actionItem; }

	public void setActionFeature(Feature ft) { actionFeature = ft; }
	public Feature getActionFeature() { return actionFeature; }

	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }
	public int getTurns() { return turns; }

}