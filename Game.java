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
	MAGIC_ROOM,
	PLAYER_INVENTORY,
	NULL_LOCATION

	}

enum ActionType {

	BLANK

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
	HIGH_FIVE,

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
	READ,
	KICK,

	ATTACK,
	TIE

	}






public final class Game {



	private static boolean gameover = true;
	private static boolean godmode = false;

	private static HashMap<String, Action> commandOne = new HashMap<String, Action>();
	private static HashMap<String, Action> commandTwo = new HashMap<String, Action>();
	private static HashMap<String, Action> commandThree = new HashMap<String, Action>();
	private static HashMap<String, String> actionObjects = new HashMap<String, String>();

	private static ArrayList<String> fakeItems = new ArrayList<String>();

	private static Location initialLocation = Location.RED_ROOM;


	public static void main(String[] args)
	{

		GameState gameState = new GameState();

		
		initGame(gameState);


		gameover = false;

		while (!gameover)
		{	
			parsePlayerInput(gameState);
			if (validateAction(gameState))
			{
				updateGame(gameState);
				
			}

		}


		endGame(gameState);
		
	}


	private static void initGame(GameState state)
	{	
		// Create all the objects first, then the lists, then the methods



		Feature nullFeature = new Feature();
		Feature bell = new Feature("bell", Location.BLACK_ROOM);
		Feature piano = new Feature("piano", Location.WHITE_ROOM);
		Feature magDoorFeature = new Feature("door", Location.BLACK_ROOM);



		Item nullItem = new Item();
		Item itemRope = new Item("rope", Location.GREEN_ROOM);
		Item itemEgg = new Item("egg", Location.BLUE_ROOM);
		Item itemNote = new Item("note", Location.NULL_LOCATION);
		Item itemMagKey = new Item("key", Location.NULL_LOCATION);

		Actor wizard = new Actor("wizard", Location.MAGIC_ROOM);

		Door nullDoor = new Door();
		Door redGreenDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.RED_ROOM, Location.GREEN_ROOM);
		Door redBlackDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.RED_ROOM, Location.BLACK_ROOM);
		Door redBlueDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.RED_ROOM, Location.BLUE_ROOM);
		Door redWhiteDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.RED_ROOM, Location.WHITE_ROOM);
		Door blueGreenDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.BLUE_ROOM, Location.GREEN_ROOM);
		Door blueBlackDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.BLUE_ROOM, Location.BLACK_ROOM);
		Door whiteGreenDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.WHITE_ROOM, Location.GREEN_ROOM);
		Door whiteBlackDoor = new Door("passage", GameStrings.CANT_GO, GameStrings.CANT_GO, Location.WHITE_ROOM, Location.BLACK_ROOM);
		Door magicDoor = new Door("door", GameStrings.MAGICDOOR_LOCKED, GameStrings.CANT_GO, Location.BLACK_ROOM, Location.MAGIC_ROOM);

		// Name, description, ID, North, South, East, West
		Room redRoom = new Room("Red Room", GameStrings.DESC_RED_ROOM, Location.RED_ROOM, redGreenDoor, redBlackDoor, redWhiteDoor, redBlueDoor);
		Room greenRoom = new Room("Green Room", GameStrings.DESC_GREEN_ROOM, Location.GREEN_ROOM, nullDoor, redGreenDoor, whiteGreenDoor, blueGreenDoor);
		Room blackRoom = new Room("Black Room", GameStrings.DESC_BLACK_ROOM, Location.BLACK_ROOM, redBlackDoor, magicDoor, whiteBlackDoor, blueBlackDoor);
		Room whiteRoom = new Room("White Room", GameStrings.DESC_WHITE_ROOM, Location.WHITE_ROOM, whiteGreenDoor, whiteBlackDoor, nullDoor, redWhiteDoor);
		Room blueRoom = new Room("Blue Room", GameStrings.DESC_BLUE_ROOM, Location.BLUE_ROOM, blueGreenDoor, blueBlackDoor, redBlueDoor, nullDoor);
		Room magicRoom = new Room("Magic Room", GameStrings.DESC_MAGIC_ROOM, Location.MAGIC_ROOM, magicDoor, nullDoor, nullDoor, nullDoor);

		state.itemList.put(nullItem.name, nullItem);
		state.itemList.put(itemRope.name, itemRope);
		state.itemList.put(itemEgg.name, itemEgg);
		state.itemList.put(itemNote.name, itemNote);
		state.itemList.put(itemMagKey.name, itemMagKey);

		state.featureList.put(nullFeature.name, nullFeature);
		state.featureList.put(bell.name, bell);
		state.featureList.put(piano.name, piano);
		state.featureList.put(magDoorFeature.name, magDoorFeature);
	
		state.actorList.put(wizard.name, wizard);

		actionObjects.put(itemRope.name, "item");
		actionObjects.put(itemEgg.name, "item");
		actionObjects.put(itemNote.name, "item");
		actionObjects.put(itemMagKey.name, "item");
		actionObjects.put(bell.name, "feature");
		actionObjects.put(piano.name, "feature");
		actionObjects.put(wizard.name, "actor");
		actionObjects.put("door", "door");
		actionObjects.put("magicdoor", "door");


		

		state.worldMap.put(Location.RED_ROOM, redRoom);
		state.worldMap.put(Location.GREEN_ROOM, greenRoom);
		state.worldMap.put(Location.BLACK_ROOM, blackRoom);
		state.worldMap.put(Location.WHITE_ROOM, whiteRoom);
		state.worldMap.put(Location.BLUE_ROOM, blueRoom);
		state.worldMap.put(Location.MAGIC_ROOM, magicRoom);

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
		commandOne.put("scream",  Action.SHOUT);
		commandOne.put("highfive", Action.HIGH_FIVE);
		commandOne.put("wait", Action.WAIT);

		commandTwo.put("take", Action.TAKE);
		commandTwo.put("drop", Action.DROP);
		commandTwo.put("open", Action.OPEN);
		commandTwo.put("close", Action.CLOSE);
		commandTwo.put("lock", Action.LOCK);
		commandTwo.put("say", Action.SPEAK);
		commandTwo.put("ring", Action.RING);
		commandTwo.put("play", Action.PLAY);
		commandTwo.put("read", Action.READ);
		commandTwo.put("kick", Action.KICK);
		commandTwo.put("hit", Action.ATTACK);
		commandTwo.put("attack", Action.ATTACK);
		commandTwo.put("punch", Action.ATTACK);

		commandThree.put("open", Action.OPEN);
		commandThree.put("unlock", Action.UNLOCK);
		commandThree.put("lock", Action.LOCK);
		commandThree.put("tie", Action.TIE);

		ActivateMethod ringBell = () -> 
		{ 
			Action act = state.currentAction;

			switch (act)
			{
				case RING:
				{
					if (!state.bellRopeTied)
					{
						output(GameStrings.BELL_RING_FAIL);
						return;
					}

					output("Ding dong ding dong!");

					if (!state.bellRung)
					{
						Item it = state.itemList.get("note");
						it.setLocation(Location.BLACK_ROOM);
						output("A note falls out of the bell!");
						state.bellRung = true;
					}

				} break;

				case TIE:
				{
					if (state.indirectObject.name.equals("rope"))
					{
						output(GameStrings.BELL_ROPE_TIE);
						Item it = state.itemList.get("rope");
						it.setLocation(Location.BLACK_ROOM);
						state.bellRopeTied = true;
					}
					else
					{
						output(GameStrings.BELL_ROPE_TIE_FAIL);
					}
				} break;

				case KICK:
				{
					output("BWWWOOONG!! Ow!");
				} break;

				default: {} break;

			}

		};

		ActivateMethod readNote = () ->
		{
			Action act = state.currentAction;

			switch (act)
			{
				case READ:
				{
					output("The note says: ");
					output("It may seem odd, but sometimes playing a piano will do something to an egg.");
				} break;

				default: {} break;
			}

		};

		ActivateMethod playPiano = () ->
		{
			Action act = state.currentAction;

			switch (act)
			{
				case PLAY:
				{
					output("Da-da Da-da Da Da-da-da DUNNN...");
					Item it = state.itemList.get("egg");
					if (it.getLocation() == Location.PLAYER_INVENTORY)
					{
						if (!state.eggOpened)
						{
							output("The egg cracks open, revealing a key!");
							it = state.itemList.get("key");
							it.setLocation(Location.PLAYER_INVENTORY);
						}

					}
				} break;

				case KICK:
				{
					output("That's not something you should do to this nice piano.");
				} break;

				default: {} break;
			}
		};

		ActivateMethod wizardMethod = () ->
		{
			Action act = state.currentAction;

			switch (act)
			{
				case ATTACK:
				case KICK:
				{
					output("The wizard easily evades your feeble attempts at violence.");
				} break;

				case HIGH_FIVE:
				{
					if (state.wizardHandUp)
					{
						output(GameStrings.WIZARD_HIGHFIVE);
						output(GameStrings.GAME_WON);
						gameover = true;						
					}
					else
					{
						output(GameStrings.HIGHFIVE_FAIL);
					}
				} break;

				default: {} break;
			}
		};

		ActivateMethod openMagicDoor = () -> 
		{
			Item it = state.indirectObject;
			
			if (it.name == itemMagKey.name)
			{
				output("You unlock the door with the key.");
				magicDoor.unlock();
				magicDoor.open();
			}

			else
			{
				output(magicDoor.lockFail);
			}
		};


		


		ActorMethod wizardAct = () ->
		{
			Actor self = state.actorList.get("wizard");

			if (state.getPlayerLocation() != self.currentLocation)
			{
				return;
			}

			if (!self.playerHasEncountered())
			{
				// output("There is a wizard here!");
				self.setEncountered(true);
			}

			String[] wizStrings = {GameStrings.WIZARD_ONE, GameStrings.WIZARD_TWO, GameStrings.WIZARD_THREE, GameStrings.WIZARD_FOUR, GameStrings.WIZARD_FIVE};

			int x = (int)(Math.random() * 5);
			if (state.wizardTurns % 5 == 0)
				x = 4;

			state.wizardHandUp = (x == 4? true : false);

			output(wizStrings[x]);
			++state.wizardTurns;

		};

		bell.setMethod(ringBell);
		piano.setMethod(playPiano);
		itemNote.setMethod(readNote);
		wizard.setMethod(wizardMethod);
		wizard.setActorMethod(wizardAct);


		



		magicDoor.close();
		magicDoor.lock();


		fakeItems.add("juniper");


		// Put the player in the starting location
		state.setPlayerLocation(initialLocation);
		state.worldMap.get(initialLocation).firstVisit = false;

		// Beginning text of the game.
		outputLine();
		output(GameStrings.GAME_INTRO);
		
	}




	private static void parsePlayerInput(GameState state)
	{

		/* Takes whatever the player entered and sets three strings in the gamestate:
		   first (action), second (actionable object), third (item).
		   Also sets the number of action arguments (non-empty strings).

		*/

		state.resetInput();

		String playerText = getPlayerText();
		String[] words = playerText.split(" ");

		String arg1 = "";
		String arg2 = "";
		String arg3 = "";
		String arg4 = "";

		try { arg1 = words[0]; } catch (Exception e) {}
		try { arg2 = words[1]; } catch (Exception e) {}
		try { arg3 = words[2]; } catch (Exception e) {}
		try { arg4 = words[3]; } catch (Exception e) {}

		if (!arg4.isEmpty() && arg3.equals("with"))
		{
			arg3 = arg4;
		}

		state.first = arg1;
		state.second = arg2;
		state.third = arg3;

		int numWords = 0;

		if(!arg1.isEmpty())
			++numWords;

		if (!arg2.isEmpty())
			++numWords;

		if(!arg3.isEmpty())
			++numWords;

		state.numInputWords = numWords;




		




	}

	private static boolean validateAction(GameState state)
	{
		/* Verifies that the action arguments are recognized by the game.

		   Gets more information from the player if the action is incomplete.
		*/
		boolean result = true;


		String first = state.first;
		String second = state.second;
		String third = state.third;

		Action act = Action.NULL_ACTION;
		if (commandOne.containsKey(first)) { act = commandOne.get(first); }
		else if (commandTwo.containsKey(first)) { act = commandTwo.get(first); }
		else if (commandThree.containsKey(first)) { act = commandThree.get(first); }
		else
		{ 
			output("I don't know what \"" + first + "\" means.");
			return false;
		}

		state.currentAction = act;

		switch(state.numInputWords)
		{
			case 1:
			{

			} break;


			case 2:
			case 3:
			{
				if (!actionObjects.containsKey(second))
				{
					output("I don't know what \"" + second + "\" means.");
					return false;
				}

				
				if (actionObjects.get(second).equals("feature"))
				{
					state.objectFeature = state.featureList.get(second);
				}

				if (actionObjects.get(second).equals("item"))
				{
					state.objectItem = state.itemList.get(second);
				}

				if (actionObjects.get(second).equals("actor"))
				{
					state.objectActor = state.actorList.get(second);
				}

				if (actionObjects.get(second).equals("door"))
				{
					state.objectDoor = second;
				}
				

				if (!third.isEmpty() && actionObjects.containsKey(third))
				{
					if (actionObjects.get(third).equals("item"))
					{
						Item it = state.itemList.get(third);
						if (it.getLocation() != Location.PLAYER_INVENTORY)
						{
							output("You're not carrying the " + it.name + ".");
							return false;
						}

						state.indirectObject = it;


					}
				}
			
			} break;


			default:
			{
				// we should never be here
				output("Illegal number of command argments: " + state.numInputWords);
			} break;
		}

		return result;

	}


	private static void updateGame(GameState state)
	{
		


		Location curLoc = state.getPlayerLocation();
		Room curRoom = state.worldMap.get(curLoc);

		Action curAction = state.getCurrentAction();

		Feature objFeature = state.objectFeature;
		Item objItem = state.objectItem;
		Actor objActor = state.objectActor;
		String objDoor = state.objectDoor;

		Item indItem = state.indirectObject;

		/*
		output("Selection action is " + curAction);
		output("Selected feature is " + objFeature.name);
		output("Selected item is " + objItem.name);
		output("Selected actor is " + objActor.name);
		output("Selected door is " + objDoor);
		output("Indirect object is " + indItem.name);
		*/



		switch (curAction)
		{

			// Features

			case ACTIVATE:
			case RING:
			case PLAY:
			case KICK:
			case READ:
			case TIE:
			case ATTACK:
			case HIGH_FIVE:
			{
				if (!objFeature.name.equals("null"))
				{
					if (objFeature.location == curLoc)
						objFeature.activate();
					else
						output("There's no " + objFeature.name + " here.");
				}

				if (!objActor.name.equals("null"))
				{
					if (objActor.location == curLoc)
						objActor.activate();
					else
						output("There's no " + objActor.name + " here.");
				}

				if (!objItem.name.equals("null"))
				{
					if (objItem.getLocation() == Location.PLAYER_INVENTORY)
						objItem.activate();
					else
						output("You're not carrying the " + objItem.name + ".");
				}

				

			} break;



			case LOOK:
			{
				curRoom.lookAround(state);

			} break;

			case TAKE:
			{
				
				if (objItem.getLocation() == curLoc)
				{
					if (objItem.getLocation() == Location.PLAYER_INVENTORY)
					{
						output("You're already carrying the " + objItem.name);
						return;
					}
					else
					{
						objItem.setLocation(Location.PLAYER_INVENTORY);
						output("You picked up the " + objItem.name + ".");
						
					}
				}				
				else
				{
					output("There's no " + objItem.name + " here.");
				}	
			} break;

			case DROP:
			{
				if (objItem.getLocation() == Location.PLAYER_INVENTORY)
				{
					objItem.setLocation(curLoc);
					output("You dropped the " + objItem.name + ".");
				}
				else
				{
					output("You're not carrying that.");
				}
			} break;

			case INVENTORY:
			{
				output("You are carrying: \n");
				for (Item it : state.itemList.values())
				{
					if (it.getLocation() == Location.PLAYER_INVENTORY)
						output(it.name);
				}
			} break;

			case OPEN:
			case UNLOCK:
			{
				for (Door d : curRoom.exits)
				{
					if (d.name.equals(objDoor))
					{
						d.unlock();
						d.open();
					}
					
				}
			
			} break;

			case LOCK:
			{
				for (Door d : curRoom.exits)
				{
					if (d.name.equals(objDoor))
					{
						d.lock();
						d.close();
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
					curRoom = state.worldMap.get(state.getPlayerLocation());
					output(curRoom.name);
					outputLine();
					if (curRoom.firstVisit)
					{
						curRoom.firstVisit = false;
						curRoom.lookAround(state);
					}
				}

			} break;


			// Simple actions

			case WAIT: { output("Time passes..."); } break;
			case FAIL_ACTION: { output("Fail action."); } break;
			case JUMP: { output("Wheeeeeeee!"); } break;
			case SHOUT: { output("Yaaaaarrrrggghhh!"); } break;
			case NULL_ACTION: {} break;
			case VERBOSE: { output("You said too many words."); } break;
			case PROFANITY: { output("There's no need for that kind of language."); } break;			
			case QUIT: { /* if (verifyQuit()) */ gameover = true; } break;

			default: {} break;
		}

		// The player's action could end the game before anything else happens.
		if (gameover) return;


		for (Actor a : state.actorList.values())
		{
			if (a.isAlive()) { a.actorTurn(); }
		}

		state.addTurn();

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

		output("Game has ended.");
		output("Total turns: " + state.turns);

	}



}