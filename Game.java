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
	EXIT_UP,
	EXIT_DOWN,
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
	private static HashMap<String, Action> commands = new HashMap<String, Action>();

	private static Location initialLocation = Location.RED_ROOM;


	public static void main(String[] args)
	{

		GameState gameState = new GameState();

		
		initGame(gameState);




		gameover = false;

		while (!gameover)
		{	

			getPlayerAction(gameState);
			if (validateAction(gameState))
			{
				updateGame(gameState);
			}
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

		commands.put("north", Action.EXIT_NORTH);
		commands.put("n",     Action.EXIT_NORTH);
		commands.put("south", Action.EXIT_SOUTH);
		commands.put("s",     Action.EXIT_SOUTH);
		commands.put("east",  Action.EXIT_EAST);
		commands.put("e",     Action.EXIT_EAST);
		commands.put("west",  Action.EXIT_WEST);
		commands.put("w",     Action.EXIT_WEST);
		commands.put("up",	  Action.EXIT_UP);
		commands.put("u",	  Action.EXIT_UP);
		commands.put("down",  Action.EXIT_DOWN);
		commands.put("d",     Action.EXIT_DOWN);
		commands.put("quit",  Action.QUIT);
		commands.put("q",     Action.QUIT);
		commands.put("jump",  Action.JUMP);
		commands.put("look",  Action.LOOK);
		commands.put("l",     Action.LOOK);
		commands.put("inventory", Action.INVENTORY);
		commands.put("i",         Action.INVENTORY);
		commands.put("shout", Action.SHOUT);
		commands.put("yell",  Action.SHOUT);
		commands.put("fuck",  Action.PROFANITY);
		commands.put("shit",  Action.PROFANITY);


		// Put the player in the starting location
		state.setPlayerLocation(initialLocation);
		worldMap.get(initialLocation).firstVisit = false;

		// Beginning text of the game.
		outputLine();
		output(StringList.INTRO);
		
	}


	private static void getPlayerAction(GameState state)
	{


		state.setPlayerAction(Action.NULL_ACTION);

		prompt();
		String playerText = getPlayerText();
		state.setPlayerInput(playerText);
		String inputWords[] = playerText.split(" ");

		switch(inputWords.length)
		{
			case 0:
			{
				state.setPlayerAction(Action.NULL_ACTION);
			} break;

			case 1:
			{
				String first = inputWords[0];
				if (commands.containsKey(first)) { state.setPlayerAction(commands.get(first)); }

			} break;

			default:
			{
				state.setPlayerAction(Action.NULL_ACTION);
			} break;

		}




	}


	private static boolean validateAction(GameState state)
	{
		boolean result = true;




		return result;
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
				// output("Fail action.");
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
						String word = (i.vowelStart()? "an " : "a ");
						output("There is " + word + i.name + " here.");
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
				else
				{
					output("There's no " + item.name + " here.");
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
				else
				{
					output("You're not carrying that.");
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

			case PROFANITY:
			{
				output("There's no need for that kind of language.");
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
	private static String getPlayerText()
	{
		Scanner scn = new Scanner(System.in);

		return scn.nextLine();
	}



	private static void endGame(GameState state)
	{
		// Save the gamestate

		System.out.println("Game has ended.");
	}



}