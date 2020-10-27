package cm.belrose.jwtspringsecurity.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cm.belrose.jwtspringsecurity.entities.Tutorial;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provide two importants functions
 */
public class ExcelUtils {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Id", "Title", "Description", "Published"};
    static String SHEET = "Tutorial";

    /**
     * Cette methode verifie si un fichier est au format excel ou non
     * @param file
     * @return
     */
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**
     * Cette methode lit le strem d'entrée du fichier et retourne une liste de tutorials
     * bref converti un fichier excel en un objet
     * @param is
     * @return
     */
    public static List<Tutorial> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Tutorial> tutorials = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Tutorial tutorial = new Tutorial();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            tutorial.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            tutorial.setTitle(currentCell.getStringCellValue());
                            break;
                        case 2:
                            tutorial.setDescription(currentCell.getStringCellValue());
                            break;
                        case 3:
                            tutorial.setPublished(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                tutorials.add(tutorial);
            }
            workbook.close();
            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    /**
     * Cette methode lit une liste de tutorials en entrée et retourne un fichier excel
     * bref converti un objet en un fichier excel
     * @param tutorials
     * @return
     */
    public static ByteArrayInputStream tutorialsToExcel(List<Tutorial> tutorials) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            CreationHelper creationHelper=workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet(SHEET);
            sheet.autoSizeColumn(ExcelUtils.HEADERs.length);

            Font headerFont=workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle cellStyle=workbook.createCellStyle();
            cellStyle.setFont(headerFont);

            // Row for header
            Row headerRow = sheet.createRow(0);

            //header
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
                cell.setCellStyle(cellStyle);
            }

            CellStyle cellStyle1=workbook.createCellStyle();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;
            for (Tutorial tutorial : tutorials) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(tutorial.getId());
                row.createCell(1).setCellValue(tutorial.getTitle());
                row.createCell(2).setCellValue(tutorial.getDescription());
                row.createCell(3).setCellValue(tutorial.isPublished());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}

