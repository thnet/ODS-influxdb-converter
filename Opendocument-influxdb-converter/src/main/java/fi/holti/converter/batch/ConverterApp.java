package fi.holti.converter.batch;

import org.springframework.boot.SpringApplication;

import fi.holti.converter.batch.spring.BatchConfiguration;

public class ConverterApp {
	  public static void main(String [] args) {
		    System.exit(SpringApplication.exit(SpringApplication.run(
		        BatchConfiguration.class, args)));
		  }
}
