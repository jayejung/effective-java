package com.ijys.effectivejava.item02;

public class NutritionFacts {
    private final int servingSize;      // (ml, 1회 제공량)     필수
    private final int servings;         // (회, 총 n회 제공량)   필수
    private final int calories;         // (1회 제공량당)        선택
    private final int fat;              // (g/1회 제공량)       선택
    private final int sodium;           // (mg/1회 제공량)      선택
    private final int carbonhydrate;     // (g/1회 제공량)       선택

    public NutritionFacts(NutritionFactsBuilder nutritionFactsBuilder) {
        servingSize = nutritionFactsBuilder.servingSize;
        servings = nutritionFactsBuilder.servings;
        calories = nutritionFactsBuilder.calories;
        fat = nutritionFactsBuilder.fat;
        sodium = nutritionFactsBuilder.sodium;
        carbonhydrate = nutritionFactsBuilder.carbonhydrate;
    }

    @Override
    public String toString() {
        return "NutritionFactsBuilder{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", carbonhydrate=" + carbonhydrate +
                '}';
    }

    public static class NutritionFactsBuilder {
        // 필수 매개 변수
        private final int servingSize;
        private final int servings;

        // 선택 매개 변수 - 기본 값으로 초기화
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbonhydrate = 0;

        // 빌더 생성자에서 필수 파라메터를 꼭 전달하게 강제화 하였음.
        public NutritionFactsBuilder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public NutritionFactsBuilder calories(int val) {
            this.calories = val;
            return this;
        }

        public NutritionFactsBuilder fat(int val) {
            this.fat = val;
            return this;
        }

        public NutritionFactsBuilder sodium(int val) {
            this.sodium = val;
            return this;
        }

        public NutritionFactsBuilder carbonhydrate(int val) {
            this.carbonhydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }
}
