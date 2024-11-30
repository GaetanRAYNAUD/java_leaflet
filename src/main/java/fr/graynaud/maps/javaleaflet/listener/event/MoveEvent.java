package fr.graynaud.maps.javaleaflet.listener.event;

import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;
import fr.graynaud.maps.javaleaflet.model.JLBounds;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;

/**
 * @param action    movement action
 * @param center    coordinate of map
 * @param bounds    bounds of map
 * @param zoomLevel zoom level of map
 */
public record MoveEvent(OnJLMapViewListener.Action action, JLLatLng center, JLBounds bounds, int zoomLevel) implements Event {}
