package com.example.ams.simplenotes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcamen on 3/03/15.
 */
public interface IDatabase {
    void initDatabase(TableSpecification[] specifications);
    ArrayList<String> selectColumnFromTable(String table_name, String column, String order_by);
    int getColumnFromId(String table_name, String column, String idColumn, String idValue);
    void getColumnAndIds(String[] tables, String column, String idColumn, String where, String[] sel_args,
                         String sortBy, List<String> elements, List<Integer> ids);

    <T> boolean insertRecordInTable(String table_name, String column, T value);
    <T1, T2> boolean insertRecordInTable(String table_name, String column1, T1 value1, String column2, T2 value2);
    <T1, T2> void updateRecordInTable(String table_name, String keyColumn, int keyValue, String column1,
                                      T1 value1, String column2, T2 value2);
    void deleteRecordInTable(String table_name, String where, String[] where_args);
}
