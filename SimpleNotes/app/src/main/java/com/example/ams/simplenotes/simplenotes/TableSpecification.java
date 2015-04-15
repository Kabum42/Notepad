package com.example.ams.simplenotes.simplenotes;

/**
 * Created by jcamen on 24/03/15.
 */
public class TableSpecification {
    private final String name;
    private final String[] columns;
    private final String[] types;
    private final String constraints;
    private final String initialColumn;
    private final String[] initialValues;

    public TableSpecification(String Name, String[] columns, String[] types, String constraints, String initialColumn, String[] initialValues) {
        this.name = Name;
        this.columns = columns;
        this.types = types;
        this.constraints = constraints;
        this.initialColumn = initialColumn;
        this.initialValues = initialValues;
    }

    public String getName() {
        return name;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getTypes() {
        return types;
    }

    public String getConstraints() {
        return constraints;
    }

    public String getInitialColumn() {
        return initialColumn;
    }

    public String[] getInitialValues() {
        return initialValues;
    }
}
