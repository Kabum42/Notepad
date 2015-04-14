package com.example.ams.simplenotes;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * Created by jcamen on 24/03/15.
 * Preference that displays a number picker for picking an integer from a given interval
 */
public class NumberPickerPreference extends DialogPreference {

    public static final int MAXIMUM = 120;
    public static final int MINIMUM = 5;

    private NumberPicker picker;

    private int value;
    private int minValue;
    private int maxValue;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");

        parseAttributes(attrs);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");

        parseAttributes(attrs);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(this.minValue);
        picker.setMaxValue(this.maxValue);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setValue(picker.getValue());
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, this.minValue);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedInt(this.minValue) : (Integer)defaultValue);
    }

    private void parseAttributes(AttributeSet attributes) {
        // It reads parameters from XML file and sets corresponding properties
        this.minValue = attributes.getAttributeIntValue(null, "minpicker", MINIMUM);
        this.maxValue = attributes.getAttributeIntValue(null, "maxpicker", MAXIMUM);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        persistInt(this.value);
    }

}
