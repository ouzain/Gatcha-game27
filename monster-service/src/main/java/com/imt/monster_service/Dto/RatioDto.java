package com.imt.monster_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imt.monster_service.Model.Ratio;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatioDto implements Serializable {

    @JsonProperty("stat")
    protected String stat;

    @JsonProperty("percent")
    protected double percent;

    protected RatioDto() {
        super();
    }

    /**
     * Constructeur privé qui prend le Builder en paramètre
     */
    private RatioDto(Builder builder) {
        this.stat = builder.stat;
        this.percent = builder.percent;
    }
    public String getStat() {
        return stat;
    }

    public double getPercent() {
        return percent;
    }
    public boolean isValid() {
        return ObjectUtils.allNotNull(stat);
    }

    /**
     * Convertit ce DTO en entité Ratio
     */
    public Ratio toRatioEntity() {
        return Ratio.Builder
                .builder()
                .stat(this.stat)
                .percent(this.percent)
                .build();
    }

    public static final class Builder {
        private String stat;
        private double percent;

        private Builder() {}

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

        public RatioDto build() {
            return new RatioDto(this);
        }
    }
}
