package fi.holti.converter.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class presenting one Open Document spreadsheet in certain home-grown format. <br/> <br/>
 * Each speadsheet row has timestamp and category-based expense value, for example: <br/>
 * timestamp | food | living  <br/>
 * 1.1.2016    100    500  <br/>
 * @author Timo
 *
 */
@ToString
@Getter@Setter
public class MonthlyFinancialSerie {
	private String sheetName;
	private Collection<DailyFinancialSerie> rows = new ArrayList<DailyFinancialSerie>();
	private BigDecimal monthlyIncome;
	private BigDecimal montlyExpenses;
	private BigDecimal monthlyProfit;

}
