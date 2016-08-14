package fi.holti.converter.batch.io;

import java.io.File;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import fi.holti.converter.entity.ODSMonthlySheet;
import fi.holti.converter.parser.ODSFileSheetParser;

/**
 * Reader for reading Open Document Spreadsheets. Each read call reads one
 * sheet.
 * 
 * @author Timo
 *
 */
public class ODSReader implements ItemReader<ODSMonthlySheet> {
	private File file;
	// TODO: extract hardcoding.
	private String fileName = "D:/spreadsheet_test.ods";
	private static final Logger logger = LoggerFactory.getLogger(ODSReader.class);
	private int currentSheetMonth = 1;
	private int currentSheetYear = 2011;
	private ODSFileSheetParser odsFilerParser = new ODSFileSheetParser();

	public ODSMonthlySheet read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		openFile();

		String name = parseSheetName();

		Sheet sheet = SpreadSheet.createFromFile(file).getSheet(name);
		logger.debug("Loaded sheet: " + sheet.getName());

		ODSMonthlySheet monthlySheet = odsFilerParser.parseSheet(sheet);

		monthlySheet.setSheetName(name);
		if (currentSheetMonth < 12) {
			logger.info("Month sheet parsed for month" + currentSheetMonth + ", going to next month sheet");
			currentSheetMonth++;
		} else {
			currentSheetMonth = 1;
			currentSheetYear = currentSheetYear + 1;
			logger.info("Going to next year sheet, nextYear=" + currentSheetYear);
		}

		logger.info("MonthlySheet parsed" + monthlySheet);

		return monthlySheet;
	}

	private String parseSheetName() {
		return currentSheetMonth + "_" + currentSheetYear;
	}

	private void openFile() {
		if (file == null) {
			file = new File(fileName);
		}
	}

}
