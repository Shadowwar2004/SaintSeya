package com.helha.saintseya;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.io.InputStream;

public class VuePlateau {
    private Scene scene;
    private Joueur modele;
    private GameController controleur;

    public VuePlateau(SaintSeiyaApp app, String nomChevalier, String fichierImage) {
        this.modele = new Joueur();
        this.controleur = new GameController(modele);

        StackPane rootContainer = new StackPane();
        rootContainer.setStyle(StyleConstantes.FOND);

        chargerFond(rootContainer);

        Pane plateauPane = new Pane();
        plateauPane.setPrefSize(1000, 1000);
        dessinerPlateau(plateauPane);


        StackPane pionVisuel = new StackPane();
        pionVisuel.setPrefSize(40, 40);

        try {

            Image imgPion = new Image(getClass().getResourceAsStream("/com/helha/saintseya/pion/" + fichierImage));
            ImageView vueImage = new ImageView(imgPion);


            vueImage.setFitWidth(45);
            vueImage.setFitHeight(45);
            vueImage.setPreserveRatio(true);

            pionVisuel.getChildren().add(vueImage);
        } catch (Exception e) {
            System.out.println("⚠️ Impossible de charger l'image du pion : " + fichierImage);
        }


        pionVisuel.layoutXProperty().bind(modele.xProperty().subtract(20));


        pionVisuel.layoutYProperty().bind(modele.yProperty().subtract(30));

        plateauPane.getChildren().add(pionVisuel);


        Group plateauGroup = new Group(plateauPane);
        plateauPane.scaleXProperty().bind(Bindings.min(rootContainer.widthProperty().divide(1000), rootContainer.heightProperty().divide(1000)));
        plateauPane.scaleYProperty().bind(plateauPane.scaleXProperty());


        Button btnRetour = new Button("Retour (" + nomChevalier + ")");
        btnRetour.setStyle(StyleConstantes.BOUTON);
        btnRetour.setOnAction(e -> app.changerVersAccueil());

        VBox interfaceVBox = new VBox(btnRetour);
        interfaceVBox.setAlignment(Pos.TOP_LEFT);
        interfaceVBox.setPadding(new javafx.geometry.Insets(20));
        interfaceVBox.setPickOnBounds(false);

        rootContainer.getChildren().addAll(plateauGroup, interfaceVBox);

        this.scene = new Scene(rootContainer, 1200, 800);
    }

    public Scene getScene() {
        return scene;
    }

    private void dessinerPlateau(Pane plateau) {
        double cx = 500, cy = 500;
        int nbBranches = 12;
        double espacement = 60;

        String[] nomsImages = {"Belier.png", "Taureau.png", "gemo.png", "cancer.png", "Lion.png", "Vierge.png", "Balance.png", "Scorpion.png", "Sagittaire.png", "Capricorne.png", "Verseau.png", "Poisson.png"};
        String[] nomsBoss = {"ARIES", "TAURUS", "GEMINI", "CANCER", "LEO", "VIRGO", "LIBRA", "SCORPIO", "SAGITTARIUS", "CAPRICORN", "AQUARIUS", "PISCES"};

        for (int i = 0; i < nbBranches; i++) {
            double angle = Math.toRadians(i * (360.0 / nbBranches) - 90);
            for (int j = 1; j <= 4; j++) {
                int idxCol = (i + j) % 4;
                Color c = (idxCol == 1) ? Color.ORANGE : (idxCol == 2) ? Color.DODGERBLUE : (idxCol == 3) ? Color.LIMEGREEN : Color.GOLD;
                ajouterCase(plateau, cx, cy, 80 + (j * espacement), angle, c);
            }
            ajouterFleche(plateau, cx, cy, 80 + (5 * espacement), angle);
            ajouterCaseBoss(plateau, cx, cy, 80 + (6.2 * espacement), angle, nomsImages[i], nomsBoss[i]);
        }

        double rayonCercle = 80 + (4 * espacement);
        for (int i = 0; i < nbBranches; i++) {
            double a1 = i * (360.0 / nbBranches) - 90;
            double a2 = (i + 1) * (360.0 / nbBranches) - 90;
            for (int k = 1; k <= 2; k++) {
                int idxCol = (i + k + 4) % 4;
                Color c = (idxCol == 1) ? Color.ORANGE : (idxCol == 2) ? Color.DODGERBLUE : (idxCol == 3) ? Color.LIMEGREEN : Color.GOLD;
                ajouterCase(plateau, cx, cy, rayonCercle, Math.toRadians(a1 + (k * (a2 - a1) / 3)), c);
            }
        }
        plateau.getChildren().add(creerCaseStart(cx, cy));
    }

