package julien.hammer.p4lamzone.ui.mareu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    private List<User> mUsers = null;
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
    @BindView(R.id.create_meeting)
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
//    ("01 17 2012 10:00:00")
    private Room mSeletedRoom;
    private int mYear, mMonth, mDay, mHourStart, mMinuteStart, mHourEnd, mMinuteEnd;
    List<User> usersAddedInMeeting = new ArrayList<>();
    Integer mMeetingId;
    List<String> userEmailList = new ArrayList<>();
    Calendar mMeetingDayCalendar = null;
    Calendar mMeetingHourStartCalendar = null;
    Calendar mMeetingHourEndCalendar = null;
    Date mMeetingStartDate = null;
    Date mMeetingEndDate = null;
    Boolean mMeetingDayAdded = false;
    Boolean mMeetingStartTimeAdded = false;
    Boolean mMeetingEndTimeAdded = false;



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

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
//                            mMeetingDay.setText((monthOfYear + 1) + " " + dayOfMonth +  " " + year);
//                            mMeetingDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR,year);
                            cal.set(Calendar.MONTH,monthOfYear);
                            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            mMeetingDayCalendar = cal;
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                            mMeetingDay.setText(formatter.format(cal.getTime()));
                            mMeetingDayAdded = true;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        mMeetingBtnTimeStart.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHourStart = c.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

//                            mMeetingTimeStart.setText(hourOfDay + ":" + minute);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            cal.set(Calendar.MINUTE,minute);
                            mMeetingHourStartCalendar = cal;
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
                            mMeetingTimeStart.setText(formatter.format(cal.getTime()));
                            mMeetingStartTimeAdded = true;
                        }
                    }, mHourStart, mMinuteStart, true);
            timePickerDialog.show();
        });

        mMeetingBtnTimeEnd.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHourEnd = c.get(Calendar.HOUR_OF_DAY);
            mMinuteEnd = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            cal.set(Calendar.MINUTE,minute);
                            mMeetingHourEndCalendar = cal;
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
                            mMeetingTimeEnd.setText(formatter.format(cal.getTime()));
                            mMeetingEndTimeAdded = true;
                        }
                    }, mHourEnd, mMinuteEnd, true);
            timePickerDialog.show();
        });
//        String mSecond = "00";

//        mMeetingId = mApiService.getMeetings().size() + 1;

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
        mUsers = mApiService.getUsers();
        for(User user:mUsers){
            userEmailList.add(user.getEmail().toLowerCase());
        }
