package julien.hammer.p4lamzone.ui.mareu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import julien.hammer.p4lamzone.MainActivity;
import julien.hammer.p4lamzone.events.DeleteMeetingEvent;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.R;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;
import julien.hammer.p4lamzone.service.MareuApiService;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class MareuRecyclerViewAdapter extends RecyclerView.Adapter<MareuRecyclerViewAdapter.ViewHolder>{
    private MareuApiService mApiService;
    private final List<Meeting> mMeetings;
    private final List<User> mUsers;
    private final List<Room> mRooms;

    public MareuRecyclerViewAdapter(List<Meeting> mMeetings, List<User> mUsers, List<Room> mRooms) {
        this.mMeetings = mMeetings;
        this.mUsers = mUsers;
        this.mRooms = mRooms;
    }


//    @BindDrawable(R.drawable.ic_baseline_brightness_pale_green_1_24)
//    public Drawable mPastilMoreThan5CanJoin;
//    @BindDrawable(R.drawable.ic_baseline_brightness_pale_orange_1_24)
//    public Drawable mPastilBellow5CanJoin;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MareuRecyclerViewAdapter.ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        Date date = meeting.getStartOfTheMeeting(); // your date

        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String strDate = dateFormat.format(date);
        holder.mMeetingTitleItem.setText(holder.mMeetingTitleItem.getContext().getString(R.string.meetingItemTitle,meeting.getSubject(), strDate, meeting.getRoom().getName()));
//        holder.mMeetingTitleItem.setText(String.format( meeting.getSubject(), strDate, meeting.getRoom()));
        if (meeting.getUsers().size() < 5) {
            holder.mMeetingPastil.setImageResource(R.drawable.ic_baseline_brightness_pale_green_1_24);
        } else {
            holder.mMeetingPastil.setImageResource(R.drawable.ic_baseline_brightness_pale_orange_1_24);
        }
        List<String> userEmailList = new ArrayList<>();
        for(User user:meeting.getUsers()){
//            for (int i = 0; i < meeting.getUsers().size(); i++){
//                holder.mMeetingMailsItem.setText(mUsers.get(i).getEmail());
                userEmailList.add(user.getEmail().toLowerCase());
//            }
        }
        holder.mMeetingMailsItem.setText(userEmailList.toString().replace("[","").replace("]",""));
        holder.mMeetingMailsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                CharSequence text = userEmailList.toString().replace("[","").replace("]","");
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        holder.mMeetingDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_mareu_meeting_item)
        public ConstraintLayout mMeetingLayout;
        @BindView(R.id.pastil_states_item_list)
        public ImageView mMeetingPastil;
        @BindView(R.id.title_item_list)
        public TextView mMeetingTitleItem;
        @BindView(R.id.mails_item_list)
        public TextView mMeetingMailsItem;
        @BindView(R.id.item_list_delete_button)
        public ImageView mMeetingDeleteButton;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
