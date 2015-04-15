package com.example.ams.simplenotes;

/**
 * Created by AMS on 13/04/2015.
 */
public class ViewModel {

    private INotesModel model;
    private static ViewModel instance;
    private INotesView notesView;
    private IEditView editView;

    private ViewModel(INotesModel model) {
        this.model = model;
    }
    public static ViewModel getInstance(INotesModel model) {
        if (instance == null) {
            //throw IllegalArgumentException;
        }
        return instance;
    }

    public void setNotesView(INotesView newView) {

        notesView = newView;

        //KEEP TRACK OF THE TOPIC CHOSEN IN BOTH VIEWS, ORIGINAL TEXT AND EDITED TEXT

    }
    public void setEditView(IEditView newView) {

        editView = newView;

    }

    public void onConfirmDelete() {

    }

    public ViewModel getInstance(NotesModel notesModel) {
        return null;
    }

    public void onTopicsListPressed(int groupPosition, String topicItemName) {
    }

    public void onNotesListPressed(int position, String s) {

    }

    public void setTopic(String topic) {

    }
}
