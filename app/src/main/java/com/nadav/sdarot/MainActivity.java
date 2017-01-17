package com.nadav.sdarot;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanfuentes.hintcase.HintCase;
import com.joanfuentes.hintcase.RectangularShape;
import com.joanfuentes.hintcaseassets.hintcontentholders.SimpleHintContentHolder;
import com.joanfuentes.hintcaseassets.shapeanimators.RevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.RevealRectangularShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealCircleShapeAnimator;
import com.joanfuentes.hintcaseassets.shapeanimators.UnrevealRectangularShapeAnimator;
import com.joanfuentes.hintcaseassets.shapes.CircularShape;
import com.nadav.sdarot.DrawerFragments.ReminderFragment;
import com.nadav.sdarot.DrawerFragments.SyncDriveFragment;
import com.nadav.sdarot.DrawerFragments.Top10Fragment;
import com.nadav.sdarot.DrawerFragments.TutorialFragment;
import com.nadav.sdarot.DrawerFragments.feedbackFragment;
import com.nadav.sdarot.preferences.AlarmPreferencesActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import ru.katso.livebutton.LiveButton;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.widget.Toast.*;
import static java.lang.System.*;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    static FloatingActionButton plus;
    ArrayAdapter<CharSequence> spinnerAdapter;
    static int  position_for_remove;
    public static DrawerLayout mDrawerLayout;
    static ListView listview;
    static AutoCompleteTextView select_Series;
    static String selected_series, selectedUrlImage;
    static MaterialNumberPicker np, np2;
    static TextView t, t2, t3;
    MenuItem miActionProgressItem;
    InterstitialAd mInterstitialAd;
    Adapter ap;
    AdapterSelectSeries spinnerAdapter2;
    Fragment fragment;
    int checkNextEpisode;
    ActionBar actionBar;
    private ListView mDrawerList;
    static AdView mAdView;
    DatabaseReference myRef;
    private DatabaseReference mDatabase;
    String list = "";
    static int click= 0;
    static private FirebaseAuth auth;
    LinearLayout linprogress;
    static View fr;
    private boolean ifstart = false;


    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);
        ifstart = true;
        auth = FirebaseAuth.getInstance();
        linprogress = (LinearLayout) findViewById(R.id.linprogress);
        /*costumaize the action bar*/
        final Context context = this;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.layout, null);
        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());

        //assign the view to the actionbar
        actionBar.setCustomView(v);
        /*end costumaize the action bar*/


        fr =  findViewById(R.id.fragments);

        /*costumaize the  ads*/
        //banner ad
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("284C628A80680C07E21AE13728ADE937")
                .addTestDevice("0E9830DF43C4EB440157B8C079727CF9")
                .build();
        mAdView.loadAd(adRequest);
        //InterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6162413570337571/7247832040");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        /*end costumaize the  ads*/


        File file_alarm = new File(getApplicationContext().getFilesDir(), "alarm");
        String list_alarm = FileManager.readFromFile(file_alarm);

        if (list_alarm.equals("") || list_alarm.equals("first")) {
            final AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            final PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.HOUR_OF_DAY) < 20) {
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, pIntent);
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, pIntent);
            }
            FileManager.writeToFile(file_alarm, "second");
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.Right_drawer);

                            /*set up drawer*/
        // set up the drawer's list view with items and click listener
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[7];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.tutorial_icon, getResources().getString(R.string.Tutorial));
        drawerItem[1] = new ObjectDrawerItem(R.drawable.reminder_icon, getResources().getString(R.string.Day_alarm));
        drawerItem[2] = new ObjectDrawerItem(R.drawable.top_series_icon, getResources().getString(R.string.Top_10_series));
        drawerItem[3] = new ObjectDrawerItem(R.drawable.icon_alarm, getResources().getString(R.string.Alarms));
        drawerItem[4] = new ObjectDrawerItem(R.drawable.icon_feedback, getResources().getString(R.string.Send_feedback));
        drawerItem[5] = new ObjectDrawerItem(R.drawable.iconrate, getResources().getString(R.string.Rate_App));
        drawerItem[6] = new ObjectDrawerItem(R.drawable.login_icon, "Account Settings");
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_list_item, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.action_search,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                mDrawerLayout.setVisibility(View.INVISIBLE);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
                        /*end set up drawer*/



        listview = (ListView) fr.findViewById(R.id.listView);
        ap = new Adapter(getApplicationContext(), R.layout.row_layout);
        listview.setAdapter(ap);
        plus = (FloatingActionButton) fr.findViewById(R.id.fab);
        selected_series = "";

                    /* ** getting the data from save ** */
                            click = 1;
                            getDataFromMemory();


                    /* **listview touches** */
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listview,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {

                            @Override
                            public boolean canDismiss(int position) {
                                plus.setVisibility(View.VISIBLE);
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (final int position : reverseSortedPositions) {


                                    if (mInterstitialAd.isLoaded()) {
//                                        mInterstitialAd.show();
                                        update(listview, ap, position);
                                    }else {
                                        update(listview, ap, position);
                                    }
                                }
                                ap.notifyDataSetChanged();

                            }
                        });
        listview.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(ap.getCount()>4){
                    if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
                        plus.hide();
                    }else{
                        plus.show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_for_remove = position;
                EpisodeClass a = (EpisodeClass) ap.getItem(position) ;
                new seriesCounterBeforeEditMode().execute(a.getName_of_series());
                showOneClickDialog(a);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                ap.remove(position);
                                save(context, listview, ap);
                                checkSetHelpGuide(listview.getCount());
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(position);
                String name_of_del_series = x1.getName_of_series();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setIcon(R.drawable.icon_tv_clock).
                        setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_stop_following_this_series) + "  \"" + name_of_del_series + "\"?")
                        .setPositiveButton(R.string.Yes, dialogClickListener)
                        .setNegativeButton(R.string.No, dialogClickListener)
                        .show().setIcon(R.drawable.icon_tv_clock);
                return true;
            }
        });
                         /*end listview touches*/


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {

                /*make the dialog*/
                final Dialog d = new Dialog(context);
                d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    d.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                }
