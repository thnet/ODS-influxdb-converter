package fi.holti.converter.batch.io;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import fi.holti.converter.entity.DailyFinancialSerie;
import fi.holti.converter.entity.MonthlyFinancialSerie;
import fi.holti.converter.entity.MonthlyFinancialSerieCalculator;

public class MonthlyFinancialSerieCalculatorTest {

	@Test
	public void calculatesMonthlyIncome() {
		MonthlyFinancialSerie monthlySerie = new MonthlyFinancialSerie();

		DailyFinancialSerie dailyFinancialSerie1 = new DailyFinancialSerie();
		dailyFinancialSerie1.setDate(new Date());
		dailyFinancialSerie1.setIncome(BigDecimal.valueOf(100.5));
		monthlySerie.getRows().add(dailyFinancialSerie1);

		DailyFinancialSerie dailyFinancialSerie2 = new DailyFinancialSerie();
		dailyFinancialSerie2.setDate(new Date());
		dailyFinancialSerie2.setIncome(null);
		monthlySerie.getRows().add(dailyFinancialSerie2);

		DailyFinancialSerie dailyFinancialSerie3 = new DailyFinancialSerie();
		dailyFinancialSerie3.setDate(new Date());
		dailyFinancialSerie3.setIncome(BigDecimal.valueOf(100));
		monthlySerie.getRows().add(dailyFinancialSerie3);

		DailyFinancialSerie dailyFinancialSerie4 = new DailyFinancialSerie();
		dailyFinancialSerie4.setDate(new Date());
		dailyFinancialSerie4.setIncome(BigDecimal.valueOf(200.55));
		monthlySerie.getRows().add(dailyFinancialSerie4);

		BigDecimal monthlyIncome = MonthlyFinancialSerieCalculator.calculateMonthlyIncome(monthlySerie);

		BigDecimal expected = dailyFinancialSerie1.getIncome()
				.add(dailyFinancialSerie3.getIncome().add(dailyFinancialSerie4.getIncome()));

		assertEquals(expected, monthlyIncome);
	}

	@Test
	public void calculatesMonthlyExpenses() {
		MonthlyFinancialSerie monthlySerie = new MonthlyFinancialSerie();

		DailyFinancialSerie dailyFinancialSerie1 = new DailyFinancialSerie();
		dailyFinancialSerie1.setDate(new Date());
		dailyFinancialSerie1.setIncome(BigDecimal.valueOf(100.5));
		dailyFinancialSerie1.getExpensesByCategory().put("Food", BigDecimal.valueOf(20.5));
		dailyFinancialSerie1.getExpensesByCategory().put("Sports", BigDecimal.valueOf(100));
		dailyFinancialSerie1.getExpensesByCategory().put("Living", BigDecimal.valueOf(500.25));
		monthlySerie.getRows().add(dailyFinancialSerie1);

		DailyFinancialSerie dailyFinancialSerie2 = new DailyFinancialSerie();
		dailyFinancialSerie2.setDate(new Date());
		dailyFinancialSerie2.setIncome(BigDecimal.valueOf(100));
		monthlySerie.getRows().add(dailyFinancialSerie2);

		DailyFinancialSerie dailyFinancialSerie3 = new DailyFinancialSerie();
		dailyFinancialSerie3.setDate(new Date());
		dailyFinancialSerie3.setIncome(null);
		dailyFinancialSerie3.getExpensesByCategory().put("Medicine", BigDecimal.valueOf(30.5));
		monthlySerie.getRows().add(dailyFinancialSerie3);

		DailyFinancialSerie dailyFinancialSerie4 = new DailyFinancialSerie();
		dailyFinancialSerie4.setDate(new Date());
		dailyFinancialSerie4.getExpensesByCategory().put("Hobbies", BigDecimal.valueOf(100.5));
		dailyFinancialSerie4.getExpensesByCategory().put("Car", BigDecimal.valueOf(50));
		monthlySerie.getRows().add(dailyFinancialSerie4);

		BigDecimal monthlyTotalExpenses = MonthlyFinancialSerieCalculator.calculateMonthlyExpenses(monthlySerie);

		BigDecimal expected = BigDecimal.ZERO;
		for (BigDecimal value : dailyFinancialSerie1.getExpensesByCategory().values()) {
			expected = expected.add(value);
		}
		for (BigDecimal value : dailyFinancialSerie2.getExpensesByCategory().values()) {
			expected = expected.add(value);
		}
		for (BigDecimal value : dailyFinancialSerie3.getExpensesByCategory().values()) {
			expected = expected.add(value);
		}
		for (BigDecimal value : dailyFinancialSerie4.getExpensesByCategory().values()) {
			expected = expected.add(value);
		}

		assertEquals(expected, monthlyTotalExpenses);
	}
}
