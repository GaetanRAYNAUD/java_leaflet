package fr.graynaud.maps.javaleaflet.model;

/**
 * A class for drawing circle overlays on a map
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLCircle extends JLObject<JLCircle> {

    /**
     * id of object! this is an internal id for JLMap Application and not related to Leaflet!
     */
    protected int id;
    /**
     * Radius of the circle, in meters.
     */
    private double radius;
    /**
     * Coordinates of the JLMarker on the map
     */
    private JLLatLng latLng;
    /**
     * theming options for JLCircle. all options are not available!
     */
    private JLOptions options;

    public JLCircle() {
    }

    public JLCircle(int id, double radius, JLLatLng latLng, JLOptions options) {
        this.id = id;
        this.radius = radius;
        this.latLng = latLng;
        this.options = options;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public JLLatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(JLLatLng latLng) {
        this.latLng = latLng;
    }

    public JLOptions getOptions() {
        return options;
    }

    public void setOptions(JLOptions options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "JLCircle{" +
               "id=" + id +
               ", radius=" + radius +
               ", latLng=" + latLng +
               ", options=" + options +
               '}';
    }
}
