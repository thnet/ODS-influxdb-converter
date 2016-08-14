package fi.holti.converter.parser;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.holti.converter.entity.ODSMonthlySheet;
import fi.holti.converter.entity.ODSMonthlySheetDailyRow;

/**
 * Parses one sheet from ODS file.
 * 
 * @author Timo
 *
 */
public class ODSFileSheetParser {
	private static final int MAX_AMOUNT_OF_CATEGORIES = 19;
	private static final int LAST_DATA_ROW = 34;
	private int incomeColumn = 0;
	public Logger logger = LoggerFactory.getLogger(ODSFileSheetParser.class);
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public ODSMonthlySheet parseSheet(Sheet sheet) {
		Map<Integer, String> categoriesByColumnNumber = new TreeMap<Integer, String>();

		int currentRow = getFirstDataRow(sheet);
		Integer column = 2;
		parseCategoriesByColunmNumber(sheet, categoriesByColumnNumber, currentRow, column);

		logger.info("Categories found for monthly sheet =" + categoriesByColumnNumber);

		int firstDataRow = 3;
		currentRow = firstDataRow;

		ODSMonthlySheet monthlySheet = new ODSMonthlySheet();

		while (currentRow < LAST_DATA_ROW) {
			Integer dataColumn = 1;
			ODSMonthlySheetDailyRow dailyRow = new ODSMonthlySheetDailyRow();

			parseDailyCategoryValues(categoriesByColumnNumber, sheet, currentRow, dataColumn, dailyRow);
			parseIncomeValues(sheet, currentRow, dailyRow);
			monthlySheet.getRows().add(dailyRow);
			currentRow++;
		}
		return monthlySheet;
	}

	private void parseIncomeValues(Sheet sheet, int currentRow, ODSMonthlySheetDailyRow dailyRow) {
		String text = sheet.getCellAt(incomeColumn, currentRow).getTextValue();
		if (hasValues(text)) {
			BigDecimal incomeValue = parseBigdecimal(text);
			dailyRow.setIncome(incomeValue);
		}

	}

	private void parseCategoriesByColunmNumber(Sheet sheet, Map<Integer, String> categoriesByColumnNumber,
			int currentRow, Integer column) {
		while (thereMightBeCategoryValue(column)) {
			String text = sheet.getCellAt(column, currentRow).getTextValue();
			if (hasValues(text)) {
				categoriesByColumnNumber.put(column, text);
			} else {
				// IncomeColumn is always after empty column
				incomeColumn = column + 1;

				// Last category reached, return.
				break;
			}
			column++;
		}
	}

	private boolean thereMightBeCategoryValue(Integer column) {
		return column <= MAX_AMOUNT_OF_CATEGORIES + 2;
	}

	private void parseDailyCategoryValues(Map<Integer, String> categoriesByColumnNumber, Sheet sheet, int currentRow,
			Integer dataColumn, ODSMonthlySheetDailyRow dailyRow) {

		for (int i = dataColumn; dataColumn < categoriesByColumnNumber.size(); i++) {

			if (i == 1) {
				String timestamp = getCellValue(sheet, currentRow, i);
				Date date = null;
				try {

					date = simpleDateFormat.parse(timestamp);
				} catch (java.text.ParseException e) {
					logger.debug("Reached last timestamp row, returning. Row=" + currentRow + " dataColum=" + i);
					return;
				}
				dailyRow.setDate(date);
				i++;
			} else {
				String value = "";
				try {
					value = getCellValue(sheet, currentRow, i);
				} catch (IllegalArgumentException iae) {
					logger.error("Invalid cell, column" + i + " row=" + currentRow);
					throw iae;
				}
				if (hasValues(value)) {
					BigDecimal decimalValue = parseBigdecimal(value);
					if (!decimalValue.equals(BigDecimal.ZERO)) {
						String category = categoriesByColumnNumber.get(i);
						dailyRow.getExpensesByCategory().put(category, decimalValue);
					}
				}
				dataColumn++;
			}
		}
	}

	private String getCellValue(Sheet sheet, int currentRow, int i) {
		return sheet.getCellAt(i, currentRow).getTextValue();
	}

	private boolean hasValues(String value) {
		return value != null && !value.isEmpty() && !value.trim().isEmpty();
	}

	private BigDecimal parseBigdecimal(String value) {
		try {
			return new BigDecimal(value.replace(",", "."));
		} catch (NumberFormatException e) {
			logger.debug(e + " with value=" + value);
			return BigDecimal.ZERO;
		}
	}

	private int getFirstDataRow(Sheet sheet) {
		int firstRowHavingData = 2;
		return firstRowHavingData;
	}
}