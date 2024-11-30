package fr.graynaud.maps.javaleaflet.layer;

import fr.graynaud.maps.javaleaflet.JLMapCallbackHandler;
import fr.graynaud.maps.javaleaflet.JLProperties;
import fr.graynaud.maps.javaleaflet.layer.leaflet.LeafletVectorLayerInt;
import fr.graynaud.maps.javaleaflet.model.JLCircle;
import fr.graynaud.maps.javaleaflet.model.JLCircleMarker;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMultiPolyline;
import fr.graynaud.maps.javaleaflet.model.JLOptions;
import fr.graynaud.maps.javaleaflet.model.JLPolygon;
import fr.graynaud.maps.javaleaflet.model.JLPolyline;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;

import java.util.Locale;

/**
 * Represents the Vector layer on Leaflet map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLVectorLayer extends JLLayer implements LeafletVectorLayerInt {

    public JLVectorLayer(WebEngine engine, JLMapCallbackHandler callbackHandler) {
        super(engine, callbackHandler);
    }

    /**
     * Drawing polyline overlays on the map with {@link JLOptions#DEFAULT} options
     *
     * @see JLVectorLayer#addPolyline(JLLatLng[], JLOptions)
     */
    @Override
    public JLPolyline addPolyline(JLLatLng[] vertices) {
        return addPolyline(vertices, JLOptions.DEFAULT);
    }

    /**
     * Drawing polyline overlays on the map.
     *
     * @param vertices arrays of LatLng points
     * @param options  see {@link JLOptions} for customizing
     *
     * @return the added {@link JLPolyline}  to map
     */
    @Override
    public JLPolyline addPolyline(JLLatLng[] vertices, JLOptions options) {
        String latlngs = convertJLLatLngToString(vertices);
        String hexColor = convertColorToString(options.getColor());
        int index = Integer.parseInt(this.engine.executeScript(
                String.format(Locale.US, "addPolyLine(%s, '%s', %d, %b, %f, %f)", latlngs, hexColor, options.getWeight(), options.isStroke(),
                              options.getOpacity(), options.getSmoothFactor())).toString());
        JLPolyline polyline = new JLPolyline(index, options, vertices);

        this.callbackHandler.addJLObject(polyline);

        return polyline;
    }

    /**
     * Remove a polyline from the map by id.
     *
     * @param id of polyline
     *
     * @return {@link Boolean#TRUE} if removed successfully
     */
    @Override
    public boolean removePolyline(int id) {
        String result = this.engine.executeScript(String.format(Locale.US, "removePolyLine(%d)", id)).toString();

        this.callbackHandler.remove(JLPolyline.class, id);
        this.callbackHandler.remove(JLMultiPolyline.class, id);

        return Boolean.parseBoolean(result);
    }

    /**
     * Drawing multi polyline overlays on the map with {@link JLOptions#DEFAULT} options.
     *
     * @return the added {@link JLMultiPolyline}  to map
     *
     * @see JLVectorLayer#addMultiPolyline(JLLatLng[][], JLOptions)
     */
    @Override
    public JLMultiPolyline addMultiPolyline(JLLatLng[][] vertices) {
        return addMultiPolyline(vertices, JLOptions.DEFAULT);
    }

    /**
     * Drawing MultiPolyline shape overlays on the map with multi-dimensional array.
     *
     * @param vertices arrays of LatLng points
     * @param options  see {@link JLOptions} for customizing
     *
     * @return the added {@link JLMultiPolyline}  to map
     */
    @Override
    public JLMultiPolyline addMultiPolyline(JLLatLng[][] vertices, JLOptions options) {
        String latlngs = convertJLLatLngToString(vertices);
        String hexColor = convertColorToString(options.getColor());
        int index = Integer.parseInt(this.engine.executeScript(
                String.format(Locale.US, "addPolyLine(%s, '%s', %d, %b, %f, %f)", latlngs, hexColor, options.getWeight(), options.isStroke(),
                              options.getOpacity(), options.getSmoothFactor())).toString());

        JLMultiPolyline multiPolyline = new JLMultiPolyline(index, options, vertices);

        this.callbackHandler.addJLObject(multiPolyline);

        return multiPolyline;
    }

    /**
     * @see JLVectorLayer#removePolyline(int)
     */
    @Override
    public boolean removeMultiPolyline(int id) {
        return removePolyline(id);
    }

    /**
     * Drawing polygon overlays on the map. Note that points you pass when creating a polygon shouldn't have an additional last point equal to the first one.
     * You can also pass an array of arrays of {{@link JLLatLng}}, with the first dimension representing the outer shape and the other dimension representing
     * holes in the outer shape! Additionally, you can pass a multi-dimensional array to represent a MultiPolygon shape!
     *
     * @param vertices array of {{@link JLLatLng}} points
     * @param options  see {{@link JLOptions}}
     *
     * @return Instance of the created {{@link JLPolygon}}
     */
    @Override
    public JLPolygon addPolygon(JLLatLng[][][] vertices, JLOptions options) {
        String latlngs = convertJLLatLngToString(vertices);

        int index = Integer.parseInt(this.engine.executeScript(
                String.format(Locale.US, "addPolygon(%s, '%s', '%s', %d, %b, %b, %f, %f, %f)", latlngs, convertColorToString(options.getColor()),
                              convertColorToString(options.getFillColor()), options.getWeight(), options.isStroke(), options.isFill(), options.getOpacity(),
                              options.getFillOpacity(), options.getSmoothFactor())).toString());

        JLPolygon polygon = new JLPolygon(index, options, vertices);

        this.callbackHandler.addJLObject(polygon);

        return polygon;
    }

    /**
     * Drawing polygon overlays on the map with {@link JLOptions#DEFAULT} option.
     *
     * @see JLVectorLayer#addMultiPolyline(JLLatLng[][], JLOptions)
     */
    @Override
    public JLPolygon addPolygon(JLLatLng[][][] vertices) {
        return addPolygon(vertices, JLOptions.DEFAULT);
    }

    /**
     * Remove a {{@link JLPolygon}} from the map by id.
     *
     * @param id of Polygon
     *
     * @return {{@link Boolean#TRUE}} if removed successfully
     */
    @Override
    public boolean removePolygon(int id) {
        String result = this.engine.executeScript(String.format(Locale.US, "removePolygon(%d)", id)).toString();

        this.callbackHandler.remove(JLPolygon.class, id);

        return Boolean.parseBoolean(result);
    }

    /**
     * Add a {@link JLCircle} to the map;
     *
     * @param center  coordinate of the circle.
     * @param radius  radius of circle in meter.
     * @param options see {@link JLOptions}
     *
     * @return the instance of created {@link JLCircle}
     */
    @Override
    public JLCircle addCircle(JLLatLng center, int radius, JLOptions options) {
        int index = Integer.parseInt(this.engine.executeScript(
                String.format(Locale.US, "addCircle([%f, %f], %d, '%s', '%s', %d, %b, %b, %f, %f, %f)", center.lat(), center.lng(), radius,
                              convertColorToString(options.getColor()), convertColorToString(options.getFillColor()), options.getWeight(), options.isStroke(),
                              options.isFill(), options.getOpacity(), options.getFillOpacity(), options.getSmoothFactor())).toString());

        JLCircle circle = new JLCircle(index, radius, center, options);

        this.callbackHandler.addJLObject(circle);

        return circle;
    }

    /**
     * Add {{@link JLCircle}} to the map with {@link JLOptions#DEFAULT} options. Default value for radius is {@link JLProperties#DEFAULT_CIRCLE_RADIUS}
     *
     * @see JLVectorLayer#addCircle(JLLatLng, int, JLOptions)
     */
    @Override
    public JLCircle addCircle(JLLatLng center) {
        return addCircle(center, JLProperties.DEFAULT_CIRCLE_RADIUS, JLOptions.DEFAULT);
    }

    /**
     * Remove a {@link JLCircle} from the map by id.
     *
     * @param id of Circle
     *
     * @return {@link Boolean#TRUE} if removed successfully
     */
    @Override
    public boolean removeCircle(int id) {
        String result = this.engine.executeScript(String.format(Locale.US, "removeCircle(%d)", id)).toString();

        this.callbackHandler.remove(JLCircle.class, id);

        return Boolean.parseBoolean(result);
    }

    /**
     * Add a {@link JLCircleMarker} to the map;
     *
     * @param center  coordinate of the circle.
     * @param radius  radius of circle in meter.
     * @param options see {@link JLOptions}
     *
     * @return the instance of created {@link JLCircleMarker}
     */
    @Override
    public JLCircleMarker addCircleMarker(JLLatLng center, int radius, JLOptions options) {
        int index = Integer.parseInt(this.engine.executeScript(
                String.format(Locale.US, "addCircleMarker([%f, %f], %d, '%s', '%s', %d, %b, %b, %f, %f, %f)", center.lat(), center.lng(), radius,
                              convertColorToString(options.getColor()), convertColorToString(options.getFillColor()), options.getWeight(), options.isStroke(),
                              options.isFill(), options.getOpacity(), options.getFillOpacity(), options.getSmoothFactor())).toString());

        JLCircleMarker circleMarker = new JLCircleMarker(index, radius, center, options);

        this.callbackHandler.addJLObject(circleMarker);

        return circleMarker;
    }

    /**
     * Add {@link JLCircleMarker} to the map with {@link JLOptions#DEFAULT} options. Default value for radius is
     * {@link JLProperties#DEFAULT_CIRCLE_MARKER_RADIUS}
     *
     * @see JLVectorLayer#addCircle(JLLatLng, int, JLOptions)
     */
    @Override
    public JLCircleMarker addCircleMarker(JLLatLng center) {
        return addCircleMarker(center, JLProperties.DEFAULT_CIRCLE_MARKER_RADIUS, JLOptions.DEFAULT);
    }

    /**
     * Remove a {@link JLCircleMarker} from the map by id.
     *
     * @param id of Circle
     *
     * @return {@link Boolean#TRUE} if removed successfully
     */
    @Override
    public boolean removeCircleMarker(int id) {
        String result = this.engine.executeScript(String.format(Locale.US, "removeCircleMarker(%d)", id)).toString();

        this.callbackHandler.remove(JLCircleMarker.class, id);

        return Boolean.parseBoolean(result);
    }

    private String convertJLLatLngToString(JLLatLng[] latLngs) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (JLLatLng latLng : latLngs) {
            sb.append(String.format(Locale.US, "%s, ", latLng.toString()));
        }
        sb.append("]");

        return sb.toString();
    }

    private String convertJLLatLngToString(JLLatLng[][] latLngsList) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (JLLatLng[] latLngs : latLngsList) {
            sb.append(convertJLLatLngToString(latLngs)).append(",");
        }
        sb.append("]");

        return sb.toString();
    }

    private String convertJLLatLngToString(JLLatLng[][][] latLngList) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (JLLatLng[][] latLng2DArray : latLngList) {
            sb.append(convertJLLatLngToString(latLng2DArray)).append(",");
        }
        sb.append("]");

        return sb.toString();
    }

    private String convertColorToString(Color c) {
        int r = (int) Math.round(c.getRed() * 255.0);
        int g = (int) Math.round(c.getGreen() * 255.0);
        int b = (int) Math.round(c.getBlue() * 255.0);
        int a = (int) Math.round(c.getOpacity() * 255.0);

        return String.format(Locale.US, "#%02x%02x%02x%02x", r, g, b, a);
    }
}
