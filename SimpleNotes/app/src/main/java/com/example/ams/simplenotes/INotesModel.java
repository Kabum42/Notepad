package com.example.ams.simplenotes;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMS on 14/04/2015.
 */

public interface INotesModel {
    String[] getListOfTopics();
    boolean insertNewTopic(String topic_name);
    int getIdTopic(String topic_name);
    void getNotesFromTopic(String topic_name, ArrayList<String>
            listOfNotes, ArrayList<Integer> listOfIdNotes);
    boolean insertNewNote(int topicId, String text);
    boolean updateNote(int noteId, int topicId, String text);
    boolean deleteNote(int noteId);
}
