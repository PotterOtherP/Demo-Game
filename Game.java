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
	SLAP,

	ATTACK,
	TIE

	}

enum ActionType {

	BLANK,
	REFLEXIVE,
	DIRECT,
	INDIRECT
}



/**
 * This is a demo of a text-based adventure game, modeled after the original Zork.
 * The purpose of this is to demonstrate the features that could be expanded
 * into a full-length game of arbitrary size and complexity.
 *
 * @author Nathan Tryon January 2020
 */
public final class Game {

	/*
		TODO: To complete the demo.

		 - (DONE) Solve the issue of tying and untying the rope to the bell. Refactor item objects?
		 - (DONE) For the demo, hacks will be good enough.
		 - Handle ambiguous commands and object words
		 - (DONE) Prompt user for incomplete three-word commands
		 - (DONE) Create dictionaries to parse user input as tokens of one or more words.
	*/

	private static boolean gameover = true;
	private static boolean godmode = false;

	private static HashMap<String, Action> commandOne = new HashMap<String, Action>();
	private static HashMap<String, Action> commandTwo = new HashMap<String, Action>();
	private static HashMap<String, Action> commandThree = new HashMap<String, Action>();

	private static ArrayList<String> dictionary = new ArrayList<String>();

	private static Location initialLocation = Location.RED_ROOM;


	public static void main(String[] args)
	{

		GameState gameState = new GameState();
		String playerText = "";

		
		initGame(gameState);


		gameover = false;

		while (!gameover)
		{	
			playerText = getPlayerText();
			parsePlayerInput(gameState, playerText);
			validateAction(gameState);
			updateGame(gameState);

		}


		endGame(gameState);
		
	}


