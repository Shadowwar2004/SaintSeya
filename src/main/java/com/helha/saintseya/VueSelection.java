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


        VBox mainBox = new VBox(50);
        mainBox.setAlignment(Pos.CENTER);


        Label titre = new Label("CHOISISSEZ VOTRE CHEVALIER");
        titre.setStyle("-fx-text-fill: linear-gradient(to bottom, #FFDF00, #DAA520); -fx-font-size: 42px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        DropShadow ombreTitre = new DropShadow();
        ombreTitre.setColor(Color.BLACK);
        ombreTitre.setRadius(15);
        titre.setEffect(ombreTitre);


        HBox conteneurPions = new HBox(30);
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
            pion.setGraphicTextGap(20);


            try {
                Image img = new Image(getClass().getResourceAsStream("/com/helha/saintseya/pion/" + fichierImage));
                ImageView vueImage = new ImageView(img);
                vueImage.setFitHeight(130);
                vueImage.setPreserveRatio(true);
                pion.setGraphic(vueImage);
            } catch (Exception ex) {
                System.out.println("⚠️ Image introuvable : /pion/" + fichierImage);
            }


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


            pion.setOnMouseExited(e -> {
                pion.setStyle(stylePionNormal);
                pion.setScaleX(1.0);
                pion.setScaleY(1.0);
                pion.setEffect(null);
            });


            pion.setOnAction(e -> {
                System.out.println("Vous avez sélectionné le Chevalier : " + nom);
                app.changerVersPlateau(nom, fichierImage);
            });

            conteneurPions.getChildren().add(pion);
        }


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


        mainBox.getChildren().addAll(titre, conteneurPions, btnRetour);
        root.getChildren().add(mainBox);

        scene = new Scene(root, 1200, 800);
    }

    public Scene getScene() {
        return scene;
    }
}