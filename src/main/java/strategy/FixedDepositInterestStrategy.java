
package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedDepositInterestStrategy implements InterestStrategy {

    private static final BigDecimal RATE =
            new BigDecimal("0.065");

    @Override
    public BigDecimal calculateInterest(BigDecimal balance, long days) {

        return balance
                .multiply(RATE)
                .multiply(BigDecimal.valueOf(days))
                .divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);
    }
}
