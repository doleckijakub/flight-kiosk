package registry;

import model.Plane;

import java.util.*;

public final class PlaneRegistry {
    private static PlaneRegistry INSTANCE = new PlaneRegistry();
    private final Map<String, Plane> planes = new HashMap<>();

    private PlaneRegistry() {
        populate();
    }

    private void populate() {
        add(new Plane("BOEING-737", "Boeing 737-800", 12, 16, 3, 3));
        add(new Plane("BOEING-747", "Boeing 747-400", 15, 25, 4, 3));
        add(new Plane("AIRBUS-A320", "Airbus A320", 10, 15, 3, 3));
        add(new Plane("AIRBUS-A380", "Airbus A380", 20, 30, 5, 4));
        add(new Plane("EMBRAER-190", "Embraer E190", 8, 12, 2, 2));
    }

    private void add(Plane plane) {
        planes.put(plane.number, plane);
    }

    public static Plane get(String number) {
        return INSTANCE.planes.get(number);
    }

    public static List<Plane> getAll() {
        return new ArrayList<>(INSTANCE.planes.values());
    }

    public static Plane getRandomPlane() {
        List<Plane> allPlanes = getAll();
        if (allPlanes.isEmpty()) {
            throw new IllegalStateException("No planes in registry");
        }
        return allPlanes.get(new Random().nextInt(allPlanes.size()));
    }
}