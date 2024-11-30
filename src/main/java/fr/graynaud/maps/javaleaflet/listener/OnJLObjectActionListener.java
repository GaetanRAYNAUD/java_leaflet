package fr.graynaud.maps.javaleaflet.listener;

import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLObject;

public abstract class OnJLObjectActionListener<T extends JLObject<?>> {

    public abstract void click(T t, Action action);

    public abstract void move(T t, Action action);

    public enum Action {
        /**
         * Fired when the marker is moved via setLatLng or by dragging. Old and new coordinates are included in event arguments as oldLatLng, {@link JLLatLng}.
         */
        MOVE("move"),
        MOVE_START("movestart"),
        MOVE_END("moveend"),
        /**
         * Fired when the user clicks (or taps) the layer.
         */
        CLICK("click"),
        /**
         * Fired when the user zooms.
         */
        ZOOM("zoom");

        final String jsEventName;

        Action(String name) {
            this.jsEventName = name;
        }

        public String getJsEventName() {
            return jsEventName;
        }
    }
}
