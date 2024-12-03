package exporter;

import exceptions.DataExportException;
import java.io.OutputStream;

public interface DataExporter {
    <T> void exportData(T[] data, OutputStream outputStream) throws DataExportException;
}
