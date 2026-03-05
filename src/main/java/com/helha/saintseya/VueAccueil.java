package com.helha.saintseya;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;

public class VueAccueil {
    private Scene scene;

    public VueAccueil(SaintSeiyaApp app) {

        StackPane root = new StackPane();

        InputStream is = getClass().getResourceAsStream("/com/helha/saintseya/Menu/MainMenu.jpg");
        if (is != null) {
            ImageView fond = new ImageView(new Image(is));
            fond.fitWidthProperty().bind(root.widthProperty());
            fond.fitHeightProperty().bind(root.heightProperty());
            fond.setPreserveRatio(false);
            root.getChildren().add(fond);
        } else {

            root.setStyle(StyleConstantes.FOND);
        }


        VBox menuBox = new VBox(40);
        menuBox.setAlignment(Pos.CENTER);


        Label titre = new Label("SAINT SEIYA\nTHE 12 HOUSES OF KNOWLEDGE");
        titre.setTextAlignment(TextAlignment.CENTER);

        titre.setStyle("-fx-text-fill: linear-gradient(to bottom, #FFDF00, #DAA520); -fx-font-size: 52px; -fx-font-weight: bold; -fx-font-family: 'Arial';");


        DropShadow ombreTitre = new DropShadow();
        ombreTitre.setColor(Color.BLACK);
        ombreTitre.setRadius(15);
        ombreTitre.setSpread(0.7);
        titre.setEffect(ombreTitre);


        Button btnJouer = creerBoutonMenu("PLAY");
        btnJouer.setOnAction(e -> app.changerVersSelection());

        Button btnOptions = creerBoutonMenu("OPTIONS");
        btnOptions.setOnAction(e -> System.out.println("Options Soon..."));

        Button btnQuitter = creerBoutonMenu("QUIT");
        btnQuitter.setOnAction(e -> Platform.exit());

        menuBox.getChildren().addAll(titre, btnJouer, btnOptions, btnQuitter);


        root.getChildren().add(menuBox);


        scene = new Scene(root, 1200, 800);
    }

    public Scene getScene() {
        return scene;
    }


    private Button creerBoutonMenu(String texte) {
        Button btn = new Button(texte);


        String styleNormal = "-fx-background-color: linear-gradient(to bottom, #8B6508, #5C4305); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 30; " +
                "-fx-border-color: #FFDF00; " +
                "-fx-border-radius: 30; " +
                "-fx-border-width: 2px; " +
                "-fx-padding: 10 50 10 50;";

        btn.setStyle(styleNormal);
        btn.setPrefWidth(300);


        DropShadow ombreBtn = new DropShadow();
        ombreBtn.setColor(Color.BLACK);
        ombreBtn.setRadius(10);
        btn.setEffect(ombreBtn);


        btn.setOnMouseEntered(e -> {

            btn.setStyle(styleNormal.replace("#8B6508, #5C4305", "#FFDF00, #DAA520").replace("white", "#1e2d4a"));
            btn.setScaleX(1.1); // Grossit de 10%
            btn.setScaleY(1.1);
        });


        btn.setOnMouseExited(e -> {
            btn.setStyle(styleNormal);
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });

        return btn;
    }
}