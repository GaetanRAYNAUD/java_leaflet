package fr.graynaud.maps.javaleaflet.model;

import fr.graynaud.maps.javaleaflet.JLProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code JLMapOption} class represents options for configuring a Leaflet map.  This class allows you to specify various map configuration parameters, such
 * as the starting coordinates, map type, and additional parameters.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLMapOption {

    /**
     * The starting geographical coordinates (latitude and longitude) for the map. Default value is (0.00, 0.00).
     */
    private JLLatLng startCoordinate = new JLLatLng(0, 0);
    /**
     * The map type for configuring the map's appearance and behavior. Default value is the default map type.
     */
    private JLProperties.MapType mapType = JLProperties.MapType.getDefault();
    /**
     * Additional parameters to include in the map configuration.
     */
    private Set<Parameter> additionalParameter = new HashSet<>();

    private int zoom = 11;

    private int maxZoom = 19;

    private int minZoom = 1;

    private JLBounds maxBounds;

    public JLMapOption() {
    }

    public JLMapOption(JLLatLng startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public JLMapOption(JLProperties.MapType mapType) {
        this.mapType = mapType;
    }

    public JLMapOption(JLLatLng startCoordinate, JLProperties.MapType mapType) {
        this(startCoordinate, mapType, null);
    }

    public JLMapOption(JLLatLng startCoordinate, JLProperties.MapType mapType, boolean showZoomController) {
        this(startCoordinate, mapType, new HashSet<>(List.of(new Parameter("zoomControl", Objects.toString(showZoomController)))));
    }

    public JLMapOption(JLLatLng startCoordinate, JLProperties.MapType mapType, Set<Parameter> additionalParameter) {
        this.startCoordinate = startCoordinate;
        this.mapType = mapType;
        this.additionalParameter = additionalParameter;
    }

    /**
     * Converts the map options to a query string format, including both map-specific parameters and additional parameters.
     *
     * @return The map options as a query string.
     */
    public String toQueryString() {
        return Stream.concat(getParameters().stream(), this.additionalParameter.stream()).map(Parameter::toString).collect(Collectors.joining("&", "?", ""))
               + "&lat=" + String.format(Locale.US, "%f", this.startCoordinate.lat())
               + "&lng=" + String.format(Locale.US, "%f", this.startCoordinate.lng()) +
               "&zoom=" + this.zoom + "&maxZoom=" + this.maxZoom + "&minZoom=" + this.minZoom +
               "&north=" + String.format(Locale.US, "%f", this.maxBounds.getNorth()) +
               "&south=" + String.format(Locale.US, "%f", this.maxBounds.getSouth()) +
               "&west=" + String.format(Locale.US, "%f", this.maxBounds.getWest()) +
               "&east=" + String.format(Locale.US, "%f", this.maxBounds.getEast());
    }

    public JLLatLng getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(JLLatLng startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public JLProperties.MapType getMapType() {
        return mapType;
    }

    public void setMapType(JLProperties.MapType mapType) {
        this.mapType = mapType;
    }

    public Set<Parameter> getAdditionalParameter() {
        return additionalParameter;
    }

    public void setAdditionalParameter(Set<Parameter> additionalParameter) {
        this.additionalParameter = additionalParameter;
    }

    /**
     * Gets the map-specific parameters based on the selected map type.
     *
     * @return A set of map-specific parameters.
     */
    public Set<Parameter> getParameters() {
        return this.mapType.parameters();
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(int minZoom) {
        this.minZoom = minZoom;
    }

    public JLBounds getMaxBounds() {
        return maxBounds;
    }

    public void setMaxBounds(JLBounds maxBounds) {
        this.maxBounds = maxBounds;
    }

    /**
     * Represents a key-value pair used for additional parameters in the map configuration.
     */
    public record Parameter(String key, String value) {

        @Override
        public String toString() {
            return String.format(Locale.US, "%s=%s", key, value);
        }
    }
}

