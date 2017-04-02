package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class ExcelWriter {

    private Workbook dataWorkbook;
    private Sheet userDataSheet;
    private Logger actionDetailLogger;
    private String filePath;

    public ExcelWriter(Logger actionDetailLogger, String filePath){
        this.actionDetailLogger = actionDetailLogger;
        this.filePath = filePath;
        dataWorkbook = new XSSFWorkbook();
        userDataSheet = dataWorkbook.createSheet("UserData");
    }

    public void writeToWorkBook(String[] userData, int verifiedUserDataIndex) {
        Row row = userDataSheet.createRow(verifiedUserDataIndex);
        int cellIndex = 0;
        row.createCell(cellIndex++).setCellValue(userData[0]);
        row.createCell(cellIndex++).setCellValue(userData[1].substring(userData[1].length() - 4));
        row.createCell(cellIndex++).setCellValue(userData[2].substring(0,10));
        row.createCell(cellIndex++).setCellValue(userData[3]);
        row.createCell(cellIndex++).setCellValue(userData[4]);
        row.createCell(cellIndex++).setCellValue(userData[5]);
        row.createCell(cellIndex).setCellValue(userData[6]);
    }

    public  void writeToExcel(int verifiedUserDataIndex) {
        int index = verifiedUserDataIndex;
        try (FileOutputStream fos = new FileOutputStream(filePath);) {
            if (verifiedUserDataIndex == 0) {
                Row row = userDataSheet.createRow(index++);
                row.createCell(0).setCellValue("NO");
                row.createCell(1).setCellValue("WORKING");
                row.createCell(2).setCellValue("DATA");
                row.createCell(3).setCellValue("\"We're Sorry\"");
                actionDetailLogger.info("NO WORKING DATA FOUND");
            }
            Row row = userDataSheet.createRow(index);
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MMM_dd_hh");
            row.createCell(0).setCellValue(dateTime.format(format));
            dataWorkbook.write(fos);
            actionDetailLogger.info("EXCEL FILE: " + filePath + " is successfully written");
        } catch (FileNotFoundException e) {
            actionDetailLogger.error("File not found", e);
        } catch (IOException e) {
            actionDetailLogger.error("IOException", e);
        }

}}
