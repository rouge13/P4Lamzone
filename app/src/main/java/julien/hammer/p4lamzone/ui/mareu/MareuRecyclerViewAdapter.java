package julien.hammer.p4lamzone.ui.mareu;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import julien.hammer.p4lamzone.events.DeleteMeetingEvent;
import julien.hammer.p4lamzone.model.Meeting;
import julien.hammer.p4lamzone.R;
import julien.hammer.p4lamzone.model.Room;
import julien.hammer.p4lamzone.model.User;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class MareuRecyclerViewAdapter extends RecyclerView.Adapter<MareuRecyclerViewAdapter.ViewHolder>{
    private final List<Meeting> mMeetings;
    private final List<User> mUsers;
    private final List<Room> mRooms;

    public MareuRecyclerViewAdapter(List<Meeting> mMeetings, List<User> mUsers, List<Room> mRooms) {
        this.mMeetings = mMeetings;
        this.mUsers = mUsers;
        this.mRooms = mRooms;
    }
    @SuppressLint("NonConstantResourceId")
    @BindDrawable(R.drawable.ic_baseline_brightness_pale_green_1_24)
    public Drawable mPastilMoreThan5CanJoin;
    @SuppressLint("NonConstantResourceId")
    @BindDrawable(R.drawable.ic_baseline_brightness_pale_orange_1_24)
    public Drawable mPastilBellow5CanJoin;
    public Drawable mPastil;

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

        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.FRANCE);
        String strDate = dateFormat.format(date);
        holder.mMeetingTitleItem.setText(holder.mMeetingTitleItem.getContext().getString(R.string.meetingItemTitle,meeting.getSubject(), strDate, meeting.getRoom().getName()));
//        holder.mMeetingTitleItem.setText(String.format( meeting.getSubject(), strDate, meeting.getRoom()));
        if (meeting.getUsers().size() < 5) {
            mPastil = mPastilMoreThan5CanJoin;
        } else {
            mPastil = mPastilBellow5CanJoin;
        }
        holder.mMeetingPastil.setImageDrawable(mPastil);
        for(User user:meeting.getUsers()){
                holder.mMeetingMailsItem.setText(user.getEmail());
        }
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
