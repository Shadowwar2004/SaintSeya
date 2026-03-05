package com.helha.saintseya;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class VueSelection {
    private Scene scene;

    public VueSelection(SaintSeiyaApp app) {

        StackPane root = new StackPane();

        // 1. L'IMAGE DE FOND (Toujours la même pour la cohérence)
        InputStream is = getClass().getResourceAsStream("/com/helha/saintseya/Menu/Selection.png");
        if (is != null) {
            ImageView fond = new ImageView(new Image(is));
            fond.fitWidthProperty().bind(root.widthProperty());
            fond.fitHeightProperty().bind(root.heightProperty());
            fond.setPreserveRatio(false);
            root.getChildren().add(fond);
        } else {
            root.setStyle(StyleConstantes.FOND);
        }

        // 2. CONTENEUR PRINCIPAL
        VBox mainBox = new VBox(50); // Espace généreux entre le titre, les persos et le bouton retour
        mainBox.setAlignment(Pos.CENTER);

        // 3. LE TITRE DORÉ
        Label titre = new Label("CHOISISSEZ VOTRE CHEVALIER");
        titre.setStyle("-fx-text-fill: linear-gradient(to bottom, #FFDF00, #DAA520); -fx-font-size: 42px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        DropShadow ombreTitre = new DropShadow();
        ombreTitre.setColor(Color.BLACK);
        ombreTitre.setRadius(15);
        titre.setEffect(ombreTitre);

        // 4. LES CARTES DES CHEVALIERS
        HBox conteneurPions = new HBox(30); // Plus d'espace entre les cartes
        conteneurPions.setAlignment(Pos.CENTER);

        String[][] chevaliers = {
                {"Pégase", "seiya.png"},
                {"Dragon", "shiryu.png"},
                {"Cygne", "hyoga.png"},
                {"Andromède", "shun.png"}
        };

        for (String[] donnees : chevaliers) {
            String nom = donnees[0];
            String fichierImage = donnees[1];

            Button pion = new Button(nom);

            // Nouveau style "Carte de Chevalier"
            String stylePionNormal = "-fx-background-color: rgba(30, 45, 74, 0.8); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 20px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 15; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-radius: 15; " +
                    "-fx-border-width: 2px; " +
                    "-fx-pref-width: 180; " +
                    "-fx-pref-height: 240;";

            pion.setStyle(stylePionNormal);
            pion.setContentDisplay(ContentDisplay.TOP);
            pion.setGraphicTextGap(20); // Espace entre l'image et le nom

            // Chargement de l'image (attention au dossier /pion/ !)
            try {
                Image img = new Image(getClass().getResourceAsStream("/com/helha/saintseya/pion/" + fichierImage));
                ImageView vueImage = new ImageView(img);
                vueImage.setFitHeight(130); // Image un peu plus grande
                vueImage.setPreserveRatio(true);
                pion.setGraphic(vueImage);
            } catch (Exception ex) {
                System.out.println("⚠️ Image introuvable : /pion/" + fichierImage);
            }

            // Animation de la carte (Survol)
            pion.setOnMouseEntered(e -> {

                pion.setStyle(stylePionNormal.replace("rgba(30, 45, 74, 0.8)", "linear-gradient(to bottom, #FFDF00, #DAA520)").replace("white", "#1e2d4a"));
                pion.setScaleX(1.1);
                pion.setScaleY(1.1);


                DropShadow haloDore = new DropShadow();
                haloDore.setColor(Color.GOLD);
                haloDore.setRadius(25);
                haloDore.setSpread(0.4);
                pion.setEffect(haloDore);
            });

            // Fin de l'animation
            pion.setOnMouseExited(e -> {
                pion.setStyle(stylePionNormal);
                pion.setScaleX(1.0);
                pion.setScaleY(1.0);
                pion.setEffect(null);
            });

            // Lancement du jeu !
            pion.setOnAction(e -> {
                System.out.println("Vous avez sélectionné le Chevalier : " + nom);
                app.changerVersPlateau(nom, fichierImage);
            });

            conteneurPions.getChildren().add(pion);
        }

        // 5. LE BOUTON RETOUR (Même style que l'accueil)
        Button btnRetour = new Button("RETOUR");
        String styleRetour = "-fx-background-color: linear-gradient(to bottom, #8B6508, #5C4305); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 30; " +
                "-fx-border-color: #FFDF00; " +
                "-fx-border-radius: 30; " +
                "-fx-border-width: 2px; " +
                "-fx-padding: 10 40 10 40;";

        btnRetour.setStyle(styleRetour);
        DropShadow ombreRetour = new DropShadow();
        ombreRetour.setColor(Color.BLACK);
        ombreRetour.setRadius(10);
        btnRetour.setEffect(ombreRetour);

        btnRetour.setOnMouseEntered(e -> {
            btnRetour.setStyle(styleRetour.replace("#8B6508, #5C4305", "#FFDF00, #DAA520").replace("white", "#1e2d4a"));
            btnRetour.setScaleX(1.1);
            btnRetour.setScaleY(1.1);
        });

        btnRetour.setOnMouseExited(e -> {
            btnRetour.setStyle(styleRetour);
            btnRetour.setScaleX(1.0);
            btnRetour.setScaleY(1.0);
        });

        btnRetour.setOnAction(e -> app.changerVersAccueil());

        // 6. ASSEMBLAGE
        mainBox.getChildren().addAll(titre, conteneurPions, btnRetour);
        root.getChildren().add(mainBox);

        scene = new Scene(root, 1200, 800);
    }

    public Scene getScene() {
        return scene;
    }
}