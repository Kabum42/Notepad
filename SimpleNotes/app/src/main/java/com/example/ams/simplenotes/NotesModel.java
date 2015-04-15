package com.example.ams.simplenotes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMS on 13/04/2015.
 */

public class NotesModel implements INotesModel {
    private static final String TABLE_TOPICS = "topics_for_notes";
    private static final String TOPIC_ID = "topic_id";
    private static final String TOPIC_NAME = "topic_name";

    private static final String INTEGER_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String INTEGER_NOT_NULL = "INTEGER NOT NULL";
    private static final String TEXT_NOT_NULL = "TEXT NOT NULL";

    private static final String[] TOPIC_COLUMNS = { TOPIC_ID, TOPIC_NAME };
    private static final String [] TOPIC_TYPES = { INTEGER_KEY, TEXT_NOT_NULL};
    private static final String TOPIC_CONSTRAINTS = null;

    private static final String TABLE_NOTES = "simple_notes";
    private static final String NOTE_ID = "note_id";
    private static final String NOTE_TEXT = "text_in_note";

    private static final String [] NOTE_COLUMNS = { NOTE_ID, TOPIC_ID, NOTE_TEXT };
    private static final String [] NOTE_TYPES = { INTEGER_KEY, INTEGER_NOT_NULL, TEXT_NOT_NULL};
    private static final String NOTE_CONSTRAINTS = "CONSTRAINT FK_simple_notes_topic_id FOREIGN KEY ("+TOPIC_ID+") REFERENCES "+TABLE_TOPICS+"("+TOPIC_ID+")";


    private IDatabase myDBNotes;

    private static NotesModel instance;

    private NotesModel(IDatabase myDBNotes, String [] initialTopics) {
        this.myDBNotes = myDBNotes;
        myDBNotes.initDatabase(new TableSpecification[] { new TableSpecification(TABLE_TOPICS, TOPIC_COLUMNS, TOPIC_TYPES, TOPIC_CONSTRAINTS, TOPIC_NAME, initialTopics),
                new TableSpecification(TABLE_NOTES, NOTE_COLUMNS, NOTE_TYPES, NOTE_CONSTRAINTS, null, null)});
    }

    public static NotesModel getInstance(NotesDatabase myDatabase, String[] initialTopics) {
        if (instance == null) {
            instance = new NotesModel(myDatabase, initialTopics);
        }
        return instance;
    }

    @Override
    public String[] getListOfTopics() {
        ArrayList<String> newList;
        newList = myDBNotes.selectColumnFromTable(TABLE_TOPICS, TOPIC_NAME, TOPIC_NAME);
        return newList.toArray(new String[newList.size()]);
    }

    @Override
    public boolean insertNewTopic(String topicName) {
        return myDBNotes.insertRecordInTable(TABLE_TOPICS, TOPIC_NAME, topicName);
    }

    @Override
    public int getIdTopic(String topic_name) {
        ArrayList<Integer> idList = new ArrayList<>();

        myDBNotes.getColumnAndIds(new String[]{TABLE_TOPICS}, TOPIC_ID, TOPIC_ID, TOPIC_NAME + " = " + topic_name, new String[]{}, TOPIC_ID, new ArrayList<String>(), idList);

        return idList.get(0).intValue();
    }

    @Override
    public void getNotesFromTopic(String topicName, ArrayList<String> listOfNotes, ArrayList<Integer> listOfIdNotes) {
        //myDBNotes.getColumnAndIds(new String[]{TABLE_NOTES}, NOTE_TEXT, NOTE_ID, TOPIC_ID + " = " + getIdTopic(topicName), new String[]{}, NOTE_ID, listOfNotes, listOfIdNotes);

        myDBNotes.getColumnAndIds(new String[]{TABLE_NOTES, TABLE_TOPICS}, NOTE_TEXT, NOTE_ID,
                TOPIC_NAME + " = ? AND " + TABLE_NOTES + "." + TOPIC_ID + " = " + TABLE_TOPICS + "."+TOPIC_ID,
                new String[]{topicName}, NOTE_ID+" ASC", listOfNotes, listOfIdNotes);
    }

    @Override
    public boolean insertNewNote(int topicId, String text) {
        return myDBNotes.insertRecordInTable(TABLE_NOTES, TOPIC_ID, topicId, NOTE_TEXT, text);
    }

    @Override
    public boolean updateNote(int noteId, int topicId, String text) {
        return myDBNotes.updateRecordInTable(TABLE_NOTES, NOTE_ID, noteId, TOPIC_ID, topicId, NOTE_TEXT, text);
    }

    @Override
    public boolean deleteNote(int noteId) {
        return myDBNotes.deleteRecordInTable(TABLE_NOTES,NOTE_ID + " = " + noteId, new String[]{});
    }
}
