package com.imt.invocation_service.InvoModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "invocations")
public class Invocation {

    @Id
    private Integer id;
    private String playerId;
    private String baseMonsterId; // Référence vers l'ID du monstre de base
    private Integer createdMonsterId; // ID créé par l'API Monstres
    private LocalDateTime createdAt;
    private String status; // PENDING, FINISHED, ERROR, etc.
    private List<String> logs = new ArrayList<>();

    // Constructors, getters, setters

    public void addLog(String log) {
        this.logs.add(log);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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
}
