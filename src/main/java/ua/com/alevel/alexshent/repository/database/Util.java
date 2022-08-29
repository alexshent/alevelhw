package ua.com.alevel.alexshent.repository.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Util {
    public static LocalDateTime timestampStringToLocalDateTime(String timestampString) {
        Timestamp timestamp = Timestamp.valueOf(timestampString);
        return timestamp.toLocalDateTime();
    }

    public static boolean hasColumn(ResultSet resultSet, String columnName) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            if (columnName.equals(metaData.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
}
