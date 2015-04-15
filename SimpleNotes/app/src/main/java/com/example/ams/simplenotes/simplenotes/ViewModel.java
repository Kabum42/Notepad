package com.example.ams.simplenotes.simplenotes;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AMS on 13/04/2015.
 */
public class ViewModel {

    ExpandableListView topicGroup;

    private static ViewModel instance;
    private INotesModel model;

    private INotesView notesView;
    private IEditView editView;
    private ActionBarActivity currentView;
    private TextView selectedTopic;
    private EditText notepad;

    private ArrayList<Integer> listOfNotesID = new ArrayList<>();
    private ArrayList<String> listOfNotes = new ArrayList<>();
    private String currentTopic = "";
    private String currentText = "";
    private String currentEditText = "";
    private int currentNotePos = 0;
    private int currentTopicPos = 0;
    private int charsToShow = 16;

    private boolean isUpdate = false;

    public static ViewModel getInstance(NotesModel notesModel) {
        if (instance == null)
            instance = new ViewModel(notesModel);
        return instance;
    }

    public void onNotesListPressed(int position, String noteItem) {
        currentText = noteItem;
        currentEditText = noteItem;
        currentNotePos = position;

        notesView.switchToNoteEdition();

        setEditView(editView);
    }

    private ViewModel(INotesModel model) {
        this.model = model;
    }


    public void onTopicsListPressed(int groupPosition, String topicItemName) {
        if (topicItemName.equals("New...")){
            notesView.askForATopic();
        }
        else
        {
            currentTopicPos = groupPosition;
            setTopic(topicItemName);
            setNotesView(notesView);
        }
    }

    public void onTopicsListPressedFromEdition(int g_position, String name) {

        selectedTopic = (TextView) currentView.findViewById(R.id.selectedTopic);
        selectedTopic.setText(currentTopic);

        currentTopicPos = g_position;

    }

    public void onTextInNoteChanged(String noteContents) {

        currentText = noteContents;
    }

    public void onOkPressed() {
        String topicName = editView.getTopicName();
        currentTopic = editView.getTopicName();
        currentText = editView.getTextInNote();
        if (isUpdate){
            model.updateNote(getNoteIdFromPos(currentNotePos), model.getIdTopic(currentTopic), currentText);
        }
        else{
            model.insertNewNote(model.getIdTopic(currentTopic), currentText);
        }
        onExitEdition(true);
        editView.exit();
    }

    public void onCancelPressed() {
        onExitEdition(false);
        editView.exit();
    }

    public void onConfirmDelete() {
        model.deleteNote(getNoteIdFromPos(currentNotePos));
        onExitEdition(false);
        editView.exit();
    }



    public void setTopic(String newTopic) {
        currentTopic = newTopic;
        selectedTopic = (TextView) currentView.findViewById(R.id.selectedTopic);
        selectedTopic.setText(newTopic);
    }

    public String getTopic(){
        return currentTopic;
    }

    public void setTopicBgColor(String bgcolor) {
        selectedTopic = (TextView) currentView.findViewById(R.id.selectedTopic);
        selectedTopic.setBackgroundColor(new Integer(bgcolor));
    }

    public void collapseTopic(int groupPos) {
        topicGroup = (ExpandableListView) currentView.findViewById(R.id.TopicList);
        topicGroup.collapseGroup(groupPos);
    }

    public void onNewTopicCreated(String topicName){
        if(!topicName.equals("New...") && !topicName.equals("") && !topicName.equals(" ")) {
            model.insertNewTopic(topicName);
            setNotesView(notesView);
            setTopic(topicName);
        }
    }



    public void setNotesView(INotesView newView) {
        model.getNotesFromTopic(currentTopic, listOfNotes, listOfNotesID);
        notesView = newView;
        notesView.fillListOfNotes(listOfNotes);
        currentView = (ActionBarActivity) notesView;
    }

    public void setEditView(IEditView newView) {
        editView = newView;
        currentView = (ActionBarActivity) editView;
        editView.setTextInNote(currentText);
    }

    public void setBgNoteColor(String bgNoteColor) {
        notepad = (EditText) currentView.findViewById(R.id.textInNote);
        notepad.setBackgroundColor(new Integer(bgNoteColor));
    }
    public void setCharsToShow(int charsToShow) {
        this.charsToShow = charsToShow;
    }


    private int getNoteIdFromPos(int pos){
        ArrayList<Integer> idArray = new ArrayList<>();
        model.getNotesFromTopic(currentTopic, new ArrayList<String>(), idArray);
        return idArray.get(pos);
    }

    private void onExitEdition(boolean isEdit)
    {
        currentText = "";
        currentNotePos = 0;
    }
}
