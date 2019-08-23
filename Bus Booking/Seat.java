class Seat
{
	int number;
	boolean availability;

	public static final boolean AVAILABLE = true;
	public static final boolean UN_AVAILABLE = false;

	Seat(int number, boolean availability)
	{
		this.number = number;
		this.availability = availability;
	}

	void setUnAvailable()
	{
		this.availability = UN_AVAILABLE;
	}

	void setAvailable()
	{
		this.availability = AVAILABLE;
	}
}