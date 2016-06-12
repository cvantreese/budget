package budget;

import java.math.BigDecimal;

public class Money extends BigDecimal
{

	private static final long serialVersionUID = 7408255702433000505L;

	static BigDecimal ZERO = BigDecimal.ZERO;
	
	public Money(double val) {
		super(val);
	}

	public static BigDecimal valueOf(double val) {
		return BigDecimal.valueOf(val).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
