package fi.holti.converter.batch.io;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import fi.holti.converter.entity.DailyFinancialSerie;
import fi.holti.converter.entity.MonthlyFinancialSerie;

public class ODSMonthlysheetToInfluxDBRowConverterTest {

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

	}
}
