package com.kgawade.caraccessories.exception;

/**
 * Thrown when trying to add an accessory whose ID already exists.
 * Mirrors the original app's Idconfirm() duplicate-ID check, which showed
 * a JOptionPane message. Here it becomes a proper HTTP 409 Conflict
 * response (see GlobalExceptionHandler).
 */
public class DuplicateAccessoryIdException extends RuntimeException {
    public DuplicateAccessoryIdException(Integer id) {
        super("Accessory with ID " + id + " already exists. Please use a different ID.");
    }
}
