package julien.hammer.p4lamzone.ui.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import julien.hammer.p4lamzone.R;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final List<Meeting> mMeetings;
    private final List<User> mUsers;
    private final List<Room> mRooms;
    private Spinner mSpinnerRooms;
    private Date mDateStart;
    private Date mDateEnd;


    public AddMeetingActivity(List<Meeting> mMeetings, List<User> mUsers, List<Room> mRooms, Spinner mSpinnerRooms, Date mDateStart, Date mDateEnd) {
        this.mMeetings = mMeetings;
        this.mUsers = mUsers;
        this.mRooms = mRooms;
        this.mSpinnerRooms = mSpinnerRooms;
        this.mDateStart = mDateStart;
        this.mDateEnd = mDateEnd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        List<String> roomNameList = new ArrayList<>();
                for(Room room:mRooms){
            roomNameList.add(room.getName());
        }
        List<String> userEmailList = new ArrayList<>();
        for(User user:mUsers){
            userEmailList.add(user.getEmail());
        }
        mSpinnerRooms = (Spinner)findViewById(R.id.spinner_room_to_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,roomNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRooms.setAdapter(adapter);
        mSpinnerRooms.setOnItemSelectedListener(this);
        MultiAutoCompleteTextView textEmailAddress = findViewById(R.id.textEmailAddress);
        ArrayAdapter adapterLanguages
                = new ArrayAdapter(this,android.R.layout.simple_list_item_1,userEmailList);
        textEmailAddress.setAdapter(adapterLanguages);
        textEmailAddress.setThreshold(1);
        // The text separated by commas
        textEmailAddress.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        mRooms.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
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

