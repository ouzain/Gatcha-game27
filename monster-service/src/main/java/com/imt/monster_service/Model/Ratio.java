package com.imt.monster_service.Model;

import com.imt.monster_service.Dto.RatioDto;

public class Ratio {
    private String stat;
    private double percent;

    public Ratio() {
    }

    // Getters / Setters
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Convertit cette entité Ratio en DTO RatioDto
     */
    public RatioDto toRatioDto() {

        return RatioDto.Builder.builder()
                .stat(this.stat)
                .percent(this.percent)
                .build();
    }

    public static final class Builder {
        private String stat;
        private double percent;

        // Constructeur privé
        private Builder() {}

        // Méthode statique d'entrée du Builder
        public static Builder builder() {
            return new Builder();
        }

        public Builder stat(String stat) {
            this.stat = stat;
            return this;
        }

        public Builder percent(double percent) {
            this.percent = percent;
            return this;
        }

        public Ratio build() {
            Ratio ratio = new Ratio();
            ratio.setStat(this.stat);
            ratio.setPercent(this.percent);
            return ratio;
        }
    }
}
