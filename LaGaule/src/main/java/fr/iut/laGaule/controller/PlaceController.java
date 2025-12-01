package fr.iut.laGaule.controller;

import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.view.PlaceView;

import java.util.ArrayList;

/**
 * Controller for managing interactions between the Place model and PlaceView.
 */
public class PlaceController {
    private Place place;
    private PlaceView view;

    /**
     * Constructs a place controller.
     *
     * @param place the place model to manage
     * @param view the associated view for display
     */
    public PlaceController(Place place, PlaceView view) {
        this.place = place;
        this.view = view;
    }

    /**
     * Displays detailed information about the place through the view.
     * Delegates the display to the view by passing the place model.
     */
    public void displayPlaceInfo() {
        view.displayPlaceDetails(place);
    }

    /**
     * Adds a character to the place.
     *
     * @param character the character to add to the place
     */
    public void addCharacterToPlace(Character character) {
        // Delegates to the model
        place.addCharacter(character);
    }

    /**
     * Heals all characters present in the place.
     * Applies a specified healing amount to all characters
     * and displays a confirmation message through the view.
     *
     * @param healingAmount the amount of health points to restore
     */
    public void healAllCharacters(int healingAmount) {
        place.healCharacters(healingAmount);
        view.displayMessage("Les personnages ont été soignés.");
    }

    /**
     * Feeds all characters present in the place.
     * Delegates the feeding operation to the place model.
     */
    public void feedAllCharacters() {
        place.feedCharacters();
    }

    /**
     * Feeds a specific character in the place.
     *
     * @param character the character to feed
     */
    public void feedSpecificCharacter(Character character) {
        place.feedCharacter(character);
    }
}