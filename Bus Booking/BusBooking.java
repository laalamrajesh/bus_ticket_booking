import java.lang.*;
import java.util.*;
import java.io.*;

public class BusBooking
{
	static List<User> users;
	static List<Station> stations;
	static List<Bus> busses;

	static void setUp()
	{
		users = new ArrayList<User>();
		setUpUsers();

		stations = new ArrayList<Station>();
		setUpStations();

		busses = new ArrayList<Bus>();
		setUpBusses();
	}

	static void setUpUsers()
	{
		users.add(new User(1,"Rajesh"));
		users.add(new User(2,"John"));
		users.add(new User(3,"Peter"));
	}

	static void setUpBusses()
	{
		busses.add( new Bus(1,25,getStation(1), getStation(4), 500));
		busses.add( new Bus(2,30,getStation(2), getStation(5), 600));
	}

	static void setUpStations()
	{
		stations.add(new Station(1, "Station1"));
		stations.add(new Station(2, "Station2"));
		stations.add(new Station(3, "Station3"));
		stations.add(new Station(4, "Station4"));
		stations.add(new Station(5, "Station5"));
	}

	static Station getStation(int station_id)
	{
		for(Station station : stations)
		{
			if(station.id == station_id)
				return station;
		}

		return null;		
	}

	static User getUser(int user_id)
	{
		for(User user: users)
		{
			if(user.id == user_id)
				return user;
		}

		return null;
	}

	static Bus getBusFromAvailableBusses(List<Bus> available_busses, int bus_number)
	{
		for(Bus bus: available_busses)
		{
			if(bus.number == bus_number)
				return bus;
		}

		return null;
	}

	static List<Bus> getAvailableBusses(int source_station_id, int destination_station_id)
	{
		List<Bus> available_busses = new ArrayList<Bus>();
		boolean  is_bus_reach_source_staion, is_bus_reach_destination_staion, is_bus_available;
		for(Bus bus : busses)
		{
			is_bus_reach_source_staion = bus.source_station.id <= source_station_id;
			is_bus_reach_destination_staion = destination_station_id <= bus.destination_station.id;
			is_bus_available = is_bus_reach_source_staion && is_bus_reach_destination_staion;
			if( is_bus_available )
				available_busses.add(bus);
		}

		return available_busses;
	}

	static void showAvailableBusses(List<Bus> available_busses)
	{
		System.out.println("\nAvailable Busses are,");
		System.out.println("Bus no"+"\t"+"Price"+"\t"+"Available seats");
		for(Bus bus : available_busses)
		{
			System.out.println(bus.number+"\t"+bus.booking_price+"\t"+bus.getAvailableSeatsCount());
		}
	}

	static void showAvailableStaions()
	{
		System.out.print("\nAvailable Station id's are ");
		for(Station station : stations)
			System.out.print(station.id+", ");
	}

	static void processSeatsBooking(Bus bus)
	{
		Scanner scanner = new Scanner(System.in);

		boolean booking_succesfull= false;
		while(!booking_succesfull)
		{
			System.out.print("Enter number of Seats : ");
			int number_of_seats = scanner.nextInt();
			List<Integer> booking_seat_ids = new ArrayList<Integer>();
			for(int seat_count = 1; seat_count<=number_of_seats; seat_count++)
			{
				System.out.print("Seat Number : ");
				booking_seat_ids.add(scanner.nextInt());
			}

			boolean booking_status = bus.bookSeats(booking_seat_ids);
			booking_succesfull = booking_status == true;
			if(booking_succesfull)
				System.out.println("*******Booking Successfull!********");
			else
			{
				System.out.println("*******Seats Not Available / Invalid Given ********");
				bus.showAvailableSeats();
			}
		}
	}

	public static void main(String[] args) 
	{
		setUp();
		Scanner scanner = new Scanner(System.in);

		boolean repeat = true;
		while (repeat)
		{
			System.out.println("\n**********Start Booking************");
			System.out.print("\nEnter User ID : ");
			int user_id = scanner.nextInt();
			User user = getUser(user_id);

			showAvailableStaions();
			System.out.print("\nEnter Source Station ID : ");
			int source_station_id = scanner.nextInt();
			System.out.print("Enter Destination Station ID : ");
			int destination_station_id = scanner.nextInt();

			boolean invalid_staions_given = Validation.validateSourceDestination(source_station_id, destination_station_id);
			if (invalid_staions_given)
			{
				showAvailableStaions();
				continue;
			}

			List<Bus> available_busses = getAvailableBusses(source_station_id, destination_station_id);
			boolean no_busses = available_busses.isEmpty();
			if (no_busses)
			{
				System.out.println("Sorry, No Busses Found!");
				continue;
			}
			showAvailableBusses(available_busses);

			System.out.print("\nSelect Bus Number : ");
			int bus_number = scanner.nextInt();
			Bus bus = getBusFromAvailableBusses(available_busses,bus_number);
			boolean is_invalid_bus_given = bus==null;
			if(is_invalid_bus_given)
			{
				System.out.print("\n********Invalid Bus Number ********");
				showAvailableBusses(available_busses);
				continue;
			}
			processSeatsBooking(bus);

		}
	}
}