//                    WindowManager.LayoutParams wmlp = d.getWindow().getAttributes();
//                wmlp.gravity = Gravity.TOP | Gravity.CENTER;
//                wmlp.x = 11;   //x position
//                wmlp.y = 200;   //y position
                d.setContentView(R.layout.dialog_layout);

                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        plus.show();
                    }
                });

                final LiveButton b1 = (LiveButton) d.findViewById(R.id.buttonOk);
                LiveButton CANCEL = (LiveButton) d.findViewById(R.id.button2);
                select_Series = (AutoCompleteTextView) d.findViewById(R.id.autoCompleteTextView);
                t = (TextView) d.findViewById(R.id.textView4);
                t2 = (TextView) d.findViewById(R.id.textView555);
                t3 = (TextView) d.findViewById(R.id.TextView01);
                ImageView imageSearch = (ImageView) d.findViewById(R.id.imageView5);

                selected_series = "";
                selectedUrlImage = "";

                select_Series.setHintTextColor(Color.parseColor("#81d4e6"));
                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nrkis.ttf");
                t.setTypeface(tf);


                np = (MaterialNumberPicker) d.findViewById(R.id.numberPicker1);
                np.setMaxValue(100);
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);


                np2 = (MaterialNumberPicker) d.findViewById(R.id.NumberPicker2);
                np2.setMaxValue(30);
                np2.setMinValue(1);
                np2.setValue(1);
                np2.setWrapSelectorWheel(false);

                np.setEnabled(false);
                np2.setEnabled(false);
                b1.setEnabled(false);
                b1.setTextColor(Color.GRAY);
                b1.setBackgroundColor(ContextCompat.getColor(context, R.color.lightlightgray));
                b1.setShadowColor(Color.GRAY);


                select_Series.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EpisodeClass episode = ((EpisodeClass) spinnerAdapter2.getItem(position));
                                        selected_series = episode.title;
                                        selectedUrlImage = episode.imageUrl;
                                        select_Series.setText(episode.title);

                        t.setTextColor(Color.parseColor("#0294b5"));
                        t2.setTextColor(Color.parseColor(getString(R.string.black)));
                        t3.setTextColor(Color.parseColor(getString(R.string.black)));
                        np.setTextColor(Color.BLACK);
                        np.setSeparatorColor(Color.parseColor("#0294b5"));
                        np2.setTextColor(Color.BLACK);
                        np2.setSeparatorColor(Color.parseColor("#0294b5"));
                        b1.setEnabled(true);
                        b1.setTextColor(Color.WHITE);
                        b1.setBackgroundColor(getResources().getColor(R.color.colorAccent2));
                        b1.setShadowColor(getResources().getColor(R.color.colorPrimaryDark));
                        np.setEnabled(true);
                        np2.setEnabled(true);
                        new seriesCounter().execute();
                    }
                });

                select_Series.requestFocus();

                np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if(!selected_series.equals("")){
                            new SearchTaskForEpisodeNames().execute(newVal + "");
                        }
                    }
                });


                select_Series.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select_Series.setHintTextColor(Color.GRAY);

                    }
                });

                imageSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        new SearchTask().execute(select_Series.getText().toString());

                        new SearchTask(select_Series.getText().toString(), b1).execute();

                        hideKeyboard(select_Series);
                    }
                });

                select_Series.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                            new SearchTask(select_Series.getText().toString(), b1).execute();
                            hideKeyboard(select_Series);
                            return true;
                        }

                        return false;
                    }
                });



                CANCEL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        plus.show();
                    }
                });

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        String num_season = String.valueOf(np2.getValue());
                        String num_episode = String.valueOf(np.getValue());
                        String name_of_series = select_Series.getText().toString();

                        if (name_of_series.equals("")) {
                            makeText(context, R.string.please_select_the_series, LENGTH_SHORT).show();
                            select_Series.requestFocus();
                        }

                        else {

                            if (selectedUrlImage == null || selectedUrlImage.equals("none")) {
                                EpisodeClass obj = new EpisodeClass(2, name_of_series, num_season, num_episode);
                                ap.add(obj);
                                save(context,listview,ap);
                                checkSetHelpGuide(listview.getCount());


                            } else {
                                    //one task for add the series to the listview
                                    //and on this task start other task that chec the date
                                   new OkClickFromPlusDialog(name_of_series, num_season, num_episode).execute();
                            }
                            plus.show();
                            d.dismiss();
                        }
                    }
                });
                plus.hide();
                d.show();

            }
        });
        /* adding tutorial at the first time*/
        if(ap.getCount()==0) {
            File file_tutorial_check = new File(getApplicationContext().getFilesDir(), "file_tutorial_check");
            final String tutorial_check = FileManager.readFromFile(file_tutorial_check);
            if (tutorial_check.equals("")) {

                launchAutomaticHint();
                FileManager.writeToFile(file_tutorial_check, "1");
            }
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    }



    public void update(ListView listview, Adapter ap, int position) {

        EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(position);
        selected_series = x1.getName_of_series();
        new SearchTaskForEpisodeCount(x1, position, ap).execute();

    }

    private void animate(ListView listview) {
        Animation anim = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.shake
        );
        anim.setDuration(1500);
        listview.getChildAt(0).startAnimation(anim);
    }

    private boolean listIsAtTop()   {
        if(listview.getChildCount() == 0) return true;
        return listview.getChildAt(0).getTop() == 0;
    }

    private void animatePaused(ListView listview, int position){
        Animation anim = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.shake
        );
        anim.setDuration(1500);
        if(listview.getChildCount()==position){
            scrollMyListViewToBottom(listview,ap);
            listview.getChildAt(position-1).startAnimation(anim);
        }else if(listIsAtTop()){
            listview.getChildAt(position).startAnimation(anim);
        }else{
            listview.getChildAt(position-1).startAnimation(anim);
        }
    }

    DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(position_for_remove);
                    x1.setNumOfEpisode((Integer.parseInt(x1.getNum_of_episode()) + 1) + "");
                    save(getBaseContext(), listview, ap);
                    ap.notifyDataSetChanged();
                    checkSetHelpGuide(listview.getCount());

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;

            }
        }
    };

                                        /* save */
    public static void save(Context context, ListView listview, Adapter ap) {
        String x = "";
        for (int i = 0; i < ap.getCount(); i++) {
            EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(i);

            x = x + x1.getName_of_series() + "|" + x1.getNum_of_season() + "|" + x1.getNum_of_episode() + "|" + x1.imageUrl + "|" + x1.Date + "|"+ x1.pausetime + "|"+x1.nameOfEpisode+"|"+x1.plot+"|";
        }

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = auth.getCurrentUser();

        click = 0;
        try {
            mDatabase.child("users").child(user.getUid()).setValue(x);
            File f_list = new File(context.getFilesDir(), "list");
            FileManager.writeToFile(f_list, x);
        }catch(Exception e){

        }
    }


    private void scrollMyListViewToBottom(final ListView myListView, final Adapter myListAdapter) {
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(myListAdapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nadav.sdarot/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        save(getBaseContext(), listview, ap);
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nadav.sdarot/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void hideKeyboard(AutoCompleteTextView edit) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    public void setHelpGuideVisible() {
        ImageView img_swipe = (ImageView) findViewById(R.id.imageView);
        TextView text_swipe = (TextView) findViewById(R.id.textView9);
        ImageView img_click = (ImageView) findViewById(R.id.imageView2);
        TextView text_click = (TextView) findViewById(R.id.textView10);
        img_swipe.setVisibility(View.VISIBLE);
        text_swipe.setVisibility(View.VISIBLE);
        img_click.setVisibility(View.VISIBLE);
        text_click.setVisibility(View.VISIBLE);
    }

    public void setHelpGuideInvisible() {
        ImageView img_swipe = (ImageView) findViewById(R.id.imageView);
        TextView text_swipe = (TextView) findViewById(R.id.textView9);
        ImageView img_click = (ImageView) findViewById(R.id.imageView2);
        TextView text_click = (TextView) findViewById(R.id.textView10);
        img_swipe.setVisibility(View.INVISIBLE);
        text_swipe.setVisibility(View.INVISIBLE);
        img_click.setVisibility(View.INVISIBLE);
        text_click.setVisibility(View.INVISIBLE);
    }


    public void makeOwnTextLong(String string) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, string, duration).show();
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.xml_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        switch (item.getItemId()) {

            case android.R.id.home:
            /*actually this is the back button from fragment to the app*/
                getFragmentManager().beginTransaction().remove(fragment).commit();
                mAdView.setVisibility(View.VISIBLE);
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar = getSupportActionBar();
                actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                LayoutInflater inflator = LayoutInflater.from(this);
                View v = inflator.inflate(R.layout.layout, null);
                //if you need to customize anything else about the text, do it here.
                //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
                ((TextView) v.findViewById(R.id.title)).setText(this.getTitle());
                //assign the view to the actionbar
                actionBar.setCustomView(v);
                plus.show();
                for (int i = 0; i < ap.getCount(); i++) {
                    EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(i);
                    new StartTask(x1, i).execute();
                }
                mDrawerLayout.setVisibility(View.INVISIBLE);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                showFloatingActionButton();
                return true;

            case R.id.action_info:

                mDrawerLayout.setVisibility(View.VISIBLE);
                mDrawerLayout.openDrawer(GravityCompat.END);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_info).setVisible(!drawerOpen);


        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item

//        if(ifstart) {
//            for (int i = 0; i < ap.getCount(); i++) {
//                EpisodeClass x1 = (EpisodeClass) listview.getItemAtPosition(i);
//                new StartTask(x1, i).execute();
//            }
//        }
//        ifstart = false;
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            fragment = null;
            switch (position) {
                case 0:
                    fragment = new TutorialFragment();
                    break;
                case 1:
                    fragment = new ReminderFragment();
                    break;
                case 3:
                    Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
                    startActivity(i);
                    break;
                case 2:
                    mAdView.setVisibility(View.INVISIBLE);
                    fragment = new Top10Fragment();
                    break;
                case 4:
                    fragment = new feedbackFragment();
                    break;
                case 5:
                    //open the app page on play store

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.nadav.sdarot")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nadav.sdarot")));
                    }
                    break;
                case 6:
