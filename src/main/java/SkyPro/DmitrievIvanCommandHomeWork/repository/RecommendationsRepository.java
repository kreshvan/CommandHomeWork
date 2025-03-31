package SkyPro.DmitrievIvanCommandHomeWork.repository;

import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.ProductTypeConstants;
import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<String> findUserIdByUserName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT id From users WHERE username=?",
                String.class, name));


    }


    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1", Integer.class, user);
        return result != null ? result : 0;
    }

    public boolean checkTransactionProductUser(UUID userID, ProductTypeConstants productTypeConstants) {
        return jdbcTemplate.queryForObject(
                "select exists(select 1 from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ?)",
                Boolean.class,
                userID, productTypeConstants.name());// цель метода , проверить совершал ли USER
        // операции с продуктом этого типа

    }

    public boolean checkNotTransactionProductUser(UUID userID, ProductTypeConstants productTypeConstants) {
        return (jdbcTemplate.queryForObject(
                "select not exists(select 1 from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ?)",
                Boolean.class,//указываю в каком классе будет работать (Boolean)
                userID, productTypeConstants.name()));
    }

    public int getTransactionDepositSum(UUID userID, ProductTypeConstants productTypeConstants) {
        return jdbcTemplate.queryForObject(
                "select coalesce(sum(AMOUNT),0) from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ? and t.type = 'DEPOSIT'",
                Integer.class,//указываю в каком классе будет работать (INTEGER)
                userID, productTypeConstants.name());
    }

    public int getTransactionWithdrawSum(UUID userID, ProductTypeConstants productTypeConstants) {
        return jdbcTemplate.queryForObject(
                "select coalesce(sum(AMOUNT),0) from TRANSACTIONS t join PRODUCT p on t.PRODUCT_ID = p.ID" +
                        "where t. USER_ID = ? and p.type = ? and t.type = 'WITHDRAW'",
                Integer.class,
                userID, productTypeConstants.name());
    }

}

