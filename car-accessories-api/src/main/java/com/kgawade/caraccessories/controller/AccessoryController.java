package com.kgawade.caraccessories.controller;

import com.kgawade.caraccessories.model.Accessory;
import com.kgawade.caraccessories.service.AccessoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for the Car Accessories Management System.
 *
 * This replaces the original Swing UI entirely -- every button click in
 * the old CAD_Info.java (Add, Delete item, Delete all, Search by price,
 * Search by category) now has a matching HTTP endpoint here. A future
 * web frontend (HTML/React) will call these endpoints directly instead
 * of talking to Swing components.
 *
 * CORS is enabled with a wildcard origin for local development, so a
 * frontend running on a different port (e.g. React on localhost:3000)
 * can call this API without being blocked by the browser. Tighten this
 * to a specific origin before deploying anywhere public.
 */
@RestController
@RequestMapping("/api/accessories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccessoryController {

    private final AccessoryService accessoryService;

    // GET /api/accessories -> list every accessory
    @GetMapping
    public List<Accessory> getAll() {
        return accessoryService.getAll();
    }

    // GET /api/accessories/{id} -> get one accessory
    @GetMapping("/{id}")
    public Accessory getById(@PathVariable Integer id) {
        return accessoryService.getById(id);
    }

    // POST /api/accessories -> add a new accessory
    @PostMapping
    public ResponseEntity<Accessory> add(@Valid @RequestBody Accessory accessory) {
        Accessory saved = accessoryService.add(accessory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // DELETE /api/accessories/{id} -> delete one accessory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        accessoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE /api/accessories -> delete everything
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        accessoryService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    // GET /api/accessories/search/price/{price} -> find by exact price
    @GetMapping("/search/price/{price}")
    public List<Accessory> searchByPrice(@PathVariable Double price) {
        return accessoryService.searchByPrice(price);
    }

    // GET /api/accessories/search/category/{category} -> find by category
    @GetMapping("/search/category/{category}")
    public List<Accessory> searchByCategory(@PathVariable String category) {
        return accessoryService.searchByCategory(category);
    }
}
