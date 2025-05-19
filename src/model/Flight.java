package model;

import java.util.*;

public class Flight {
    private final String number;
    private final Plane plane;
    private final City destination;
    private final String departureTime;
    private final Set<String> takenSeats;

    public Flight(String number, Plane plane, City destination, String departureTime) {
        this.number = Objects.requireNonNull(number, "Flight number cannot be null");
        this.plane = Objects.requireNonNull(plane, "Plane cannot be null");
        this.destination = Objects.requireNonNull(destination, "Destination cannot be null");
        this.departureTime = Objects.requireNonNull(departureTime, "Departure time cannot be null");
        this.takenSeats = new HashSet<>();
    }

    public String getNumber() {
        return number;
    }

    public Plane getPlane() {
        return plane;
    }

    public City getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public Set<String> getTakenSeats() {
        return new HashSet<>(takenSeats);
    }

    public void takeSeat(String seat) {
        takenSeats.add(seat);
    }

    private static final double EARTH_RADIUS_KM = 6371.0;

    private static double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private static final double ORIGIN_LONGITUDE = 52.0;
    private static final double ORIGIN_LATTITUDE = 20.0;

    private static final double PRICE_PER_KM_FLOWN = 0.42;

    public double getPrice() {
        return distanceBetween(ORIGIN_LONGITUDE, ORIGIN_LATTITUDE, destination.getLon(), destination.getLat()) * PRICE_PER_KM_FLOWN;
    }

}