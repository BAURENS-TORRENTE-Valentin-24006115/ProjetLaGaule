package fr.iut.laGaule.view;

import fr.iut.laGaule.model.Place.Place;

/**
 * View class responsible for displaying information about places.
 * This class handles the presentation layer for place-related data,
 * including place details, messages, and errors.
 */
public class PlaceView {

    /**
     * Displays detailed information about a place.
     * Shows the place name and all its attributes.
     *
     * @param place the place to display
     */
    public void displayPlaceDetails(Place place) {
        System.out.println("=== " + place.getName() + " ===");
        System.out.println(place.toString());
    }

    /**
     * Displays a general message to the user.
     *
     * @param message the message to display
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     * Error messages are printed to the standard error stream.
     *
     * @param error the error message to display
     */
    public void displayError(String error) {
        System.err.println("Erreur : " + error);
    }
}