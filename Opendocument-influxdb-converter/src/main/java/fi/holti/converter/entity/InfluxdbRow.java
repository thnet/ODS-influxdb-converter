package fi.holti.converter.entity;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class InfluxdbRow {
	private String serieName;
	private Map<String, Float> tagValues;
	private long nanoSecondsSinceEpoch;

}
