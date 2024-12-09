package exporter;

import annotations.DataAttribute;
import annotations.DataClass;
import exceptions.DataExportException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class ExcelExporter implements DataExporter {

    @Override
    public <T> void exportData(T[] data, OutputStream outputStream) throws DataExportException {
        try ( XSSFWorkbook workbook = new XSSFWorkbook() ) { // 1. create an Excel worksheet
            Class<?> componentType = data.getClass().getComponentType(); //return type of array components

            String[] header = createHeader(componentType);

            Field[] declaredFields = componentType.getDeclaredFields();

            // create sheet name from DataClass annotation if present
            String sheetName;
            if (componentType.isAnnotationPresent(DataClass.class)) {
                DataClass dataClassAnnotation = componentType.getAnnotation(DataClass.class);
                sheetName = dataClassAnnotation.target();
            } else {
                sheetName = componentType.getSimpleName();
            }

            XSSFSheet sheet = workbook.createSheet(sheetName);

            // 2. create header row
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                headerRow.createCell(i).setCellValue(header[i]);
            }

            // 3. create data row for each object item from `data`
            int rowNum = 1;
            for (T item : data) {
                XSSFRow row = sheet.createRow(rowNum++);

                int cellNum = 0;
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Object value = field.get(item);

                    XSSFCell cell = row.createCell(cellNum++);
                    // write value to cell based on type
                    ExcelCellWriterUtil.writeValueToCell(cell, value);
                }

                // 4. write the Excel worksheet into the output stream
                workbook.write(outputStream);
            }
        } catch (IllegalAccessException | IOException e) {
            throw new DataExportException(e.getMessage());
        }
    }

    //create header
    private <T> String[] createHeader(Class<T> type) {
        Field[] declaredFields = type.getDeclaredFields();
        String[] header = new String[declaredFields.length];

        for (int i = 0; i < header.length; i++) { //declaredFields.length
            if (declaredFields[i].isAnnotationPresent(DataAttribute.class)) {
                DataAttribute dataAttribute = declaredFields[i].getAnnotation(DataAttribute.class);
                header[i] = dataAttribute.target();
            } else {
                header[i] = declaredFields[i].getName();
            }
        }

        return header;
    }
}