//        AppCompatMultiAutoCompleteTextView mTextEmailAddress = findViewById(R.id.textEmailAddress);
        ArrayAdapter<String> adapterEmail
                = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userEmailList);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.create_meeting)
    void addMeeting() {
//        String mSecond = "00";
//        String mDateStart;
//        String mDateEnd;
//        mDateStart = mMeetingDay.getText().toString().trim() + " " + mMeetingTimeStart.getText().toString().trim();
//        mDateEnd = mMeetingDay.getText().toString().trim() + " " + mMeetingTimeEnd.getText().toString().trim();
////        mDateStart = mMeetingDay + " " + mMeetingTimeStart + ":" + mSecond;
////        mDateEnd = mMeetingDay + " " + mMeetingTimeEnd + ":" + mSecond;
//        System.out.println(mMeetingDay.getText().toString().trim()+"\t"+ mMeetingTimeStart.getText().toString().trim());
//        System.out.println(mMeetingDay.getText().toString().trim()+"\t"+ mMeetingTimeEnd.getText().toString().trim());




//        LocalDate dateStartPart = LocalDate.parse(mMeetingDay .getText().toString().trim());
//        LocalTime timeStartPart = LocalTime.parse(mMeetingTimeStart.getText().toString().trim());
//        LocalDateTime dtStart = LocalDateTime.of(dateStartPart,timeStartPart);
//        LocalDate dateEndPart = LocalDate.parse(mMeetingDay.getText().toString().trim());
//        LocalTime timeEndPart = LocalTime.parse(mMeetingTimeEnd.getText().toString().trim());
//        LocalDateTime dtEnd = LocalDateTime.of(dateEndPart,timeEndPart);
//        Date dateStart = Date.from(dtStart.atZone(ZoneId.systemDefault()).toInstant());
//        Date dateEnd = Date.from(dtEnd.atZone(ZoneId.systemDefault()).toInstant());
//        System.out.println(dateStartPart+"\t"+timeStartPart);
//        System.out.println(dateEndPart+"\t"+timeEndPart);
//        System.out.println(dtStart+"\t"+dtEnd);
//        System.out.println(dateStart+"\t"+dateEnd);

//        mDateStart = Conver (mMeetingDay + " " + mMeetingTimeStart).;
//        mDateEnd = mMeetingDay + " " + mMeetingTimeEnd;
        mMeetingId = mApiService.getMeetings().size() + 1;
        String[] selectedUsers = mTextEmailAddress.getText().toString().toLowerCase().split(", ");

        for (String selectedUser : selectedUsers) {
            for (User user : mUsers) {
                if (selectedUser.contains(user.getEmail().toLowerCase())) {
                    usersAddedInMeeting.add(user);
                }
//                userEmailList.add(user.getEmail().toLowerCase());
            }

//            if (selectedUsers[i].contains(mUsers.get(i).getEmail())   mUsers.get(i).getEmail().contains(selectedUsers)  equals(selectedUsers[i]))
        }






//        mMeetingDayCalendar + mMeetingHourStartCalendar;
//        SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy HH:mm", Locale.FRANCE);
////        String sDateStart = mDateStart;
////        String sDateEnd = mDateEnd;
////        Date dateStart = null;
////        Date dateEnd = null;
////        Date dateStart = DateUtils.   parseDate(sDateStart,
////                new String[] { "yyyy MM dd HH:mm:ss", "dd/MM/yyyy hh:mm:ss" });
//        Date dateStart = null;
//        try {
//            dateStart = formatter.parse(mDateStart);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Date dateEnd = null;
//        try {
//            dateEnd = formatter.parse(mDateEnd);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//        dateStart = formatter.parse(sDateStart);
//        System.out.println(sDateStart+"\t"+dateStart);
////        try {
////            dateStart = formatter.parse(sDateStart);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//        dateEnd = formatter.parse(sDateEnd);
//        System.out.println(sDateEnd+"\t"+dateEnd);
////        try {
////            dateEnd = formatter.parse(sDateEnd);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Integer idRoom = mSeletedRoom.getId();
        int nbUser = usersAddedInMeeting.size();
        // Avoids meeting creation if the duration is 0h0min *******************************

//        else if (mMeetingDayAdded = false) {
//            popupErrorMessage( R.string.meeting_day_empty );
//        } else if (mMeetingStartTimeAdded = false) {
//            popupErrorMessage( R.string.meeting_time_start_empty );
//        } else if (mMeetingEndTimeAdded = false) {
//            popupErrorMessage( R.string.meeting_time_End_empty );
//        }

        if (mSubjectName.length() <= 0) {
            popupErrorMessage( R.string.subject_empty );
        } else if (nbUser == 0) {
            popupErrorMessage( R.string.meeting_user_empty );
        } else if (!mMeetingDayAdded) {
            popupErrorMessage( R.string.meeting_day_empty );
        } else if (!mMeetingStartTimeAdded){
            popupErrorMessage( R.string.meeting_time_start_empty );
        } else if (!mMeetingEndTimeAdded) {
            popupErrorMessage( R.string.meeting_time_End_empty );
        } else if (mSeletedRoom.getId() == null) {
            popupErrorMessage( R.string.empty_room );
        } else if (!mApiService.checkRoomBookedOff(idRoom, mMeetingStartDate, mMeetingEndDate)) {
            popupErrorMessage( R.string.room_already_booked );
        } else {
            Calendar calStart = Calendar.getInstance();
            calStart.set(Calendar.YEAR,mMeetingDayCalendar.get(Calendar.YEAR));
            calStart.set(Calendar.MONTH,mMeetingDayCalendar.get(Calendar.MONTH));
            calStart.set(Calendar.DAY_OF_MONTH,mMeetingDayCalendar.get(Calendar.DAY_OF_MONTH));
            calStart.set(Calendar.HOUR_OF_DAY,mMeetingHourStartCalendar.get(Calendar.HOUR_OF_DAY));
            calStart.set(Calendar.MINUTE,mMeetingHourStartCalendar.get(Calendar.MINUTE));
            mMeetingStartDate = calStart.getTime();

            Calendar calEnd = Calendar.getInstance();
            calEnd.set(Calendar.YEAR,mMeetingDayCalendar.get(Calendar.YEAR));
            calEnd.set(Calendar.MONTH,mMeetingDayCalendar.get(Calendar.MONTH));
            calEnd.set(Calendar.DAY_OF_MONTH,mMeetingDayCalendar.get(Calendar.DAY_OF_MONTH));
            calEnd.set(Calendar.HOUR_OF_DAY,mMeetingHourEndCalendar.get(Calendar.HOUR_OF_DAY));
            calEnd.set(Calendar.MINUTE,mMeetingHourEndCalendar.get(Calendar.MINUTE));
            mMeetingEndDate = calEnd.getTime();
            Meeting meeting = new Meeting(
                    mMeetingId,
                    mMeetingStartDate,
                    mMeetingEndDate,
                    Objects.requireNonNull(mSubjectName.getText()).toString(),
                    usersAddedInMeeting,
                    mSeletedRoom
            );
            mApiService.createMeeting(meeting);
            finish();
        }
    }

     private void popupErrorMessage(int intString) {
        Toast alertToast = Toast.makeText(this, intString, Toast.LENGTH_SHORT);
//         TextView alertError =  ;
         alertToast.show();

    }


//
//    String dateStr = "04/05/2010";
//
//    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
//    Date dateObj = curFormater.parse(dateStr);
//    SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
//
//    String newDateStr = postFormater.format(dateObj);

}

