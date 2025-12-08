module fr.iut.laGaule {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens fr.iut.laGaule to javafx.fxml;
    opens fr.iut.laGaule.view to javafx.fxml;
    opens fr.iut.laGaule.controller to javafx.fxml;

    exports fr.iut.laGaule;
    exports fr.iut.laGaule.view;
    exports fr.iut.laGaule.controller;
    exports fr.iut.laGaule.model;
    exports fr.iut.laGaule.model.Character;
    exports fr.iut.laGaule.model.Character.Gaul;
    exports fr.iut.laGaule.model.Character.Roman;
    exports fr.iut.laGaule.model.Character.MythicalCreature;
    exports fr.iut.laGaule.model.Place;
    exports fr.iut.laGaule.model.Consumables.Foods;
    exports fr.iut.laGaule.model.Consumables.Potions;
    exports fr.iut.laGaule.model.Items;
}

