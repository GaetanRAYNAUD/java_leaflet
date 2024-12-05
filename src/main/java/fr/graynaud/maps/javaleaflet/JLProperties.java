package fr.graynaud.maps.javaleaflet;

import fr.graynaud.maps.javaleaflet.model.JLMapOption;

import java.util.Collections;
import java.util.Set;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLProperties {

    public static final int INIT_MIN_WIDTH = 1024;

    public static final int INIT_MIN_HEIGHT = 576;

    public static final int EARTH_RADIUS = 6367;

    public static final int DEFAULT_CIRCLE_RADIUS = 200;

    public static final int DEFAULT_CIRCLE_MARKER_RADIUS = 10;

    public static final int INIT_MIN_WIDTH_STAGE = INIT_MIN_WIDTH;

    public static final int INIT_MIN_HEIGHT_STAGE = INIT_MIN_HEIGHT;

    public static final double START_ANIMATION_RADIUS = 10;

    public record MapType(String name, int tileSize, Set<JLMapOption.Parameter> parameters) {

        public static final MapType OSM = new MapType("https://tile.openstreetmap.org/{z}/{x}/{y}.png");

        public MapType(String name) {
            this(name, 256);
        }

        public MapType(String name, int tileSize) {
            this(name, tileSize, Collections.emptySet());
        }

        public MapType(String name, int tileSize, Set<JLMapOption.Parameter> parameters) {
            this.name = name.replace("https://", "https//").replace("http://", "http//");
            this.tileSize = tileSize;
            this.parameters = parameters;
        }

        public static MapType getDefault() {
            return OSM;
        }
    }
}
