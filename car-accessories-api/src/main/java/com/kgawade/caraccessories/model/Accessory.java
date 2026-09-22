package com.kgawade.caraccessories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a single car accessory record.
 *
 * This replaces the original app's JTable row (Accessory ID, Accessory Name,
 * Category, Price, Price Range, Recommendation) with a real, persisted JPA
 * entity backed by a database table, instead of an in-memory table model
 * that reset every time the app closed.
 *
 * Note: the ID is client-supplied (not auto-generated), matching the
 * original app's behaviour where the user typed in their own ID and the
 * system checked for duplicates before inserting (see Idconfirm() in the
 * original CAD_Info.java).
 */
@Entity
@Table(name = "accessories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accessory {

    @Id
    private Integer id;

    @NotBlank(message = "Accessory name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Price level is required")
    @Enumerated(EnumType.STRING)
    private PriceLevel priceLevel;

    @NotNull(message = "Recommendation source is required")
    @Enumerated(EnumType.STRING)
    private RecommendationSource recommendation;
}
