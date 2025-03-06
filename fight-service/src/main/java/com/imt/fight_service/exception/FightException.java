package com.imt.fight_service.exception;

/**
 * Exception spécifique aux erreurs liées aux combats.
 */
public class FightException extends RuntimeException {
    public FightException(String message) {
        super(message);
    }
}
