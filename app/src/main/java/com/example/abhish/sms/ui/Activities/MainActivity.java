package com.example.abhish.sms.ui.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhish.sms.R;
import com.example.abhish.sms.util.Structuremsg;
import com.example.abhish.sms.services.MessegeReceiveService;
import com.example.abhish.sms.database.DatabaseHandler;
import com.example.abhish.sms.util.Sms_format;
import com.example.abhish.sms.ui.Fragments.MsgFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private BottomNavigationView bottomNavigationView;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_All_MSG = "all_msg";
    private static final String TAG_TEMPORARY = "temporary";
    private static final String TAG_IMPORTANTS = "importants";
    private static final String TAG_SPAM = "spams";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_All_MSG;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;


    // toolbar titles respected to selected nav menu item

    private String[] activityTitles={"abc","dbd","fff","ddds","ddddd"};
    private DatabaseHandler databaseHandler;

    // flag to load all_msg fragment when user presses back key
    private boolean shouldLoadall_msgFragOnBackPress = true;
    private Handler mHandler;
    ArrayList<Structuremsg> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startService(new Intent(this, MessegeReceiveService.class));
       /* DataParser d = new DataParser();
        d.writeToJson("kutte kamine bc netbanking",1);*/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        }

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        //imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        //activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_All_MSG;
            loadall_msgFragment();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_Primary:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        MainActivity.navItemIndex=0;
                        loadall_msgFragment();
                        CURRENT_TAG = TAG_All_MSG;
                        return  true;

                    case R.id.nav_OTP:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                        MainActivity.navItemIndex=1;
                        loadall_msgFragment();
                        CURRENT_TAG = TAG_IMPORTANTS;
                        return true;

                    case R.id.nav_Transactions:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        MainActivity.navItemIndex=2;
                        loadall_msgFragment();
                        CURRENT_TAG = TAG_SPAM;
                        return true;

                    case R.id.nav_Promotions:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                        MainActivity.navItemIndex=3;
                        loadall_msgFragment();
                        CURRENT_TAG = TAG_SPAM;
                        return true;

                    case R.id.nav_Spams:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        MainActivity.navItemIndex=4;
                        loadall_msgFragment();
                        CURRENT_TAG = TAG_SPAM;
                        return true;

                    default:
                        return false;
                }
            }
        });

    }


    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, spams action view (dot)
     */
      private void loadNavHeader() {
        // name, website
        txtName.setText("Ashish Jain");
        txtWebsite.setText("www.ashish.jain@gmail.com");

        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
      private void loadall_msgFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getall_msgFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getall_msgFragment() {
        List<Structuremsg> list = null;
        MsgFragment msgFragment = null;
        switch (navItemIndex) {
            case 0:
                // all_msg
                list = convertList(databaseHandler.getsms());
                msgFragment = new MsgFragment();
                msgFragment.setListMsg(list);
                return msgFragment;
            case 1:
                // importants fragment
                list = convertList(databaseHandler.getsmsByCat("0"));
                msgFragment = new MsgFragment();
                msgFragment.setListMsg(list);
                return msgFragment;
            case 2:
                // spams fragment
                list = convertList(databaseHandler.getsmsByCat("1"));
                msgFragment = new MsgFragment();
                msgFragment.setListMsg(list);
                return msgFragment;
            case 3:
                // temporary
                list = convertList(databaseHandler.getsmsByCat("2"));
                msgFragment = new MsgFragment();
                msgFragment.setListMsg(list);
                return msgFragment;

            default:
                list = convertList(databaseHandler.getsms());
                msgFragment = new MsgFragment();
                msgFragment.setListMsg(list);
                return msgFragment;
        }
    }

    private List<Structuremsg> convertList(List<Sms_format> getsms) {
        List<Structuremsg> list = new ArrayList<>();
        Log.d("MERA", getsms.size()+"");
        for(Sms_format sms: getsms){
            Log.d("MERA", sms.toString());
            list.add(new Structuremsg(sms.number, sms.body));
        }
        return list;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.all_msg:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_All_MSG;
                        break;
                    case R.id.nav_importants:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_IMPORTANTS;
                        break;
                    case R.id.nav_spams:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SPAM;
                        break;
                    case R.id.nav_temporary:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_TEMPORARY;
                        break;
                    case R.id.nav_settings:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_SETTINGS;
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadall_msgFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads all_msg fragment when back key is pressed
        // when user is in other fragment than all_msg
        if (shouldLoadall_msgFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than all_msg
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_All_MSG;
                loadall_msgFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when all_msg fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is spams, load the menu created for spams
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.spams, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the all_msg/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in spams fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All spams marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in spams fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_spams) {
            Toast.makeText(getApplicationContext(), "Clear all spams!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }


    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public List<Structuremsg> refreshSmsInbox(String category) {
        List<Structuremsg> list = new ArrayList<>();
        ContentResolver contentResolver = this.getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int detailCol = smsInboxCursor.getColumnIndex("body");
        int senderCol = smsInboxCursor.getColumnIndex("address");
        if (detailCol < 0 || !smsInboxCursor.moveToFirst())
            return list;
        Log.d("MERA", senderCol + " " + detailCol);
        do {
            String sender = smsInboxCursor.getString(senderCol);
            String detail = smsInboxCursor.getString(detailCol);
            //Log.d("MERA", sender + " " + detail);
            list.add(new Structuremsg(sender, detail));
        } while (smsInboxCursor.moveToNext());

        return list;
    }
}