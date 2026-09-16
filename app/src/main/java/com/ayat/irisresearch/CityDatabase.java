package com.ayat.irisresearch;

import java.util.Arrays;
import java.util.List;

public final class CityDatabase {
    public static final class City {
        public final String name, timezone;
        public final double latitude, longitude;
        City(String n, double lat, double lon, String tz) {
            name=n; latitude=lat; longitude=lon; timezone=tz;
        }
        @Override public String toString() { return name; }
    }

    private static final List<City> CITIES = Arrays.asList(
        new City("تهران",35.6892,51.3890,"Asia/Tehran"),
        new City("مشهد",36.2605,59.6168,"Asia/Tehran"),
        new City("اصفهان",32.6546,51.6680,"Asia/Tehran"),
        new City("شیراز",29.5918,52.5837,"Asia/Tehran"),
        new City("تبریز",38.0962,46.2738,"Asia/Tehran"),
        new City("کرج",35.8400,50.9391,"Asia/Tehran"),
        new City("رشت",37.2808,49.5832,"Asia/Tehran"),
        new City("باکو",40.4093,49.8671,"Asia/Baku"),
        new City("استانبول",41.0082,28.9784,"Europe/Istanbul"),
        new City("لندن",51.5074,-0.1278,"Europe/London"),
        new City("نیویورک",40.7128,-74.0060,"America/New_York"),
        new City("لس‌آنجلس",34.0522,-118.2437,"America/Los_Angeles"),
        new City("دبی",25.2048,55.2708,"Asia/Dubai"),
        new City("دهلی",28.6139,77.2090,"Asia/Kolkata"),
        new City("پکن",39.9042,116.4074,"Asia/Shanghai"),
        new City("توکیو",35.6762,139.6503,"Asia/Tokyo")
    );

    public static List<City> all() { return CITIES; }
    public static City find(String name) {
        for (City c : CITIES) if (c.name.equals(name)) return c;
        return null;
    }
}
