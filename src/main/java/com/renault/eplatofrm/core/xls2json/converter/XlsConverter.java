package com.renault.eplatofrm.core.xls2json.converter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.renault.eplatofrm.core.xls2json.json.JsonObject;
import com.renault.eplatofrm.core.xls2json.json.JsonObjectFactory;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: phoenix
 * Date: 28/05/12
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
public class XlsConverter {

    private final static Logger logger = LoggerFactory.getLogger(XlsConverter.class);

    public XlsConverter() {
    }

    public Map<String, List<JsonObject>> convert(String xlsFileName) {
        Map<String, List<JsonObject>> objects = new HashMap<String, List<JsonObject>>();
        logger.debug("Converting "+xlsFileName);
        try {
            Workbook wb = new HSSFWorkbook(new FileInputStream(xlsFileName));
            logger.info("Sheets: "+wb.getNumberOfSheets());
            for (int i =0; i < wb.getNumberOfSheets() -1; i++) {
                Map<Integer, String> headers = new HashMap<Integer, String> ();
                Sheet sheet = wb.getSheetAt(i);
                String index = sheet.getSheetName();
                logger.info("Processing "+index+"...");
                logger.info("Rows : "+sheet.getLastRowNum());
                List<JsonObject> indexObjects = new ArrayList<JsonObject>();
                for (int rowId=0; rowId < sheet.getLastRowNum(); rowId++) {
                    Row row = sheet.getRow(rowId);
                    int cells = row.getLastCellNum();
                    Map<String, String> data = new HashMap<String, String>();
                    for (int cellId=0; cellId < cells; cellId++) {
                        Cell cell = row.getCell(cellId);
                        if (cell == null) {
                            break;
                        }
                        int cellType = cell.getCellType();
                        String value = null;
                        if (cellType == Cell.CELL_TYPE_NUMERIC) {
                            value = Integer.toString((int)row.getCell(cellId).getNumericCellValue());
                            data.put(headers.get(cellId), value);
                        } else if (cellType == Cell.CELL_TYPE_STRING) {
                            value = cell.getStringCellValue();
                            if (rowId==0) {
                                headers.put(cellId, value);
                            } else {
                                data.put(headers.get(cellId), value);
                            }
                        }
                    }
                    JsonObject json = JsonObjectFactory.createJsonObject(data, index);
                    if (json != null) {
                        logger.info(" JSON : "+json.toJson());
                        indexObjects.add(json);
                    }
                }
                objects.put(index, indexObjects);
            }

        } catch (IOException e) {
            logger.error("Could not read file "+xlsFileName, e);
        }
        return objects;
    }

}
