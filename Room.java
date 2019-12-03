public class Room {

	private final String roomName;
	private final String fullDescription;
	private final int roomID;


	public Room()
	{
		roomName = "";
		fullDescription = "";
		roomID = 0;
	}

	public Room(String name, String fulldesc, int ID)
	{
		this.roomName = name;
		this.fullDescription = fulldesc;
		this.roomID = ID;
	}

	public int getRoomID()
	{
		return roomID;
	}

	public void printFullDescription()
	{
		System.out.println(fullDescription);
	}

	
}