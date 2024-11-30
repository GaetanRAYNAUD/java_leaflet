package fr.graynaud.maps.javaleaflet.model;

import javafx.scene.paint.Color;

/**
 * Optional value for theming objects inside the map! Note that all options are not available for all objects! Read more at Leaflet Official Docs.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLOptions {

    /**
     * Default value for theming options.
     */
    public static final JLOptions DEFAULT = new JLOptions();

    /**
     * Stroke color. Default is {{@link Color#BLUE}}
     */
    private Color color = Color.BLUE;

    /**
     * Fill color. Default is {{@link Color#BLUE}}
     */
    private Color fillColor = Color.BLUE;

    /**
     * Stroke width in pixels. Default is 3
     */
    private int weight = 3;

    /**
     * Whether to draw stroke along the path. Set it to false for disabling borders on polygons or circles.
     */
    private boolean stroke = true;

    /**
     * Whether to fill the path with color. Set it to false fo disabling filling on polygons or circles.
     */
    private boolean fill = true;

    /**
     * Stroke opacity
     */
    private double opacity = 1.0;

    /**
     * Fill opacity.
     */
    private double fillOpacity = 0.2;

    /**
     * How much to simplify the polyline on each zoom level. greater value means better performance and smoother look, and smaller value means more accurate
     * representation.
     */
    private double smoothFactor = 1.0;

    /**
     * Controls the presence of a close button in the popup.
     */
    private boolean closeButton = true;

    /**
     * Set it to false if you want to override the default behavior of the popup closing when another popup is opened.
     */
    private boolean autoClose = true;

    /**
     * Whether the marker is draggable with mouse/touch or not.
     */
    private boolean draggable = false;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isStroke() {
        return stroke;
    }

    public void setStroke(boolean stroke) {
        this.stroke = stroke;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public double getSmoothFactor() {
        return smoothFactor;
    }

    public void setSmoothFactor(double smoothFactor) {
        this.smoothFactor = smoothFactor;
    }

    public boolean isCloseButton() {
        return closeButton;
    }

    public void setCloseButton(boolean closeButton) {
        this.closeButton = closeButton;
    }

    public boolean isAutoClose() {
        return autoClose;
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
}