	private static void initGame(GameState state)
	{	
		// Populate the action lists and the dictionary
		createActions();
		fillDictionary();

		// Create all the objects, then add them to the lists, then add their methods
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

		state.worldMap.put(Location.RED_ROOM, redRoom);
		state.worldMap.put(Location.GREEN_ROOM, greenRoom);
		state.worldMap.put(Location.BLACK_ROOM, blackRoom);
		state.worldMap.put(Location.WHITE_ROOM, whiteRoom);
		state.worldMap.put(Location.BLUE_ROOM, blueRoom);
		state.worldMap.put(Location.MAGIC_ROOM, magicRoom);

		createActions();


        ActivateMethod ringBell = (GameState gs, Action act) -> 
        { 
            
            switch (act)
            {
                case RING:
                {
                    if (!gs.bellRopeTied)
                    {
                        output(GameStrings.BELL_RING_FAIL);
                        return;
                    }

                    output(GameStrings.BELL_RING);

                    if (!gs.bellRung)
                    {
                        Item it = gs.itemList.get("note");
                        it.setLocation(Location.BLACK_ROOM);
                        output(GameStrings.BELL_NOTE_FALL);
                        gs.bellRung = true;
                    }

                } break;

                case TIE:
                {
                    if (gs.usedItem.name.equals("rope"))
                    {
                        output(GameStrings.BELL_ROPE_TIE);
                        Item it = gs.itemList.get("rope");
                        it.setLocation(Location.BLACK_ROOM);
                        gs.bellRopeTied = true;
                    }
                    else
                    {
                        output(GameStrings.BELL_ROPE_TIE_FAIL);
                    }
                } break;

                case KICK:
                {
                    output(GameStrings.BELL_KICK);
                } break;

                default:
                {
                	output(GameStrings.BELL_ACTION_FAIL);
                } break;

            }

        };


		ActivateMethod readNote = (GameState gs, Action act) ->
		{
			
			switch (act)
			{
				case READ:
				{
					output(GameStrings.NOTE_TEXT);
				} break;

				default:
				{
					output(GameStrings.NOTE_ACTION_FAIL);
				} break;
			}

		};

		ActivateMethod playPiano = (GameState gs, Action act) ->
		{
			
			switch (act)
			{
				case PLAY:
				{
					output(GameStrings.PIANO_PLAY);
					Item it = gs.itemList.get("egg");
					if (it.getLocation() == Location.PLAYER_INVENTORY)
					{
						if (!gs.eggOpened)
						{
							output(GameStrings.PIANO_EGG);
							it = gs.itemList.get("key");
							it.setLocation(Location.PLAYER_INVENTORY);
						}

					}
				} break;

				case KICK:
				{
					output(GameStrings.PIANO_KICK);
				} break;

				default:
				{
					output(GameStrings.PIANO_ACTION_FAIL);
				} break;
			}
		};

		ActivateMethod wizardMethod = (GameState gs, Action act) ->
		{
			
			switch (act)
			{
				case ATTACK:
				case KICK:
				{
					output(GameStrings.WIZARD_ATTACK);
				} break;

				case HIGH_FIVE:
				{
					if (gs.wizardHandUp)
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

				default:
				{
					output(GameStrings.WIZARD_ACTION_FAIL);
				} break;
			}
		};

		ActivateMethod openMagicDoor = (GameState gs, Action act) -> 
		{
			switch (act)
			{
				case OPEN:
				case UNLOCK:
				{
					Item it = gs.usedItem;
			
					if (it.name == itemMagKey.name)
					{
						output(GameStrings.MAGICDOOR_OPEN);
						magicDoor.unlock();
						magicDoor.open();
					}

					else
					{
						output(magicDoor.lockFail);
					}
				} break;

				case KICK:
				{
					output(GameStrings.MAGICDOOR_KICK);
				} break;

				default:
				{
					output(GameStrings.MAGICDOOR_ACTION_FAIL);
				} break;
				
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
		magDoorFeature.setMethod(openMagicDoor);
		wizard.setMethod(wizardMethod);
		wizard.setActorMethod(wizardAct);


		// Game objects complete. Start setting up the game



		magicDoor.close();
		magicDoor.lock();


		


		// Put the player in the starting location
		state.setPlayerLocation(initialLocation);
		state.worldMap.get(initialLocation).firstVisit = false;

		// Beginning text of the game.
		outputLine();
		output(GameStrings.GAME_INTRO);
		
	}






	private static void parsePlayerInput(GameState state, String playerText)
	{

		/* Takes whatever the player entered and sets three strings in the gamestate:
		   first (action), second (object 1), third (object 2).
		   Also sets the number of action arguments (non-empty strings).

		*/

		state.resetInput();

		// Just hacking this for the purposes of the demo
		if (playerText.equals("tie rope to bell")
			|| playerText.equals("tie rope to lever")
			|| playerText.equals("attach rope to bell")
			|| playerText.equals("attach rope to lever"))
		{
			state.type = ActionType.INDIRECT;
			state.first = "tie";
			state.second = "bell";
			state.third = "rope";

			return;
		}

		String[] words = playerText.split(" ");

		for (int i = 0; i < words.length; ++i)
		{
			if (!isGameWord(words[i]))
			{
				output("I don't know what " + words[i] + " means.");
				return;
			}
		}

		// Make sure we're deleting the words, not portions of other words...
		playerText = playerText.replaceAll(" the ", " ");
		playerText = playerText.replaceAll(" to ", " ");
		playerText = playerText.replaceAll(" with ", " ");

		// get rid of extra spaces
		while (playerText.contains("  "))
		{
			playerText = playerText.replaceAll("  ", " ");		
		}

		/* Check for an action. If the action is found, set it and trim it
		   from the beginning of the string. If not, check if the first word
		   is recognized by the game.

		*/

		String arg1 = "";
		String arg2 = "";
		String arg3 = "";

		for (String token : commandOne.keySet())
		{
			
			if (startsWith(token, playerText))
			{
				arg1 = token;
				state.type = ActionType.REFLEXIVE;
			}
		}

		for (String token : commandTwo.keySet())
		{
			if (startsWith(token, playerText))
			{
				arg1 = token;
				state.type = ActionType.DIRECT;
			}
		}

		for (String token : commandThree.keySet())
		{
			if (startsWith(token, playerText))
			{
				arg1 = token;
				state.type = ActionType.INDIRECT;
			}
		}

				
		if (arg1.isEmpty())
		{
			arg1 = words[0];
		}

		state.first = arg1;

		playerText = playerText.substring(arg1.length()).trim();
		if (playerText.isEmpty()) return;


		// Set the second argument

		for (String token : state.featureList.keySet())
		{
			if (startsWith(token, playerText))
				arg2 = token;
		}

		for (String token : state.itemList.keySet())
		{
			if (startsWith(token, playerText))
				arg2 = token;
		}

		for (String token : state.actorList.keySet())
		{
			if (startsWith(token, playerText))
				arg2 = token;
		}

		words = playerText.split(" ");
		if (arg2.isEmpty())
		{
			arg2 = words[0];
		}

		state.second = arg2;
		playerText = playerText.substring(arg2.length()).trim();
		if (playerText.isEmpty()) return;


		// Set the third argument
		
		for (String token : state.featureList.keySet())
		{
			if (startsWith(token, playerText))
				arg3 = token;
		}

		for (String token : state.itemList.keySet())
		{
			if (startsWith(token, playerText))
				arg3 = token;
		}

		for (String token : state.actorList.keySet())
		{
			if (startsWith(token, playerText))
				arg3 = token;
		}

		words = playerText.split(" ");
		if (arg3.isEmpty())
		{
			arg3 = words[0];
		}

		state.third = arg3;
		playerText = playerText.substring(arg3.length()).trim();
		if (!playerText.isEmpty())
		{
			output("I don't know what \"" + playerText + "\" means.");
		}



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
			return false;
		}

		state.playerAction = act;


		// Incomplete actions
		if (commandTwo.containsKey(first) && second.isEmpty())
		{
			output("What do you want to " + first + "?");
			String input = getPlayerText();
			if (isGameWord(input))
			{
				second = input;
			}
			else
			{
				parsePlayerInput(state, input);
				return validateAction(state);
			}
		}


		if (commandThree.containsKey(first) && third.isEmpty())
		{

			if (second.isEmpty())
			{
				output("What do you want to " + first + "?");
				String input = getPlayerText();
				if (isGameWord(input))
				{
					second = input;
				}
				else
				{
					parsePlayerInput(state, input);
					return validateAction(state);
				}
			}

			output("What do you want to " + first + " the " + second + " with?");
			{
				String input2 = getPlayerText();
				if (isGameWord(input2))
				{
					third = input2;
				}
				else
				{
					parsePlayerInput(state, input2);
					return validateAction(state);
				}
			}
		}

		switch(state.type)
		{
			case BLANK:
			{

			} break;
			case REFLEXIVE:
			{

			} break;


			case DIRECT:
			case INDIRECT:
			{

				if (state.featureList.containsKey(second))
				// if (actionObjects.get(second).equals("feature"))
				{
					state.objectFeature = state.featureList.get(second);
				}

				else if (state.itemList.containsKey(second))
				//if (actionObjects.get(second).equals("item"))
				{
					state.objectItem = state.itemList.get(second);
				}

				else if (state.actorList.containsKey(second))
				//if (actionObjects.get(second).equals("actor"))
				{
					state.objectActor = state.actorList.get(second);
				}
				
				else 
				{
					return false;
				}

				if (!third.isEmpty())
				{
				
					if (state.itemList.containsKey(third))
					{
						Item it = state.itemList.get(third);
						if (it.getLocation() != Location.PLAYER_INVENTORY)
						{
							output("You're not carrying the " + it.name + ".");
							return false;
						}

						state.usedItem = it;
					}

					else
					{
						output("That isn't going to work.");
					}
					

				}
			
			} break;


			default:
			{
				// we should never be here
			} break;
		}

		return result;

	}


	private static void updateGame(GameState state)
	{
		


		Location curLoc = state.getPlayerLocation();
		Room curRoom = state.worldMap.get(curLoc);

		Action curAction = state.getPlayerAction();

		Feature objFeature = state.objectFeature;
		Item objItem = state.objectItem;
		Actor objActor = state.objectActor;

		Item indItem = state.usedItem;

		// For testing
		if (false)
		{
			output("Selection action is " + curAction);
			output("Selected feature is " + objFeature.name);
			output("Selected item is " + objItem.name);
			output("Selected actor is " + objActor.name);
			output("Indirect object is " + indItem.name);
		}
		



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
			case OPEN:
			{
				if (!objFeature.name.equals("null"))
				{
					if (objFeature.location == curLoc)
						objFeature.activate(state, curAction);
					else
						output("There's no " + objFeature.name + " here.");
				}

				if (!objActor.name.equals("null"))
				{
					if (objActor.location == curLoc)
						objActor.activate(state, curAction);
					else
						output("There's no " + objActor.name + " here.");
				}

				if (!objItem.name.equals("null"))
				{
					if (objItem.getLocation() == Location.PLAYER_INVENTORY)
						objItem.activate(state, curAction);
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
                if (objItem.name.equals("null"))
                {
                    output("That's not something you can take.");
                    return;
                }

                if (objItem.getLocation() == Location.PLAYER_INVENTORY)
				{
					output("You're already carrying the " + objItem.name + "!");
					return;
				}
				
				// Successful take
				if (objItem.getLocation() == curLoc)
				{
					objItem.setLocation(Location.PLAYER_INVENTORY);
					output("You picked up the " + objItem.name + ".");

					// Special cases where taking items affects the game state
					if (objItem.name.equals("rope") && state.bellRopeTied)
						state.bellRopeTied = false;
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
			case JUMP: { output("Wheeeeeeee!"); } break;
			case SHOUT: { output("Yaaaaarrrrggghhh!"); } break;
			case NULL_ACTION: {} break;
			case VERBOSE: { output("You said too many words."); } break;
			case PROFANITY: { output(GameStrings.PROFANITY_ONE); } break;			
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


	// Utility methods used by the other methods in Game.java

	private static void createActions()
	{
		commandOne.put("north", Action.EXIT_NORTH);
		commandOne.put("go north", Action.EXIT_NORTH);
		commandOne.put("walk north", Action.EXIT_NORTH);
		commandOne.put("exit north", Action.EXIT_NORTH);
		commandOne.put("n",     Action.EXIT_NORTH);
		commandOne.put("go n", Action.EXIT_NORTH);
		commandOne.put("walk n", Action.EXIT_NORTH);
		commandOne.put("exit n", Action.EXIT_NORTH);

		commandOne.put("south", Action.EXIT_SOUTH);
		commandOne.put("go south", Action.EXIT_SOUTH);
		commandOne.put("walk south", Action.EXIT_SOUTH);
		commandOne.put("exit south", Action.EXIT_SOUTH);
		commandOne.put("s",     Action.EXIT_SOUTH);
		commandOne.put("go s",     Action.EXIT_SOUTH);
		commandOne.put("walk s",     Action.EXIT_SOUTH);
		commandOne.put("exit s",     Action.EXIT_SOUTH);

		commandOne.put("east",  Action.EXIT_EAST);
		commandOne.put("e",     Action.EXIT_EAST);
		commandOne.put("go east",  Action.EXIT_EAST);
		commandOne.put("walk east",  Action.EXIT_EAST);
		commandOne.put("exit east",  Action.EXIT_EAST);
		commandOne.put("go e",     Action.EXIT_EAST);
		commandOne.put("walk e",     Action.EXIT_EAST);
		commandOne.put("exit e",     Action.EXIT_EAST);

		commandOne.put("west",  Action.EXIT_WEST);
		commandOne.put("go west",  Action.EXIT_WEST);
		commandOne.put("walk west",  Action.EXIT_WEST);
		commandOne.put("exit west",  Action.EXIT_WEST);
		commandOne.put("w",     Action.EXIT_WEST);
		commandOne.put("go w",     Action.EXIT_WEST);
		commandOne.put("walk w",     Action.EXIT_WEST);
		commandOne.put("exit w",     Action.EXIT_WEST);

		commandOne.put("up",	Action.EXIT_UP);
		commandOne.put("go up",	Action.EXIT_UP);
		commandOne.put("exit up",	Action.EXIT_UP);
		commandOne.put("u",	    Action.EXIT_UP);
		commandOne.put("go u",	    Action.EXIT_UP);
		commandOne.put("exit u",	    Action.EXIT_UP);

		commandOne.put("down",  Action.EXIT_DOWN);
		commandOne.put("go down",  Action.EXIT_DOWN);
		commandOne.put("exit down",  Action.EXIT_DOWN);
		commandOne.put("d",     Action.EXIT_DOWN);
		commandOne.put("go d",     Action.EXIT_DOWN);
		commandOne.put("exit d",     Action.EXIT_DOWN);

		commandOne.put("quit",  Action.QUIT);
		commandOne.put("q",     Action.QUIT);
		commandOne.put("jump",  Action.JUMP);

		commandOne.put("look",  Action.LOOK);
		commandOne.put("look around",  Action.LOOK);
		commandOne.put("l",     Action.LOOK);

		commandOne.put("inventory", Action.INVENTORY);
		commandOne.put("i",         Action.INVENTORY);
		commandOne.put("fuck",  Action.PROFANITY);
		commandOne.put("shit",  Action.PROFANITY);
		commandOne.put("shout", Action.SHOUT);
		commandOne.put("yell",  Action.SHOUT);
		commandOne.put("scream",  Action.SHOUT);
		commandOne.put("wait", Action.WAIT);

		commandTwo.put("take", Action.TAKE);
		commandTwo.put("pick up", Action.TAKE);
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
		commandTwo.put("slap", Action.SLAP);
		commandTwo.put("highfive", Action.HIGH_FIVE);
		commandTwo.put("high five", Action.HIGH_FIVE);

		commandThree.put("open", Action.OPEN);
		commandThree.put("unlock", Action.UNLOCK);
		commandThree.put("lock", Action.LOCK);
		commandThree.put("tie", Action.TIE);
	}

	private static void fillDictionary()
	{
		for (int i = 0; i < GameStrings.GAME_WORDS.length; ++i)
		{
			dictionary.add(GameStrings.GAME_WORDS[i]);
		}
	}

	public static void prompt() { System.out.print(">> "); }
	public static void outputLine() { System.out.println(); }
	public static void output() { System.out.println(); }
	public static void output(String s) { System.out.println(s); }

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


		return result.trim().toLowerCase();
	}

	// Checks if "input" starts with "token"
	private static boolean startsWith(String tok, String inp)
	{
		String[] token = tok.split(" ");
		String[] input  = inp.split(" ");

		if (input.length < token.length)
			return false;

		for (int i = 0; i < token.length; ++i)
		{
			if (!token[i].equals(input[i]))
				return false;
		}

		return true;
	}

	private static boolean isGameWord(String str)
	{
		return (dictionary.contains(str));
	}

	public static boolean verifyQuit()
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