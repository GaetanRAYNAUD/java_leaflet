package fr.graynaud.maps.javaleaflet;

import fr.graynaud.maps.javaleaflet.geojson.JLGeoJsonObject;
import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;
import fr.graynaud.maps.javaleaflet.listener.OnJLObjectActionListener;
import fr.graynaud.maps.javaleaflet.listener.event.ClickEvent;
import fr.graynaud.maps.javaleaflet.listener.event.Event;
import fr.graynaud.maps.javaleaflet.listener.event.MoveEvent;
import fr.graynaud.maps.javaleaflet.listener.event.ZoomEvent;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMapOption;
import fr.graynaud.maps.javaleaflet.model.JLMarker;
import fr.graynaud.maps.javaleaflet.model.JLOptions;
import fr.graynaud.maps.javaleaflet.model.JLPolygon;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class Leaflet extends Application {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Leaflet.class);

    @Override
    public void start(Stage stage) {
        //building a new map view
        final JLMapView map = new JLMapView(new JLMapOption(new JLLatLng(51.044, 114.07), JLProperties.MapType.OSM, true));
        //creating a window
        AnchorPane root = new AnchorPane(map);
        root.setBackground(Background.EMPTY);
        root.setMinHeight(JLProperties.INIT_MIN_HEIGHT_STAGE);
        root.setMinWidth(JLProperties.INIT_MIN_WIDTH_STAGE);
        Scene scene = new Scene(root);

        stage.setMinHeight(JLProperties.INIT_MIN_HEIGHT_STAGE);
        stage.setMinWidth(JLProperties.INIT_MIN_WIDTH_STAGE);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Java-Leaflet Test");
        stage.setScene(scene);
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY(100);

        //set listener fo map events
        map.setMapListener(new OnJLMapViewListener() {
            @Override
            public void mapLoadedSuccessfully(JLMapView mapView) {
                LOGGER.info("map loaded!");
                addMultiPolyline(map);
                addPolyline(map);
                addPolygon(map);

                map.setView(new JLLatLng(10, 10));
                map.getUiLayer().addMarker(new JLLatLng(35.63, 51.45), "Tehran", true).setOnActionListener(getListener());

                map.getVectorLayer().addCircleMarker(new JLLatLng(35.63, 51.45));

                map.getVectorLayer().addCircle(new JLLatLng(35.63, 51.45), 30000, JLOptions.DEFAULT);

                // map zoom functionalities
                map.getControlLayer().setZoom(5);
                map.getControlLayer().zoomIn(2);
                map.getControlLayer().zoomOut(1);

                JLGeoJsonObject geoJsonObject = map.getGeoJsonLayer()
                                                   .addFromUrl(
                                                           "https://pkgstore.datahub.io/examples/geojson-tutorial/example/data/db696b3bf628d9a273ca9907adcea5c9/example.geojson");
            }

            @Override
            public void mapFailed() {
                LOGGER.error("map failed!");
            }

            @Override
            public void onAction(Event event) {
                if (event instanceof MoveEvent(Action action, JLLatLng center, fr.graynaud.maps.javaleaflet.model.JLBounds bounds, int zoomLevel)) {
                    LOGGER.info("move event: {} c: {} \t bounds: {}\t z: {}", action, center, bounds, zoomLevel);
                } else if (event instanceof ClickEvent(JLLatLng center)) {
                    LOGGER.info("click event: {}", center);
                    JLOptions jlOptions = new JLOptions();
                    jlOptions.setCloseButton(true);
                    jlOptions.setAutoClose(false);
                    map.getUiLayer().addPopup(center, "New Click Event!", jlOptions);
                } else if (event instanceof ZoomEvent zoomEvent) {
                    LOGGER.info("zoom event: {}", zoomEvent.zoomLevel());
                }

            }
        });
    }

    private OnJLObjectActionListener<JLMarker> getListener() {
        return new OnJLObjectActionListener<>() {
            @Override
            public void click(JLMarker object, Action action) {
                LOGGER.info("object click listener for marker: {}", object);
            }

            @Override
            public void move(JLMarker object, Action action) {
                LOGGER.info("object move listener for marker: {}", object);
            }
        };
    }

    private void addMultiPolyline(JLMapView map) {
        JLLatLng[][] verticesT = new JLLatLng[2][];

        verticesT[0] = new JLLatLng[] {new JLLatLng(41.509, 20.08), new JLLatLng(31.503, -10.06), new JLLatLng(21.51, -0.047)};

        verticesT[1] = new JLLatLng[] {new JLLatLng(51.509, 10.08), new JLLatLng(55.503, 15.06), new JLLatLng(42.51, 20.047)};

        map.getVectorLayer().addMultiPolyline(verticesT);
    }

    private void addPolyline(JLMapView map) {
        JLLatLng[] vertices = new JLLatLng[] {new JLLatLng(51.509, -0.08), new JLLatLng(51.503, -0.06), new JLLatLng(51.51, -0.047)};

        map.getVectorLayer().addPolyline(vertices);
    }

    private void addPolygon(JLMapView map) {

        JLLatLng[][][] vertices = new JLLatLng[2][][];

        vertices[0] = new JLLatLng[2][];
        vertices[1] = new JLLatLng[1][];
        //first part
        vertices[0][0] = new JLLatLng[] {new JLLatLng(37, -109.05), new JLLatLng(41, -109.03), new JLLatLng(41, -102.05), new JLLatLng(37, -102.04)};
        //hole inside the first part
        vertices[0][1] = new JLLatLng[] {new JLLatLng(37.29, -108.58), new JLLatLng(40.71, -108.58), new JLLatLng(40.71, -102.50),
                                         new JLLatLng(37.29, -102.50)};
        //second part
        vertices[1][0] = new JLLatLng[] {new JLLatLng(41, -111.03), new JLLatLng(45, -111.04), new JLLatLng(45, -104.05), new JLLatLng(41, -104.05)};
        map.getVectorLayer().addPolygon(vertices).setOnActionListener(new OnJLObjectActionListener<>() {
            @Override
            public void click(JLPolygon jlPolygon, Action action) {
                LOGGER.info("object click listener for jlPolygon: {}", jlPolygon);
            }

            @Override
            public void move(JLPolygon jlPolygon, Action action) {
                LOGGER.info("object move listener for jlPolygon: {}", jlPolygon);
            }
        });
    }
}
