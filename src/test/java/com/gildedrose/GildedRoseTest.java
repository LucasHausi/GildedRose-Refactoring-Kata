package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 0})
    void givenNewItem_whenUpdatingQuality_thenInitialSellInValueDecrements(int initialSellIn) {
        // Arrange
        Item testItem = new Item("Sample Item", initialSellIn, 10);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        // Assert
        assertEquals(initialSellIn-1, testItems[0].sellIn, "The SellIn value should be decreased by one after one day");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 1})
    void givenNewItem_whenUpdatingQuality_thenInitialQualityDecrements(int initialQuality) {
        // Arrange
        Item testItem = new Item("Sample Item", 5, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        // Assert
        assertEquals(initialQuality-1, testItems[0].quality, "The Quality value should be decreased by one after one day");
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 0, 3})
    void givenNewItemWithZeroQuality_whenUpdatingQuality_thenInitialQualityDecrementsNotBelowZero(int initialQuality) {
        // Arrange
        Item testItem = new Item("Sample Item", 5, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        app.updateQuality();
        // Assert
        assertTrue(testItems[0].quality >= 0, "The Quality value should be decreased by one after one day");
    }


}
