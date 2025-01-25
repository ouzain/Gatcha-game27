package com.imt.player_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imt.player_service.Model.Player;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class PlayerDto implements Serializable {
    
    @JsonProperty("username")
    protected String username;
    @JsonProperty("password")
    protected String password;
    @JsonProperty("level")
    protected int level;
    @JsonProperty("experience")
    protected int experience;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    /**
     * Default constructor for JSON Serializer
     */
    protected PlayerDto(){super();}

    private PlayerDto(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.level = builder.level;
        this.experience = builder.experience; 
    }
    
    public  boolean isValid(){
        return ObjectUtils.allNotNull(username, password);
    }



    public static final class Builder {
        private String username;
        private String password;
        private int level;
        private int experience;

        private Builder(){}
        public static Builder builder() {
            return new Builder();
        }
        

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }


        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }


        public PlayerDto build() {
            return new PlayerDto(this);
        }
    }

    public Player toPlayerEntity(){
        return Player.Builder.builder()
                .username(this.username)
                .level(this.level)
                .experience(this.experience)
                .build();
    }
}
