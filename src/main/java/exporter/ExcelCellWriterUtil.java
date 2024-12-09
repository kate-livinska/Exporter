package exporter;

import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class ExcelCellWriterUtil {

    // Map for type to writer function
    private static final Map<Class<?>, BiConsumer<Cell, Object>> typeWriterMap = new HashMap<>();

    // Populate the map with appropriate handlers
    static {
        typeWriterMap.put(String.class, createWriter(String.class));
        typeWriterMap.put(Integer.class, createWriter(Integer.class));
        typeWriterMap.put(int.class, createWriter(int.class));
        typeWriterMap.put(Double.class, createWriter(Double.class));
        typeWriterMap.put(double.class, createWriter(double.class));
        typeWriterMap.put(Boolean.class, createWriter(Boolean.class));
        typeWriterMap.put(boolean.class, createWriter(boolean.class));
        typeWriterMap.put(Long.class, createWriter(Long.class));
        typeWriterMap.put(long.class, createWriter(long.class));
        typeWriterMap.put(Float.class, createWriter(Float.class));
        typeWriterMap.put(float.class, createWriter(float.class));
        typeWriterMap.put(LocalDate.class, createWriter(LocalDate.class));
        typeWriterMap.put(LocalDateTime.class, createWriter(LocalDateTime.class));
    }

    public static void writeValueToCell(Cell cell, Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Cannot write null value to Excel cell");
        }

        Class<?> valueType = value.getClass();
        BiConsumer<Cell, Object> writer = typeWriterMap.get(valueType);

        if (writer != null) {
            writer.accept(cell, value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + valueType);
        }
    }

    // Create handlers for desired types
    private static <T> BiConsumer<Cell, Object> createWriter(Class<T> type) {
        return (cell, value) -> {
            if (type == String.class) {
                cell.setCellValue(value.toString());
            } else if (type == Integer.class || type == int.class) {
                cell.setCellValue((int) value);
            } else if (type == Double.class || type == double.class) {
                cell.setCellValue((double) value);
            } else if (type == Boolean.class || type == boolean.class) {
                cell.setCellValue((boolean) value);
            } else if (type == Long.class || type == long.class) {
                cell.setCellValue((long) value);
            } else if (type == Float.class || type == float.class) {
                cell.setCellValue((float) value);
            } else if (type == LocalDateTime.class) {
                cell.setCellValue((LocalDateTime) value);
            } else if (type == LocalDate.class) {
                cell.setCellValue((LocalDate) value);
            } else {
                throw new IllegalArgumentException("Unsupported value type: " + type);
            }
        };
    }
}
