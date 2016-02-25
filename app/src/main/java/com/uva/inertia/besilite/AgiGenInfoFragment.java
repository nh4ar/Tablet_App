package com.uva.inertia.besilite;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgiGenInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgiGenInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgiGenInfoFragment extends Fragment implements passBackInterface{


    java.text.DateFormat[] dateFormats;
    Calendar calendar;
    Date agidate;

    TextView selDate;
    TextView selTime;

    public static final int DATEPICKER_FRAGMENT=1; // adding this line
    public static final int TIMEPICKER_FRAGMENT=2; // adding this line

    public AgiGenInfoFragment() {
        // Required empty public constructor
    }


    public static AgiGenInfoFragment newInstance() {
        AgiGenInfoFragment fragment = new AgiGenInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateFormats = new java.text.DateFormat[] {
                    java.text.DateFormat.getDateInstance(),
                    java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT),
            };
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_agi_gen_info, container, false);

        Button dater = (Button)rootView.findViewById(R.id.agi_date);
        Button timer = (Button)rootView.findViewById(R.id.agi_time);

        dater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAgiDatePickerDialog(v);
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAgiTimePickerDialog(v);
            }
        });


        selDate = (TextView) rootView.findViewById(R.id.agi_date_viewer);
        selTime = (TextView) rootView.findViewById(R.id.agi_time_viewer);
        calendar = new GregorianCalendar();
        agidate = new Date();
        calendar.setTime(agidate);

        selDate.setText(dateFormats[0].format(agidate));
        selTime.setText(dateFormats[1].format(agidate));

        return rootView;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void setDate(int year, int month, int day){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        agidate = calendar.getTime();
        Log.v("PICKER", agidate.toString());
        selDate.setText(dateFormats[0].format(agidate));
    }

    public void setTime( int hourOfDay, int minute){
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        agidate = calendar.getTime();
        Log.v("PICKER", agidate.toString());
        selTime.setText(dateFormats[1].format(agidate));
    }

    public static void method(int hourOfDay, int minute){

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Intent i = new Intent();
            i.putExtra("hour",hourOfDay);
            i.putExtra("minute",minute);
            passBackInterface mHost = (passBackInterface)getTargetFragment();
            mHost.passData(getTargetRequestCode(), Activity.RESULT_OK, i);
        }
    }
    public void showAgiTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setTargetFragment(this, TIMEPICKER_FRAGMENT);
        newFragment.show((getActivity()).getSupportFragmentManager().beginTransaction(), "agiTimePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Intent i = new Intent();
            i.putExtra("year",year);
            i.putExtra("month",month);
            i.putExtra("day",day);
            passBackInterface mHost = (passBackInterface)getTargetFragment();
            mHost.passData(getTargetRequestCode(), Activity.RESULT_OK, i);
        }
    }
    public void showAgiDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(this, DATEPICKER_FRAGMENT);
        newFragment.show((getActivity()).getSupportFragmentManager().beginTransaction(), "agiDatePicker");
    }

    public void passData(int code, int status, Intent data){
        if (code == DATEPICKER_FRAGMENT){
            if (status == Activity.RESULT_OK) {
                Bundle bundle=data.getExtras();
                setDate(bundle.getInt("year"), bundle.getInt("month"),bundle.getInt("day"));
            }
        }
        else if (code == TIMEPICKER_FRAGMENT){
            if (status == Activity.RESULT_OK) {
                Bundle bundle=data.getExtras();
                setTime(bundle.getInt("hour"), bundle.getInt("minute"));
            }
        }
    }
}

interface passBackInterface{
    void passData(int code, int status, Intent i);
}