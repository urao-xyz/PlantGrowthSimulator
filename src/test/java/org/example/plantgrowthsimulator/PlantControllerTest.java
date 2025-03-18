package org.example.plantgrowthsimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantControllerTest {
    private Plant plant;
    private PlantController plantController;

    @BeforeEach
    void setUp() {
        plant = new Plant();
        plantController = new PlantController(plant, 1.0); // Light level = 1.0
    }

    @Test
    void testWaterPlant() {
        // Arroser la plante de 10 unités
        plantController.waterPlant(10);
        assertEquals(10, plant.getWaterLevel(), "Le niveau d'eau devrait être de 10 après arrosage.");

        // Arroser la plante jusqu'à la noyade (dépassement de 150)
        plantController.waterPlant(150);
        assertTrue(plant.isDead(), "La plante devrait être morte si elle est trop arrosée.");
    }

    @Test
    void testUpdateGrowth() {
        // Ajouter de l'eau à la plante
        plantController.waterPlant(10);
        long lastGrowthTime = 0;
        long now = lastGrowthTime;
        // Simuler 1 seconde
        plantController.updateGrowth(now);
        assertEquals(now, plant.getGrowthVariable(), "La croissance devrait augmenter de 1 après 1 seconde.");

    }

    @Test
    void testDecreaseWater() {
        // Ajouter de l'eau à la plante
        plantController.waterPlant(30);
        assertEquals(30, plant.getWaterLevel(), "Le niveau d'eau devrait être de 30 après arrosage.");

        // Diminuer l'eau jusqu'à 0
        for (int i = 0; i < 15; i++) { // 30 / 2 = 15 itérations pour atteindre 0
            plantController.decreaseWater();
        }
        assertTrue(plant.isDead(), "La plante devrait mourir si elle n'a plus d'eau.");
    }

    @Test
    void testResetPlant() {
        // Modifier l'état de la plante
        plantController.waterPlant(50);
        plantController.updateGrowth(1_000_000_000L);
        plant.setDead(true);

        // Réinitialiser la plante
        plantController.resetPlant();

        // Vérifier que tout est réinitialisé
        assertEquals(0, plant.getWaterLevel(), "Le niveau d'eau devrait être réinitialisé à 0.");
        assertEquals(0, plant.getGrowthVariable(), "La croissance devrait être réinitialisée à 0.");
        assertFalse(plant.isDead(), "La plante ne devrait plus être morte après réinitialisation.");
    }
}