//                    i = new Intent(getApplicationContext(), UserDetails.class);
//                    startActivity(i);
                    fragment = new UserDetails();
                    break;
                default:
                    break;


            }
            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragments, fragment).commit();
                plus.hide();
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");

            }


        }
    }




    private void showOneClickDialog(final EpisodeClass x1) {
        final Dialog d = new Dialog(this);
        selectedUrlImage = x1.imageUrl;
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            d.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        }

        d.setContentView(R.layout.layout_one_click2);

        ListView OneClickList = (ListView) d.findViewById(R.id.listView3);

        final TextView title = (TextView) d.findViewById(R.id.textView20);
        title.setText(x1.getName_of_series());
        TextView titleEpisodeNumber = (TextView) d.findViewById(R.id.textView21);
        titleEpisodeNumber.setText(x1.Date);

        ImageView image = (ImageView) d.findViewById(R.id.imageView8);
        if(x1.imageUrl == null || x1.imageUrl.equals("")){
            image.setImageResource(R.drawable.nophotoavalibale);
        }else{
            Picasso.with(this).load(x1.imageUrl).resize(560,680).into(image);
        }

        //check everything for get the plot
        final TextView plot = (TextView) d.findViewById(R.id.textView);
        plot.setMovementMethod(new ScrollingMovementMethod());

        final TextView nameofepisode = (TextView) d.findViewById(R.id.textViewEpisodeName);
        final ProgressBar progressBarPlot = (ProgressBar) d.findViewById(R.id.progressBarPlot);

        if(x1.Date.charAt(1)=='0'){
            x1.setNextNumOfSeason(x1.Date.charAt(2)+"");
        }else{
            x1.setNextNumOfSeason(x1.Date.substring(1,3));
        }

        if(x1.Date.charAt(4)=='0'){
            x1.setNextNumOfEpisode(x1.Date.charAt(5)+""); ;
        }else{
            x1.setNextNumOfEpisode(x1.Date.substring(4,6));
        }

        nameofepisode.setText("\""+x1.nameOfEpisode+"\"");
        plot.setText(x1.plot);
        progressBarPlot.setVisibility(View.INVISIBLE);


        // set a custom shadow that overlays the main content when the drawer opens
        // set up the drawer's list view with items and click listener
        ObjectDrawerItem[] ObjectItem = new ObjectDrawerItem[5];
        ObjectItem[0] = new ObjectDrawerItem(R.drawable.v_sign_watched, getResources().getString(R.string.I_WATCHED_EPISODE));
        ObjectItem[1] = new ObjectDrawerItem(R.drawable.pause_icon, getResources().getString(R.string.Paused));
        ObjectItem[2] = new ObjectDrawerItem(R.drawable.edit_icon, getResources().getString(R.string.Edit));
        ObjectItem[3] = new ObjectDrawerItem(R.drawable.trash_icon, getResources().getString(R.string.STOP_TRACKING));
        ObjectItem[4] = new ObjectDrawerItem(R.drawable.reminder_icon, getResources().getString(R.string.MAKE_ALARM));

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_list_item, ObjectItem);
        OneClickList.setAdapter(adapter);

        OneClickList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch(position){
                    case 0 :
                        ap.remove(ap.getItem(MainActivity.position_for_remove));
                        update(listview, ap, MainActivity.position_for_remove);
                        d.dismiss();
                        break;
                    case 1 :
                        d.dismiss();
                        final Dialog middleStop = new Dialog(MainActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                            middleStop.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                        }
                        middleStop.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        middleStop.setContentView(R.layout.middlepicker);
                        middleStop.show();
                        LiveButton b = (LiveButton)  middleStop.findViewById(R.id.livebutton);

                        final NumberPicker numberpicker1 = (NumberPicker) middleStop.findViewById(R.id.numberPicker);
                        final NumberPicker numberpicker2 = (NumberPicker) middleStop.findViewById(R.id.numberPicker2);
                        final NumberPicker numberpicker3 = (NumberPicker) middleStop.findViewById(R.id.numberPicker3);
                        numberpicker1.setMinValue(0);
                        numberpicker2.setMinValue(0);
                        numberpicker3.setMinValue(0);
                        numberpicker1.setMaxValue(5);
                        numberpicker2.setMaxValue(60);
                        numberpicker3.setMaxValue(60);

                        String[] str = new String[61];
                        final NumberFormat formatter = new DecimalFormat("00");
                        for(int i=0;i<=60;i++){
                            str[i]= formatter.format(i);
                        }
                        numberpicker1.setDisplayedValues( new String[] { "00", "01", "02", "03" , "04" , "05" } );
                        numberpicker2.setDisplayedValues(str);
                        numberpicker3.setDisplayedValues(str);

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                middleStop.dismiss();
                                Animation anim = AnimationUtils.loadAnimation(
                                        getApplicationContext(), R.anim.shake
                                );
                                anim.setDuration(1500);
                                if(listIsAtTop()){
                                    listview.getChildAt(position_for_remove).startAnimation(anim);
                                }else{
                                    /*add animation to here*/
                                }
                                EpisodeClass ep = (EpisodeClass) ap.getItem(position_for_remove);

                                String theTimeOfPause = formatter.format(numberpicker1.getValue()) +":"+formatter.format(numberpicker2.getValue()) +":"+formatter.format(numberpicker3.getValue()) ;
                                ep.setPausetime(theTimeOfPause);
                                ap.notifyDataSetChanged();
                                save(MainActivity.this, listview, ap);

                            }
                        });

                        break;
                    case 2 :
                        d.dismiss();
                        final Dialog d2 = new Dialog(MainActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                            d2.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                        }
                        d2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        d2.setContentView(R.layout.dialog_layout);
                        TextView t = (TextView) d2.findViewById(R.id.textView4) ;
                        TextView t2 = (TextView) d2.findViewById(R.id.textView555);
                        TextView t3 = (TextView) d2.findViewById(R.id.TextView01);
                        t.setTextColor(Color.parseColor("#0294b5"));
                        t2.setTextColor(Color.parseColor(getString(R.string.black)));
                        t3.setTextColor(Color.parseColor(getString(R.string.black)));


                        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nrkis.ttf");
                        t.setTypeface(tf);
                        np2 = (MaterialNumberPicker) d2.findViewById(R.id.NumberPicker2);
                        np = (MaterialNumberPicker) d2.findViewById(R.id.numberPicker1);
                        np.setTextColor(Color.BLACK);
                        np.setSeparatorColor(Color.parseColor("#0294b5"));
                        np2.setTextColor(Color.BLACK);
                        np2.setSeparatorColor(Color.parseColor("#0294b5"));
                        LiveButton OK = (LiveButton) d2.findViewById(R.id.buttonOk);
                        OK.setEnabled(true);
                        LiveButton CANCEL = (LiveButton) d2.findViewById(R.id.button2);
                        selected_series = "";
                        final AutoCompleteTextView select_Series = (AutoCompleteTextView)
                                d2.findViewById(R.id.autoCompleteTextView);
                        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.list_of_drawer, R.layout.costum_spinner);
                        spinnerAdapter.setDropDownViewResource(R.layout.costum_spinner);
                        select_Series.setAdapter(spinnerAdapter);
                        select_Series.setThreshold(0);
                        selected_series = x1.getName_of_series();

                        select_Series.setText(selected_series);
                        select_Series.setEnabled(false);

                        final EditText edit = (EditText) d2.findViewById(R.id.textstam);

                        np.setMinValue(1);
                        np.setMaxValue(60);
                        np2.setValue(1);
                        new seriesCounterForEditMode(x1).execute();

                        np2.setWrapSelectorWheel(false);

                        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                if(!Objects.equals(x1.imageUrl, "none")) {
                                    new SearchTaskForEpisodeNames().execute(newVal + "");
                                }
                            }
                        });

                        edit.requestFocus();

                        select_Series.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (select_Series.getText().toString().equals("enter series name")) {
                                    select_Series.setText("");
                                }
                            }
                        });

                        select_Series.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select_Series.showDropDown();
                            }
                        });

                        select_Series.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                                    hideKeyboard(select_Series);
                                    select_Series.dismissDropDown();

                                    return true;
                                }
                                return false;
                            }
                        });

                        select_Series.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                hideKeyboard(select_Series);
                                select_Series.dismissDropDown();
                            }
                        });

                        CANCEL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d2.dismiss();
                                d.dismiss();
                            }
                        });

                        OK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String num_season = String.valueOf(np2.getValue());
                                String num_episode = String.valueOf(np.getValue());
                                String name_of_series = select_Series.getText().toString();
                                if (name_of_series.equals("")) {
                                    makeText(MainActivity.this, "please select the series first", LENGTH_SHORT).show();
                                    select_Series.requestFocus();
                                } else {
                                    if (selectedUrlImage.equals("none") || selectedUrlImage == null) {
                                        EpisodeClass obj = new EpisodeClass(2, name_of_series, num_season, num_episode);
                                        ap.remove(position_for_remove);
                                        ap.add(obj);
                                        save(MainActivity.this, listview, ap);
                                        checkSetHelpGuide(listview.getCount());
                                    } else {
                                        new SearchTaskForEpisodeNextDateForEditMode(name_of_series, num_season,num_episode,position_for_remove).execute();
                                    }
        //                                        scrollMyListViewToBottom(listview,ap );
                                    d2.dismiss();
                                    d.dismiss();
                                }
                            }
                        });
                        d2.show();
                        break;
                    case 3:
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //Yes button clicked

                                                ap.remove(position_for_remove);
                                                save(MainActivity.this, listview, ap);
                                                checkSetHelpGuide(listview.getCount());


                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;

                                        }
                                  }
                        };
                        String name_of_del_series = x1.getName_of_series();

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setIcon(R.drawable.icon_tv_clock).
                        setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_stop_following_this_series) + " \"" + name_of_del_series + "\"?")
                        .setPositiveButton(R.string.Yes, dialogClickListener)
                        .setNegativeButton(R.string.No, dialogClickListener)

                        .show().setIcon(R.drawable.icon_tv_clock);
                        d.dismiss();
                        break;

                    case 4:
                        d.dismiss();
                        String name_of__series = x1.getName_of_series();
                        Intent i = new Intent(MainActivity.this, AlarmPreferencesActivity.class);
                        i.putExtra("name", name_of__series);
                        startActivity(i);
                        }
                    }
        });

        d.show();

    }

                                        /* search series on omdb api*/
    private static final int SERIES_FOUND = 0;
    private static final int SERIES_NOT_FOUND = 1;

    private class SearchTask extends AsyncTask<String, Void, Integer> {
        private ProgressDialog pDialog;
        List<EpisodeClass> episodes;
        String s;
        LiveButton b1;

        public SearchTask(String s, LiveButton b1) {
            this.s = s;
            this.b1 = b1;

        }

        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getResources().getString(R.string.Search_the_series_Please_wait));
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.show();
            //do initialization of required objects objects here
        }

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input

            String result = Network.GET("http://www.omdbapi.com/?s=" + s.replaceAll("\\s","+") + "&y=&plot=short&type=series&r=json");

            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Search");
                episodes = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    EpisodeClass episode = new EpisodeClass();
                    episode.title = jsonArray.getJSONObject(i).getString("Title");
                    episode.imageUrl = jsonArray.getJSONObject(i).getString("Poster");
                    episodes.add(episode);
                }
                return SERIES_FOUND;

            } catch (JSONException e) {
                e.printStackTrace();
                return SERIES_NOT_FOUND;
            }

        }

        @Override
        protected void onPostExecute(Integer s) {
         if(s == SERIES_FOUND){
             spinnerAdapter2 = new AdapterSelectSeries(getApplicationContext(),R.array.list_of_drawer,episodes);
             select_Series.setAdapter(spinnerAdapter2);
             select_Series.showDropDown();
             pDialog.dismiss();
         }else{
             pDialog.dismiss();
             Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_to_find_this_series_continue_manually), Toast.LENGTH_LONG);
             toast.setGravity(Gravity.CENTER, 0, 0);
             toast.show();
             b1.setEnabled(true);
             b1.setTextColor(Color.WHITE);
             b1.setBackgroundColor(getResources().getColor(R.color.colorAccent2));
             b1.setShadowColor(getResources().getColor(R.color.colorPrimaryDark));
             selected_series = "";
             t.setTextColor(Color.parseColor("#0294b5"));
             t2.setTextColor(Color.parseColor(getString(R.string.black)));
             t3.setTextColor(Color.parseColor(getString(R.string.black)));
             np.setTextColor(Color.BLACK);
             np.setSeparatorColor(Color.parseColor("#0294b5"));

             np2.setTextColor(Color.BLACK);
             np2.setSeparatorColor(Color.parseColor("#0294b5"));

             np.setEnabled(true);
             np2.setEnabled(true);
         }

        }
    }


                  /*************************************************************/



    private class OkClickFromPlusDialog extends AsyncTask<String, Void, String> {
        String a;
        String name_of_series, num_season, num_episode;
        EpisodeClass obj;
        private String nextep;
        private String date;
        private String nameOfEpisode;



        public OkClickFromPlusDialog(String name_of_series, String num_season, String num_episode) {
            this.name_of_series = name_of_series;
            this.num_episode = num_episode;
            this.num_season = num_season;
        }

        protected void onPreExecute() {
            obj = new EpisodeClass(selectedUrlImage, name_of_series, num_season, num_episode, "");
            ap.add(obj);

            //check if tutorial helpers are nedded
            checkSetHelpGuide(listview.getCount());
            // scroll to the bootom of listview
            scrollMyListViewToBottom(listview,ap );

        }

        protected String doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input


            String result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + num_season + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");
                checkNextEpisode = jsonArray.length();

                if (checkNextEpisode == (Integer.parseInt(num_episode))) {

                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(num_season) + 1) + "" + "&episode=" + 1 + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");
                        if (date == null) {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + "N/A";
                        } else {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        obj.plot = searchArr.getString("Plot");

                    } catch (JSONException e) {
                        //if there isnt data for next episode
                        date = getResources().getString(R.string.No_data_for_next_episode);

                    }
                    return date;
                }else {
                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" +num_season + "&episode=" + (Integer.parseInt(num_episode)+1) + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + "N/A";
                        } else {
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        obj.plot = searchArr.getString("Plot");

                    }catch (JSONException ignored){

                    }

                    return date;
                }


            } catch (JSONException e) {
                e.printStackTrace();
                date = getResources().getString(R.string.No_data_for_next_episode);
                return date;
            }
        }


        protected void onPostExecute(String s) {


            // remove the pre-execute EPISODECLASS and add the EPISODECLASS with the date
            ap.remove(ap.getCount()-1);
            obj.setDate(s);
            obj.setNameOfEpisode(nameOfEpisode);
            obj.setPausetime("");
            ap.add(obj);

            //save
            save(getApplicationContext(), listview, ap);

           // scroll to the bootom of listview
            scrollMyListViewToBottom(listview,ap );
        }

    }


                         /*************************************************************/

    private class SearchTaskForEpisodeNextDateForEditMode extends AsyncTask<String, Void, String> {
        String a;
        String checkNextDate;
        EpisodeClass obj;
        String name_of_series, num_season, num_episode;
        int position;
         private String nextep;
         private String date;
        String nameOfEpisode;


        public SearchTaskForEpisodeNextDateForEditMode(String name_of_series,String num_season,String num_episode,int position) {
            checkNextDate = ("http://www.omdbapi.com/?t=" + name_of_series.replaceAll("\\s", "+") + "&Season=" + num_season + "&episode=" + (Integer.parseInt(num_episode)+1) + "&r=json");
            this.name_of_series = name_of_series;
            this.num_episode = num_episode;
            this.num_season = num_season;
            this.position = position;
        }

        protected void onPreExecute() {
            obj = new EpisodeClass(selectedUrlImage, name_of_series, num_season, num_episode, "");
            ap.remove(position);
            ap.add(obj);
            //check if tutorial helpers are nedded
            scrollMyListViewToBottom(listview,ap );
            checkSetHelpGuide(listview.getCount());
        }

        protected String doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input

            String result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + num_season + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");
                checkNextEpisode = jsonArray.length();

                if (checkNextEpisode == (Integer.parseInt(num_episode))) {

                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(num_season) + 1) + "" + "&episode=" + 1 + "&r=json");

                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + "N/A";
                        } else {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + date;
                        }
                         nameOfEpisode = searchArr.getString("Title");
                         obj.plot = searchArr.getString("Plot");

                    } catch (JSONException e) {
                        date = getResources().getString(R.string.No_data_for_next_episode);

                    }
                    return date;
                }else {
                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" +num_season + "&episode=" + (Integer.parseInt(num_episode)+1) + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + "N/A";
                        } else {
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        obj.plot = searchArr.getString("Plot");

                    }catch (JSONException ignored){

                    }

                    return date;
                }


            } catch (JSONException e) {
                e.printStackTrace();
                return getResources().getString(R.string.No_data_for_next_episode);
            }
        }

        protected void onPostExecute(String s) {
            //remove the previous episodeclass from list and add the new one with the date
            ap.remove(ap.getCount()-1);
            obj.setDate(s);
            obj.setNameOfEpisode(nameOfEpisode);
            obj.setPausetime("");
            ap.add(obj);
            save(getApplicationContext(),listview,ap);

        }

    }

    /**********************************************************/

    private class seriesCounter extends AsyncTask<String, Void, Integer> {
        private ProgressDialog pDialog;
        int maxNP2=0;
        List<String> zoom = new ArrayList<>();
        String[] stringArray;

        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Downloading episodes names. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.show();
            //do initialization of required objects objects here
        }

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input
            int counterFlag = 0;

            /*get total seasons*/
            try {
                    JSONObject searchArr = new JSONObject(Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + 1 + "&r=json"));
                    counterFlag = Integer.parseInt(searchArr.getString("totalSeasons"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                maxNP2=counterFlag;
            /*end of getting total seasons*/


             String result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + counterFlag + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");


                for (int j = 0; j < jsonArray.length(); j++) {
                    zoom.add((j + 1) + " - " + jsonArray.getJSONObject(j).getString("Title"));
                }

                if (zoom.size() == 1) {
                    zoom.add("0000");
                    stringArray = zoom.toArray(new String[zoom.size()]);

                    return 0;

                } else {
                    stringArray = zoom.toArray(new String[zoom.size()]);
                    return 1;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
                return 12;
        }

        protected void onPostExecute(Integer s) {

                np2.setMaxValue(maxNP2);
                np2.setValue(maxNP2);
            Log.e("check", maxNP2+"");

            if (s == 0) {
                    np.setMinValue(1);
                    np.setDisplayedValues(stringArray);
                    np.setMaxValue(zoom.size() - 1);
                    np.setValue(0);

                } else {
                    np.setMinValue(1);
                    np.setMaxValue(zoom.size());
                    np.setDisplayedValues(stringArray);
                    np.setValue(0);
                }

            pDialog.dismiss();


        }

    }

    /*****************************************************************/

    private static final int IN_LAST_CHAPTER = 0;
    private static final int NEXT_SEASON_AVALIABLE = 1;
    private static final int NEXT_SEASON_NOT_AVALIABLE = 2;
    private static final int HAS_MORE_EPISODES = 3;
    private static final int NO_EPISODES = 4;

    private class SearchTaskForEpisodeCount extends AsyncTask<String, Void, Integer> {
        private final EpisodeClass x1;
        private final int position;
        private final Adapter ap;
        private String date;
        private String nextep;
        private String nameOfEpisode;


        public SearchTaskForEpisodeCount(EpisodeClass x1, int position, Adapter ap) {
            this.x1 = x1;
            this.position = position;
            this.ap = ap;
        }

        protected void onPreExecute() {

            if(x1.imageUrl != null || !x1.imageUrl.equals("") ){
                x1.FlagIfSwipe = true;
            }

            //do initialization of required objects objects here
        }

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input

            String result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + x1.getNum_of_season() + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");
                checkNextEpisode = jsonArray.length();

                if (checkNextEpisode == (Integer.parseInt(x1.getNum_of_episode()) + 1)) {
                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(x1.getNum_of_season()) + 1) + "" + "&episode=" + 1 + "&r=json");
                    //now i want to check the date of episode 1 in the next season
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode((Integer.parseInt(x1.getNum_of_season()) + 1),1) +" - " + "N/A";
                        } else {
                            date = formatEpisode((Integer.parseInt(x1.getNum_of_season()) + 1),1) +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        x1.plot = searchArr.getString("Plot");

                    }catch (JSONException e){
                        date = getResources().getString(R.string.No_data_for_next_episode);
                    }
                        return IN_LAST_CHAPTER;
                }
                else if (checkNextEpisode < (Integer.parseInt(x1.getNum_of_episode()) + 1)) { //no more episodes this series
                        //check if there is more series
                        result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(x1.getNum_of_season())+1) + "&r=json");

                        try {
                            searchArr = new JSONObject(result);
                            searchArr.getJSONArray("Episodes");

                            //this is the episode to check - episode 2
                            nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(x1.getNum_of_season()) + 1) + "" + "&episode=" + 2 + "&r=json");
                            //now i want to check the date of episode 2 in the next series
                            try {

                                searchArr = new JSONObject(nextep);
                                date = searchArr.getString("Released");
                                if (date == null) {
                                    date = formatEpisode((Integer.parseInt(x1.getNum_of_season()) + 1),2) +" - " + "N/A";
                                } else {
                                    date = formatEpisode((Integer.parseInt(x1.getNum_of_season()) + 1),2) +" - " + date;
                                }
                                nameOfEpisode = searchArr.getString("Title");
                                x1.plot = searchArr.getString("Plot");


                            }catch (JSONException e){
                                date = formatEpisode((Integer.parseInt(x1.getNum_of_season()) + 1),2) +" - " + "N/A";
                            }
                            return NEXT_SEASON_AVALIABLE;

                        } catch (JSONException e) {
                        e.printStackTrace();
                            date = getResources().getString(R.string.No_data_for_next_episode);
                        }

                    return NEXT_SEASON_NOT_AVALIABLE;


                } else {

                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" +x1.getNum_of_season() + "&episode=" + (Integer.parseInt(x1.getNum_of_episode())+2) + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode(x1.getNum_of_season(),(Integer.parseInt(x1.getNum_of_episode())+2)+"") +" - " + "N/A";

                        } else {
                            date = formatEpisode(x1.getNum_of_season(),(Integer.parseInt(x1.getNum_of_episode())+2)+"") +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        x1.plot = searchArr.getString("Plot");

                    }catch (JSONException e){
                        date = getResources().getString(R.string.No_data_for_next_episode);
                        //there is more episodes without data of the dates
                    }

                    return HAS_MORE_EPISODES;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                date = getResources().getString(R.string.No_data_for_next_episode);

            }
            return NO_EPISODES;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == HAS_MORE_EPISODES) {//when there is more episodes
                x1.setNumOfEpisode((Integer.parseInt(x1.getNum_of_episode()) + 1) + "");
                x1.setDate(date);
                x1.FlagIfSwipe = false;
                x1.setNameOfEpisode(nameOfEpisode);
            }else
            if (integer == IN_LAST_CHAPTER) {
                x1.setNumOfEpisode((Integer.parseInt(x1.getNum_of_episode()) + 1) + "");
                x1.setDate(date);
                x1.FlagIfSwipe = false;
                x1.setNameOfEpisode(nameOfEpisode);

            } else if (integer == NEXT_SEASON_AVALIABLE) { // there is another series?
                makeOwnTextLong("episode number " + x1.getNum_of_episode() + " was the last episode of season " + x1.getNum_of_season());
                x1.setNumOfSeason((Integer.parseInt(x1.getNum_of_season()) + 1) + "");
                x1.setNumOfEpisode("1");
                x1.setDate(date);
                x1.FlagIfSwipe = false;
//                animate(listview, position);
                x1.setNameOfEpisode(nameOfEpisode);

            } else if (integer == NEXT_SEASON_NOT_AVALIABLE) {//when there isnt more episodes and either more series
                //the dialog ask the user if he sure that he saw this episode because there is no more episode or series
                x1.FlagIfSwipe = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setIcon(R.drawable.icon_tv_clock).
                        setMessage("episode " + (Integer.parseInt(x1.getNum_of_episode()) + 1) + " didnt published yet. are you sure you saw it?")
                        .setPositiveButton("Yes", dialogClickListener2)
                        .setNegativeButton("No", dialogClickListener2)
                        .show().setIcon(R.drawable.icon_tv_clock);
                position_for_remove = position;
            } else if(integer == NO_EPISODES){
                x1.setNumOfEpisode((Integer.parseInt(x1.getNum_of_episode()) + 1) + "");
                x1.setDate(getResources().getString(R.string.No_data_for_next_episode));
                x1.FlagIfSwipe = false;

            }
                if(position == ap.getCount()-1){
                    scrollMyListViewToBottom(listview,ap);
                }else{
                    listview.setSelection(position);
                }
            x1.setPausetime("");
            makeOwnTextLong(x1.nameOfEpisode);
            save(MainActivity.this, listview, ap);
            ap.notifyDataSetChanged();
        }
    }

    private class seriesCounterForEditMode extends AsyncTask<String, Void, Integer> {
        private ProgressDialog pDialog;
        private final EpisodeClass x1;
        int number_of_max_seasons;
        String[] stringArray = new String[50];
        List<String> zoomEdit = new ArrayList<>();


        public seriesCounterForEditMode(EpisodeClass x1) {
            this.x1 = x1;
        }


        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Downloading episodes names. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.show();
            //do initialization of required objects objects here
        }

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input
            number_of_max_seasons = 0;

            String result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + x1.getNum_of_season() + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");

                for (int j = 0; j < jsonArray.length();j++) {
                    zoomEdit.add((j + 1) + " - " + jsonArray.getJSONObject(j).getString("Title"));
                }

                if (zoomEdit.size() == 1) {
                    zoomEdit.add("0000");
                    stringArray = zoomEdit.toArray(new String[zoomEdit.size()]);
                    return 0;
                } else {
                    stringArray = zoomEdit.toArray(new String[zoomEdit.size()]);
                    return 1;
                }


            } catch (JSONException e) {

                e.printStackTrace();
                return 2;
            }


        }

        protected void onPostExecute(Integer s) {
            SharedPreferences sp = getSharedPreferences("key", 0);
            String tValue = sp.getString("textvalue","");
            number_of_max_seasons = Integer.parseInt(tValue);
            if (number_of_max_seasons == 0) {
                np2.setMaxValue(30);
                np2.setMinValue(1);
                np2.setValue(Integer.parseInt(x1.getNum_of_season()));

            } else {
                np2.setMaxValue(number_of_max_seasons);
                np2.setMinValue(1);
                np2.setValue(Integer.parseInt(x1.getNum_of_season()));
            }

            if (s == 0) {
                    np.setDisplayedValues(stringArray);
                    np.setMaxValue(zoomEdit.size() - 1);
                    np.setValue(0);
            } else if (s == 1) {
                    np.setMaxValue(zoomEdit.size());
                    np.setDisplayedValues(stringArray);
                    np.setValue(Integer.parseInt(x1.getNum_of_episode()));

            } else if (s==2){
                np.setValue(Integer.parseInt(x1.getNum_of_episode()));
            }
            np2.setWrapSelectorWheel(false);
            np.setWrapSelectorWheel(false);
            pDialog.dismiss();

        }
    }


    private class SearchTaskForEpisodeNames extends AsyncTask<String, Void, Integer> {
        String[] stringArray;
        List<String> zoom = new ArrayList<>();

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input

            String  result = Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s","+") +"&Season="+ params[0] +"&r=json");

            try {

                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");

                for (int i = 0; i < jsonArray.length(); i++) {
                    zoom.add((i+1) + " - " + jsonArray.getJSONObject(i).getString("Title"));
                }

                if(zoom.size()==1){
                    zoom.add("0000");
                    stringArray = zoom.toArray(new String[zoom.size()]);
                    return 0;

                }else{
                    stringArray = zoom.toArray(new String[zoom.size()]);
                    return 1;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return 2;
        }

        @Override
        protected void onPostExecute(Integer s) {
                np.setValue(0);

                if(s==0){
                    np.setDisplayedValues(stringArray);
                    np.setMaxValue(zoom.size()-1);
                    np.setValue(0);

                }else if(s==1){
                    np.setDisplayedValues(stringArray);
                    np.setMaxValue(zoom.size());
                    np.setValue(0);
                }else{
                    np.setMinValue(1);
                    np.setMaxValue(60);

                }

        }
    }


    private class seriesCounterBeforeEditMode extends AsyncTask<String, Void, Integer> {
        int number_of_max_seasons;

        @Override
        protected Integer doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input
            number_of_max_seasons = 0;
            int counterFlag = 0;
                /*get total seasons*/
            try {
                JSONObject searchArr = new JSONObject(Network.GET("http://www.omdbapi.com/?t=" + selected_series.replaceAll("\\s", "+") + "&Season=" + 1 + "&r=json"));
                counterFlag = Integer.parseInt(searchArr.getString("totalSeasons"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            number_of_max_seasons=counterFlag;
            /*end of getting total seasons*/

            return number_of_max_seasons;
        }

        protected void onPostExecute(Integer s) {
            SharedPreferences sp = getSharedPreferences("key", 0);
            SharedPreferences.Editor sedt = sp.edit();
            sedt.putString("textvalue", s+"");
            sedt.apply();

        }
    }



    private class StartTask extends AsyncTask<String, Void, String> {
        String a;
        EpisodeClass obj;
        String name_of_series, num_season, num_episode;
        int position;
        private String nextep;
        private String date;
        String nameOfEpisode;


        public StartTask(EpisodeClass obj, int position) {
            this.name_of_series = obj.getName_of_series();
            this.num_episode = obj.getNum_of_episode();
            this.num_season = obj.getNum_of_season();
            this.position = position;
            this.obj = obj;
        }


        protected void onPreExecute() {

            showProgressBar();
        }

        protected String doInBackground(String... params) {
            // param[0] - we know we will have only one param, our search input

            String result = Network.GET("http://www.omdbapi.com/?t=" + name_of_series.replaceAll("\\s", "+") + "&Season=" + num_season + "&r=json");
            try {
                JSONObject searchArr = new JSONObject(result);
                JSONArray jsonArray = searchArr.getJSONArray("Episodes");
                checkNextEpisode = jsonArray.length();

                if (checkNextEpisode == (Integer.parseInt(num_episode))) {

                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + name_of_series.replaceAll("\\s", "+") + "&Season=" + (Integer.parseInt(num_season) + 1) + "" + "&episode=" + 1 + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + "N/A";

                        } else {
                            date = formatEpisode((Integer.parseInt(num_season) + 1),1) +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        obj.plot = searchArr.getString("Plot");

                    } catch (JSONException e) {
                        date = getResources().getString(R.string.No_data_for_next_episode);

                    }
                    return date;
                }else {
                    //this is the episode to check
                    nextep = Network.GET("http://www.omdbapi.com/?t=" + name_of_series.replaceAll("\\s", "+") + "&Season=" +num_season + "&episode=" + (Integer.parseInt(num_episode)+1) + "&r=json");
                    //now i want to check the date of episode 1 in the next series
                    try {
                        searchArr = new JSONObject(nextep);
                        date = searchArr.getString("Released");

                        if (date == null) {
//                            date = " Episode "+ (Integer.parseInt(num_episode)+1)  + " - " + "N/A";
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + "N/A";

                        } else {
                            date = formatEpisode(num_season,(Integer.parseInt(num_episode)+1)+"") +" - " + date;
                        }
                        nameOfEpisode = searchArr.getString("Title");
                        obj.plot = searchArr.getString("Plot");

                    }catch (JSONException ignored){

                    }

                    return date;
                }


            } catch (JSONException e) {
                e.printStackTrace();
                return getResources().getString(R.string.No_data_for_next_episode);
            }
        }

        protected void onPostExecute(String s) {
            //remove the previous episodeclass from list and add the new one with the date
            if(s == null){
                obj.setDate(getResources().getString(R.string.No_data_for_next_episode));
            }else {
                obj.setDate(s);
            }
            obj.setNameOfEpisode(nameOfEpisode);
            save(getApplicationContext(),listview,ap);
            ap.notifyDataSetChanged();
            hideProgressBar();
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();}
        }

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("284C628A80680C07E21AE13728ADE937")
                .addTestDevice("0E9830DF43C4EB440157B8C079727CF9")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }



    public static String formatEpisode (int num_season, int num_episode){
        String seas;
        String epis;

        if (num_season<10){
            seas = "0"+num_season;
        }else{
            seas = num_season+"";
        }

        if (num_episode<10){
            epis = "0"+num_episode;
        }else{
            epis = num_episode+"";
        }

        return "S"+seas+"E"+epis;


    }

    public static String formatEpisode (String num_season, String num_episode) {
        String seas;
        String epis;

        if (Integer.parseInt(num_season)<10){
            seas = "0"+num_season;
        }else{
            seas = num_season;
        }

        if (Integer.parseInt(num_episode)<10){
            epis = "0"+num_episode;
        }else{
            epis = num_episode+"";
        }

        return "S"+seas+"E"+epis;
    }

    public void checkSetHelpGuide(int number){
        if (number <= 2 && number!=0) {
            //make help views to be visible
            setHelpGuideVisible();
        } else {
            //make help views to be invisible
            setHelpGuideInvisible();
        }
    }





    private void launchAutomaticHint() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View actionSearchView = findViewById(R.id.fab);

                if (actionSearchView != null) {
                    SimpleHintContentHolder blockInfo =
                            new SimpleHintContentHolder.Builder(actionSearchView.getContext())
                                    .setContentTitle("Add series")
                                    .setContentText("Press here to add series for tracking")
                                    .setTitleStyle(R.style.title)
                                    .setContentStyle(R.style.content)
                                    .setGravity(Gravity.BOTTOM | Gravity.CENTER)
                                    .setMarginByResourcesId(R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin,
                                            R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin)
                                    .build();
                    new HintCase(actionSearchView.getRootView())
                            .setTarget(actionSearchView, new CircularShape())
                            .setShapeAnimators(new RevealCircleShapeAnimator(),
                                    new UnrevealCircleShapeAnimator())
                            .setHintBlock(blockInfo)
                            .setOnClosedListener(new HintCase.OnClosedListener() {
                                @Override
                                public void onClosed() {
                                    EpisodeClass obj = new EpisodeClass("http://ia.media-imdb.com/images/M/MV5BMTk1MjYzOTU2Nl5BMl5BanBnXkFtZTgwMzAxMTg5MTE@._V1_SX300.jpg", "Suits", 5+"", 4+"", "S05E05 - 22 Jul 2015");
                                    ap.add(obj);

                                    launchAutomaticHint2();
                                }
                            })
                            .show();
                }
            }
        }, 500);
    }

    private void launchAutomaticHint2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                ListView list =  (ListView)findViewById(R.id.listView);
                View actionSearchView = list.getChildAt(0);

                if (actionSearchView != null) {
                    SimpleHintContentHolder blockInfo =
                            new SimpleHintContentHolder.Builder(actionSearchView.getContext())
                                    .setContentTitle("Click")
                                    .setContentText("Click on the series name for more options")
                                    .setTitleStyle(R.style.title)
                                    .setContentStyle(R.style.content)
                                    .setGravity(Gravity.TOP | Gravity.CENTER)
                                    .setMarginByResourcesId(R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin,
                                            R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin)
                                    .build();

                    new HintCase(actionSearchView.getRootView())
                            .setTarget(actionSearchView, new RectangularShape())
                            .setShapeAnimators(new RevealRectangularShapeAnimator(),
                                    new UnrevealRectangularShapeAnimator())
                            .setHintBlock(blockInfo)
                            .setOnClosedListener(new HintCase.OnClosedListener() {
                                @Override
                                public void onClosed() {

                                    if(ap.getCount()!=0) {

                                        launchAutomaticHint2andhalf();
                                    }
                                }
                            })
                            .show();

                }
            }
        }, 10);
    }

    private void launchAutomaticHint2andhalf() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                animate(listview);

                ListView list =  (ListView)findViewById(R.id.listView);
                View actionSearchView = list.getChildAt(0);

                if (actionSearchView != null) {
                    SimpleHintContentHolder blockInfo =
                            new SimpleHintContentHolder.Builder(actionSearchView.getContext())
                                    .setContentTitle("Swipe")
                                    .setContentText("Swipe left or right when you see new episode")
                                    .setTitleStyle(R.style.title)
                                    .setContentStyle(R.style.content)
                                    .setGravity(Gravity.TOP | Gravity.CENTER)
                                    .setMarginByResourcesId(R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin,
                                            R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin)
                                    .build();

                    new HintCase(actionSearchView.getRootView())
                            .setTarget(actionSearchView, new RectangularShape())
                            .setShapeAnimators(new RevealRectangularShapeAnimator(),
                                    new UnrevealRectangularShapeAnimator())
                            .setHintBlock(blockInfo)
                            .setOnClosedListener(new HintCase.OnClosedListener() {
                                @Override
                                public void onClosed() {

                                    if(ap.getCount()!=0) {
                                        ap.remove(0);
                                        checkSetHelpGuide(4);
                                        save(MainActivity.this, listview, ap);
                                        launchAutomaticHint3();

                                    }
                                }
                            })
                            .show();

                }
            }
        }, 10);
    }

    private void launchAutomaticHint3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View actionSearchView = findViewById(R.id.action_info);
                if (actionSearchView != null) {
                    SimpleHintContentHolder blockInfo =
                            new SimpleHintContentHolder.Builder(actionSearchView.getContext())
                                    .setContentTitle("Menu")
                                    .setContentText("This is the menu for app settings")
                                    .setTitleStyle(R.style.title)
                                    .setContentStyle(R.style.content)
                                    .setGravity(Gravity.TOP | Gravity.CENTER)
                                    .setMarginByResourcesId(R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin,
                                            R.dimen.activity_vertical_margin,
                                            R.dimen.activity_horizontal_margin)
                                    .build();

                    new HintCase(actionSearchView.getRootView())
                            .setTarget(actionSearchView, new CircularShape())
                            .setShapeAnimators(new RevealCircleShapeAnimator(),
                                    new UnrevealCircleShapeAnimator())
                            .setHintBlock(blockInfo)
                            .setOnClosedListener(new HintCase.OnClosedListener() {
                                @Override
                                public void onClosed() {

                                }
                            })
                            .show();

                }
            }
        }, 10);
    }


    private void getDataFromMemory(String list) {
        final String[] StringArray = new String[200];
        if (!list.equals("")) {
            String[] raw = list.split("[|]");
            arraycopy(raw, 0, StringArray, 0, raw.length);

            for (int j = 0; j < raw.length; ) {
                if (StringArray[j + 3].equals("null")||StringArray[j + 3].equals("none")) {
                    EpisodeClass obj = new EpisodeClass(R.drawable.nophotoavalibale, StringArray[j], StringArray[j + 1], StringArray[j + 2]);
                    if(StringArray[j+5] == null){
                        obj.setPausetime("");
                    }else {
                        obj.setPausetime(StringArray[j + 5]);
                    }
                    ap.add(obj);
                } else {
                    EpisodeClass obj = new EpisodeClass(StringArray[j + 3], StringArray[j], StringArray[j + 1], StringArray[j + 2], StringArray[j + 4]);
                    if(StringArray[j+5] == null){
                        obj.setPausetime("");
                    }else {
                        obj.setPausetime(StringArray[j + 5]);
                    }
                    obj.plot = StringArray[j+7];
                    obj.nameOfEpisode = StringArray[j+6];
                    ap.add(obj);
                }
                j = j + 8;
            }
            checkSetHelpGuide(listview.getCount());
        }else{
            Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_LONG).show();

        }
        linprogress.setVisibility(View.INVISIBLE);

    }
    private void getDataFromMemory() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                File f_list = new File(getApplicationContext().getFilesDir(), "list");
//                final String list = FileManager.readFromFile(f_list);
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                myRef = database.getReference("users");
                FirebaseUser user = auth.getCurrentUser();
                myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        try{
                            list = snapshot.getValue().toString();
                            if(click==1){
                                getDataFromMemory(list);
                            }
                        }catch (Exception e){
                            FirebaseUser user = auth.getCurrentUser();
                            myRef.child(user.getUid()).setValue("");
                            linprogress.setVisibility(View.INVISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        }, 1000);
    }




    public void hideFloatingActionButton() {
                        plus.hide();
                    };
                    public void showFloatingActionButton() {
                        plus.show();
                    };
}
