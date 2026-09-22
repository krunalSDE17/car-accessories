package com.kgawade.caraccessories.repository;

import com.kgawade.caraccessories.model.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access layer for Accessory records.
 *
 * The original app implemented its own binary search over an in-memory
 * array to find an accessory by price (see binarySearch() in the old
 * CAD_Info.java). Here, Spring Data JPA generates the SQL automatically
 * from the method name below, and the database's own indexing handles
 * the lookup efficiently -- no manual search algorithm needed.
 */
@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Integer> {

    List<Accessory> findByCategoryIgnoreCase(String category);

    List<Accessory> findByPrice(Double price);
}
