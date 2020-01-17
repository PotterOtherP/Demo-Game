import java.util.HashMap;

class GameState {

	// gameplay information
	public int turns;
	public boolean bellRung;
	public boolean eggOpened;


	// player attributes
	public Location currentLocation;
	public Location previousLocation;
	public int hitPoints;
	public int agility;


	// player action
	public String first;
	public String second;
	public String third;
	public int numInputWords;


	public Action currentAction;
	public ActionType currentActionType;
	public Feature objectFeature;
	public Actor objectActor;
	public Item objectItem;
	public String objectDoor;

	public Item indirectObject;
	public String speechText;

	public Feature dummyFeature;
	public Item dummyItem;
	public Actor dummyActor;

	// lists of game objects
	public HashMap<Location, Room> worldMap;
	public HashMap<String, Feature> featureList;
	public HashMap<String, Item> itemList;
	public HashMap<String, Actor> actorList;


	// constructor
	public GameState()
	{
		this.dummyFeature = new Feature();
		this.dummyItem = new Item();
		this.dummyActor = new Actor();
		this.resetInput();

		this.turns = 0;
		this.currentLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.bellRung = false;
		this.eggOpened = false;

		worldMap = new HashMap<Location, Room>();
		featureList = new HashMap<String, Feature>();
		itemList = new HashMap<String, Item>();
		actorList = new HashMap<String, Actor>();
	}



	public void setCurrentLocation(Location loc) { currentLocation = loc; }
	public Location getCurrentLocation() { return currentLocation; }

	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }

	public void setCurrentAction(Action act) { currentAction = act; }
	public Action getCurrentAction() { return currentAction; }



	public void resetInput()
	{
		this.first = "";
		this.second = "";
		this.third = "";
		this.numInputWords = 0;

		this.currentAction = Action.NULL_ACTION;
		this.currentActionType = ActionType.BLANK;
		this.objectFeature = dummyFeature;
		this.objectItem = dummyItem;
		this.objectActor = dummyActor;
		this.objectDoor = "";
		this.indirectObject = dummyItem;
	}




	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }
	public int getTurns() { return turns; }



}