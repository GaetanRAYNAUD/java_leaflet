package fr.graynaud.maps.javaleaflet.model;

import fr.graynaud.maps.javaleaflet.listener.OnJLObjectActionListener;

/**
 * JLMarker is used to display clickable/draggable icons on the map!
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLMarker extends JLObject<JLMarker> {

    /**
     * id of object! this is an internal id for JLMap Application and not related to Leaflet!
     */
    protected int id;
    /**
     * optional text for showing on created JLMarker tooltip.
     */
    private String text;
    /**
     * Coordinates of the JLMarker on the map
     */
    private JLLatLng latLng;

    public JLMarker() {
    }

    public JLMarker(int id, String text, JLLatLng latLng) {
        this.id = id;
        this.text = text;
        this.latLng = latLng;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JLLatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(JLLatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public void update(Object... params) {
        super.update(params);
        if (params != null && params.length > 0
            && String.valueOf(params[0]).equals(
                OnJLObjectActionListener.Action.MOVE_END.getJsEventName())
            && params[1] != null) {
            latLng = (JLLatLng) params[1];
        }
    }

    @Override
    public final boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
