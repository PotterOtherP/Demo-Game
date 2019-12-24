import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

enum Location {

	/* A location is any place a moveable object can exist. */

	RED_ROOM,
	GREEN_ROOM,
	BLUE_ROOM,
	WHITE_ROOM,
	BLACK_ROOM,
	PLAYER_INVENTORY,
	NULL_LOCATION

	}

enum Action {


	JUMP,
	SHOUT,
	LOOK,
	INVENTORY,
	EXIT_NORTH,
	EXIT_SOUTH,
	EXIT_EAST,
	EXIT_WEST,
	NULL_ACTION,
	FAIL_ACTION,
	GODMODE_TOGGLE,
	GIBBERISH,
	QUIT,
	VERBOSE,
	PROFANITY,

	TAKE,
	DROP,
	SAY,
	ACTIVATE

	}




public class Game {



	private static boolean gameover = true;
	private static boolean godmode = false;

	private static HashMap<Location, Room> worldMap = new HashMap<Location, Room>();
	private static HashMap<String, Item> itemList = new HashMap<String, Item>();


	public static void main(String[] args)
	{

		Scanner scn = new Scanner(System.in);
		GameState gameState = new GameState();

		
		initGame(gameState);




		gameover = false;

		while (!gameover)
		{	
			getPlayerInput(scn, gameState);
			updateGame(gameState);
		}


		endGame(gameState);
		
	}


	private static void initGame(GameState state)
	{		
		// Create the world map

		Room redRoom = new Room("Red Room", "This is a red room.", Location.RED_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.WHITE_ROOM, Location.BLUE_ROOM, Location.NULL_LOCATION, Location.NULL_LOCATION);

		Room greenRoom = new Room("Green Room", "This is a green room.", Location.GREEN_ROOM, Location.NULL_LOCATION, Location.RED_ROOM,
			Location.WHITE_ROOM, Location.BLUE_ROOM, Location.NULL_LOCATION, Location.NULL_LOCATION);

		Room blackRoom = new Room("Black Room", "This is a black room.", Location.BLACK_ROOM, Location.RED_ROOM, Location.NULL_LOCATION,
			Location.WHITE_ROOM, Location.BLUE_ROOM, Location.NULL_LOCATION, Location.NULL_LOCATION);

		Room whiteRoom = new Room("White Room", "This is a white room.", Location.WHITE_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.NULL_LOCATION, Location.RED_ROOM, Location.NULL_LOCATION, Location.NULL_LOCATION);

		Room blueRoom = new Room("Blue Room", "This is a blue room.", Location.BLUE_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.RED_ROOM, Location.NULL_LOCATION, Location.NULL_LOCATION, Location.NULL_LOCATION);

		

		worldMap.put(Location.RED_ROOM, redRoom);
		worldMap.put(Location.GREEN_ROOM, greenRoom);
		worldMap.put(Location.BLACK_ROOM, blackRoom);
		worldMap.put(Location.WHITE_ROOM, whiteRoom);
		worldMap.put(Location.BLUE_ROOM, blueRoom);


		// Create the item objects
		Item emptyItem = new Item("None", Location.NULL_LOCATION);
		Item itemRope = new Item("Rope", Location.GREEN_ROOM);
		Item itemEgg = new Item("Egg", Location.BLUE_ROOM);

		itemList.put("rope", itemRope);
		itemList.put("egg", itemEgg);
		itemList.put("empty", emptyItem);


		// Initialize the gamestate

		state.setPlayerAction(Action.NULL_ACTION);
		state.setActionItem(emptyItem);

		// Put the player in the starting location
		state.setPlayerLocation(Location.RED_ROOM);
		redRoom.firstVisit = false;

		// Beginning text of the game.
		outputLine();
		output(StringList.INTRO);
		
	}



