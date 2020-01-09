abstract class GameObject {
	
	public final String name;
	public final Runnable method1;
	public final Runnable method2;
	public final Runnable method3;
	public final Action keyAction1;
	public final Action keyAction2;
	public final Action keyAction3;


	public Location location;

	public GameObject()
	{
		this.name = "";
		this.method1 = GameState::nullMethod;
		this.method2 = GameState::nullMethod;
		this.method3 = GameState::nullMethod;
		this.keyAction1 = Action.NULL_ACTION;
		this.keyAction2 = Action.NULL_ACTION;
		this.keyAction3 = Action.NULL_ACTION;
	}

	

	public GameObject(String name, Location loc, Runnable r1, Runnable r2, Runnable r3, Action act1, Action act2, Action act3)
	{
		this.name = name;
		this.location = loc;
		this.method1 = r1;
		this.method2 = r2;
		this.method3 = r3;
		this.keyAction1 = act1;
		this.keyAction2 = act2;
		this.keyAction3 = act3;
	}

	public void activate(Runnable actMethod1, Runnable actMethod2, Runnable actMethod3, Action act)
	{
		
		if (act == keyAction1)
		{
			actMethod1.run();
			
		}
		else if (act == keyAction2)
		{
			actMethod2.run();
		}

		else if (act == keyAction3)
		{
			actMethod3.run();
		}

		else
		{
			Game.output("That's not something you can do to the " + name + ".");
		}
	}



}