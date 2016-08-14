package fi.holti.converter.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class InfluxdbRow {
	private String serieName;
	private BigDecimal value;
	private Map<String, String> tagValues = new LinkedHashMap<String, String>();
	private Date date;
	private long nanoSecondsSinceEpoch;

}
