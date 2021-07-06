package com.mhvmedia.unknowncaller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.mhvmedia.unknowncaller.Adapters.BottomNavPagerAdapter;
import com.mhvmedia.unknowncaller.Extra.Function;
import com.mhvmedia.unknowncaller.Fragments.CreditsFragment;
import com.mhvmedia.unknowncaller.Fragments.HomeFragment;
import com.mhvmedia.unknowncaller.Model.User;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Service.CallService;
import com.mhvmedia.unknowncaller.Variables.Variables;
import com.mhvmedia.unknowncaller.Viewpager.CustomViewPager;

import java.util.List;
/** Created by AwsmCreators * */
public class MainActivity extends BaseActivity implements CallService.StartFailedListener {
    CustomViewPager bottompager;
    BottomNavigationView bottomNavigationView;
    public static Boolean isCallEnabled = false;
    private DatabaseReference mDatabase;
    FirebaseUser currentUser;
    Double previousPoints;
    Dialog dialog;
    private FirebaseAuth mAuth;
    String userid;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); }

        changeStatusBarColor();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        bottompager =  findViewById(R.id.pager);
        bottompager.setOffscreenPageLimit(3);
        bottompager.disableScroll(true);
        setupViewPager(bottompager);
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        //
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        dialog.setCancelable(false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userid = currentUser.getUid();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            //loginClicked();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        retriveData();


        bottompager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setAlpha(0f);
                page.setVisibility(View.VISIBLE);

                page.animate()
                        .alpha(1f)
                        .setDuration(page.getResources().getInteger(android.R.integer.config_shortAnimTime));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.make_call:
                    bottompager.setCurrentItem(0,false);
                    return true;
                case R.id.earn_credits:
                    bottompager.setCurrentItem(1,false);
                    return true;
            }
            return false;
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

            }
        });
    }

    private void retriveData() {
        mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                previousPoints = user.getCredits();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setupViewPager(CustomViewPager bottompager) {
        BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new CreditsFragment());
        bottompager.setAdapter(adapter);
        bottompager.setCurrentItem(0,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);


        SearchView mSearchView = new SearchView(getSupportActionBar().getThemedContext());
        mSearchView.setQueryHint("Search contacts"); /// YOUR HINT MESSAGE
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        menuItem = menu.add("searchMenu").setVisible(false).setActionView(mSearchView);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);


        assert searchManager != null;
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String query) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof HomeFragment) {
                            ((HomeFragment) fragment).TextChanged(query);
                            break;
                        }
                    }
                }
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof HomeFragment) {
                            ((HomeFragment) fragment).TextChanged(query);
                            break;
                        }
                    }
                }

                menuItem.collapseActionView();


                return true;
            }
        };

        mSearchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call_rates:
                startActivity(new Intent(MainActivity.this, CallRatesActivity.class));
                return true;
            case R.id.share_app:
                shareApplication();
                return true;
            case R.id.rate:
                rateApp();
                return true;

            case R.id.log_out:
                logOut();
                return true;

            case R.id.more_appps:
                moreApps();
                return true;
            case R.id.search:
                menuItem.expandActionView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void moreApps() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=7869046472755305166");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=7869046472755305166")));
        }
    }

    private void logOut() {
        new AlertDialog.Builder(this)
                .setTitle("Log out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with logout operation
                        dialog.dismiss();
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
        loginClicked();
        isCallEnabled = true;
    }

    public void CallNumber(String number){
        if (number.length() <6) {
            Toast.makeText(this, "Please Enter Valid Number To Call", Toast.LENGTH_LONG).show();
            return;
        }else if (!number.contains("+")){
            Toast.makeText(this, "Please put country code before number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isCallEnabled){
            if (Variables.ENABLE_ADVANCE_CREDITS_DEDUCTION){
                if (previousPoints !=null && previousPoints >= Double.parseDouble(Function.checkCountry(number))){
                    Call call = getSinchServiceInterface().callPhoneNumber(number);
                    String callId = call.getCallId();

                    Intent callScreen = new Intent(MainActivity.this, CallScreenActivity.class);
                    callScreen.putExtra(CallService.CALL_ID, callId);
                    callScreen.putExtra("number",number);
                    startActivity(callScreen);
                }else {
                    Toast.makeText(MainActivity.this, "Minimum " +Function.checkCountry(number)+ " credits required to make a call", Toast.LENGTH_SHORT).show();

                }
            }else {
                if (previousPoints !=null && previousPoints >= Variables.MINIMUM_CREDITS){
                    Call call = getSinchServiceInterface().callPhoneNumber(number);
                    String callId = call.getCallId();

                    Intent callScreen = new Intent(MainActivity.this, CallScreenActivity.class);
                    callScreen.putExtra(CallService.CALL_ID, callId);
                    callScreen.putExtra("number",number);
                    startActivity(callScreen);
                }else {
                    Toast.makeText(MainActivity.this, "Minimum "+ Variables.MINIMUM_CREDITS.intValue() + " credits required to make a call", Toast.LENGTH_SHORT).show();

                }
            }

            //callButtonClicked();
        }else {
            Toast.makeText(this, "Call is not ready", Toast.LENGTH_SHORT).show();
        }

    }

    public void shareApplication() {
        try {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = getString(R.string.share_title) + "\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose One"));

        }
        catch(Exception e){
            //e.toString();
        }
    }

    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        dialog.dismiss();

    }

    @Override
    public void onStarted() {
        dialog.dismiss();

    }
    private void loginClicked() {

        if (userid.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userid.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userid);
            dialog.show();
        } else {
            dialog.dismiss();
            //openPlaceCallActivity();
        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
