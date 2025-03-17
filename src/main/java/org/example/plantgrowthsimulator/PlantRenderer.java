package org.example.plantgrowthsimulator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlantRenderer {
    public void drawPot(GraphicsContext gc, double x) {
        // Dessiner le pot
        gc.setFill(Color.SIENNA);
        gc.fillRect(x, 500, 100, 100); // Base du pot
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, 500, 100, 100); // Contour du pot

        // Dessiner le bord supérieur du pot
        gc.setFill(Color.SIENNA);
        gc.fillOval(x - 10, 480, 120, 40); // Bord arrondi
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 10, 480, 120, 40); // Contour du bord
    }

    public void drawPlant(GraphicsContext gc, double x, double height, Color stemColor, Color leafColor) {
        // Dessiner la tige
        gc.setStroke(stemColor);
        gc.setLineWidth(10);
        gc.strokeLine(x + 50, 500, x + 50, 500 - height); // Tige principale

        // Dessiner des feuilles seulement si la plante a une certaine hauteur
        if (height > 50) {
            gc.setFill(leafColor);
            drawLeaf(gc, x + 30, 500 - height + 50, 40, 80); // Feuille gauche
            drawLeaf(gc, x + 70, 500 - height + 50, 40, 80); // Feuille droite
        }
        if (height > 100) {
            gc.setFill(leafColor);
            drawLeaf(gc, x + 20, 500 - height + 100, 30, 60); // Feuille basse gauche
            drawLeaf(gc, x + 80, 500 - height + 100, 30, 60); // Feuille basse droite
        }
    }

    private void drawLeaf(GraphicsContext gc, double x, double y, double width, double height) {
        // Dessiner une feuille ovale
        gc.fillOval(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x, y, width, height); // Contour de la feuille
    }

    public void drawDeadPlant(GraphicsContext gc, double x, String message) {
        // Dessiner une plante morte
        gc.setFill(Color.GRAY);
        gc.fillRect(x + 40, 500, 20, 50); // Tige morte
        gc.setFill(Color.BLACK);
        gc.fillText(message, x, 550);
    }
}