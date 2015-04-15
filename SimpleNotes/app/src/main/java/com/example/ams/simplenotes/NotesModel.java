package main.java.com.example.ams.simplenotes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMS on 13/04/2015.
 */
<<<<<<< HEAD
public class NotesModel {
    public static NotesModel getInstance(NotesDatabase myDBNotes, String[] initial_topics) {
        return null;
    }
=======
public class NotesModel implements INotesModel {
    private static final String TABLE_TOPICS = "topics_for_notes";
    private static final String TOPIC_ID = "topic_id";
    private static final String TOPIC_NAME = "topic_name";

    private static final String INTEGER_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";

    private static final String[] TOPIC_COLUMNS = {TOPIC_ID, TOPIC_NAME};
>>>>>>> origin/kabum42
}

    private NotesModel(IDatabase myDBNotes, String [] initialTopics) {
        this.myDBNotes = myDBNotes;
        myDBNotes.initDatabase(new TableSpecification[]{..., ...});
    }
    public static NotesModel getInstance(com.example.ams.simplenotes.IDatabase myDBNotes, String [] initialTopics) {
        return instance;
    }

    @Override
    public ArrayList<String> getListOfTopics() {
        return null;
    }

    @Override
    public boolean insertNewTopic(String topic_name) {
        return false;
    }

    @Override
    public int getTopicId(String topic_name) {
        return 0;
    }

    @Override
    public void getNotesFromTopic(String topic_name, List<String> listOfNotes, List<Integer> listOfIdNotes) {

    }

    @Override
    public void insertNewNote(int topic_id, String text) {

    }

    @Override
    public void updateNote(int note_id, int topic_id, String text) {

    }

    @Override
    public void deleteNote(int note_id) {

    }
