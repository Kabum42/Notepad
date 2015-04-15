<<<<<<< HEAD
package com.example.ams.simplenotes;
=======
package main.java.com.example.ams.simplenotes;
>>>>>>> origin/kabum42

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Created by AMS on 14/04/2015.
 */
=======
>>>>>>> origin/kabum42
public interface INotesModel {
    ArrayList<String> getListOfTopics();
    boolean insertNewTopic(String topic_name);
    int getTopicId(String topic_name);
    void getNotesFromTopic(String topic_name, List<String> listOfNotes,
                           List<Integer> listOfIdNotes);
    void insertNewNote(int topic_id, String text);
    void updateNote(int note_id, int topic_id, String text);
    void deleteNote(int note_id);
}
<<<<<<< HEAD
=======



>>>>>>> origin/kabum42
