package org.example.plantgrowthsimulator;

import java.io.*;

public class SaveManager {
    public void saveState(Plant plant1, Plant plant2, Plant plant3, double lightLevel, boolean plant3Unlocked, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeDouble(plant1.getHeight());
            oos.writeDouble(plant2.getHeight());
            oos.writeDouble(plant3.getHeight());
            oos.writeDouble(plant1.getWaterLevel());
            oos.writeDouble(plant2.getWaterLevel());
            oos.writeDouble(plant3.getWaterLevel());
            oos.writeDouble(plant1.getMaxWaterLevel());
            oos.writeDouble(plant2.getMaxWaterLevel());
            oos.writeDouble(plant3.getMaxWaterLevel());
            oos.writeDouble(plant1.getGrowthVariable());
            oos.writeDouble(plant2.getGrowthVariable());
            oos.writeDouble(plant3.getGrowthVariable());
            oos.writeDouble(lightLevel);
            oos.writeBoolean(plant3Unlocked);
            oos.writeBoolean(plant1.isDead());
            oos.writeBoolean(plant2.isDead());
            oos.writeBoolean(plant3.isDead());
            System.out.println("État sauvegardé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadState(Plant plant1, Plant plant2, Plant plant3, double[] lightLevel, boolean[] plant3Unlocked, String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            plant1.setHeight(ois.readDouble());
            plant2.setHeight(ois.readDouble());
            plant3.setHeight(ois.readDouble());
            plant1.setWaterLevel(ois.readDouble());
            plant2.setWaterLevel(ois.readDouble());
            plant3.setWaterLevel(ois.readDouble());
            plant1.setGrowthVariable(ois.readDouble());
            plant2.setGrowthVariable(ois.readDouble());
            plant3.setGrowthVariable(ois.readDouble());
            lightLevel[0] = ois.readDouble();
            plant3Unlocked[0] = ois.readBoolean();
            plant1.setDead(ois.readBoolean());
            plant2.setDead(ois.readBoolean());
            plant3.setDead(ois.readBoolean());
            System.out.println("État chargé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}