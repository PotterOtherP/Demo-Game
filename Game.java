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

enum ActionType {

	BLANK,
	REFLEXIVE,
	DIRECT,
	INDIRECT

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
	DEFEND,

	TAKE,
	DROP,
	SPEAK,
	ACTIVATE,
	RING,
	PLAY,
	OPEN,
	CLOSE,
	UNLOCK,
	LOCK,

	ATTACK

	}




public final class Game {



	private static boolean gameover = true;
	private static boolean godmode = false;

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
			gameState.resetInput();
			gameState.setPlayerInput(getPlayerText());
			parsePlayerInput(gameState);
			updateGame(gameState);
		}


		endGame(gameState);
		
	}


	private static void initGame(GameState state)
	{	

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
		commandTwo.put("open", Action.OPEN);
		commandTwo.put("close", Action.CLOSE);
		commandTwo.put("say", Action.SPEAK);
		commandTwo.put("ring", Action.RING);
		commandTwo.put("play", Action.PLAY);

		commandThree.put("open", Action.OPEN);
		commandThree.put("unlock", Action.UNLOCK);


		// Create the item objects
		Item nullItem = new Item();
		Item itemRope = new Item("rope", Location.GREEN_ROOM);
		Item itemEgg = new Item("egg", Location.BLUE_ROOM);

		state.itemList.put(itemRope.name, itemRope);
		state.itemList.put(itemEgg.name, itemEgg);
		state.itemList.put(nullItem.name, nullItem);

		fakeItems.add("juniper");



		// Create the world map

		// name, location A, location B, key item
		Door nullDoor = new Door();
		Door redGreenDoor = new Door("passage", Location.RED_ROOM, Location.GREEN_ROOM, itemEgg);
		Door redBlackDoor = new Door("door", Location.RED_ROOM, Location.BLACK_ROOM, itemEgg);
		Door redBlueDoor = new Door("passage", Location.RED_ROOM, Location.BLUE_ROOM, itemEgg);
		Door redWhiteDoor = new Door("passage", Location.RED_ROOM, Location.WHITE_ROOM, itemEgg);
		Door blueGreenDoor = new Door("passage", Location.BLUE_ROOM, Location.GREEN_ROOM, itemEgg);
		Door blueBlackDoor = new Door("passage", Location.BLUE_ROOM, Location.BLACK_ROOM, itemEgg);
		Door whiteGreenDoor = new Door("passage", Location.WHITE_ROOM, Location.GREEN_ROOM, itemEgg);
		Door whiteBlackDoor = new Door("passage", Location.WHITE_ROOM, Location.BLACK_ROOM, itemEgg);


		redBlackDoor.close();
		redBlackDoor.lock();


		// Name, description, ID, North, South, East, West, Up, Down
		Room redRoom = new Room("Red Room", StringList.DESCREDROOM, Location.RED_ROOM, redGreenDoor, redBlackDoor, redWhiteDoor, redBlueDoor);
		Room greenRoom = new Room("Green Room", StringList.DESCGREENROOM, Location.GREEN_ROOM, nullDoor, redGreenDoor, whiteGreenDoor, blueGreenDoor);
		Room blackRoom = new Room("Black Room", StringList.DESCBLACKROOM, Location.BLACK_ROOM, redBlackDoor, nullDoor, whiteBlackDoor, blueBlackDoor);
		Room whiteRoom = new Room("White Room", StringList.DESCWHITEROOM, Location.WHITE_ROOM, whiteGreenDoor, whiteBlackDoor, nullDoor, redWhiteDoor);
		Room blueRoom = new Room("Blue Room", StringList.DESCBLUEROOM, Location.BLUE_ROOM, blueGreenDoor, blueBlackDoor, redBlueDoor, nullDoor);

		state.worldMap.put(Location.RED_ROOM, redRoom);
		state.worldMap.put(Location.GREEN_ROOM, greenRoom);
		state.worldMap.put(Location.BLACK_ROOM, blackRoom);
		state.worldMap.put(Location.WHITE_ROOM, whiteRoom);
		state.worldMap.put(Location.BLUE_ROOM, blueRoom);

		// Create the feature objects

		Feature nullFeature = new Feature();
		Feature bell = new Feature("bell", Location.BLACK_ROOM, Feature::ringBell, Action.RING);
		Feature piano = new Feature("piano", Location.WHITE_ROOM, Feature::playPiano, Action.PLAY);

		state.featureList.put(nullFeature.name, nullFeature);
		state.featureList.put(bell.name, bell);
		state.featureList.put(piano.name, piano);



		Actor troll = new Actor(Location.BLACK_ROOM, "troll");
		state.actorList.put(troll.name, troll);



		// Put the player in the starting location
		state.setCurrentLocation(initialLocation);
		state.worldMap.get(initialLocation).firstVisit = false;

		// Beginning text of the game.
		outputLine();
		output(StringList.INTRO);
		
	}

	private static void altParse(GameState state)
	{
		String first = state.first;
		String second = state.second;
		String third = state.third;
		String fourth = state.fourth;
		String fifth = state.fifth;
		String sixth = state.sixth;
		String seventh = state.seventh;
		String eighth = state.eighth;
		String ninth = state.ninth;
		String tenth = state.tenth;




	}


	private static void parsePlayerInput(GameState state)
	{

		/* What is the player trying to do?

		   A reflexive action? One word. Is it a valid action?

		   A direct action? An action word and an object (feature, item, door, actor. phrase). Is the object valid?

		   An indirect action? An action word, a direct object, and an indirect object. Are they all valid?

		*/

		String first = state.first;
		String second = state.second;
		String third = state.third;
		String fourth = state.fourth;
		String fifth = state.fifth;
		String sixth = state.sixth;
		String seventh = state.seventh;
		String eighth = state.eighth;
		String ninth = state.ninth;
		String tenth = state.tenth;


		switch(state.numInputWords)
		{

			case 1:
			{
				if (commandOne.containsKey(first))
				{ 
					state.setCurrentAction(commandOne.get(first));
				}

				else if (commandTwo.containsKey(first) || commandThree.containsKey(first))
				{
					output("What do you want to " + first + "?");
					state.second = getPlayerText();
					parsePlayerInput(state);
				}
				else
				{
					state.setCurrentAction(Action.FAIL_ACTION);
				}


			} break;

			case 2:
			{
				

				if (commandTwo.containsKey(state.first)) { state.setCurrentAction(commandTwo.get(state.first)); }
				if (state.itemList.containsKey(state.second)) { state.setActionItem(state.itemList.get(state.second)); }
				if (state.featureList.containsKey(state.second)) { state.setActionFeature(state.featureList.get(state.second)); }

			} break;


			case 3:
			{
				

				if (commandThree.containsKey(state.first)) { state.setCurrentAction(commandThree.get(state.first)); }
				if (state.featureList.containsKey(state.second)) { state.setActionFeature(state.featureList.get(state.second)); }
				if (state.itemList.containsKey(state.third)) { state.setActionItem(state.itemList.get(state.third)); }

			} break;

			default:
			{
				state.setCurrentAction(Action.NULL_ACTION);
			} break;

		}




	}


	private static void updateGame(GameState state)
	{
		/*
			Where are we right now?
			What is the player trying to do?
			If the player is using an object, is that object present?

		*/


		Location curLoc = state.getCurrentLocation();
		Room curRoom = state.worldMap.get(curLoc);

		ActionType curActionType = state.getCurrentActionType();
		Action curAction = state.getCurrentAction();
		Feature curFeat = state.getActionFeature();
		Item curItem = state.getActionItem();


		switch (curAction)
		{

			case ACTIVATE:
			case RING:
			case PLAY:
			{
				if (curFeat.name.equals("null")) return;

				if (curFeat.location == curLoc)
					curFeat.activate(curFeat.method1, curAction);
				else
					output("There's no " + curFeat.name + " here.");

			} break;
			

			case LOOK:
			{
				output(curRoom.description);

				for (Item i : state.itemList.values())
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
				else if (item.name.equals("null"))
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
				else if (item.name.equals("null"))
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
				for (Item i : state.itemList.values())
				{
					if (i.getLocation() == Location.PLAYER_INVENTORY)
						output(i.name);
				}
			} break;

			case OPEN:
			{
				String word = "door";
				for (Door d : curRoom.exits)
				{
					if (d.name.equals(word))
					{
						d.unlock(curItem);
						d.open();
						
					}
				}
			} break;


			case EXIT_NORTH:
			case EXIT_SOUTH:
			case EXIT_EAST:
			case EXIT_WEST:
			case EXIT_UP:
			case EXIT_DOWN:
			{
				boolean exited = curRoom.exit(state, curAction);

				if (exited)
				{
					curRoom = state.worldMap.get(state.getCurrentLocation());
					output(curRoom.name);
					outputLine();
					if (curRoom.firstVisit)
					{
						curRoom.firstVisit = false;
						output(curRoom.description);
					}
				}

			} break;


			// Simple actions

			case GODMODE_TOGGLE:
			{
				if (!godmode) { godmode = true; output("God mode enabled."); }
				else if (godmode) { godmode = false; output("God mode disabled."); }
			} break;

			case FAIL_ACTION: { output("Fail action."); } break;
			case JUMP: { output("Wheeeeeeee!"); } break;
			case SHOUT: { output("Yaaaaarrrrggghhh!"); } break;
			case NULL_ACTION: { output("Null action detected."); } break;
			case VERBOSE: { output("You said too many words."); } break;
			case PROFANITY: { output("There's no need for that kind of language."); } break;			
			case QUIT: { /* if (verifyQuit()) */ gameover = true; } break;

			default: {} break;
		}


		Actor actor = state.actorList.get("troll");
		actor.move();

		state.addTurn();
		if (state.getTurns() >=50)
		{
			output("Maximum turns reached, ending game.");
			gameover = true;
		}

	}

	protected static void prompt() { System.out.print(">> "); }
	protected static void outputLine() { System.out.println(); }
	protected static void output() { System.out.println(); }
	protected static void output(String s) { System.out.println(s); }
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

	protected static boolean verifyQuit()
	{
		boolean result = false;
		Scanner scn = new Scanner(System.in);
		output("Are you sure you want to quit?");
		String input = scn.nextLine().toLowerCase();
		if (input.equals("y")) result = true;
		if (input.equals("yes")) result = true;

		return result;
	}



	private static void endGame(GameState state)
	{
		// Save the gamestate

		System.out.println("Game has ended.");
	}



}