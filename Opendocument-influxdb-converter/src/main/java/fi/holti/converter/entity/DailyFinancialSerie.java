package fi.holti.converter.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(includeFieldNames = true)
@Getter@Setter
public class DailyFinancialSerie {
	private Date date;
	private Map<String, BigDecimal> expensesByCategory = new LinkedHashMap<String, BigDecimal>();
	private BigDecimal income;
}
	

