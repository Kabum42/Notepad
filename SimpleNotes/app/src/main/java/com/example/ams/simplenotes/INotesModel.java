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
    boolean insertNewNote(int id_topic, String text);
    boolean updateNote(int note_id, int topic_id, String text);
    boolean deleteNote(int note_id);
}
