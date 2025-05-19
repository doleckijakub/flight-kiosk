package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class City {
    private final String code;
    private final String name;
    private final double longitude;
    private final double latitude;

    public City(String code, String name, double longitude, double latitude) {
        this.code = code;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getLon() { return longitude; }
    public double getLat() { return latitude; }
}