	private static void getPlayerInput(Scanner scn, GameState state)
	{
		outputLine();
		prompt();

		String playerInput = scn.nextLine().toLowerCase();
		String[] input = playerInput.split(" ");

		String first = "";
		String second = "";
		String third = "";
		String fourth = "";
		String fifth = "";
		String sixth = "";
		String seventh = "";
		String eighth = "";
		String ninth = "";
		String tenth = "";

		try { first = input[0]; } catch (Exception e) {}
		try { second = input[1]; } catch (Exception e) {}
		try { third = input[2]; } catch (Exception e) {}
		try { fourth = input[3]; } catch (Exception e) {}
		try { fifth = input[4]; } catch (Exception e) {}
		try { sixth = input[5]; } catch (Exception e) {}
		try { seventh = input[6]; } catch (Exception e) {}
		try { eighth = input[7]; } catch (Exception e) {}
		try { ninth = input[8]; } catch (Exception e) {}
		try { tenth = input[9]; } catch (Exception e) {}

		Action resultAction = Action.NULL_ACTION;
		Item resultItem = itemList.get("empty");

		int len = input.length;
		if (len < 1)
		{
			state.setPlayerAction(Action.NULL_ACTION);
			return;
		}
		

		// Reflexive actions

		if (first.contentEquals("quit") || first.contentEquals("q")) { resultAction = Action.QUIT; }
		if (first.contentEquals("jump")) { resultAction = Action.JUMP; }
		if (first.contentEquals("shout")) { resultAction = Action.SHOUT; }
		if (first.contentEquals("look") || first.contentEquals("l")) { resultAction = Action.LOOK; }
		if (first.contentEquals("inventory") || first.contentEquals("i")) { resultAction = Action.INVENTORY; }
		if (first.contentEquals("north") || first.contentEquals("n")) { resultAction = Action.EXIT_NORTH; }
		if (first.contentEquals("south") || first.contentEquals("s")) { resultAction = Action.EXIT_SOUTH; }
		if (first.contentEquals("east") || first.contentEquals("e")) { resultAction = Action.EXIT_EAST; }
		if (first.contentEquals("west") || first.contentEquals("w")) { resultAction = Action.EXIT_WEST; }


		if (first.contentEquals("take"))
		{
			resultAction = Action.TAKE;

			if (!second.isEmpty())
			{				
				resultItem = itemList.get(second);
			}

			if (second.isEmpty())
			{
				output("What do you want to take?");
				String nxt = scn.nextLine();
				try 
				{ 
					resultItem = itemList.get(nxt);
				}
				catch (Exception ex)
				{
					output("That's not something you can take.");
					resultAction = Action.FAIL_ACTION;
				}
			}

		}

		if (first.contentEquals("drop"))
		{
			resultAction = Action.DROP;
			resultItem = itemList.get(second);
		}
		

		state.setPlayerAction(resultAction);
		state.setActionItem(resultItem);
	}


	private static void updateGame(GameState state)
	{

		Action action = state.getPlayerAction();
		Location curLoc = state.getPlayerLocation();
		Room curRoom = worldMap.get(curLoc);


		switch (action)
		{
			case FAIL_ACTION:
			{
				output("Fail action.");
			} break;

			case JUMP:
			{
				output("Wheeeeeeee!");

			} break;

			case SHOUT:
			{
				output("Yaaaaarrrrggghhh!");
			} break;

			case LOOK:
			{
				output(curRoom.fullDescription);

				for (Item i : itemList.values())
				{
					if (i.getLocation() == curLoc)
					{
						String word = (i.vowelStart()? "an" : "a");
						output("There is " + word + " " + i.name + " here.");
					}
				}

			} break;

			case TAKE:
			{
				Item item = state.getActionItem();
				if (item.getLocation() == curLoc)
				{
					item.setLocation(Location.PLAYER_INVENTORY);
					output("You picked up the " + item.name + ".");
				}	
			} break;

			case DROP:
			{
				Item item = state.getActionItem();
				if (item.getLocation() == Location.PLAYER_INVENTORY)
				{
					item.setLocation(curLoc);
					output("You dropped the " + item.name + ".");
				}
			} break;

			case INVENTORY:
			{
				output("You are carrying: \n");
				for (Item i : itemList.values())
				{
					if (i.getLocation() == Location.PLAYER_INVENTORY)
						output(i.name);
				}
			} break;

			case QUIT:
			{
				gameover = true;
			} break;

			case EXIT_NORTH:
			case EXIT_SOUTH:
			case EXIT_EAST:
			case EXIT_WEST:
			{
				Location dest = Location.NULL_LOCATION;
				switch (action)
				{
					case EXIT_NORTH: { dest = curRoom.northExit; } break;
					case EXIT_SOUTH: { dest = curRoom.southExit; } break;
					case EXIT_EAST: { dest = curRoom.eastExit; } break;
					case EXIT_WEST: { dest = curRoom.westExit; } break;
					default: {} break;

				}

				if (dest == Location.NULL_LOCATION) output("You can't go that way.");
				else
				{
					state.setPreviousLocation(curLoc);
					state.setPlayerLocation(dest);

					curLoc = dest;
					curRoom = worldMap.get(curLoc);

					output(curRoom.name);
					outputLine();
					if (curRoom.firstVisit)
					{
						output(curRoom.fullDescription);
						curRoom.firstVisit = false;
						outputLine();
					}
				}

			} break;


			case GODMODE_TOGGLE:
			{
				if (!godmode)
				{
					godmode = true;
					output("God mode enabled.");
				}

				else if (godmode)
				{
					godmode = false;
					output("God mode disabled.");

				}
			} break;

			case NULL_ACTION:
			{
				output("What?");
			} break;

			case VERBOSE:
			{
				output("You said too many words.");
			} break;

			default:
			{

			} break;
		}



		state.addTurn();
		if (state.getTurns() >=50)
		{
			output("Maximum turns reached, ending game.");
			gameover = true;
		}

	}

	private static void prompt() { System.out.print(">> "); }
	private static void outputLine() { System.out.println(); }
	private static void output() { System.out.println(); }
	private static void output(String s) { System.out.println(s); }



	private static void endGame(GameState state)
	{
		// Save the gamestate

		System.out.println("Game has ended.");
	}



}