import ui.*;
import registry.*;
import model.*;
import util.*;

import java.util.*;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        UI.start(Main::windowRoot);
    }

    private static boolean isNumber(String s) {
        return s.matches("\\d+");
    }

    private static String input(String prompt) {
        System.out.print("Enter " + prompt + ": ");
        return new Scanner(System.in).nextLine();
    }

    private static void simpleOptionMenu(List<MenuOption> options) {
        System.out.println("\nOptions:");
        for (MenuOption option : options) {
            System.out.println("    - " + option.key + ". " + option.description);
        }

        while (true) {
            String choice = input("choice");
            for (MenuOption option : options) {
                if (choice.equals(option.key)) {
                    UI.setWindow(option.action);
                    return;
                }
            }
            System.out.println("Not an option");
        }
    }

    private static void windowRoot() {
        simpleOptionMenu(Arrays.asList(
            new MenuOption("q", "Quit", UI::stop),
            new MenuOption("1", "Flyer", Main::windowFlyer),
            new MenuOption("2", "Flight", Main::windowFlight)
        ));
    }

    private static void windowFlyer() {
        simpleOptionMenu(Arrays.asList(
            new MenuOption("q", "Back (root)", Main::windowRoot),
            new MenuOption("1", "Register a new flyer", Main::windowRegisterFlyer),
            new MenuOption("2", "Get flyer info", Main::windowGetFlyerInfo),
            new MenuOption("3", "Upgrade flyer (Loyalty program)", Main::windowFlyerUpgrade)
        ));
    }

    private static void windowFlight() {
        simpleOptionMenu(Arrays.asList(
            new MenuOption("q", "Back (Flyer)", Main::windowFlyer),
            new MenuOption("1", "Get all flights", Main::windowGetAllFlights),
            new MenuOption("2", "Book a flight", Main::windowBookFlight)
        ));
    }

    private static void windowRegisterFlyer() {
        String passportNumber = input("passport number");
        String firstName = input("first name");
        String lastName = input("last name");

        FlyerRegistry.add(firstName, lastName, passportNumber);
        System.out.println("Flyer registered successfully");
        UI.setWindow(Main::windowRoot);
    }

    private static void windowGetFlyerInfo() {
        String passportNumber = input("passport number");
        Flyer flyer = FlyerRegistry.get(passportNumber);

        if (flyer == null) {
            System.out.println("Error: No flyer with passport number '" + passportNumber + "' exists.");
        } else {
            System.out.println("Passport number: " + flyer.getPassportNumber());
            System.out.println("First name: " + flyer.getFirstName());
            System.out.println("Last name: " + flyer.getLastName());
            System.out.println("Loyalty status: " + flyer.getLoyaltyStatus().toString());
        }

        UI.setWindow(Main::windowRoot);
    }

    private static void windowFlyerUpgrade() {
        String passportNumber = input("passport number");
        Flyer flyer = FlyerRegistry.get(passportNumber);

        if (flyer == null) {
            System.out.println("Error: No flyer with passport number '" + passportNumber + "' exists.");
            UI.setWindow(Main::windowRoot);
            return;
        }

        LoyaltyStatus current = flyer.getLoyaltyStatus();
        
        if (current == LoyaltyStatus.PLATINUM) {
            System.out.println("Error: Flyer already has maximum status (Platinum)");
            UI.setWindow(Main::windowRoot);
            return;
        }

        System.out.println("Current status: " + current.toString());
        System.out.println("Available upgrades:");

        int currentLevel = current.ordinal();
        int options = LoyaltyStatus.PLATINUM.ordinal() - currentLevel;
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= options; i++) {
            LoyaltyStatus newStatus = LoyaltyStatus.values()[currentLevel + i];
            System.out.println("  " + i + ". " + newStatus.toString() + 
                              " (" + (newStatus.getPrice() - current.getPrice()) + " PLN)");
        }

        System.out.print("Select upgrade (1-" + options + "): ");
        try {
            int choice = scanner.nextInt();
            if (choice < 1 || choice > options) {
                System.out.println("Invalid selection");
                UI.setWindow(Main::windowRoot);
                return;
            }

            LoyaltyStatus newStatus = LoyaltyStatus.values()[currentLevel + choice];

            try {
                System.out.println("Receipt: file://" + Printer.printReceipt(new String[] { "Upgrade loyalty status of " + flyer.getFullName() + " [" + flyer.getPassportNumber() + "] from " + flyer.getLoyaltyStatus() + " to " + newStatus }, new double[] { (double) (newStatus.getPrice() - current.getPrice()) }));
            } catch (Exception e) {
                System.out.println(ANSI.RED + "Failed to print a ticket: " + e.getMessage() + ANSI.RESET);
            }

            flyer.upgradeStatus(newStatus);
            System.out.println("Upgraded to " + newStatus.toString());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }

        UI.setWindow(Main::windowRoot);
    }

    private static void printFlightInfo(Flight flight) {
        System.out.println(
            flight.getNumber()
                + " -> "
                + flight.getDestination().getCode()
                + " ("
                + flight.getDepartureTime()
                + ")"
        );
    }

    private static void windowGetAllFlights() {
        List<Flight> flights = FlightRegistry.getAllFlights();
        for (Flight flight : flights) {
            printFlightInfo(flight);
        }
        UI.setWindow(Main::windowRoot);
    }

    private static void windowBookFlight() {
        System.out.println("Options:");
        System.out.println("    - q. Back (Flight)");
        System.out.println("    - 1. By flight code");
        System.out.println("    - 2. By city code");
        System.out.println("    - 3. By city name");

        Flight flight = null;
        do {
            String choice = input("choice");
            switch (choice) {
                case "q": {
                    UI.setWindow(Main::windowFlight);
                    return;
                }
                case "1": {
                    do {
                        String code = input("code");
                        flight = FlightRegistry.get(code);
                    } while (flight == null);
                    break;
                }
                case "2": {
                    City city = null;

                    do {
                        String code = input("city code");
                        city = CityRegistry.get(code);
                    } while (city == null);

                    System.out.println("Flights to " + city.getName() + ":");
                    for (Flight f : FlightRegistry.getFlightsTo(city)) {
                        printFlightInfo(f);
                    }

                    do {
                        String code = input("flight code");
                        flight = FlightRegistry.get(code);
                    } while (flight == null);

                    break;
                }
                case "3": {
                    City city = null;
                    List<City> cities = CityRegistry.getAll();

                    do {
                        String name = input("city name");
                        
                        City closest = null;
                        int closestScore = 1024;
                        
                        for (City c : cities) {
                            int score = StringDifference.calculateDifference(
                                name.toLowerCase(),
                                c.getName().toLowerCase()
                            );

                            if (score == 0) {
                                city = c;
                                break;
                            }

                            if (score < closestScore) {
                                closestScore = score;
                                closest = c;
                            }
                        }

                        if (city != null) break;

                        System.out.println("City " + name + " not found, did you mean " + closest.getName() + "?");

                        char c = 'X';
                        do {
                            c = input("choice y/n").toLowerCase().charAt(0);
                        } while ("yn".indexOf(c) == -1);

                        if (c == 'y') {
                            city = closest;
                        }
                    } while (city == null);

                    System.out.println("Flights to " + city.getName() + ":");
                    for (Flight f : FlightRegistry.getFlightsTo(city)) {
                        printFlightInfo(f);
                    }

                    do {
                        String code = input("flight code");
                        flight = FlightRegistry.get(code);
                    } while (flight == null);

                    break;
                }
            }
        } while (flight == null);

        flight.getPlane().draw(flight.getTakenSeats());

        String[] passportNumbers = null;
        boolean ok = true;
        do {
            passportNumbers = input("passport numbers (comma separated)").split(",");
            
            ok = true;
            
            if (passportNumbers.length == 0) {
                System.out.println("No passport numbers provided");
                ok = false;
                continue;
            }

            for (String passportNumber : passportNumbers) {
                if (FlyerRegistry.get(passportNumber) == null) {
                    System.out.println("Flyer " + passportNumber + " not found");
                    ok = false;
                }
            }
        } while (!ok);
        
        String[] seats = null;
        do {
            seats = input("seat numbers (comma separated)").split(",");
            
            ok = true;

            if (seats.length == 0) {
                System.out.println("No seats provided");
                ok = false;
                continue;
            }

            if (passportNumbers.length != seats.length) {
                System.out.println("Passport number count (" + passportNumbers.length + ") != seat count (" + seats.length + ")");
                ok = false;
            }
            
            for (String seat : seats) {
                if (flight.getTakenSeats().contains(seat)) {
                    System.out.println("Seat " + seat + " already occupied");
                    ok = false;
                }
            }
        } while (!ok);

        String[] items = new String[passportNumbers.length];
        double[] prices = new double[passportNumbers.length];

        for (int i = 0; i < passportNumbers.length; i++) {
            String passportNumber = passportNumbers[i];
            String seat = seats[i];

            Flyer flyer = FlyerRegistry.get(passportNumber);

            items[i] = "Seat " + seat + " at flight " + flight.getNumber();
            prices[i] = flyer.calculateDiscountedPrice(flight.getPrice());

            try {
                System.out.println("Ticket " + (i + 1) + ": file://" + Printer.printPlaneTicket(flight, flyer, seat));
            } catch (Exception e) {
                System.out.println(ANSI.RED + "Failed to print a ticket: " + e.getMessage() + ANSI.RESET);
            }

            flight.takeSeat(seat);
        }

        try {
            System.out.println("Receipt: file://" + Printer.printReceipt(items, prices));
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Failed to print a ticket: " + e.getMessage() + ANSI.RESET);
        }

        UI.setWindow(Main::windowRoot);
    }
}