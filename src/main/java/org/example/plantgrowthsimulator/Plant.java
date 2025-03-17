package org.example.plantgrowthsimulator;

public class Plant {
    private double height; // Hauteur de la plante
    private double waterLevel; // Niveau d'eau actuel
    private double maxWaterLevel; // Niveau d'eau maximal atteint
    private double growthVariable; // Variable de croissance
    private boolean isDead; // La plante est-elle morte ?
    private final double maxGrowth = 150; // Croissance maximale

    public Plant() {
        this.height = 0;
        this.waterLevel = 0;
        this.maxWaterLevel = 0;
        this.growthVariable = 0;
        this.isDead = false;
    }

    // Getters et setters
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
        this.maxWaterLevel = Math.max(this.maxWaterLevel, waterLevel);
    }

    public double getGrowthVariable() {
        return growthVariable;
    }

    public void setGrowthVariable(double growthVariable) {
        this.growthVariable = Math.min(growthVariable, maxGrowth);
        this.height = this.growthVariable; // La hauteur est égale à la variable de croissance
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public double getMaxWaterLevel() {
        return maxWaterLevel;
    }

    public void reset() {
        this.height = 0;
        this.waterLevel = 0;
        this.maxWaterLevel = 0;
        this.growthVariable = 0;
        this.isDead = false;
    }
}