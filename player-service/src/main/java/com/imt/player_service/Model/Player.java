package com.imt.player_service.Model;


import com.imt.player_service.Dto.PlayerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;



@NoArgsConstructor(force = true)
@Document(collection = "users")

public class Player {

    @Id
    private ObjectId id;
    @NotBlank
    private final String username;
    private String token;
    private final int level;
    private final int experience;
    private List<Integer> monsterList;


    /**
     * Constructeur que Spring Data utilisera pour hydrater l'entité depuis la base.
     * Les noms et l'ordre des paramètres doivent correspondre aux champs.
     */

    public Player(String username,
                  String token,
                  int level,
                  int experience,
                  List<Integer> monsterList) {
        this.username = username;
        this.token = token;
        this.level = level;
        this.experience = experience;
        this.monsterList = monsterList;
    }





    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public  String getToken(){return token;}

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<Integer> getMonsterList(){return monsterList;}

    public void addMonster(Integer monsterId){
        this.monsterList.add(monsterId);
    }


    private Player(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.token =builder.token;
        this.level = builder.level;
        this.experience = builder.experience;
        this.monsterList=builder.monsterList;
    }



    public static final class Builder {
        private ObjectId id;
        private String username;
        private String token;
        private int level;
        private int experience;
        private List<Integer> monsterList;

        private Builder(){}
        public static Builder builder() {
            return new Builder();
        }

        public Builder id(ObjectId id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }


        public Builder token(String token) {
            this.token = token;
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
            return  this;
        }


        public Player build() {
            return new Player(this);
        }
    }


    public PlayerDto toPlayerDtoEntity(){
        return PlayerDto.Builder.builder()
                .username(this.username)
                .token(this.token)
                .level(this.level)
                .experience(this.experience)
                .monsterList(this.monsterList)
                .build();
    }
}
