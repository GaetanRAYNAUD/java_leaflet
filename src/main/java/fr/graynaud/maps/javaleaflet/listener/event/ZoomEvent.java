package fr.graynaud.maps.javaleaflet.listener.event;

import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;

public record ZoomEvent(OnJLMapViewListener.Action action, int zoomLevel) implements Event {}
