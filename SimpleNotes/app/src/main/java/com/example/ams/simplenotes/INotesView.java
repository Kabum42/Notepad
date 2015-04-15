package main.java.com.example.ams.simplenotes;

import java.util.ArrayList;

/**
 * Created by AMS on 13/04/2015.
 */
public interface INotesView {
    void setTopic(String topic);
    void setTopicBgColor(String bgColor);
    void fillListOfTopics(ArrayList<String> listOfTopics);
    void collapseTopic(int groupPos);
    void fillListOfNotes(ArrayList<String> listOfNotes);
    void switchToNoteEdition();
    void askForATopic();
}
