package fi.holti.converter.entity;

import java.math.BigDecimal;

public class MonthlyFinancialSerieCalculator {

	public static BigDecimal calculateMonthlyIncome(MonthlyFinancialSerie monthlySerie) {

		BigDecimal monhtlyIncome = BigDecimal.ZERO;
		for (DailyFinancialSerie dailyFinancialSerie : monthlySerie.getRows()) {
			if (dailyFinancialSerie.getIncome() != null) {
				monhtlyIncome = monhtlyIncome.add(dailyFinancialSerie.getIncome());
			}
		}
		return monhtlyIncome;
	}

	public static BigDecimal calculateMonthlyExpenses(MonthlyFinancialSerie monthlySerie) {
		BigDecimal monthlyExpenses = BigDecimal.ZERO;

		for (DailyFinancialSerie dailyFinancialSerie : monthlySerie.getRows()) {
			for (BigDecimal value : dailyFinancialSerie.getExpensesByCategory().values()) {
				monthlyExpenses = monthlyExpenses.add(value);
			}
		}
		return monthlyExpenses;
	}

}
