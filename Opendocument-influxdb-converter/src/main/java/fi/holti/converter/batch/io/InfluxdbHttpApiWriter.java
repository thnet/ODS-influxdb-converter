package fi.holti.converter.batch.io;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import fi.holti.converter.entity.InfluxdbRow;

public class InfluxdbHttpApiWriter implements ItemWriter<List<InfluxdbRow>>{

	public void write(List<? extends List<InfluxdbRow>> items) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
