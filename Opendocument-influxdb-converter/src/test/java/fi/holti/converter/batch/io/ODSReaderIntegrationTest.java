package fi.holti.converter.batch.io;

import org.junit.Test;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ODSReaderIntegrationTest {

	@Test
	public void parseTestFile()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {

		// No detailed unit test in this case, only integration test that parses
		// test data file and logs everything necessary.

		ODSReader odsReader = new ODSReader();
		for (int i = 0; i < 12 * 5; i++) {
			odsReader.read();
		}

	}
}
