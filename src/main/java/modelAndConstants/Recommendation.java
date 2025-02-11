package modelAndConstants;

import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    private String name;
    private UUID id;
    private String text;

    public Recommendation(String name, UUID id,String text) {
        this.name = name;
        this.text = text;
        this.id = id;
    }
    public Recommendation (){      //УТОЧНИТЬ
    }
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public UUID getId() {

        return id;
    }
    public void setId(UUID id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, text);
    }













}
