class Validation
{
	static boolean validateSourceDestination(int source_station_id, int destination_station_id)
	{
		if (source_station_id<destination_station_id)
		{
			int total_staions = BusBooking.stations.size();
			if(source_station_id<1 || destination_station_id>total_staions)
				return true;
		}
		else
			return true;

		return false;
	}
}