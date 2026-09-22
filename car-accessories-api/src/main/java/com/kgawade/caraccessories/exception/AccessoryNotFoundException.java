package com.kgawade.caraccessories.exception;

/**
 * Thrown when an accessory ID doesn't exist.
 * Mirrors the original app's ArrayIndexOutOfBoundsException handling in
 * delItembtnActionPerformed(), now expressed as a proper HTTP 404.
 */
public class AccessoryNotFoundException extends RuntimeException {
    public AccessoryNotFoundException(Integer id) {
        super("No accessory found with ID " + id);
    }
}
