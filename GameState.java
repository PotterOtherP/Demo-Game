class GameState {

	// All of the data members of the game state should be private.
	private int turns;


	// Player attributes
	private Location playerLocation;
	private Location previousLocation;
	private int hitPoints;
	private int agility;


	// player action
	private String playerInput;
	private String addInputOne;
	private String addInputTwo;	
	private Action playerAction;
	private Item actionItem;
	private Feature actionFeature;


	// constructor
	public GameState()
	{
		this.turns = 0;
		this.playerLocation = Location.NULL_LOCATION;
		this.previousLocation = Location.NULL_LOCATION;
		this.playerInput = "";
		this.addInputOne = "";
		this.addInputTwo = "";
		this.playerAction = Action.NULL_ACTION;
		this.actionItem = new Item();
		this.actionFeature = new Feature();
	}



	public void setPlayerLocation(Location loc) { playerLocation = loc; }
	public Location getPlayerLocation() { return playerLocation; }

	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }

	public void setPlayerAction(Action act) { playerAction = act; }
	public Action getPlayerAction() { return playerAction; }

	public void setPlayerInput(String str) { playerInput = str; }
	public String getPlayerInput() { return playerInput; }

	public void setAddInputOne(String str) { addInputOne = str; }
	public String getAddInputOne() { return addInputOne; }

	public void setAddInputTwo(String str) { addInputTwo = str; }
	public String getAddInputTwo() { return addInputTwo; }


	public void setActionItem(Item it) { actionItem = it; }
	public Item getActionItem() { return actionItem; }

	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }
	public int getTurns() { return turns; }

}