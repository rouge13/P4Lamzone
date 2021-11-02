package julien.hammer.p4lamzone.ui.mareu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import julien.hammer.p4lamzone.di.DI;
import butterknife.BindView;
import butterknife.OnClick;
import julien.hammer.p4lamzone.R;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;
import julien.hammer.p4lamzone.service.MareuApiService;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class AddMeetingActivity extends AppCompatActivity {
    private MareuApiService mApiService;
//    private final List<Meeting> mMeetings;
//
//    {
//        assert false;
//        mMeetings = mApiService.getMeetings();
//    }
    private final List<User> mUsers = null;
    private final List<Room> mRooms = null;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner_room_to_select)
    Spinner mSpinnerRooms;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.textEmailAddress)
    MultiAutoCompleteTextView mTextEmailAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.subject_name)
    TextInputEditText mSubjectName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create)
    MaterialButton addButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.in_date)
    EditText mMeetingDay;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_date)
    Button mMeetingBtnDay;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.in_time_start)
    EditText mMeetingTimeStart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_time_start)
    Button mMeetingBtnTimeStart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.in_time_end)
    EditText mMeetingTimeEnd;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_time_end)
    Button mMeetingBtnTimeEnd;
    private Date mDateStart;
    private Date mDateEnd;

    private Room mSeletedRoom;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        mApiService = DI.getMareuApiService();
//        mMeetingBtnDay.setOnClickListener();
//        mMeetingBtnTimeStart.setOnClickListener(this);
//        mMeetingBtnTimeEnd.setOnClickListener(this);
        configureSpinnerRoom();
        configureMultiAutoComplete();

        mMeetingBtnDay.setOnClickListener(v -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mMeetingDay.setText(dayOfMonth + " " + (monthOfYear + 1) + " " + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        mMeetingBtnTimeStart.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mMeetingTimeStart.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

        mMeetingBtnTimeEnd.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mMeetingTimeEnd.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

    }

//    @OnClick(R.id.create)
//    void addMeetings() {
//        Meeting meeting = new Meeting(
//                mSubjectName.getText().toString(),
//                mSeletedRoom.getName(),
//
//
////                mNeighbourImage,
////                addressInput.getEditText().getText().toString(),
////                phoneInput.getEditText().getText().toString(),
////                aboutMeInput.getEditText().getText().toString(),
////                mNeighbourFavoritesInAddActivity = false
//        );
//        mApiService.createMeeting(meeting);
//        finish();
//    }

//    public void onClick(View v) {
//
//        if (v == mMeetingBtnDay) {
//
//            // Get Current Date
//            final Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year,
//                                              int monthOfYear, int dayOfMonth) {
//
//                            mMeetingDay.setText(dayOfMonth + " " + (monthOfYear + 1) + " " + year);
//
//                        }
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        }
//        if (v == mMeetingBtnTimeStart) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            mMeetingTimeStart.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }
//        if (v == mMeetingBtnTimeEnd) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            mMeetingTimeEnd.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }
//    }

    private void configureSpinnerRoom() {
        //get List Room
        List<String> roomNameList = new ArrayList<>();
        List<Room> mRooms = mApiService.getRooms();
        for(Room room:mRooms){
            roomNameList.add(room.getName());
        }
//        mSpinnerRooms = (Spinner)findViewById(R.id.spinner_room_to_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,roomNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRooms.setAdapter(adapter);
//        mSpinnerRooms.setOnItemSelectedListener(this);
        //add values in room arrayList
        mSpinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                mSeletedRoom = mRooms.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void configureMultiAutoComplete() {
        List<String> userEmailList = new ArrayList<>();
        Collection<User> mUsers = mApiService.getUsers();
        for(User user:mUsers){
            userEmailList.add(user.getEmail().toLowerCase());
        }
//        AppCompatMultiAutoCompleteTextView mTextEmailAddress = findViewById(R.id.textEmailAddress);
        ArrayAdapter<String> adapterEmail
                = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,userEmailList);
        mTextEmailAddress.setAdapter(adapterEmail);
        mTextEmailAddress.setThreshold(1);
        // The text separated by commas
        mTextEmailAddress.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

}

