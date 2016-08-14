package fi.holti.converter.batch.io;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import fi.holti.converter.entity.InfluxdbRow;

public class InfluxdbHttpApiWriter implements ItemWriter<List<InfluxdbRow>> {
	private Logger logger = LoggerFactory.getLogger(InfluxdbHttpApiWriter.class);

	public void write(List<? extends List<InfluxdbRow>> items) throws Exception {
		for (List<InfluxdbRow> list : items) {
			for (InfluxdbRow row : list) {
				logger.info(row.toString());
			}
		}
	}

}
