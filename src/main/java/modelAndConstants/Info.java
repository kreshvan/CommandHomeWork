package modelAndConstants;

public class  Info {
    private String name;
    private String id;
    private String string;

    public Info(String name, String string, String id) {
        this.name = name;
        this.string = string;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}