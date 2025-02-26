package existsOffers;

import modelAndConstants.ProductTypeConstants;
import modelAndConstants.Recommendation;
import org.springframework.stereotype.Component;
import repository.RecommendationsRepository;
import RecommendationService.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

import static modelAndConstants.RecommendationConstants.*;

@Component
public class GetIfExistInvest500 implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;


    public GetIfExistInvest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId)   {
        boolean debitUser = recommendationsRepository.checkTransactionProductUser(userId, ProductTypeConstants.DEBIT);
        boolean notInvestUser = recommendationsRepository.checkNotTransactionProductUser(userId, ProductTypeConstants.INVEST);
        boolean sumReplenishMore1000 = recommendationsRepository.getTransactionDepositSum(
                userId, ProductTypeConstants.SAVING) > 1000;


        if (debitUser & notInvestUser & sumReplenishMore1000) {
            return Optional.of(new Recommendation(
                    INVEST_500_NAME.getValue(),
                    UUID.fromString(INVEST_500_ID.getValue()),
                    //(UUID.fromString) преобразует строку в объект типа UUID БЫЛА String - стала UUID-объект
                    INVEST_500_TEXT.getValue()));
        }
        return Optional.empty();// ЕСЛИ ХОТЬ ОДНО ИЗ УСЛОВИЙ НЕ БУДЕТ ВЫПОЛНИНО - ТО ПУСТОЕ ЗНАЧ.(Optional.empty)
    }


}