package fr.graynaud.maps.javaleaflet.listener.event;

import fr.graynaud.maps.javaleaflet.model.JLLatLng;

public record ClickEvent(JLLatLng center) implements Event {}
