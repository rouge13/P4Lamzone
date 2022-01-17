package julien.hammer.p4lamzone;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProjectActivitiesTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void cancelFilterTest() {
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Filtre par salle."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(1)));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.title), withText("Annuler les filtres."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));
    }

    @Test
    public void filterByRoomInMeetingTest(){
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(4)));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Filtre par salle."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(1)));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.title), withText("Annuler les filtres."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(4)));
    }

    @Test
    public void filterByDateInMeetingTest(){
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Filtre par date."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        // Sets a date on the date picker widget
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2021, 11, 16));
        // Confirm the selected date. This example uses a standard DatePickerDialog
        // which uses
        // android.R.id.button1 for the positive button id.
        onView(withId(android.R.id.button1)).perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(1)));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.meeting_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.title), withText("Annuler les filtres."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));
    }

    @Test
    public void backwardInMeetingTest(){
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_meeting), withContentDescription("Add a new meeting"),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));
    }

    @Test
    public void deleteAMeetingTest(){
        //        // Vérification que nous avons bien 3 éléments dans notre liste de meetings
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(4)));
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.item_list_delete_button),
                        childAtPosition(
                                allOf(withId(R.id.fragment_mareu_meeting_item),
                                        childAtPosition(
                                                withId(R.id.list_meetings),
                                                2)),
                                3),
                        isDisplayed()));
        appCompatImageView.perform(click());
        // Vérifier si la suppression d'un des meetings est bien fait dans la liste des meetings 2 pour le nombre de meetings dans la liste car suppression du premier.
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));
    }

    @Test
    public void addANewMeetingTest(){
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(3)));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_meeting), withContentDescription("Add a new meeting"),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());
        onView(allOf(withId(R.id.subject_name))).perform(replaceText("Reunion D"), closeSoftKeyboard());
        onView(allOf(withId(R.id.textEmailAddress))).perform(typeText("c"), closeSoftKeyboard());
        ViewInteraction materialTextView2 = onData(equalTo("charles@lamzone.com")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        // Show the date picker
        onView(withId(R.id.btn_date)).perform(click());
        // Sets a date on the date picker widget
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(2022, 1, 12));
        // Confirm the selected date. This example uses a standard DatePickerDialog
        // which uses
        // android.R.id.button1 for the positive button id.
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the selected date is correct and is displayed in the Ui.
        onView(withId(R.id.in_date)).check(matches(allOf(withText("12/01/2022"),
                isDisplayed())));
        // Show the TimeStart picker
        onView(withId(R.id.btn_time_start)).perform(click());
        // Sets a time in a view picker widget
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(9, 30));
        // Confirm the time
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the date result is displayed.
        onView(withId(R.id.in_time_start)).check(matches(allOf(withText("09:30"),
                isDisplayed())));
        // Show the TimeEnd picker
        onView(withId(R.id.btn_time_end)).perform(click());
        // Sets a time in a view picker widget
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(12, 0));
        // Confirm the time
        onView(withId(android.R.id.button1)).perform(click());
        // Check if the date result is displayed.
        onView(withId(R.id.in_time_end)).check(matches(allOf(withText("12:00"),
                isDisplayed())));
        onView(withId(R.id.spinner_room_to_select)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinner_room_to_select)).check(matches(withSpinnerText(is("Luigi"))));
        onView(withId(R.id.create_meeting)).perform(click());
        onView(AllOf.allOf(withId(R.id.list_meetings), isDisplayed())).check(matches(hasChildCount(4)));
    }

    @Test
    public void projectActivitiesTest() {

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
