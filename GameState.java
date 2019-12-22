class GameState {

	// All of the data members of the game state should be private.
	private int turns = 0;


	// Player attributes
	private Location playerLocation;
	private Location previousLocation;
	private int hitPoints;
	private int agility;


	// player action
	private Action playerAction;
	private Item actionItem;



	public void setPlayerLocation(Location loc) { playerLocation = loc; }
	public Location getPlayerLocation() { return playerLocation; }
	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }
	public void setPlayerAction(Action act) { playerAction = act; }
	public Action getPlayerAction() { return playerAction; }
	public void setActionItem(Item it) { actionItem = it; }
	public Item getActionItem() { return actionItem; }

	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }
	public int getTurns() { return turns; }

}