package fr.graynaud.maps.javaleaflet.model;

import fr.graynaud.maps.javaleaflet.listener.OnJLObjectActionListener;

/**
 * Represents basic object classes for interacting with Leaflet
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public abstract class JLObject<T extends JLObject<?>> {

    private OnJLObjectActionListener<T> listener;

    public OnJLObjectActionListener<T> getOnActionListener() {
        return listener;
    }

    public void setOnActionListener(OnJLObjectActionListener<T> listener) {
        this.listener = listener;
    }

    public abstract int getId();

    public void update(Object... params) {

    }
}
