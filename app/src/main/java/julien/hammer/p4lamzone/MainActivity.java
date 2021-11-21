package julien.hammer.p4lamzone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import julien.hammer.p4lamzone.di.DI;
import butterknife.BindView;
import butterknife.ButterKnife;
import julien.hammer.p4lamzone.events.DeleteMeetingEvent;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;
import julien.hammer.p4lamzone.service.MareuApiService;
import julien.hammer.p4lamzone.service.ProjectMareuApiService;
import julien.hammer.p4lamzone.ui.mareu.AddMeetingActivity;
import julien.hammer.p4lamzone.ui.mareu.MareuRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private MareuApiService mApiService;
    //    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;
    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.date_filter)
//    MenuItem mDateFilter;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.room_filter)
//    MenuItem mRoomFilter;
    Boolean mRoomFilterActivated = false;
    Boolean mDateFilterActivated = false;
    List<Meeting> mMeetings = null;
    List<User> mUsers = null;
    List<Room> mRooms = null;
//    private RecyclerView mRecyclerView;
//    private ProjectMareuApiService mProjectMareuApiService;
//    private MareuRecyclerViewAdapter mMareuRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMareuApiService();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);
        initList();
    }

    /**
     * Get the menu to add the filter_menu to the main activity
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.date_filter:
                dateSelected();
                return true;
            case R.id.room_filter:
                roomSelected();
                return true;
            case R.id.cancel_filters:
                cancelFilters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
//        Charger la liste suivant la page sélectionné
        initList();
        return view;
    }

    private void initList() {

        if (!mDateFilterActivated & !mRoomFilterActivated){
            mMeetings = mApiService.getMeetings();
        }
        mUsers = mApiService.getUsers();
        mRooms = mApiService.getRooms();
//        mMareuRecyclerViewAdapter = new MareuRecyclerViewAdapter(mMeetings,mUsers,mRooms);
        mRecyclerView.setAdapter(new MareuRecyclerViewAdapter(mMeetings, mUsers, mRooms));

    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        Context context = getApplicationContext();
        CharSequence text = "Suppression de la réunion bien effectué";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
        mApiService.deleteMeeting(event.meeting);
        initList();

    }
    /**
    * Add a new meeting
     */
    @OnClick(R.id.add_meeting)
    void addMeetings() {
        AddMeetingActivity.navigate(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dateSelected() {
            final Calendar c = Calendar.getInstance();
            // Get Current Date
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
//                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
//                            mApiService.getMeetingsByDate(cal.getTime());
                            mMeetings = mApiService.getMeetingsByDate(cal.getTime());
                            mDateFilterActivated = true;
                            initList();

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
              datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
              datePickerDialog.show();

    }

    public void roomSelected() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisissez la salle");// add a list
//        String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        List<String> roomNameList = new ArrayList<>();
        List<Room> mRooms = mApiService.getRooms();
        for(Room room:mRooms){
            roomNameList.add(room.getName());
        }

        builder.setItems(roomNameList.toArray(new String[roomNameList.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMeetings = mApiService.getMeetingsByRoom(mRooms.get(which));
                mRoomFilterActivated = true;
                initList();

            }
        });// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();



    }

    public void cancelFilters(){
        mRoomFilterActivated = false;
        mDateFilterActivated = false;
        initList();
    }

//    @OnClick(R.id.room_filter)
//    void roomFilter() {
//        mRoomFilter = true;
//    }
//
//    @OnClick(R.id.date_filter)
//    void dateFilter() {
//        mDateFilter = true;
//    }

}