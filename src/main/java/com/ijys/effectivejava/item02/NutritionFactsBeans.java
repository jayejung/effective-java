package com.ijys.effectivejava.item02;

public class NutritionFactsBeans {
    private int servingSize;      // (ml, 1회 제공량)     필수
    private int servings;         // (회, 총 n회 제공량)   필수
    private int calories;         // (1회 제공량당)        선택
    private int fat;              // (g/1회 제공량)       선택
    private int sodium;           // (mg/1회 제공량)      선택
    private int carbohydrate;     // (g/1회 제공량)       선택

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    @Override
    public String toString() {
        return "NutritionFactsBeans{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", carbohydrate=" + carbohydrate +
                '}';
    }
}
