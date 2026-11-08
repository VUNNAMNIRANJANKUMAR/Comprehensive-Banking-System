
package strategy;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import model.Account;
import model.AccountType;
import strategy.CurrentInterestStrategy;
import strategy.FixedDepositInterestStrategy;
import strategy.InterestStrategy;
import strategy.SavingsInterestStrategy;

public class InterestService {

    private final Map<AccountType, InterestStrategy> strategies =
            new EnumMap<>(AccountType.class);

    public InterestService() {
        strategies.put(AccountType.SAVINGS,
                new SavingsInterestStrategy());

        strategies.put(AccountType.CURRENT,
                new CurrentInterestStrategy());

        strategies.put(AccountType.FIXED_DEPOSIT,
                new FixedDepositInterestStrategy());
    }

    public BigDecimal calculateInterest(Account account, long days) {

        Objects.requireNonNull(account, "Account is required");

        if (days < 0) {
            throw new IllegalArgumentException(
                    "Days cannot be negative");
        }

        InterestStrategy strategy =
                strategies.get(account.getAccountType());

        return strategy.calculateInterest(
                account.getBalance(), days);
    }
}
