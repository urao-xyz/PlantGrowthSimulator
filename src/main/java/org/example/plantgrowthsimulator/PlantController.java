package org.example.plantgrowthsimulator;

public class PlantController {
    private Plant plant;
    private double lightLevel;
    private long lastGrowthTime = 0;

    public PlantController(Plant plant, double lightLevel) {
        this.plant = plant;
        this.lightLevel = lightLevel;
    }

    public void waterPlant(double amount) {
        if (!plant.isDead()) {
            plant.setWaterLevel(plant.getWaterLevel() + amount);
            if (plant.getWaterLevel() > 150) { // La plante meurt si elle est trop arrosée
                plant.setDead(true);
            }
        }
    }

    public void updateGrowth(long now) {
        if (!plant.isDead() && plant.getWaterLevel() > 0) {
            // Augmenter la croissance de 1 toute les 1 secondes
            if (now - lastGrowthTime >= 1_000_000_000) { // 1 seconde en nanosecondes
                plant.setGrowthVariable(plant.getGrowthVariable() + 1);
                lastGrowthTime = now;
            }
        }
    }

    public void decreaseWater() {
        if (!plant.isDead()) {
            plant.setWaterLevel(Math.max(0, plant.getWaterLevel() - 2));
            if (plant.getWaterLevel() <= 0 && plant.getMaxWaterLevel() >= 30) {
                plant.setDead(true);
            }
        }
    }

    public void resetPlant() {
        plant.reset();
    }

    public Plant getPlant() {
        return plant;
    }
}