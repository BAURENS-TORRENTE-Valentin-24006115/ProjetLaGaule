package fr.iut.laGaule.controller;

import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.view.PlaceView;

import java.util.ArrayList;

public class PlaceController {
    private Place place;
    private PlaceView view;

    public PlaceController(Place place, PlaceView view) {
        this.place = place;
        this.view = view;
    }

    public void displayPlaceInfo() {
        view.displayPlaceDetails(place);
    }

    public void addCharacterToPlace(Character character) {
        // Délègue au modèle
        place.addCharacter(character);
    }

    public void healAllCharacters(int healingAmount) {
        place.healCharacters(healingAmount);
        view.displayMessage("Les personnages ont été soignés.");
    }

    public void feedAllCharacters() {
        place.feedCharacters();
    }

    public void feedSpecificCharacter(Character character) {
        place.feedCharacter(character);
    }
}