package registry;

import model.*;
import registry.CityRegistry;

import java.util.*;
import java.time.*;
import java.time.format.*;

public class FlightRegistry {
    private static FlightRegistry INSTANCE = new FlightRegistry();
    private final Map<String, Flight> flights = new HashMap<>();
    
    private static final String getRandomTime() {
        Random random = new Random();
        
        int h = random.nextInt(24);
        int m = random.nextInt(60);

        String hh = String.format("%02d", h);
        String mm = String.format("%02d", m);

        return "" + hh + ":" + mm;
    }

    private FlightRegistry() {
        populate();
    }

    private void populate() {
        Random random = new Random();
        List<City> cities = CityRegistry.getAll();
        boolean everyCityPresent = false;

        while (!everyCityPresent) {
            String flightNumber = "FL" + (100 + random.nextInt(900));
            Plane plane = PlaneRegistry.getRandomPlane();
            City destination = cities.get(random.nextInt(cities.size()));
            String time = getRandomTime();

            add(flightNumber, plane, destination, time);

            everyCityPresent = true;
            for (City city : cities) {
                if (!flights.values().stream().map(c -> c.getDestination()).anyMatch(c -> c.equals(city))) {
                    everyCityPresent = false;
                    break;
                }
            }
        }
    }

    private void add(String number, Plane plane, City destination, String departureTime) {
        flights.put(number, new Flight(number, plane, destination, departureTime));
    }

    public static Flight get(String number) {
        return INSTANCE.flights.get(number);
    }

    public static List<Flight> getAllFlights() {
        List<Flight> result = new ArrayList<>(INSTANCE.flights.values());
        
        Collections.sort(result, new Comparator<Flight>() {
            @Override
            public int compare(Flight l, Flight r) {
                try {
                    LocalTime lt = LocalTime.parse(l.getDepartureTime(), 
                        DateTimeFormatter.ofPattern("H:mm"));
                    LocalTime rt = LocalTime.parse(r.getDepartureTime(), 
                        DateTimeFormatter.ofPattern("H:mm"));
                    return lt.compareTo(rt);
                } catch (DateTimeParseException e) {
                    return 0;
                }
            }
        });

        return Collections.unmodifiableList(result);
    }

    public static List<Flight> getFlightsTo(City destination) {
        List<Flight> result = new ArrayList<>();

        for (Flight flight : getAllFlights()) {
            if (flight.getDestination().equals(destination)) {
                result.add(flight);
            }
        }
        
        return result;
    }
}