package SkyPro.DmitrievIvanCommandHomeWork.modelTelegramBot;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.util.Objects;

@Entity
public class Command {
    @Id
    private long id;
    private String command;
    private String textCommand;

    public Command(int id, String textCommand, String command) {
        this.id = id;
        this.textCommand = textCommand;
        this.command = command;
    }
    public Command(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTextCommand() {
        return textCommand;
    }

    public void setTextCommand(String textCommand) {
        this.textCommand = textCommand;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command1 = (Command) o;
        return id == command1.id && Objects.equals(command, command1.command) && Objects.equals(textCommand, command1.textCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, command, textCommand);
    }
}
