package exceptions;

import java.io.IOException;

public class DataExportException extends IOException {
    public DataExportException(String message) {
        super(message);
    }
}
