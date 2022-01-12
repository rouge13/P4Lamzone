package julien.hammer.p4lamzone;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.OnClick;
import julien.hammer.p4lamzone.di.DI;
import butterknife.BindView;
import butterknife.ButterKnife;
import julien.hammer.p4lamzone.events.DeleteMeetingEvent;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;
import julien.hammer.p4lamzone.service.MareuApiService;
import julien.hammer.p4lamzone.ui.mareu.AddMeetingActivity;
import julien.hammer.p4lamzone.ui.mareu.MareuRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private MareuApiService mApiService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;
    @SuppressLint("NonConstantResourceId")
    List<Meeting> mMeetings = null;
    List<User> mUsers = null;
    List<Room> mRooms = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMareuApiService();
        setContentView(R.layout.activity_main);
//        configureActionButtonBackward();
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);
        mMeetings = mApiService.getMeetings();
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

    @SuppressLint("NonConstantResourceId")
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

        mUsers = mApiService.getUsers();
        mRooms = mApiService.getRooms();
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
    @SuppressLint("NonConstantResourceId")
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
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR,year);
                            cal.set(Calendar.MONTH,monthOfYear);
                            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            mMeetings = mApiService.getMeetingsByDate(cal.getTime());
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
        List<String> roomNameList = new ArrayList<>();
        List<Room> mRooms = mApiService.getRooms();
        for(Room room:mRooms){
            roomNameList.add(room.getName());
        }
        builder.setItems(roomNameList.toArray(new String[roomNameList.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMeetings = mApiService.getMeetingsByRoom(mRooms.get(which));
                initList();
            }
        });// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cancelFilters(){
        mMeetings = mApiService.getMeetings();
        initList();
    }
}