package com.helha.saintseya;

public class GameController {
    private final Joueur modele;

    public GameController(Joueur modele) {
        this.modele = modele;
    }

    public void demanderDeplacement(double x, double y) {
        modele.setPosition(x, y);
    }
}
