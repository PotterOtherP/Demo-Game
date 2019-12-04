public class CaveGame {

	public static boolean gameover = true;


	public static void main(String[] args)
	{
		System.out.println("Begin program.");


		GameState gameState = new GameState();
		gameover = false;


		initGame(gameState);

		while (!gameover)
		{		
			updateGame(gameState);
		}


		endGame(gameState);
		

		System.out.println("End program.");
	}


	private static void initGame(GameState state)
	{
		// Create all the objects in the game and set the gamestate 

		state.testNumber = 1111;

		System.out.println("Game initialized.");
	}


	private static void updateGame(GameState state)
	{
		// Get the player's input and print the results

		int num = state.testNumber;
		System.out.println("Num is " + num);
		
		state.testNumber += 2;



		++state.turns;
		if (state.turns > 100)
		{
			gameover = true;
		}

	}

	private static void endGame(GameState state)
	{
		// Save the gamestate
		System.out.println("Game has ended.");
	}



}