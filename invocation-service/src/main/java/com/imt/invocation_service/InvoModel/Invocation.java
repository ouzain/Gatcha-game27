package com.imt.invocation_service.InvoModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "invocations")
public class Invocation {

    @Id
    private String id;
    private String playerUsername; // Référence vers le nom d'utilisateur du joueur
    private String baseMonsterId; // Référence vers l'ID du monstre de base
    private Integer createdMonsterId; // ID créé par l'API Monstres
    private LocalDateTime createdAt;
    private String status; // PENDING, FINISHED, ERROR, etc.
    private List<String> logs = new ArrayList<>();

    public void addLog(String log) {
        this.logs.add(log);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }



    public String getBaseMonsterId() {
        return baseMonsterId;
    }

    public void setBaseMonsterId(String baseMonsterId) {
        this.baseMonsterId = baseMonsterId;
    }

    public Integer getCreatedMonsterId() {
        return createdMonsterId;
    }

    public void setCreatedMonsterId(Integer createdMonsterId) {
        this.createdMonsterId = createdMonsterId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    /**
     * Builder pour la classe Invocation
     */
    public static class Builder {
        private String id;
        private String playerUsername;
        private String baseMonsterId;
        private Integer createdMonsterId;
        private LocalDateTime createdAt;
        private String status;
        private List<String> logs = new ArrayList<>();

        public Builder() {
            // Constructeur vide
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder playerUsername(String playerUsername) {
            this.playerUsername = playerUsername;
            return this;
        }

        public Builder baseMonsterId(String baseMonsterId) {
            this.baseMonsterId = baseMonsterId;
            return this;
        }

        public Builder createdMonsterId(Integer createdMonsterId) {
            this.createdMonsterId = createdMonsterId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder logs(List<String> logs) {
            this.logs = logs;
            return this;
        }

        /**
         * Construit et retourne une instance de Invocation
         */
        public Invocation build() {
            Invocation invocation = new Invocation();
            invocation.setId(this.id);
            invocation.setPlayerUsername(this.playerUsername);
            invocation.setBaseMonsterId(this.baseMonsterId);
            invocation.setCreatedMonsterId(this.createdMonsterId);
            invocation.setCreatedAt(this.createdAt);
            invocation.setStatus(this.status);
            invocation.setLogs(this.logs);
            return invocation;
        }
    }
}
