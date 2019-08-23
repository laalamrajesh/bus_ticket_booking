import java.util.*;
import java.lang.*;

class Bus
{
	int number;
	int seats_count;
	List<Seat> seats;
	Station source_station; 
	Station destination_station;
	int booking_price;

	public static final boolean AVAILABLE = true;
	public static final boolean UN_AVAILABLE = false;

	Bus(int number, int seats_count, Station source_station, 
		Station destination_station, int booking_price)
	{
		this.number = number;
		this.seats_count = seats_count;
		this.source_station = source_station;
		this.destination_station = destination_station;
		this.booking_price = booking_price;

		this.setUpSeats();
	}

	void setUpSeats()
	{
		this.seats = new ArrayList<Seat>();
		for( int seat_number = 1 ; seat_number <= this.seats_count; seat_number++ )
		{
			Seat seat = new Seat(seat_number, AVAILABLE);
			seats.add(seat);
		}
	}

	int getAvailableSeatsCount()
	{
		int available_seats_count = 0;
		for(Seat seat: seats)
		{
			if (seat.availability == AVAILABLE )
				available_seats_count+=1;
		}

		return available_seats_count;
	}


	boolean checkSeatsAvailability(List<Integer> booking_seat_ids, HashMap<Integer, Seat> seat_hash_map)
	{
		for(int seat_id : booking_seat_ids)
		{
			Seat seat = seat_hash_map.get(seat_id);
			try
			{
				boolean seat_not_available = seat.availability == UN_AVAILABLE;
				if(seat_not_available)
					return false;
			}
			catch(NullPointerException n)
			{
				return false;
			}
		}


		return true;
	}


	boolean bookSeats(List<Integer> booking_seat_ids)
	{
		HashMap<Integer, Seat> seat_hash_map = this.getSeatHashMap();

		boolean is_all_seats_available = checkSeatsAvailability(booking_seat_ids, seat_hash_map);
		if(is_all_seats_available)
		{
			for(int seat_id : booking_seat_ids)
			{
				Seat seat = seat_hash_map.get(seat_id);
				seat.setUnAvailable();
			}
		}

		boolean booking_status = is_all_seats_available == true;
		return booking_status;
	}

	HashMap<Integer, Seat> getSeatHashMap()
	{
		HashMap<Integer, Seat> seat_hash_map = new HashMap<Integer, Seat>();
		for(Seat seat: seats)
		{
			seat_hash_map.put(seat.number,seat);
		}

		return seat_hash_map;
	}

	void showAvailableSeats()
	{
		System.out.print("Available Seats are ");
		for(Seat seat: seats)
		{
			boolean is_seat_available = seat.availability == AVAILABLE;
			if(is_seat_available)
				System.out.print(seat.number+ ", ");
		}
		System.out.println();
	}
}