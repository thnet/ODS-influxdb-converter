package fi.holti.converter.batch.io;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import fi.holti.converter.entity.InfluxdbRow;
import fi.holti.converter.entity.ODSMonthlySheet;

public class ODSSpreadsheetToInfluxDBRowConverter implements ItemProcessor<ODSMonthlySheet, List<InfluxdbRow>>{

	public List<InfluxdbRow> process(ODSMonthlySheet item) throws Exception {
	
		return null;
	}

}
