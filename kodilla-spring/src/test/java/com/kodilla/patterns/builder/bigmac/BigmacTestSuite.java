package com.kodilla.patterns.builder.bigmac;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BigmacTestSuite {

    @Test
    void testBigmacBuilder() {
        // Given
        Bigmac bigmac = new Bigmac.BigmacBuilder()
                .bun(Bun.SESAME)
                .burgers(2)
                .sauce(Sauce.BARBECUE)
                .ingredient(Ingredient.LETTUCE)
                .ingredient(Ingredient.BACON)
                .ingredient(Ingredient.CHEESE)
                .build();

        System.out.println(bigmac);

        // When
        int ingredientsCount = bigmac.getIngredients().size();

        // Then
        assertEquals(3, ingredientsCount);
        assertEquals(Bun.SESAME, bigmac.getBun());
        assertEquals(2, bigmac.getBurgers());
        assertEquals(Sauce.BARBECUE, bigmac.getSauce());
    }

    @Test
    void testBuilderValidationThrowsExceptionForInvalidBurgers() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bigmac.BigmacBuilder()
                        .bun(Bun.PLAIN)
                        .burgers(0)
                        .sauce(Sauce.STANDARD)
                        .build()
        );
    }

    @Test
    void testBuilderValidationRequiresBunAndSauce() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bigmac.BigmacBuilder()
                        .burgers(1)
                        .sauce(null)
                        .build()
        );
    }

    @Test
    void testIngredientsListIsUnmodifiable() {
        // Given
        Bigmac bigmac = new Bigmac.BigmacBuilder()
                .bun(Bun.PLAIN)
                .burgers(1)
                .sauce(Sauce.STANDARD)
                .ingredient(Ingredient.LETTUCE)
                .build();

        // When
        List<Ingredient> ingredients = bigmac.getIngredients();

        // Then
        assertThrows(UnsupportedOperationException.class, () -> ingredients.add(Ingredient.BACON));
    }
}
