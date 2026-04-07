package com.kodilla.patterns.builder.bigmac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bigmac {

    private final Bun bun;
    private final int burgers;
    private final Sauce sauce;
    private final List<Ingredient> ingredients;

    private Bigmac(BigmacBuilder builder) {
        this.bun = builder.bun;
        this.burgers = builder.burgers;
        this.sauce = builder.sauce;
        this.ingredients = Collections.unmodifiableList(new ArrayList<>(builder.ingredients));
    }

    public Bun getBun() {
        return bun;
    }

    public int getBurgers() {
        return burgers;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Bigmac{" +
                "bun=" + bun +
                ", burgers=" + burgers +
                ", sauce=" + sauce +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class BigmacBuilder {
        private Bun bun;
        private int burgers;
        private Sauce sauce;
        private final List<Ingredient> ingredients = new ArrayList<>();

        public BigmacBuilder bun(Bun bun) {
            this.bun = bun;
            return this;
        }

        public BigmacBuilder burgers(int burgers) {
            if (burgers < 1 || burgers > 3) {
                throw new IllegalArgumentException("Number of burgers must be between 1 and 3");
            }
            this.burgers = burgers;
            return this;
        }

        public BigmacBuilder sauce(Sauce sauce) {
            this.sauce = sauce;
            return this;
        }

        public BigmacBuilder ingredient(Ingredient ingredient) {
            if (ingredient == null) {
                throw new IllegalArgumentException("Ingredient cannot be null");
            }
            this.ingredients.add(ingredient);
            return this;
        }

        public Bigmac build() {
            if (bun == null) {
                throw new IllegalArgumentException("Bun must be set");
            }
            if (sauce == null) {
                throw new IllegalArgumentException("Sauce must be set");
            }
            if (burgers < 1 || burgers > 3) {
                throw new IllegalArgumentException("Number of burgers must be between 1 and 3");
            }
            return new Bigmac(this);
        }
    }
}
