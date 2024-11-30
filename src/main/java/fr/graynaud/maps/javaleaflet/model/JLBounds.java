package fr.graynaud.maps.javaleaflet.model;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a rectangular geographical area on a map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLBounds {

    /**
     * the north-east point of the bounds.
     */
    private JLLatLng northEast;
    /**
     * the south-west point of the bounds.
     */
    private JLLatLng southWest;

    public JLBounds() {
    }

    public JLBounds(JLLatLng northEast, JLLatLng southWest) {
        this.northEast = northEast;
        this.southWest = southWest;
    }

    /**
     * @return the west longitude of the bounds
     */
    public double getWest() {
        return southWest.lng();
    }

    /**
     * @return the south latitude of the bounds
     */
    public double getSouth() {
        return southWest.lat();
    }

    /**
     * @return the east longitude of the bounds
     */
    public double getEast() {
        return northEast.lng();
    }

    /**
     * @return the north latitude of the bounds
     */
    public double getNorth() {
        return northEast.lat();
    }

    /**
     * @return the south-east point of the bounds
     */
    public JLLatLng getSouthEast() {
        return new JLLatLng(southWest.lat(), northEast.lng());
    }

    /**
     * @return the north-west point of the bounds.
     */
    public JLLatLng getNorthWest() {
        return new JLLatLng(northEast.lat(), southWest.lng());
    }

    public JLLatLng getCenter() {
        return new JLLatLng(((northEast.lat() + southWest.lat()) / 2), (northEast.lng() + southWest.lng()) / 2);
    }

    /**
     * @return a string with bounding box coordinates in a 'southwest_lng,southwest_lat,northeast_lng,northeast_lat' format. Useful for sending requests to web
     * services that return geo data.
     */
    public String toBBoxString() {
        return String.format(Locale.US, "%f,%f,%f,%f", southWest.lng(), southWest.lat(), northEast.lng(), northEast.lat());
    }

    /**
     * @param bounds given bounds
     *
     * @return {@code true} if the rectangle contains the given bounds.
     */
    public boolean contains(JLBounds bounds) {
        return (bounds.getSouthWest().lat() >= southWest.lat())
               && (bounds.getNorthEast().lat() <= northEast.lat())
               && (bounds.getSouthWest().lng() >= southWest.lng())
               && (bounds.getNorthEast().lng() <= northEast.lng());
    }

    /**
     * @param point given point
     *
     * @return {@code true} if the rectangle contains the given point.
     */
    public boolean contains(JLLatLng point) {
        return (point.lat() >= southWest.lat())
               && (point.lat() <= northEast.lat())
               && (point.lng() >= southWest.lng())
               && (point.lng() <= northEast.lng());
    }

    /**
     * @return {{@link Boolean#TRUE}} if the bounds are properly initialized.
     */
    public boolean isValid() {
        return northEast != null && southWest != null;
    }

    /**
     * @param bufferRatio extending or retracting value
     *
     * @return bounds created by extending or retracting the current bounds by a given ratio in each direction. For example, a ratio of 0.5 extends the bounds
     * by 50% in each direction. Negative values will retract the bounds.
     */
    public JLBounds pad(double bufferRatio) {
        double latBuffer =
                Math.abs(southWest.lat() - northEast.lat()) * bufferRatio;
        double lngBuffer =
                Math.abs(southWest.lng() - northEast.lng()) * bufferRatio;

        return new JLBounds(
                new JLLatLng(southWest.lat() - latBuffer,
                             southWest.lng() - lngBuffer),
                new JLLatLng(northEast.lat() + latBuffer,
                             northEast.lng() + lngBuffer));
    }

    public JLLatLng getNorthEast() {
        return northEast;
    }

    public void setNorthEast(JLLatLng northEast) {
        this.northEast = northEast;
    }

    public JLLatLng getSouthWest() {
        return southWest;
    }

    public void setSouthWest(JLLatLng southWest) {
        this.southWest = southWest;
    }

    /**
     * @param bounds    the given bounds
     * @param maxMargin The margin of error
     *
     * @return true if the rectangle is equivalent (within a small margin of error) to the given bounds.
     */
    public boolean equals(JLBounds bounds, float maxMargin) {
        if (bounds == null) {
            return false;
        }

        return this.getSouthWest().equals(bounds.getSouthWest(), maxMargin) &&
               this.getNorthEast().equals(bounds.getNorthEast(), maxMargin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JLBounds jlBounds = (JLBounds) o;
        return Objects.equals(northEast, jlBounds.northEast) &&
               Objects.equals(southWest, jlBounds.southWest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(northEast, southWest);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%s, %s]", northEast, southWest);
    }
}
