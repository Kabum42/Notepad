package com.example.ams.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.ams.simplenotes.Constants.INDEX_ERR;

/**
 * Created by AMS on 13/04/2015.
 */

public class NotesDatabase extends SQLiteOpenHelper implements IDatabase {

    private static final String DB_NAME = "simple_notes_db";
    private static final int VERSION = 1;

    private TableSpecification[] specifications;
    private static NotesDatabase instance;
    private SQLiteDatabase theDB;

    private NotesDatabase(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    public static NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new NotesDatabase(context, DB_NAME, VERSION);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (TableSpecification specification : specifications) {
            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE " + specification.getName() + " (");
            for (int i = 0 ; i < specification.getColumns().length ; i++) {
                if (i > 0)
                    builder.append(", ");
                builder.append(specification.getColumns()[i] + " " + specification.getTypes()[i]);
            }
            if (specification.getConstraints() != null)
                builder.append(", " + specification.getConstraints());
            builder.append(")");
            db.execSQL(builder.toString());
            if (specification.getInitialValues()!= null) {
                String insert = "INSERT INTO " + specification.getName() + "(" + specification.getInitialColumn() + ") VALUES (\"";
                for (String value : specification.getInitialValues()) {
                    String query = insert + value + "\")";
                    db.execSQL(query);
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is not used but it needs to be defined
    }

    @Override
    public void initDatabase(TableSpecification[] specifications) {
        this.specifications = specifications;
        theDB = this.getWritableDatabase();
    }

    @Override
    public ArrayList<String> selectColumnFromTable(String table_name, String columns, String order_by) {
        Cursor cursor = theDB.query(table_name, new String[]{columns}, null, new String[]{}, null, null, order_by);

        ArrayList<String> column = new ArrayList<>();
        if (cursor.getCount() > 0) {
            int indexColumnTopicName = cursor.getColumnIndexOrThrow(columns);

            if (cursor.moveToFirst()) {
                do {
                    column.add(cursor.getString(indexColumnTopicName));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return column;
    }

    @Override
    public int getColumnFromId(String table_name, String column, String idColumn, String idValue){
        String[] columns = { column };
        String where = idColumn + "= ?";
        String[] sel_args = new String[] {idValue};

        Cursor cursor = theDB.query(table_name, columns, where, sel_args, null, null, null);

        int idTopic = INDEX_ERR;
        if (cursor.getCount() == 1) {
            int index = cursor.getColumnIndexOrThrow(column);
            cursor.moveToFirst();
            idTopic = cursor.getInt(index);
        }
        cursor.close();
        return idTopic;
    }

    @Override
    public void getColumnAndIds(String[] tables, String column, String idColumn, String where, String[] sel_args,
                                String order_by, List<String> elements, List<Integer> ids) {
        Cursor queryResult;

        StringBuilder query = new StringBuilder();
        query.append("SELECT " + column + ", " + idColumn);
        query.append(" FROM ");
        for (int i = 0 ; i < tables.length ; i++) {
            if (i > 0)
                query.append(", ");
            query.append(tables[i]);
        }
        query.append(" WHERE " + where);
        query.append(" ORDER BY " + order_by);

        queryResult = theDB.rawQuery(query.toString(), sel_args);

        if (queryResult.getCount() > 0) {
            int indexColumnTextInNote = queryResult.getColumnIndexOrThrow(column);
            int indexColumnIdNote = queryResult.getColumnIndexOrThrow(idColumn);
            if (queryResult.moveToFirst()) {
                do {
                    ids.add(queryResult.getInt(indexColumnIdNote));
                    elements.add(queryResult.getString(indexColumnTextInNote));
                } while (queryResult.moveToNext());
            }
        }

        queryResult.close();
    }

    private <T> void insertInValues(ContentValues values, String column, T value) {
        if (value instanceof Integer)
            values.put(column, (Integer)value);
        else if (value instanceof String)
            values.put(column, (String)value);
        else if (value instanceof Double)
            values.put(column, (Double)value);
        else throw new IllegalArgumentException("Unknown type for insertInValues");
    }

    @Override
    public <T> boolean insertRecordInTable(String table_name, String column, T value) {
        ContentValues values = new ContentValues();
        insertInValues(values, column, value);
        if (theDB.insert(table_name, null, values) == -1) {
            Log.w(NotesDatabase.class.getName(), "Insertion of a new topic in database FAILED");
            return false;
        }
        return true;
    }

    @Override
    public <T1, T2> boolean insertRecordInTable(String table_name, String column1, T1 value1, String column2, T2 value2) {

        ContentValues values = new ContentValues();
        insertInValues(values, column1, value1);
        insertInValues(values, column2, value2);
        if (theDB.insert(table_name, null, values) == -1) {
            Log.w(NotesDatabase.class.getName(), "Insertion of a new note in database FAILED");
            return false;
        }
        return true;
    }

    @Override
    public <T1, T2> boolean updateRecordInTable(String table_name, String keyColumn, int keyValue, String column1,
                                                T1 value1, String column2, T2 value2) {

        ContentValues values = new ContentValues();

        insertInValues(values, column1, value1);
        insertInValues(values, column2, value2);
        String where = keyColumn + "= ?";
        String[] where_args = new String[] { Integer.toString(keyValue)};

        if (theDB.update(table_name, values, where, where_args) != 1) {
            Log.w(NotesDatabase.class.getName(), "Upgrade of an existing note in database FAILED");
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRecordInTable(String table_name, String where, String[] where_args) {
        if (theDB.delete(table_name, where, where_args) != 1) {
            Log.w(NotesDatabase.class.getName(), "Deletion of an existing note in database FAILED");
            return false;
        }
        return true;
    }
}
