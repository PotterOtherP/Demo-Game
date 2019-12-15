class GameState {

	// All of the data members of the game state should be private.
	private int turns = 0;


	// Playser attributes
	private Location playerLocation;
	private Location previousLocation;
	private int hitPoints;
	private int agility;






	public void setPlayerLocation(Location loc) { playerLocation = loc; }
	public Location getPlayerLocation() { return playerLocation; }
	public void setPreviousLocation(Location loc) { previousLocation = loc; }
	public Location getPreviousLocation() { return previousLocation; }

	// It should not be possible to alter the number of turns except by adding 1.
	public void addTurn() { ++turns; }

	public int getTurns() { return turns; }

}