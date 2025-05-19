package registry;

import model.City;

import java.util.*;

public class CityRegistry {
    private static CityRegistry INSTANCE = new CityRegistry();
    private final Map<String, City> cities = new HashMap<>();

    private CityRegistry() {
        populate();
    }

    private void populate() {
        add("WAR", "Warsaw", 52.2300, 21.0111);
        add("KRA", "Kraków", 50.0614, 19.9372);
        add("WRO", "Wrocław", 51.1100, 17.0325);
        add("ŁÓD", "Łódź", 51.7769, 19.4547);
        add("POZ", "Poznań", 52.4083, 16.9336);
        add("GDA", "Gdańsk", 54.3475, 18.6453);
        add("SZC", "Szczecin", 53.4325, 14.5481);
        add("BYD", "Bydgoszcz", 53.1219, 18.0003);
        add("LUB", "Lublin", 51.2500, 22.5667);
        add("BIA", "Białystok", 53.1353, 23.1456);
        add("KAT", "Katowice", 50.2625, 19.0217);
        add("GDY", "Gdynia", 54.5175, 18.5400);
        add("ZIE", "Zielona Góra", 51.9333, 15.5000);
        add("CZĘ", "Częstochowa", 50.8000, 19.1167);
        add("RAD", "Radom", 51.4036, 21.1567);
        add("TOR", "Toruń", 53.0222, 18.6111);
        add("RZE", "Rzeszów", 50.0333, 22.0000);
        add("SOS", "Sosnowiec", 50.2833, 19.1333);
        add("KIE", "Kielce", 50.8742, 20.6333);
        add("GLI", "Gliwice", 50.2833, 18.6667);
        add("OLS", "Olsztyn", 53.7778, 20.4792);
        add("BIE", "Bielsko - Biała", 49.8225, 19.0444);
        add("ZAB", "Zabrze", 50.3025, 18.7781);
        add("BYT", "Bytom", 50.3483, 18.9156);
        add("RUD", "Ruda Śląska", 50.2628, 18.8536);
        add("RYB", "Rybnik", 50.0833, 18.5500);
        add("OPO", "Opole", 50.6667, 17.9333);
        add("TYC", "Tychy", 50.1236, 18.9867);
        add("GOR", "Gorzów Wielkopolski", 52.7333, 15.2500);
        add("DĄB", "Dąbrowa Górnicza", 50.3214, 19.1872);
        add("ELB", "Elbląg", 54.1667, 19.4000);
        add("PŁO", "Płock", 52.5500, 19.7000);
        add("TAR", "Tarnów", 50.0125, 20.9886);
        add("KOS", "Koszalin", 54.2000, 16.1833);
        add("WŁO", "Włocławek", 52.6592, 19.0681);
        add("WAŁ", "Wałbrzych", 50.7667, 16.2833);
        add("CHO", "Chorzów", 50.3000, 18.9500);
        add("KAL", "Kalisz", 51.7575, 18.0800);
        add("LEG", "Legnica", 51.2083, 16.1603);
        add("NOW", "Nowy Sącz", 49.6239, 20.6972);
        add("MYS", "Mysłowice", 50.2333, 19.1333);
        add("OST", "Ostrów Wielkopolski", 51.6494, 17.8164);
        add("LUI", "Lubin", 51.4000, 16.2000);
        add("INO", "Inowrocław", 52.7931, 18.2611);
        add("STA", "Stargard Szczeciński", 53.3333, 15.0333);
        add("GNI", "Gniezno", 52.5358, 17.5958);
        add("PRU", "Pruszków", 52.1667, 20.8000);
        add("OST", "Ostrowiec Świętokrzyski", 50.9333, 21.4000);
        add("SIE", "Siemianowice Śląskie", 50.2758, 18.9858);
        add("GŁO", "Głogów", 51.6589, 16.0803);
        add("PAB", "Pabianice", 51.6500, 19.3833);
        add("LES", "Leszno", 51.8458, 16.5806);
        add("EŁK", "Ełk", 53.8214, 22.3622);
        add("ZAM", "Zamość", 50.7167, 23.2528);
        add("CHE", "Chełm", 51.1322, 23.4778);
        add("TOM", "Tomaszów Mazowiecki", 51.5167, 20.0167);
        add("MIE", "Mielec", 50.2833, 21.4333);
        add("TCZ", "Tczew", 54.0875, 18.7972);
    }

    private void add(String code, String name, double longitude, double latitude) {
        cities.put(code, new City(code, name, longitude, latitude));
    }

    public static City get(String code) {
        return INSTANCE.cities.get(code);
    }

    public static List<City> getAll() {
        return new ArrayList(INSTANCE.cities.values());
    }

}