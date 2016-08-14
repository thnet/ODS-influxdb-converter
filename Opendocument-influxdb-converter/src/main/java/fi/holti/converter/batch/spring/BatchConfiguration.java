package fi.holti.converter.batch.spring;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.holti.converter.batch.io.InfluxdbHttpApiWriter;
import fi.holti.converter.batch.io.MonthlyFinancialSerieToInfluxDbRowProcessor;
import fi.holti.converter.batch.io.ODSReader;
import fi.holti.converter.entity.InfluxdbRow;
import fi.holti.converter.entity.MonthlyFinancialSerie;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<MonthlyFinancialSerie, List<InfluxdbRow>> chunk(10).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public Job job(Step step1) throws Exception {
		return jobBuilderFactory.get("parseODSSheetsJob").incrementer(new RunIdIncrementer()).flow(step1()).end()
				.build();
	}

	@Bean
	public ODSReader reader() {
		return new ODSReader();
	}

	@Bean
	public InfluxdbHttpApiWriter writer() {
		return new InfluxdbHttpApiWriter();
	}

	@Bean
	public MonthlyFinancialSerieToInfluxDbRowProcessor processor() {
		return new MonthlyFinancialSerieToInfluxDbRowProcessor();
	}
}
