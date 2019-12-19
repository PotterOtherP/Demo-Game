import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

enum Location {

	RED_ROOM,
	GREEN_ROOM,
	BLUE_ROOM,
	WHITE_ROOM,
	BLACK_ROOM,
	PLAYER_INVENTORY,
	NULL_LOCATION

	}

enum Action {

	/*
		
	*/

	JUMP,
	SHOUT,
	LOOK,
	TAKE,
	DROP,
	INVENTORY,
	EXIT_NORTH,
	EXIT_SOUTH,
	EXIT_EAST,
	EXIT_WEST,
	NULL_ACTION,
	GODMODE_TOGGLE,
	GIBBERISH,
	QUIT

	}




public class Game {



	private static boolean gameover = true;
	private static boolean godmode = false;

	private static HashMap<Location, Room> worldMap = new HashMap<Location, Room>();
	private static ArrayList<Item> itemList = new ArrayList<Item>();


	public static void main(String[] args)
	{

		String[] playerInput = {};
		Action playerAction = Action.NULL_ACTION;
		Scanner sc = new Scanner(System.in);
		GameState gameState = new GameState();

		
		initGame(gameState);




		gameover = false;

		while (!gameover)
		{	
			playerInput = getPlayerInput(sc, gameState);
			playerAction = parseInput(playerInput);
			updateGame(playerAction, gameState);
		}


		endGame(gameState);
		
	}


	private static void initGame(GameState state)
	{		

		

		// Create the world map

		Room redRoom = new Room("Red Room", "This is a red room.", Location.RED_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.WHITE_ROOM, Location.BLUE_ROOM);

		Room greenRoom = new Room("Green Room", "This is a green room.", Location.GREEN_ROOM, Location.NULL_LOCATION, Location.RED_ROOM,
			Location.WHITE_ROOM, Location.BLUE_ROOM);

		Room blackRoom = new Room("Black Room", "This is a black room.", Location.BLACK_ROOM, Location.RED_ROOM, Location.NULL_LOCATION,
			Location.WHITE_ROOM, Location.BLUE_ROOM);

		Room whiteRoom = new Room("White Room", "This is a white room.", Location.WHITE_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.NULL_LOCATION, Location.RED_ROOM);

		Room blueRoom = new Room("Blue Room", "This is a blue room.", Location.BLUE_ROOM, Location.GREEN_ROOM, Location.BLACK_ROOM,
			Location.RED_ROOM, Location.NULL_LOCATION);

		

		worldMap.put(Location.RED_ROOM, redRoom);
		worldMap.put(Location.GREEN_ROOM, greenRoom);
		worldMap.put(Location.BLACK_ROOM, blackRoom);
		worldMap.put(Location.WHITE_ROOM, whiteRoom);
		worldMap.put(Location.BLUE_ROOM, blueRoom);

		Item itemRope = new Item("Rope", Location.GREEN_ROOM);
		Item itemEgg = new Item("Egg", Location.BLUE_ROOM);

		itemList.add(itemRope);
		itemList.add(itemEgg);


		// Put the player in the starting location
		state.setPlayerLocation(Location.RED_ROOM);
		redRoom.firstVisit = false;

		outputLine();
		output(StringList.INTRO);
		

	}



	private static String[] getPlayerInput(Scanner sc, GameState state)
	{
		String[] result = {};

		outputLine();
		prompt();

		String input = sc.nextLine().toLowerCase();

		result = input.split(" ");

		return result;
	}

	private static Action parseInput(String[] input)
	{
		Action result = Action.NULL_ACTION;

		if (input.length < 1) return Action.NULL_ACTION;

		String first = input[0];

		if (first.contentEquals("quit") || first.contentEquals("q")) { result = Action.QUIT; }
		if (first.contentEquals("jump")) { result = Action.JUMP; }
		if (first.contentEquals("shout")) { result = Action.SHOUT; }
		if (first.contentEquals("look") || first.contentEquals("l")) { result = Action.LOOK; }
		if (first.contentEquals("inventory") || first.contentEquals("i")) { result = Action.INVENTORY; }
		if (first.contentEquals("take")) { result = Action.TAKE; }
		if (first.contentEquals("drop")) { result = Action.DROP; }
		if (first.contentEquals("north") || first.contentEquals("n")) { result = Action.EXIT_NORTH; }
		if (first.contentEquals("south") || first.contentEquals("s")) { result = Action.EXIT_SOUTH; }
		if (first.contentEquals("east") || first.contentEquals("e")) { result = Action.EXIT_EAST; }
		if (first.contentEquals("west") || first.contentEquals("w")) { result = Action.EXIT_WEST; }

		if (first.contentEquals("computer"))
		{
			output("Standing by.");
			prompt();

			Scanner godscanner = new Scanner(System.in);

			if (godscanner.nextLine().contentEquals("recognize picard, jean-luc"))
			{
				result = Action.GODMODE_TOGGLE;
			}
			else
			{
				result = Action.NULL_ACTION;
			}
		}
		

		return result;
	}


	private static void updateGame(Action action, GameState state)
	{



		Location curLoc = state.getPlayerLocation();
		Room curRoom = worldMap.get(curLoc);


		switch (action)
		{
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

				for (Item i : itemList)
				{
					if (i.getLocation() == curLoc)
					{
						String word = (i.vowelStart()? "an" : "a");
						output("There is " + word + " " + i.itemName + " here.");
					}
				}

			} break;

			case TAKE:
			{
				for (Item i : itemList)
				{
					if (i.getLocation() == curLoc)
					{
						i.addToInventory();
						i.setLocation(Location.PLAYER_INVENTORY);
						output("You picked up the " + i.itemName + ".");
					}
				}
			} break;

			case DROP:
			{
				for (Item i: itemList)
				{
					if (i.isInInventory())
					{
						i.removeFromInventory();
						i.setLocation(curLoc);
					}
				}
			} break;

			case INVENTORY:
			{
				output("You are carrying: \n");
				for (Item i : itemList)
				{
					if (i.isInInventory())
						output(i.itemName);
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

					output(curRoom.roomName);
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
			}

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
	private static void output(String s) { System.out.println(s); }



	private static void endGame(GameState state)
	{
		// Save the gamestate

		System.out.println("Game has ended.");
	}



}