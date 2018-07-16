package com.example.abhish.sms.Fragment1;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.abhish.sms.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private PrimaryFragment primaryFragment;
    private OTPFragment otpFragment;
    private TransactionsFragment transactionsFragment;
    private PromotionsFragment promotionsFragment;
    private SpamFragment spamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);

        primaryFragment =  new PrimaryFragment();
        otpFragment = new OTPFragment();
        transactionsFragment =  new TransactionsFragment();
        promotionsFragment = new PromotionsFragment();
        spamFragment = new SpamFragment();
        setFragment( primaryFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_Primary:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment( primaryFragment);
                        return  true;

                    case R.id.nav_OTP:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment( otpFragment);
                        return true;

                    case R.id.nav_Transactions:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(transactionsFragment);
                        return true;

                    case R.id.nav_Promotions:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(promotionsFragment);
                        return true;

                    case R.id.nav_Spams:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(spamFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_nav,menu);
        return true;
    }

    // Side Navigation Bar for listing options Mark All As Read,Archive etc.
    //TODO: Complete actions corresponding to these
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.id_markread){
            // Write final logic
            return true;
        }
        if(id == R.id.id_refmess){
            //Write final logic
            return true;
        }
        if(id == R.id.id_archive){
            //Write final logic
            return true;
        }
        if(id == R.id.id_blocked){
            //Write final logic
            return true;
        }
        if(id == R.id.id_feedback){
            //Write final logic
            return true;
        }
        if(id == R.id.id_invite){
            //Write final logic
            return true;
        }
        if(id == R.id.id_setting){
            //Write final logic
            return true;
        }
        return true;
    }
}
