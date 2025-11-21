package fr.iut.laGaule.view;

import fr.iut.laGaule.model.Place.Place;

public class PlaceView {

    public void displayPlaceDetails(Place place) {
        System.out.println("=== " + place.getName() + " ===");
        System.out.println(place.toString());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.err.println("Erreur : " + error);
    }
}