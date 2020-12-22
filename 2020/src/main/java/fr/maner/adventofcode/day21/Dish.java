package fr.maner.adventofcode.day21;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Dish {

    private final List<String> ingredients = new ArrayList<>();
    private final List<String> allergens = new ArrayList<>();

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addAllergen(String allergen) {
        this.allergens.add(allergen);
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public List<String> getAllergens() {
        return this.allergens;
    }

    public void removeIngredient(List<String> ingredient) {
        this.ingredients.removeAll(ingredient);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Dish.class.getSimpleName() + "[", "]")
                .add("ingredients=" + this.ingredients)
                .add("allergens=" + this.allergens)
                .toString();
    }
}
