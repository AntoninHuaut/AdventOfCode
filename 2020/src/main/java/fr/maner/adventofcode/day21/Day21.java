package fr.maner.adventofcode.day21;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {

    private final List<Dish> dishList = new ArrayList<>();

    private final Set<String> ingredientSet = new HashSet<>();
    
    private final Map<String, Integer> countIngredientMap = new HashMap<>();

    private final Map<String, Set<String>> allergenToPossibleIngredients = new HashMap<>();
    private final Map<String, Set<String>> ingredientsToPossibleAllergen = new HashMap<>();

    public Day21() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day21_scanner.txt");
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day21().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            Dish dish = new Dish();

            String[] split = line.split("\\(contains ");

            for (String ingredient : split[0].split(" "))
                dish.addIngredient(ingredient.trim());

            for (String allergen : split[1].replace(")", "").split(","))
                dish.addAllergen(allergen.trim());

            this.dishList.add(dish);
        }

        init();
    }

    @Override
    public void partOne() {
        int count = this.ingredientSet.stream()
                .filter(ingredient -> this.allergenToPossibleIngredients.values().stream().noneMatch(poss -> poss.contains(ingredient)))
                .mapToInt(this.countIngredientMap::get).sum();

        System.out.println(count);
    }

    @Override
    public void partTwo() {
        List<String> safeIngredients = this.ingredientSet.stream()
                .filter(ingredient -> this.allergenToPossibleIngredients.values().stream().noneMatch(poss -> poss.contains(ingredient)))
                .collect(Collectors.toList());

        List<String> notSafeIngredients = this.ingredientSet.stream()
                .filter(ingredient -> !safeIngredients.contains(ingredient))
                .collect(Collectors.toList());

        this.dishList.forEach(dish -> dish.removeIngredient(safeIngredients));

        for (Map.Entry<String, Set<String>> entry : this.allergenToPossibleIngredients.entrySet()) {
            for (String value : entry.getValue()) {
                if (!this.ingredientsToPossibleAllergen.containsKey(value))
                    this.ingredientsToPossibleAllergen.put(value, new HashSet<>());

                this.ingredientsToPossibleAllergen.get(value).add(entry.getKey());
            }
        }

        BiMap<String, String> solution = HashBiMap.create();
        resolve(0, new ArrayList<>(notSafeIngredients), solution);

        StringBuilder res = new StringBuilder();

        solution.values().stream().sorted().forEach(allergen -> {
            if (res.length() > 0) res.append(",");
            res.append(solution.inverse().get(allergen));
        });

        System.out.println(res);
    }

    private boolean resolve(int index, List<String> ingredients, BiMap<String, String> solution) {
        for (int i = index; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);

            for (String allergen : this.ingredientsToPossibleAllergen.get(ingredient)) {
                if (solution.containsValue(allergen)) continue;

                solution.put(ingredient, allergen);
                if (!resolve(index + 1, ingredients, solution)) {
                    solution.remove(ingredient, allergen);
                }
            }

            if (!solution.containsKey(ingredient)) return false;
        }

        return true;
    }

    private void init() {
        Set<String> allergenSet = new HashSet<>();

        for (Dish dish : this.dishList) {
            dish.getIngredients().forEach(ingredient -> {
                this.ingredientSet.add(ingredient);
                this.countIngredientMap.merge(ingredient, 1, Integer::sum);
            });

            allergenSet.addAll(dish.getAllergens());
        }

        allergenSet.forEach(allergen -> this.allergenToPossibleIngredients.put(allergen, new HashSet<>(this.ingredientSet)));

        this.dishList.forEach(dish ->
                dish.getAllergens().forEach(allergen ->
                        this.ingredientSet.stream()
                                .filter(ingredient -> !dish.getIngredients().contains(ingredient))
                                .forEach(ingredient -> this.allergenToPossibleIngredients.get(allergen).remove(ingredient))
                )
        );
    }
}