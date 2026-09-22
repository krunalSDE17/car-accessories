package com.kgawade.caraccessories.service;

import com.kgawade.caraccessories.exception.AccessoryNotFoundException;
import com.kgawade.caraccessories.exception.DuplicateAccessoryIdException;
import com.kgawade.caraccessories.model.Accessory;
import com.kgawade.caraccessories.repository.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic layer -- equivalent to the action-handler methods in the
 * original CAD_Info.java (addbtnActionPerformed, delItembtnActionPerformed,
 * delAllbtnActionPerformed, priceSrchbtnActionPerformed,
 * catSrchbtnActionPerformed), but decoupled from the UI entirely.
 */
@Service
@RequiredArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public List<Accessory> getAll() {
        return accessoryRepository.findAll();
    }

    public Accessory getById(Integer id) {
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new AccessoryNotFoundException(id));
    }

    /**
     * Adds a new accessory. Mirrors the original Idconfirm() duplicate
     * check -- if the ID already exists, reject the request instead of
     * silently overwriting it.
     */
    public Accessory add(Accessory accessory) {
        if (accessoryRepository.existsById(accessory.getId())) {
            throw new DuplicateAccessoryIdException(accessory.getId());
        }
        return accessoryRepository.save(accessory);
    }

    /**
     * Deletes a single accessory by ID. Mirrors delItembtnActionPerformed().
     */
    public void deleteById(Integer id) {
        if (!accessoryRepository.existsById(id)) {
            throw new AccessoryNotFoundException(id);
        }
        accessoryRepository.deleteById(id);
    }

    /**
     * Deletes every accessory. Mirrors delAllbtnActionPerformed().
     */
    @Transactional
    public void deleteAll() {
        accessoryRepository.deleteAll();
    }

    /**
     * Finds accessories at an exact price point. The original app
     * implemented a manual binary search over an in-memory array for
     * this; here, the database's own index-backed query does the same
     * job far more efficiently and correctly (the original binary search
     * had a bug: it returned a[mid], the matched value, instead of its
     * original row position, then re-scanned the table anyway to find
     * the matching row).
     */
    public List<Accessory> searchByPrice(Double price) {
        return accessoryRepository.findByPrice(price);
    }

    /**
     * Finds accessories in a given category. Mirrors
     * catSrchbtnActionPerformed().
     */
    public List<Accessory> searchByCategory(String category) {
        return accessoryRepository.findByCategoryIgnoreCase(category);
    }
}
