package dao;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public interface RecordDao<T> {
    void pullData() throws Exception;
    List<T> readFromCSV(InputStream is) throws Exception;
    Set<T> readData();
    void createNewTable(Connection con) throws SQLException;
    boolean readToDatabase(Connection con) throws SQLException;
}

