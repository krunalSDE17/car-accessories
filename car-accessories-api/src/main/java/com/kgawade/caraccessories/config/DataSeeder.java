package com.kgawade.caraccessories.config;

import com.kgawade.caraccessories.model.Accessory;
import com.kgawade.caraccessories.model.PriceLevel;
import com.kgawade.caraccessories.model.RecommendationSource;
import com.kgawade.caraccessories.repository.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds the database with the same 12 sample rows the original Swing app
 * hardcoded directly into its JTable model, so the API starts with
 * familiar data on first run.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AccessoryRepository accessoryRepository;

    @Override
    public void run(String... args) {
        if (accessoryRepository.count() > 0) {
            return; // already seeded
        }

        accessoryRepository.saveAll(java.util.List.of(
                new Accessory(1, "Bluetooth", "Media", 1000.0, PriceLevel.MEDIUM, RecommendationSource.BY_COMPANY),
                new Accessory(2, "Air freshener", "Ambience", 1500.0, PriceLevel.MEDIUM, RecommendationSource.NONE),
                new Accessory(3, "Air bags", "Comfort", 13000.0, PriceLevel.HIGH, RecommendationSource.NONE),
                new Accessory(4, "USB charger", "Media", 500.0, PriceLevel.LOW, RecommendationSource.BY_COMMUNITY),
                new Accessory(5, "Seat cover", "Interior", 6000.0, PriceLevel.HIGH, RecommendationSource.NONE),
                new Accessory(6, "Floor mats", "Interior", 1000.0, PriceLevel.MEDIUM, RecommendationSource.BY_COMPANY),
                new Accessory(7, "Sun roof", "Ambience", 3000.0, PriceLevel.MEDIUM, RecommendationSource.BY_COMPANY),
                new Accessory(8, "Emergency supply", "Comfort", 1200.0, PriceLevel.MEDIUM, RecommendationSource.NONE),
                new Accessory(9, "Lighting", "Ambience", 1500.0, PriceLevel.MEDIUM, RecommendationSource.NONE),
                new Accessory(10, "Seat design", "Comfort", 10000.0, PriceLevel.HIGH, RecommendationSource.NONE),
                new Accessory(11, "Armrest", "Comfort", 700.0, PriceLevel.LOW, RecommendationSource.NONE),
                new Accessory(12, "Mobile holder", "Interior", 650.0, PriceLevel.LOW, RecommendationSource.NONE)
        ));
    }
}
