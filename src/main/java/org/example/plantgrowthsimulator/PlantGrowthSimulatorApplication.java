package org.example.plantgrowthsimulator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlantGrowthSimulatorApplication extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Plant plant1 = new Plant();
    private Plant plant2 = new Plant();
    private Plant plant3 = new Plant();
    private double lightLevel = 1;
    private boolean plant3Unlocked = false;
    private long lastWaterDecreaseTime = 0;

    private PlantController plantController1 = new PlantController(plant1, lightLevel);
    private PlantController plantController2 = new PlantController(plant2, lightLevel);
    private PlantController plantController3 = new PlantController(plant3, lightLevel);

    private PlantRenderer plantRenderer = new PlantRenderer();
    private SaveManager saveManager = new SaveManager();

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Créer des boutons et des labels
        Button waterPlant1Button = new Button("Arroser la plante 1");
        Button waterPlant2Button = new Button("Arroser la plante 2");
        Button waterPlant3Button = new Button("Arroser la plante 3");
        Button increaseLightButton = new Button("Augmenter la lumière");
        Button decreaseLightButton = new Button("Diminuer la lumière");
        Button resetPlant1Button = new Button("Réinitialiser plante 1");
        Button resetPlant2Button = new Button("Réinitialiser plante 2");
        Button resetPlant3Button = new Button("Réinitialiser plante 3");
        Button saveButton = new Button("Sauvegarder");
        Button loadButton = new Button("Charger");

        Label waterLabel1 = new Label("Eau plante 1: " + plant1.getWaterLevel());
        Label waterLabel2 = new Label("Eau plante 2: " + plant2.getWaterLevel());
        Label waterLabel3 = new Label("Eau plante 3: " + plant3.getWaterLevel());
        Label lightLabel = new Label("Lumière: " + lightLevel);
        Label levelLabel = new Label("Niveau: 1");
        Label growthLabel1 = new Label("Croissance plante 1: " + plant1.getGrowthVariable());
        Label growthLabel2 = new Label("Croissance plante 2: " + plant2.getGrowthVariable());
        Label growthLabel3 = new Label("Croissance plante 3: " + plant3.getGrowthVariable());

        // Gestionnaires d'événements pour les boutons
        waterPlant1Button.setOnAction(e -> {
            plantController1.waterPlant(10);
            waterLabel1.setText("Eau plante 1: " + plant1.getWaterLevel());
        });
        waterPlant2Button.setOnAction(e -> {
            plantController2.waterPlant(10);
            waterLabel2.setText("Eau plante 2: " + plant2.getWaterLevel());
        });
        waterPlant3Button.setOnAction(e -> {
            plantController3.waterPlant(10);
            waterLabel3.setText("Eau plante 3: " + plant3.getWaterLevel());
        });
        increaseLightButton.setOnAction(e -> {
            lightLevel = Math.min(2, lightLevel + 0.1);
            lightLabel.setText("Lumière: " + lightLevel);
        });
        decreaseLightButton.setOnAction(e -> {
            lightLevel = Math.max(0.5, lightLevel - 0.1);
            lightLabel.setText("Lumière: " + lightLevel);
        });
        resetPlant1Button.setOnAction(e -> {
            plantController1.resetPlant();
            waterLabel1.setText("Eau plante 1: " + plant1.getWaterLevel());
            growthLabel1.setText("Croissance plante 1: 0.00");
        });
        resetPlant2Button.setOnAction(e -> {
            plantController2.resetPlant();
            waterLabel2.setText("Eau plante 2: " + plant2.getWaterLevel());
            growthLabel2.setText("Croissance plante 2: 0.00");
        });
        resetPlant3Button.setOnAction(e -> {
            plantController3.resetPlant();
            waterLabel3.setText("Eau plante 3: " + plant3.getWaterLevel());
            growthLabel3.setText("Croissance plante 3: 0.00");
        });
        saveButton.setOnAction(e -> saveManager.saveState(plant1, plant2, plant3, lightLevel, plant3Unlocked, "save.dat"));
        loadButton.setOnAction(e -> saveManager.loadState(plant1, plant2, plant3, new double[]{lightLevel}, new boolean[]{plant3Unlocked}, "save.dat"));

        // Organiser les boutons et les labels
        HBox buttonBox = new HBox(10, waterPlant1Button, waterPlant2Button, waterPlant3Button, increaseLightButton, decreaseLightButton, resetPlant1Button, resetPlant2Button, resetPlant3Button, saveButton, loadButton);
        HBox infoBox = new HBox(10, waterLabel1, waterLabel2, waterLabel3, lightLabel, levelLabel, growthLabel1, growthLabel2, growthLabel3);
        VBox root = new VBox(10, canvas, buttonBox, infoBox);

        // Configurer la scène
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Plant Growth Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Animation de croissance
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);

                // Dessiner les pots et les plantes
                plantRenderer.drawPot(gc, 150);
                plantRenderer.drawPot(gc, 400);
                if (plant3Unlocked) {
                    plantRenderer.drawPot(gc, 650);
                }

                if (!plant1.isDead()) {
                    plantRenderer.drawPlant(gc, 150, plant1.getHeight(), Color.DARKGREEN, Color.GREEN);
                } else {
                    plantRenderer.drawDeadPlant(gc, 150, "Plante 1 morte (noyée)");
                }
                if (!plant2.isDead()) {
                    plantRenderer.drawPlant(gc, 400, plant2.getHeight(), Color.DARKBLUE, Color.LIGHTBLUE);
                } else {
                    plantRenderer.drawDeadPlant(gc, 400, "Plante 2 morte (noyée)");
                }
                if (plant3Unlocked && !plant3.isDead()) {
                    plantRenderer.drawPlant(gc, 650, plant3.getHeight(), Color.DARKRED, Color.RED);
                } else if (plant3Unlocked) {
                    plantRenderer.drawDeadPlant(gc, 650, "Plante 3 morte (noyée)");
                }

                // Mettre à jour la croissance et l'eau
                plantController1.updateGrowth(now);
                plantController2.updateGrowth(now);
                plantController3.updateGrowth(now);

                // Diminuer l'eau de 2 toutes les 1 secondes
                if (now - lastWaterDecreaseTime >= 1_000_000_000) {
                    plantController1.decreaseWater();
                    plantController2.decreaseWater();
                    plantController3.decreaseWater();
                    lastWaterDecreaseTime = now;
                }

                // Mettre à jour les labels
                waterLabel1.setText("Eau plante 1: " + String.format("%.2f", plant1.getWaterLevel()));
                waterLabel2.setText("Eau plante 2: " + String.format("%.2f", plant2.getWaterLevel()));
                waterLabel3.setText("Eau plante 3: " + String.format("%.2f", plant3.getWaterLevel()));
                growthLabel1.setText("Croissance plante 1: " + String.format("%.2f", plant1.getGrowthVariable()));
                growthLabel2.setText("Croissance plante 2: " + String.format("%.2f", plant2.getGrowthVariable()));
                growthLabel3.setText("Croissance plante 3: " + String.format("%.2f", plant3.getGrowthVariable()));

                // Débloquer la plante 3
                if (!plant3Unlocked && plant1.getHeight() > 100 && plant2.getHeight() > 100) {
                    plant3Unlocked = true;
                    levelLabel.setText("Niveau: 2");
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}