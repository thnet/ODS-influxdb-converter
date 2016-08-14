package fi.holti.converter.batch.io;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import fi.holti.converter.entity.DailyFinancialSerie;
import fi.holti.converter.entity.InfluxdbRow;
import fi.holti.converter.entity.MonthlyFinancialSerie;
import fi.holti.converter.entity.MonthlyFinancialSerieCalculator;

public class MonthlyFinancialSerieToInfluxDbRowProcessor
		implements ItemProcessor<MonthlyFinancialSerie, List<InfluxdbRow>> {
	private Logger logger = LoggerFactory.getLogger(MonthlyFinancialSerieToInfluxDbRowProcessor.class);

	public List<InfluxdbRow> process(MonthlyFinancialSerie item) throws Exception {

		List<InfluxdbRow> influxdbRows = new ArrayList<InfluxdbRow>();
		long lastItemNanosSinceEpoch = 0;
		Date serieLastDate = new Date();
		for (DailyFinancialSerie serie : item.getRows()) {

			if (serie.getDate() == null) {
				logger.warn("Skipping daily serie with null date, serie=" + serie);
				throw new IllegalArgumentException();
				// continue;
			}

			serieLastDate = serie.getDate();
			long nanos = TimeUnit.NANOSECONDS.convert(serieLastDate.getTime(), TimeUnit.MILLISECONDS);
			lastItemNanosSinceEpoch = nanos;

			for (String key : serie.getExpensesByCategory().keySet()) {
				InfluxdbRow influxdbRow = new InfluxdbRow();
				BigDecimal value = serie.getExpensesByCategory().get(key);
				influxdbRow.setSerieName("HourlyFinancialSerie");
				influxdbRow.setValue(value);
				influxdbRow.getTagValues().put("category", key);
				influxdbRow.setDate(serie.getDate());
				influxdbRow.setNanoSecondsSinceEpoch(nanos);

				influxdbRows.add(influxdbRow);
			}

		}

		BigDecimal monthlyExpensesTotal = MonthlyFinancialSerieCalculator.calculateMonthlyExpenses(item);
		InfluxdbRow influxExpensesSerie = new InfluxdbRow();
		influxExpensesSerie.setSerieName("MontlyExpensesSerie");
		influxExpensesSerie.setValue(monthlyExpensesTotal);
		influxExpensesSerie.setDate(serieLastDate);

		influxExpensesSerie.setNanoSecondsSinceEpoch(lastItemNanosSinceEpoch);

		influxdbRows.add(influxExpensesSerie);

		BigDecimal montlyIncomeTotal = MonthlyFinancialSerieCalculator.calculateMonthlyIncome(item);

		InfluxdbRow influxIncomeSerie = new InfluxdbRow();
		influxIncomeSerie.setSerieName("MontlyIncomeSerie");
		influxIncomeSerie.setValue(montlyIncomeTotal);
		influxIncomeSerie.setDate(serieLastDate);
		influxIncomeSerie.setNanoSecondsSinceEpoch(lastItemNanosSinceEpoch);

		influxdbRows.add(influxIncomeSerie);

		return influxdbRows;
	}

}
