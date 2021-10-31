package com.ijys.effectivejava.item02;

import com.ijys.effectivejava.item02.pasta.Calzone;
import com.ijys.effectivejava.item02.pasta.NyPizza;
import com.ijys.effectivejava.item02.pasta.Pizza;

import static com.ijys.effectivejava.item02.pasta.Pizza.Topping.HAM;

public class Item02Main {
    public static void main(String[] args) {
        /*
        Telescoping constructor pattern (점층적 생성자 패턴)
         */
        NutritionFactsTelescoping nutritionFactsTelescoping = new NutritionFactsTelescoping(100, 2);
        NutritionFactsTelescoping nutritionFactsTelescopingWithCalories = new NutritionFactsTelescoping(100, 2, 20);

        System.out.println("* Telescoping Constructor Pattern ====================================");
        System.out.println("nutritionFactsTelescoping: " + nutritionFactsTelescoping);
        System.out.println("nutritionFactsTelescopingWithCalories: " + nutritionFactsTelescopingWithCalories);

        /*
        Java Beans Patterns
         */
        NutritionFactsBeans nutritionFactsBeans = new NutritionFactsBeans();
        nutritionFactsBeans.setServingSize(100);
        nutritionFactsBeans.setServings(2);
        nutritionFactsBeans.setCalories(20);
        nutritionFactsBeans.setFat(0);
        nutritionFactsBeans.setSodium(0);
        nutritionFactsBeans.setCarbohydrate(0);

        NutritionFactsBeans nutritionFactsBeansWithCalories = new NutritionFactsBeans();
        nutritionFactsBeansWithCalories.setServingSize(100);
        nutritionFactsBeansWithCalories.setServings(2);
        nutritionFactsBeansWithCalories.setCalories(20);
        nutritionFactsBeansWithCalories.setFat(0);
        nutritionFactsBeansWithCalories.setSodium(0);
        nutritionFactsBeansWithCalories.setCarbohydrate(0);

        System.out.println("JavaBeans Pattern ====================================");
        System.out.println("nutritionFactsBeans: " + nutritionFactsBeans);
        System.out.println("nutritionFactsBeansWithCalories: " + nutritionFactsBeansWithCalories);

        /*
        Builder Pattern #1
         */
        NutritionFacts nutritionFacts = new NutritionFacts.NutritionFactsBuilder(100, 2)
                .build();
        NutritionFacts nutritionFactsWithCalories = new NutritionFacts.NutritionFactsBuilder(100, 2)
                .calories(0).build();

        System.out.println("Builder Pattern #1 ====================================");
        System.out.println("nutritionFacts: " + nutritionFacts);
        System.out.println("nutritionFactsWithCalories: " + nutritionFactsWithCalories);

        /*
        Builder Pattern #2
         */
        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL).addTopping(Pizza.Topping.ONION).build();
        Calzone calzone = new Calzone.Builder().sauceInside().addTopping(HAM).build();

        System.out.println("Builder Pattern #2 ====================================");
        System.out.println("nyPizza: " + nyPizza);
        System.out.println("calzone: " + calzone);
    }
}
