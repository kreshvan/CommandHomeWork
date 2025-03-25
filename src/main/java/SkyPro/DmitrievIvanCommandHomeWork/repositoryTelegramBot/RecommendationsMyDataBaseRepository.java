package SkyPro.DmitrievIvanCommandHomeWork.repositoryTelegramBot;
import SkyPro.DmitrievIvanCommandHomeWork.modelTelegramBot.RecommendationUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecommendationsMyDataBaseRepository extends JpaRepository<RecommendationUser,Integer> {
    RecommendationUser findRecommendationUserByName(String name);


}
