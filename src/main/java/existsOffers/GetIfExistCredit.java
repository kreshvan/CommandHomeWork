package existsOffers;

import modelAndConstants.ProductTypeConstants;
import modelAndConstants.Recommendation;
import repository.RecommendationsRepository;
import RecommendationService.RecommendationRuleSet;

import java.util.Optional;
import java.util.UUID;

import static modelAndConstants.RecommendationConstants.*;

public class GetIfExistCredit implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    public GetIfExistCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {
        boolean creditUser = recommendationsRepository.checkNotTransactionProductUser(userId, ProductTypeConstants.CREDIT);
        boolean transactionDebitUser = recommendationsRepository.getTransactionDepositSum(userId, ProductTypeConstants.DEBIT) >
                recommendationsRepository.getTransactionWithdrawSum(userId, ProductTypeConstants.DEBIT);
        boolean sumBayDebitUser = recommendationsRepository.getTransactionDepositSum(
                userId, ProductTypeConstants.DEBIT) > 100000;

        if (creditUser && transactionDebitUser && sumBayDebitUser) {
            return Optional.of(new Recommendation(CREDIT_NAME.getValue(),
                    UUID.fromString(CREDIT_ID.getValue()),
                    CREDIT_TEXT.getValue()));
        }


        return Optional.empty();
    }
}
