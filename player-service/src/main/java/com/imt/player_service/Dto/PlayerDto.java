package com.imt.player_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imt.player_service.Model.Player;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class PlayerDto implements Serializable {
    
    @JsonProperty("username")
    protected String username;
    @JsonProperty("password")
    protected String password;
    @JsonProperty("token")
    protected  String token;
    @JsonProperty("level")
    protected int level;
    @JsonProperty("experience")
    protected int experience;
    @JsonProperty("monster_list")
    protected List<Integer> monsterList;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<Integer> getMonsterList(){return monsterList;}

    /**
     * Default constructor for JSON Serializer
     */
    protected PlayerDto(){super();}

    private PlayerDto(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.token = builder.token;
        this.level = builder.level;
        this.experience = builder.experience;
        this.monsterList= builder.monsterList;
    }
    
    public  boolean isValid(){
        return ObjectUtils.allNotNull(username, password);
    }



    public static final class Builder {
        private String username;
        private String password;
        private String token;
        private int level;
        private int experience;
        private List<Integer> monsterList;

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

        public Builder token(String token){
            this.token=token;
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
        public Builder monsterList(List<Integer> monsterList){
            this.monsterList=monsterList;
            return this;
        }


        public PlayerDto build() {
            return new PlayerDto(this);
        }
    }

    public Player toPlayerEntity(){
        return Player.Builder.builder()
                .username(this.username)
                .token(this.token)
                .level(this.level)
                .experience(this.experience)
                .monsterList(this.monsterList)
                .build();
    }



}
