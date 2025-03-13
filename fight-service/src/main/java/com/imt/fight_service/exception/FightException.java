package com.imt.fight_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception personnalisée pour gérer les erreurs spécifiques aux combats.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FightException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Constructeur prenant un message d'erreur.
     *
     * @param message Message décrivant l'erreur.
     */
    public FightException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    /**
     * Constructeur prenant un message et un statut HTTP personnalisé.
     *
     * @param message Message décrivant l'erreur.
     * @param status  Statut HTTP associé à l'erreur.
     */
    public FightException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Constructeur prenant un message, un statut HTTP et une cause.
     *
     * @param message Message décrivant l'erreur.
     * @param status  Statut HTTP associé à l'erreur.
     * @param cause   Exception d'origine.
     */
    public FightException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Retourne le statut HTTP associé à cette exception.
     *
     * @return HttpStatus
     */
    public HttpStatus getStatus() {
        return status;
    }
}
