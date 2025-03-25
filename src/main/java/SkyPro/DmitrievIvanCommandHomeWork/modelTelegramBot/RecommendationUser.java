package SkyPro.DmitrievIvanCommandHomeWork.modelTelegramBot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.util.Objects;

@Entity
public class RecommendationUser {
    @Id
    private long id;
    private String name;
    private String recommendation;

    public RecommendationUser(int id, String recommendation, String name) {
        this.id = id;
        this.recommendation = recommendation;
        this.name = name;
    }
    public RecommendationUser(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationUser that = (RecommendationUser) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(recommendation, that.recommendation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, recommendation);
    }
}
