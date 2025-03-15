package com.imt.fight_service.model;


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
