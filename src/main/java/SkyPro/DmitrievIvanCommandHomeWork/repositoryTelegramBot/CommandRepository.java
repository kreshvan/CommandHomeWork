package SkyPro.DmitrievIvanCommandHomeWork.repositoryTelegramBot;


import SkyPro.DmitrievIvanCommandHomeWork.modelTelegramBot.Command;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommandRepository extends JpaRepository<Command,Integer> {
    Command findTextByCommand(String command);
}
