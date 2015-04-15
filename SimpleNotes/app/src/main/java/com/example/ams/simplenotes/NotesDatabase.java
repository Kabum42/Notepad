package main.java.com.example.ams.simplenotes;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMS on 13/04/2015.
 */

public class NotesDatabase extends SQLiteOpenHelper implements IDatabase {

    private SQLiteDatabase theDB;

    @Override
    public void onCreate(SQLiteDatabase db) {

// SQL sentences run through SQLiteDatabase db to create database

       db.execSQL(...);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// What to do when upgrading database to a newVersion greater than
// oldVersion. It’s likely required to run SQL code
    }

    @Override
    public void initDatabase(TableSpecification[] specifications) {
        this.specifications = specifications;
        theDB = this.getWritableDatabase();
    }

    @Override
    public ArrayList<String> selectColumnFromTable(String table_name, String column, String order_by) {
        return null;
    }

    @Override
    public int getColumnFromId(String table_name, String column, String idColumn, String idValue) {
        return 0;
    }

    @Override
    public void getColumnAndIds(String[] tables, String column, String idColumn, String where, String[] sel_args, String sortBy, List<String> elements, List<Integer> ids) {

    }

    @Override
    public <T> boolean insertRecordInTable(String table_name, String column, T value) {
        return false;
    }

    @Override
    public <T1, T2> boolean insertRecordInTable(String table_name, String column1, T1 value1, String column2, T2 value2) {
        return false;
    }

    @Override
    public <T1, T2> void updateRecordInTable(String table_name, String keyColumn, int keyValue, String column1, T1 value1, String column2, T2 value2) {

    }

    @Override
    public void deleteRecordInTable(String table_name, String where, String[] where_args) {

    }
}
