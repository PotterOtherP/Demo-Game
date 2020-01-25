interface ActivateMethod {

    public void run(GameState state);
}

abstract class GameObject {
    
    public final String name;
    public final Location location;
    private ActivateMethod method;

    // Constructors
    public GameObject()
    {
        this.name = "null";
        this.location = Location.NULL_LOCATION;
        this.method = (GameState state) -> {};
    }

    public GameObject(String name)
    {
        this.name = name;
        this.location = Location.NULL_LOCATION;
        this.method = (GameState state) -> {};

    }

    public GameObject(String name, Location loc)
    {
        this.name = name;
        this.location = loc;
        this.method = (GameState state) -> {};
    }

    public void setMethod(ActivateMethod am)
    {
        this.method = am;
    }


    public void activate(GameState state)
    {
        method.run(state);
    }

    public boolean vowelStart()
    {
        // Exceptions can go here
        if (false)
        {
            return true;
        }

        boolean result = false;

        String str = this.name.toLowerCase();
        char c  = str.charAt(0);

        switch(c)
        {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            {
                result = true;
            } break;

            default:
            {
                result = false;
            } break;
        }

        return result;
    }



    

}