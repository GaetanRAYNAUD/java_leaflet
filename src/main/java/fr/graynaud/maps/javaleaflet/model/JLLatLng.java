package fr.graynaud.maps.javaleaflet.model;

import fr.graynaud.maps.javaleaflet.JLProperties;

import java.util.Locale;

/**
 * Represents a geographical point with a certain latitude and longitude.
 *
 * @param lat geographical given latitude in degrees
 * @param lng geographical given longitude in degrees
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public record JLLatLng(double lat, double lng) {

    /**
     * Calculate distance between two points in latitude and longitude taking into account height difference.Uses Haversine method as its base.
     *
     * @param dest Destination coordinate {{@link JLLatLng}}
     *
     * @return Distance in Meters
     *
     * @author David George
     */
    public double distanceTo(JLLatLng dest) {
        double latDistance = Math.toRadians(dest.lat() - lat);
        double lonDistance = Math.toRadians(dest.lng() - lng);
        double a = Math.sin(latDistance / 2)
                   * Math.sin(latDistance / 2)
                   + Math.cos(Math.toRadians(lat))
                     * Math.cos(Math.toRadians(dest.lat()))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = JLProperties.EARTH_RADIUS * c * 1000;

        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    /**
     * @param o The given point
     *
     * @return Returns true if the given {{@link JLLatLng}} point is exactly at the same position.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JLLatLng latLng = (JLLatLng) o;
        return Double.compare(latLng.lat, lat) == 0 &&
               Double.compare(latLng.lng, lng) == 0;
    }

    /**
     * @param o         The given point
     * @param maxMargin The margin of error
     *
     * @return Returns true if the given {{@link JLLatLng}} point is at the same position (within a small margin of error). The margin of error can be
     * overridden by setting maxMargin.
     */
    public boolean equals(Object o, float maxMargin) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JLLatLng latLng = (JLLatLng) o;
        return distanceTo(latLng) <= maxMargin;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%f, %f]", lat, lng);
    }
}
