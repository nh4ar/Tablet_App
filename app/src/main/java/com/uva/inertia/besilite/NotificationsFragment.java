package com.uva.inertia.besilite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowplowanalytics.snowplow.tracker.Emitter;
import com.snowplowanalytics.snowplow.tracker.Subject;
import com.snowplowanalytics.snowplow.tracker.Tracker;
import com.snowplowanalytics.snowplow.tracker.emitter.BufferOption;
import com.snowplowanalytics.snowplow.tracker.emitter.HttpMethod;
import com.snowplowanalytics.snowplow.tracker.events.ScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationsFragment extends android.support.v4.app.Fragment
{
    final int NUMBER_OF_QUESTIONS = 3;

    AgitationReports ar;

    ConfirmFragment.OnConfirmClickedListener mListener;

    SharedPreferences sharedPref;

//    boolean[] questionAnswers;
    Question[] questions;
//    HashMap<Button, String> questionAnswers;\




    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NotificationsFragment newInstance()
    {
        Log.v("jjp5nw", "NotificationsFragment newInstance() called");
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationsFragment()
    {
        Log.v("jjp5nw", "NotificationsFragment constructor called");
    }


    private void setButtonState(Button button, boolean enabled)
    {
        button.setEnabled(enabled);
    }

    private void setButtonState(List<Button> listOfButtons, boolean enabled)
    {
        for(Button button : listOfButtons)  {
            button.setEnabled(enabled);
        }
    }

    private void setQuestionState(Question question, boolean enabled)
    {
        Log.v("jjp5nw", "setQuestionState(" + question + ", " + enabled + ") called");
        setButtonState(question.getButtons(), enabled);
        question.getTextView().setTextColor(getResources().getColor((enabled) ? (R.color.question_textview_enabled) : (R.color.question_textview_disabled)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.v("jjp5nw", "NotificationsFragment onCreateView() called");
        final View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ar = (AgitationReports) getActivity();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ////////////////////////////////////////////////////////////////
        Button btnQ1Yes, btnQ1No, btnQ2Yes, btnQ2No, btnQ3Yes, btnQ3No;

        btnQ1Yes = (Button) rootView.findViewById(R.id.btn_notif_q1_yes);
        btnQ1No = (Button) rootView.findViewById(R.id.btn_notif_q1_no);
        btnQ2Yes = (Button) rootView.findViewById(R.id.btn_notif_q2_yes);
        btnQ2No = (Button) rootView.findViewById(R.id.btn_notif_q2_no);
        btnQ3Yes = (Button) rootView.findViewById(R.id.btn_notif_q3_yes);
        btnQ3No = (Button) rootView.findViewById(R.id.btn_notif_q3_no);


        questions = new Question[NUMBER_OF_QUESTIONS];

        questions[0] = new Question(1, "event", (new ArrayList<Button>()), (TextView)rootView.findViewById(R.id.textView_notif_question1));
        questions[1] = new Question(2, "accurate", (new ArrayList<Button>()), (TextView)rootView.findViewById(R.id.textView_notif_question2));
        questions[2] = new Question(3, "helpful", (new ArrayList<Button>()), (TextView)rootView.findViewById(R.id.textView_notif_question3));

        questions[0].addButton(btnQ1Yes);
        questions[0].addButton(btnQ1No);
        questions[1].addButton(btnQ2Yes);
        questions[1].addButton(btnQ2No);
        questions[2].addButton(btnQ3Yes);
        questions[2].addButton(btnQ3No);


        btnQ1Yes.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ1Yes clicked");
                questions[0].setAnswer(true);
                setQuestionState(questions[1], true);
                setQuestionState(questions[2], true);
            }
        });

        btnQ1No.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ1No clicked");
                questions[0].setAnswer(false);
                setQuestionState(questions[1], false);
                setQuestionState(questions[2], false);
            }
        });

        btnQ2Yes.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ2Yes clicked");
                questions[1].setAnswer(true);
            }
        });

        btnQ2No.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ2No clicked");
                questions[1].setAnswer(false);
            }
        });

        btnQ3Yes.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ3Yes clicked");
                questions[2].setAnswer(true);
            }
        });

        btnQ3No.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)
            {
                Log.v("jjp5nw", "btnQ3No clicked");
                questions[2].setAnswer(false);
            }
        });