    private void ajouterCase(Pane root, double cx, double cy, double dist, double angle, Color c) {
        double x = cx + Math.cos(angle) * dist;
        double y = cy + Math.sin(angle) * dist;
        Rectangle r = new Rectangle(x - 15, y - 15, 30, 30);
        r.setArcWidth(8); r.setArcHeight(8);
        r.setFill(c);
        r.setRotate(Math.toDegrees(angle) + 90);
        r.setOnMouseClicked(e -> controleur.demanderDeplacement(x, y));
        root.getChildren().add(r);
    }

    private void chargerFond(StackPane root) {

        InputStream is = getClass().getResourceAsStream("/com/helha/saintseya/Plateau/Fond_plateau.png");
        if (is != null) {
            ImageView bg = new ImageView(new Image(is));
            bg.fitWidthProperty().bind(root.widthProperty());
            bg.fitHeightProperty().bind(root.heightProperty());
            bg.setPreserveRatio(false);
            root.getChildren().add(bg);
        } else {
            System.out.println("⚠️ Image de fond introuvable !");
        }
    }

    private void ajouterCaseBoss(Pane root, double cx, double cy, double dist, double angle, String img, String name) {
        double x = cx + Math.cos(angle) * dist;
        double y = cy + Math.sin(angle) * dist;
        VBox box = new VBox(5); box.setAlignment(Pos.CENTER);
        Rectangle r = new Rectangle(70, 70); r.setArcWidth(15); r.setArcHeight(15);
        r.setStroke(Color.GOLD);


        InputStream is = getClass().getResourceAsStream("/com/helha/saintseya/image/" + img);
        if (is != null) {
            r.setFill(new ImagePattern(new Image(is, 70, 70, true, true)));
        } else {
            System.out.println("⚠️ Image de boss introuvable : " + img);
        }


        Text texteBoss = new Text(name);
        texteBoss.setFill(Color.YELLOW);
        texteBoss.setStyle("-fx-font-weight: bold;");

        box.getChildren().addAll(r, texteBoss);
        box.setLayoutX(x - 35); box.setLayoutY(y - 45);
        box.setRotate(Math.toDegrees(angle) + 90);
        box.setOnMouseClicked(e -> controleur.demanderDeplacement(x, y));
        root.getChildren().add(box);
    }

    private void ajouterFleche(Pane root, double cx, double cy, double dist, double angle) {
        Polygon p = new Polygon(0, -12, -12, 12, 12, 12);
        p.setFill(Color.WHITE);
        p.setLayoutX(cx + Math.cos(angle) * dist);
        p.setLayoutY(cy + Math.sin(angle) * dist);
        p.setRotate(Math.toDegrees(angle) + 90);
        root.getChildren().add(p);
    }

    private StackPane creerCaseStart(double cx, double cy) {
        StackPane s = new StackPane();

        // Le fond de la case avec coins arrondis et bordure dorée
        Rectangle r = new Rectangle(130, 130, Color.WHITESMOKE);
        r.setStroke(Color.GOLD);
        r.setStrokeWidth(3); // Bordure un peu plus visible
        r.setArcWidth(15);   // Coins arrondis comme sur ta capture !
        r.setArcHeight(15);

        // Le texte exact de ta capture d'écran
        Text t = new Text("SAINT SEIYA\nKNOWLEDGE\nQUEST\n\n[ START ]");
        t.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); // On centre le texte
        t.setStyle("-fx-font-weight: bold; -fx-fill: #1e2d4a;"); // En gras avec une couleur bleu foncé

        s.getChildren().addAll(r, t);
        s.setLayoutX(cx - 65);
        s.setLayoutY(cy - 65);
        s.setOnMouseClicked(e -> controleur.demanderDeplacement(cx, cy));

        return s;
    }
}