package registry;

import model.Flyer;

import java.util.ArrayList;
import java.util.List;

public class FlyerRegistry {
    private static FlyerRegistry INSTANCE = new FlyerRegistry();
    private final List<Flyer> flyers = new ArrayList<>();

    private FlyerRegistry() {}

    public static void add(String firstName, String lastName, String passportNumber) {
        INSTANCE
            .flyers
            .add(new Flyer(firstName, lastName, passportNumber));
    }

    public static Flyer get(String passportNumber) {
        return INSTANCE.
                    flyers
                    .stream()
                   .filter(flyer -> flyer.getPassportNumber().equals(passportNumber))
                   .findFirst()
                   .orElse(null);
    }

    public List<Flyer> getAllFlyers() {
        return new ArrayList<>(flyers);
    }
}