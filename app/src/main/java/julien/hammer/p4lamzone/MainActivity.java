package julien.hammer.p4lamzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
//    private RecyclerView mRecyclerView;
    private ProjectMareuApiService mProjectMareuApiService;
    private MareuRecyclerViewAdapter mMareuRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMareuApiService();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);

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
        List<Meeting> mMeetings = mApiService.getMeetings();
        List<User> mUsers = mApiService.getUsers();
        List<Room> mRooms = mApiService.getRooms();
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
    @OnClick(R.id.add_meeting)
    void addMeetings() {
        AddMeetingActivity.navigate(this);
    }
}