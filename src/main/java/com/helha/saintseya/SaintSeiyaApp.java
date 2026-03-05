package com.helha.saintseya;

import javafx.application.Application;
import javafx.stage.Stage;

public class SaintSeiyaApp extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Saint Seiya - The Game");

        // On démarre sur l'écran d'accueil
        changerVersAccueil();

        // On ajuste la taille pour que le plateau s'affiche bien
        this.primaryStage.setWidth(1200);
        this.primaryStage.setHeight(800);
        this.primaryStage.show();
    }

    // --- MÉTHODES DE NAVIGATION ---

    public void changerVersAccueil() {
        VueAccueil vue = new VueAccueil(this);
        primaryStage.setScene(vue.getScene());
    }

    public void changerVersSelection() {
        VueSelection vue = new VueSelection(this);
        primaryStage.setScene(vue.getScene());
    }

    // Nouvelle méthode pour lancer le jeu avec le bon chevalier
    public void changerVersPlateau(String nomChevalier, String fichierImage) {
        VuePlateau vuePlateau = new VuePlateau(this, nomChevalier, fichierImage);
        primaryStage.setScene(vuePlateau.getScene());
    }
}