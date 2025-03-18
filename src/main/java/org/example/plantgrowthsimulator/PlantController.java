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
            if (lastGrowthTime == 0) {
                lastGrowthTime = now; // Initialiser le temps
            }
            long elapsedTime = now - lastGrowthTime;
            if (elapsedTime >= 1_000_000_000) { // 1 seconde en nanosecondes
                int growthIncrement = (int) (elapsedTime / 1_000_000_000); // Calculer le nombre de secondes écoulées
                plant.setGrowthVariable(plant.getGrowthVariable() + growthIncrement);
                lastGrowthTime = now; // Mettre à jour le temps
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