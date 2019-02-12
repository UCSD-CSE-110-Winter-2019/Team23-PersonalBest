package com.android.personalbest;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.personalbest.models.StepCounter;

public class DailyGoalFragment extends Fragment implements InputDialogFragment.InputDialogListener, StepCounter.Listener {

    private static final String TAG = "DailyGoalFragment";

    // use this tag to identify the source of onInputResult
    private static final String SET_GOAL = "set_goal";
    private static final String ADD_STEP = "add_step";

    // UI elements
    private Button recordBtn;
    private Button changeGoalBtn;
    private Button addStepsBtn;
    private TextView currentStepTextView;
    private TextView currentStepGoalTextView;

    // models
    private StepCounter counter;

    // Required empty public constructor
    public DailyGoalFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the model field
        counter = StepCounter.getInstance(getActivity());
        counter.addListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_daily_goal, container, false);

        // assigning listeners
        recordBtn = fragmentView.findViewById(R.id.daily_goal_record_btn);
        recordBtn.setOnClickListener(this::onRecordBtnClicked);

        changeGoalBtn = fragmentView.findViewById(R.id.daily_goal_change_goal_btn);
        changeGoalBtn.setOnClickListener(this::onChangeGoalBtnClicked);

        currentStepTextView = fragmentView.findViewById(R.id.daily_goal_steps_tv);
        currentStepGoalTextView = fragmentView.findViewById(R.id.daily_goal_goal_steps_tv);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // display step goal and current steps
        currentStepTextView.setText(String.valueOf(counter.getStep()));
        currentStepGoalTextView.setText(String.valueOf(counter.getGoal()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // clean up
        counter.removeListener(this);
    }

    public void onRecordBtnClicked(View view) {

    }

    public void onChangeGoalBtnClicked(View view) {
        DialogFragment dialog = InputDialogFragment.newInstance(this, SET_GOAL, R.string.change_goal_instruction_initial);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog.show(fm, "input_fragment");
    }

    public void onAddStepsBtnClicked(View view) {
        DialogFragment dialog = InputDialogFragment.newInstance(this, ADD_STEP, R.string.add_step_instruction_initial);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog.show(fm, "input_fragment");
    }

    @Override
    public boolean onInputResult(String tag, String result, TextView prompt) {
        // processes the result from the input dialog
        switch (tag) {

            // from set goal
            case SET_GOAL:

                // validate entered goal
                int value;
                try {
                    value = Integer.valueOf(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
                if (value > 0) {
                    counter.setGoal(value);
                    Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.format("changing daily goal to %d", value));
                    return true;
                } else {
                    prompt.setText(R.string.change_goal_instruction_failed);
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public void onStepChanged(int value) {
        currentStepTextView.setText(String.valueOf(value));
    }

    @Override
    public void onGoalChanged(int value) {
        currentStepGoalTextView.setText(String.valueOf(value));
    }
}
