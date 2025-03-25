package SkyPro.DmitrievIvanCommandHomeWork.existsOffers;

import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.ProductTypeConstants;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import org.springframework.stereotype.Component;
import SkyPro.DmitrievIvanCommandHomeWork.repository.RecommendationsRepository;
import SkyPro.DmitrievIvanCommandHomeWork.RecommendationService.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

import static SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.RecommendationConstants.*;

@Component
public class GetIfExistTopSaving implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    public GetIfExistTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public Optional<Recommendation> getRecommendation(UUID userId)   {
        boolean debitUser = recommendationsRepository.checkTransactionProductUser(userId, ProductTypeConstants.DEBIT);

        boolean sumTransactionByDebitMore = recommendationsRepository.getTransactionDepositSum(
                userId, ProductTypeConstants.DEBIT) >= 50000;

        boolean sumTransactionByInvestMore = recommendationsRepository.getTransactionDepositSum(
                userId, ProductTypeConstants.SAVING) >= 50000;

        boolean sumBayByDebit = recommendationsRepository.getTransactionWithdrawSum(
                userId, ProductTypeConstants.DEBIT) < recommendationsRepository.getTransactionDepositSum(
                userId, ProductTypeConstants.DEBIT);




        if ((debitUser&&sumTransactionByDebitMore&&sumBayByDebit)||
                (debitUser&&sumTransactionByInvestMore&&sumBayByDebit)){
return Optional.of(new Recommendation(TOP_SAVING_NAME.getValue(),
        UUID.fromString(TOP_SAVING_ID.getValue()),
        TOP_SAVING_TEXT.getValue()));
        }

        return Optional.empty();
    }


}
