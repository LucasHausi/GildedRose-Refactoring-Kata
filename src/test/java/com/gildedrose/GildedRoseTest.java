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
    void givenNewItemWithZeroQuality_whenUpdating_thenInitialQualityDecrementsNotBelowZero(int initialQuality) {
        // Arrange
        Item testItem = new Item("Sample Item", 5, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        app.updateQuality();
        // Assert
        assertTrue(testItems[0].quality >= 0, "The Quality value should be decreased below zero after two days");
    }
    @ParameterizedTest
    @ValueSource(ints = {5, 2, 3})
    void givenNewItemWithNegativeSellIn_whenUpdating_thenQualityDecrementsByTwo(int initialQuality) {
        // Arrange
        Item testItem = new Item("Sample Item", -1, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        // Assert
        assertEquals(initialQuality-2, testItems[0].quality, "The Quality value should be decreased by two after one day");
    }
    //Aged brie
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenAgedBrieWithPositiveSellIn_whenUpdating_thenQualityIncrementsByOne(int initialQuality) {
        // Arrange
        Item testItem = new Item("Aged Brie", 2, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        // Assert
        assertEquals(initialQuality+1,testItems[0].quality, "The Quality value should increase by one after one day");
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenAgedBrieWithNegativeSellIn_whenUpdating_thenQualityIncrementsByTwo(int initialQuality) {
        // Arrange
        Item testItem = new Item("Aged Brie", -1, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        // Assert
        assertEquals(initialQuality+2,testItems[0].quality, "The Quality value should increase by two after one day");
    }

    @ParameterizedTest
    @ValueSource(ints = {48,49,50})
    void givenAgedBrieWithNegativeSellIn_whenUpdating_thenQualityIncrementsNotAbove50(int initialQuality) {
        // Arrange
        Item testItem = new Item("Aged Brie", -1, initialQuality);
        Item[] testItems = {testItem};
        GildedRose app = new GildedRose(testItems);
        // Act
        app.updateQuality();
        app.updateQuality();
        // Assert
        assertTrue(testItems[0].quality <=50, "The Quality value should increase above 50");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, -10}) // Different sellIn values to demonstrate Sulfuras does not change
    void givenSulfuras_whenUpdatingQuality_thenSellInAndQualityRemainUnchanged(int sellIn) {
        // Arrange
        int initialQuality = 80; // Sulfuras has a fixed quality of 80
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", sellIn, initialQuality);
        Item[] items = {sulfuras};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(initialQuality, items[0].quality, "Sulfuras quality should remain unchanged regardless of sellIn value");
        assertEquals(sellIn, items[0].sellIn, "Sulfuras sellIn should remain unchanged regardless of sellIn value");
    }



    // Test when there are 10 days or less to the concert
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8, 9, 10}) // Days 10 or less
    void givenBackstagePass_when10DaysOrLessToUpdateQuality_thenQualityIncreasesByTwo(int sellIn) {
        // Arrange
        int initialQuality = 20;
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
        Item[] items = {backstagePass};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(initialQuality + 2, items[0].quality,
            "Backstage passes quality should increase by 2 when there are 10 days or less to the concert");
    }

    // Test when there are 5 days or less to the concert
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5}) // Days 5 or less
    void givenBackstagePass_when5DaysOrLessToUpdateQuality_thenQualityIncreasesByThree(int sellIn) {
        // Arrange
        int initialQuality = 20;
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
        Item[] items = {backstagePass};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(initialQuality + 3, items[0].quality,
            "Backstage passes quality should increase by 3 when there are 5 days or less to the concert");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void givenBackstagePassOnDayOfConcert_whenUpdatingQuality_thenQualityDropsToZero(int sellIn) {
        // Arrange
        int initialQuality = 25; // Initial quality doesn't matter as it should drop to 0
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
        Item[] items = {backstagePass};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, items[0].quality,
            "Backstage passes quality should drop to 0 on the day of the concert (sellIn = " + sellIn + ")");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -15})
    void givenBackstagePassAfterConcert_whenUpdatingQuality_thenQualityRemainsZero(int sellIn) {
        // Arrange
        int initialQuality = 0; // Quality should remain 0
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
        Item[] items = {backstagePass};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, items[0].quality,
            "Backstage passes quality should remain 0 after the concert (sellIn = " + sellIn + ")");
    }

    // Test when there are more than 10 days to the concert
    @ParameterizedTest
    @ValueSource(ints = {11, 12, 15, 20}) // Days more than 10
    void givenBackstagePass_whenMoreThan10DaysToUpdateQuality_thenQualityIncreasesByOne(int sellIn) {
        // Arrange
        int initialQuality = 20;
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
        Item[] items = {backstagePass};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(initialQuality + 1, items[0].quality,
            "Backstage passes quality should increase by 1 when there are more than 10 days to the concert");
    }
}
