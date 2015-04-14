package com.example.ams.simplenotes;

import java.util.ArrayList;

/**
 * Created by AMS on 14/04/2015.
 */
public interface IEditView {
    void fillListOfTopics(ArrayList<String> listOfTopics);
    void collapseTopic(int groupPos);
    void setTopicName(String topicName);
    String getTopicName();
    void setTopicNameBgColor(String bgColor);
    void setTextInNote(String text);
    String getTextInNote();
    void setTextInNoteBgColor(String bgColor);
    void disableDelete();
    void disableOk();
    void enableOk();
    void setTextOk(boolean isUpdate);
    void exit();
}