///*

        ////////////////////////Android Analytics Tracking Code////////////////////////////////////
        // Create an Emitter
        Emitter e1 = new Emitter.EmitterBuilder("besisnowplow.us-east-1.elasticbeanstalk.com", getContext())
                .method(HttpMethod.POST) // Optional - Defines how we send the request
                .option(BufferOption.Single) // Optional - Defines how many events we bundle in a POST
                // Optional - Defines what protocol used to send events
                .build();


        Subject s1 = new Subject.SubjectBuilder().build();
        s1.setUserId(sharedPref.getString("pref_key_api_token", ""));

        // Make and return the Tracker object
        Tracker t1 = Tracker.init(new Tracker.TrackerBuilder(e1, "agiRepNotifications", "com.uva.inertia.besilite", getContext())
                .base64(false)
                .subject(s1)
                .build()
        );

        t1.track(ScreenView.builder()
                .name("Agitation Report Notifications")
                .id("agiReportNotifs")
                .build());


        ///////////////////////////////////////////////////////////////////////////////////////////

        Button backBtn = (Button) rootView.findViewById(R.id.backFromNotifs);

        backBtn.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v)
            {
                ////////////////////////Android Analytics Tracking Code////////////////////////////////////
                // Create an Emitter
                Emitter emitter = new Emitter.EmitterBuilder("besisnowplow.us-east-1.elasticbeanstalk.com", getContext())
                        .method(HttpMethod.POST) // Optional - Defines how we send the request
                        .option(BufferOption.Single) // Optional - Defines how many events we bundle in a POST
                        // Optional - Defines what protocol used to send events
                        .build();

                Subject subject = new Subject.SubjectBuilder().build();
                subject.setUserId(sharedPref.getString("pref_key_api_token", ""));
                // Make and return the Tracker object
                Tracker tracker = Tracker.init(new Tracker.TrackerBuilder(emitter, "agitationReportNotifications", "com.uva.inertia.besilite", getContext())
                        .base64(false)
                        .subject(subject)
                        .build()
                );

                tracker.track(ScreenView.builder()
                        .name("Agitation Report / Notifications -> Back")
                        .id("agitationReportNotifsBackButton")
                        .build());
                ///////////////////////////////////////////////////////////////////////////////////////////

                //
                ((AgitationReports) getActivity()).selectPage(1);
            }
        });

        Button confirmBtn = (Button) rootView.findViewById(R.id.submitOnNotifs);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////Android Analytics Tracking Code////////////////////////////////////
                // Create an Emitter
                Emitter emitter = new Emitter.EmitterBuilder("besisnowplow.us-east-1.elasticbeanstalk.com", getContext())
                        .method(HttpMethod.POST) // Optional - Defines how we send the request
                        .option(BufferOption.Single) // Optional - Defines how many events we bundle in a POST
                        // Optional - Defines what protocol used to send events
                        .build();

                Subject subject = new Subject.SubjectBuilder().build();
                subject.setUserId(sharedPref.getString("pref_key_api_token", ""));
                // Make and return the Tracker object
                Tracker tracker = Tracker.init(new Tracker.TrackerBuilder(emitter, "agitationReportNotifications", "com.uva.inertia.besilite", getContext())
                        .base64(false)
                        .subject(subject)
                        .build()
                );

                tracker.track(ScreenView.builder()
                        .name("Agitation Report / Notifications -> Submit")
                        .id("agitationReportNotifsSubmitButton")
                        .build());
                ///////////////////////////////////////////////////////////////////////////////////////////

                mListener = (ConfirmFragment.OnConfirmClickedListener) getActivity();
                mListener.OnConfirmClicked();
            }
        });
//*/

        return rootView;
    }
}

class Question
{
    private List<Button> buttons;
    private boolean answer;
    private int id;
    private String name;

    private TextView textView;

    public Question(int id, String name, List<Button> buttons, TextView textView)
    {
        this(id, name, false, buttons, textView);
    }

    public Question(int id, String name, boolean answer, List<Button> buttons, TextView textView)
    {
        this.id = id;
        this.name = name;
        this.answer = answer;
        this.buttons = buttons;
        this.textView = textView;
    }

    public int getQuestionId()
    {
        return this.id;
    }


    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }

    public boolean getAnswer()
    {
        return this.answer;
    }

    public boolean addButton(Button button)
    {
        return this.buttons.add(button);
    }

    public TextView getTextView()
    {
        return this.textView;
    }

    public List<Button> getButtons()
    {
        return this.buttons;
    }

    public String toString()
    {
        return "(Question, {id: " + this.id + " , name: " + this.name + "})";
    }

}