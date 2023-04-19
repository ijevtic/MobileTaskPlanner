package com.example.mobiletaskplanner.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.models.TimeType;
import com.example.mobiletaskplanner.utils.Constants;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private TimeType timeType;
    TimePickerDialog p;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timeType = (TimeType) getArguments().getSerializable(Constants.TIME_TYPE);

        // Create a new instance of TimePickerDialog and return it
         p = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        p.setTitle("Select Time");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.timer_picker_title, null);
        TextView title =  view.findViewById(R.id.timer_picker_title);
        if(timeType.equals(TimeType.START_TIME))
            title.setText(getString(R.string.enter_start_time));
        else
            title.setText(getString(R.string.enter_end_time));
        p.setCustomTitle(view);



        return p;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TIME_TYPE, timeType);
        bundle.putInt(Constants.TIME_RESULT_HOUR, hourOfDay);
        bundle.putInt(Constants.TIME_RESULT_MINUTE, minute);
        getParentFragmentManager().setFragmentResult(Constants.TIME_REQUEST_KEY, bundle);
    }
}
