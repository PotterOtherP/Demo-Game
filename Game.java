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
	WAIT,

	TAKE,
	DROP,
	SPEAK,
	ACTIVATE,
	OPEN_CLOSE,

	ATTACK

	}




public class Game {



	private static boolean gameover = true;
	private static boolean godmode = false;

	private static HashMap<Location, Room> worldMap = new HashMap<Location, Room>();
	private static HashMap<String, Item> itemList = new HashMap<String, Item>();
	private static HashMap<String, Feature> featureList = new HashMap<String, Feature>();
	private static HashMap<String, Action> commandOne = new HashMap<String, Action>();
	private static HashMap<String, Action> commandTwo = new HashMap<String, Action>();
	private static HashMap<String, Action> commandThree = new HashMap<String, Action>();

	private static ArrayList<String> fakeItems = new ArrayList<String>();

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
		Item emptyItem = new Item("none", Location.NULL_LOCATION);
		Item itemRope = new Item("rope", Location.GREEN_ROOM);
		Item itemEgg = new Item("egg", Location.BLUE_ROOM);

		itemList.put("rope", itemRope);
		itemList.put("egg", itemEgg);
		itemList.put("empty", emptyItem);

		fakeItems.add("juniper");


		// Create the feature objects

		commandOne.put("north", Action.EXIT_NORTH);
		commandOne.put("n",     Action.EXIT_NORTH);
		commandOne.put("south", Action.EXIT_SOUTH);
		commandOne.put("s",     Action.EXIT_SOUTH);
		commandOne.put("east",  Action.EXIT_EAST);
		commandOne.put("e",     Action.EXIT_EAST);
		commandOne.put("west",  Action.EXIT_WEST);
		commandOne.put("w",     Action.EXIT_WEST);
		commandOne.put("up",	Action.EXIT_UP);
		commandOne.put("u",	    Action.EXIT_UP);
		commandOne.put("down",  Action.EXIT_DOWN);
		commandOne.put("d",     Action.EXIT_DOWN);
		commandOne.put("quit",  Action.QUIT);
		commandOne.put("q",     Action.QUIT);
		commandOne.put("jump",  Action.JUMP);
		commandOne.put("look",  Action.LOOK);
		commandOne.put("l",     Action.LOOK);
		commandOne.put("inventory", Action.INVENTORY);
		commandOne.put("i",         Action.INVENTORY);
		commandOne.put("fuck",  Action.PROFANITY);
		commandOne.put("shit",  Action.PROFANITY);
		commandOne.put("shout", Action.SHOUT);
		commandOne.put("yell",  Action.SHOUT);

		commandTwo.put("take", Action.TAKE);
		commandTwo.put("drop", Action.DROP);
		commandTwo.put("open", Action.OPEN_CLOSE);
		commandTwo.put("close", Action.OPEN_CLOSE);
		commandTwo.put("say", Action.SPEAK);


		// Put the player in the starting location
		state.setCurrentLocation(initialLocation);
		worldMap.get(initialLocation).firstVisit = false;

		blackRoom.northExitOpen = false;

		// Beginning text of the game.
		outputLine();
		output(StringList.INTRO);
		
	}


	private static void getPlayerAction(GameState state)
	{
		


		state.setCurrentAction(Action.NULL_ACTION);
		state.setActionItem(itemList.get("empty"));
		state.setPlayerInput("");
		state.setAddInputOne("");
		state.setAddInputTwo("");

		String playerText = getPlayerText();
		state.setPlayerInput(playerText);
		String inputWords[] = playerText.split(" ");

		String first = "";
		String second = "";
		String third = "";
		String fourth = "";

		switch(inputWords.length)
		{
			case 0:
			{
				state.setCurrentAction(Action.NULL_ACTION);
			} break;

			case 1:
			{
				first = inputWords[0];
				if (commandOne.containsKey(first)) { state.setCurrentAction(commandOne.get(first)); }

			} break;

			case 2:
			{
				first = inputWords[0];
				second = inputWords[1];

				if (commandTwo.containsKey(first)) { state.setCurrentAction(commandTwo.get(first)); }
				if (itemList.containsKey(second)) { state.setActionItem(itemList.get(second)); }
				if (featureList.containsKey(second)) { state.setActionFeature(featureList.get(second)); }

			} break;


			case 3:
			{
				first = inputWords[0];
				second = inputWords[1];
				third =  inputWords[2];

				if (commandThree.containsKey(first)) { state.setCurrentAction(commandThree.get(first)); }
				if (featureList.containsKey(second)) { state.setActionFeature(featureList.get(second)); }
				if (itemList.containsKey(third)) { state.setActionItem(itemList.get(third)); }

			} break;

			default:
			{
				state.setCurrentAction(Action.NULL_ACTION);
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

		Action action = state.getCurrentAction();
		Location curLoc = state.getCurrentLocation();
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
				else if (item.name == "none")
				{

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
				else if (item.name == "none")
				{

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
			case EXIT_UP:
			case EXIT_DOWN:
			{
				Location dest = Location.NULL_LOCATION;
				switch (action)
				{
					case EXIT_NORTH: { if (curRoom.northExitOpen)  dest = curRoom.northExit; } break;
					case EXIT_SOUTH: { if (curRoom.southExitOpen)  dest = curRoom.southExit; } break;
					case EXIT_EAST:  { if (curRoom.eastExitOpen)   dest = curRoom.eastExit;  } break;
					case EXIT_WEST:  { if (curRoom.westExitOpen)   dest = curRoom.westExit;  } break;
					case EXIT_UP:    { if (curRoom.upExitOpen)     dest = curRoom.upExit;    } break;
					case EXIT_DOWN:  { if (curRoom.downExitOpen)   dest = curRoom.downExit;  } break;
					default: {} break;

				}

				if (dest == Location.NULL_LOCATION) output("You can't go that way.");
				else
				{
					state.setPreviousLocation(curLoc);
					state.setCurrentLocation(dest);

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
				output("I don't understand that.");
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
		String result = "";
		prompt();

		while(result.isEmpty())
		{
			result = scn.nextLine();

			if (result.isEmpty())
			{
				output("What?");
				prompt();
			}
		}


		return result.toLowerCase();
	}



	private static void endGame(GameState state)
	{
		// Save the gamestate

		System.out.println("Game has ended.");
	}



}