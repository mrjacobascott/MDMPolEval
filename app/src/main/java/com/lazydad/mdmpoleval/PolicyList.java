package com.lazydad.mdmpoleval;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PolicyList extends AppCompatActivity {
    //TODOs
    // make the back button experience better
    // export the table
    // enum calculation?
    // optimize the code?
    // do the updates stuffs
    // see if there is a workaround for non-owning apps to get things like password complexity

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_list);
        setTitle("MDM Policy Evaluator");
        createToast("Importing Policy");
        String pb = System.getProperty("line.separator");

        // init table info and styling
        ViewGroup.LayoutParams lpS = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .45);
        ViewGroup.LayoutParams lpD = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .45);
        ViewGroup.LayoutParams lpB = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .1);

        TableLayout tblHeader = (TableLayout) findViewById(R.id.tblHeader);
        TableLayout tbl = (TableLayout) findViewById(R.id.table);
        System.out.println(tbl);
        TableRow initRow = (TableRow) new TableRow(this);
        TextView settingHeading = new TextView(this);
        TextView dataHeading = new TextView(this);
        TextView linkHeading = new TextView(this);
        Button button = new Button(this);
        settingHeading.setText("Setting");
        settingHeading.setLayoutParams(lpS);
        initRow.addView(settingHeading);
        dataHeading.setText("Value");
        dataHeading.setLayoutParams(lpD);
        initRow.addView(dataHeading);
        linkHeading.setText("Link");
        linkHeading.setLayoutParams(lpB);
        initRow.addView(linkHeading);
        tblHeader.addView(initRow);

        View v1 = new View(this);
        v1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        v1.setBackgroundColor(Color.rgb(51, 51, 51));
        tblHeader.addView(v1);

        View v2 = new View(this);
        v2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        v2.setBackgroundColor(Color.rgb(51, 51, 51));
        tbl.addView(v2);

        //create the different managers
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        UserManager umgr = (UserManager)getSystemService(USER_SERVICE);

        // here is where I start importing policy
        ///////////////////////////////////////////////////////////////////////////
        // listing api version of device
        int apiVersion = android.os.Build.VERSION.SDK_INT;

        TableRow row30 = (TableRow) new TableRow(this);
        TextView setting30 = new TextView(this);
        TextView data30 = new TextView(this);
        Button button30 = new Button(this);

        setting30.setText("Device API version");
        setting30.setLayoutParams(lpS);
        row30.addView(setting30);
        data30.setText(Integer.toString(apiVersion));
        data30.setLayoutParams(lpD);
        row30.addView(data30);
        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mdmpoleval","Launching browser");
                String url = "https://developer.android.com/reference/android/os/Build.VERSION#SDK_INT";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button30.setLayoutParams(lpB);
        row30.addView(button30);
        tbl.addView(row30);
        ///////////////////////////////////////////////////////////////////////////
        // checking if app is ran in work profile
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getActiveAdmins()
        List<ComponentName> activeAdmins = dpm.getActiveAdmins();
        String admins = "";
        if (activeAdmins != null){
            for (ComponentName admin : activeAdmins){
                admins = admin.getPackageName() + " " + admins;
            }
        } else {
            admins = "Not Managed" + pb;
        }
        TableRow row1 = (TableRow) new TableRow(this);
        TextView setting1 = new TextView(this);
        TextView data1 = new TextView(this);
        Button button1 = new Button(this);
        setting1.setText("Management Agent");
        row1.addView(setting1);
        setting1.setLayoutParams(lpS);
        data1.setText(admins.toString());
        data1.setLayoutParams(lpD);
        row1.addView(data1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mdmpoleval","Launching browser");
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getActiveAdmins()";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button1.setLayoutParams(lpB);
        row1.addView(button1);
        tbl.addView(row1);

        ///////////////////////////////////////////////////////////////////////////
        // checking camera is disabled
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getCameraDisabled(android.content.ComponentName)
        Boolean cameraBlocked = dpm.getCameraDisabled(null);

        TableRow row2 = (TableRow) new TableRow(this);
        TextView setting2 = new TextView(this);
        TextView data2 = new TextView(this);
        Button button2 = new Button(this);

        setting2.setText("Camera is disabled");
        setting2.setLayoutParams(lpS);
        row2.addView(setting2);
        data2.setText(cameraBlocked.toString());
        data2.setLayoutParams(lpD);
        row2.addView(data2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mdmpoleval","Launching browser");
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getCameraDisabled(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button2.setLayoutParams(lpB);
        row2.addView(button2);
        tbl.addView(row2);

        ///////////////////////////////////////////////////////////////////////////
        // Determine for how long the user will be able to use secondary, non strong auth for authentication,
        // since last strong method authentication (password, pin or pattern) was used.
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getRequiredStrongAuthTimeout(android.content.ComponentName)
        long strongAuthTimeout = dpm.getRequiredStrongAuthTimeout(null);

        TableRow row3 = (TableRow) new TableRow(this);
        TextView setting3 = new TextView(this);
        TextView data3 = new TextView(this);
        Button button3 = new Button(this);
        setting3.setText("Strong Auth Timeout (ms)");
        setting3.setLayoutParams(lpS);
        row3.addView(setting3);
        data3.setText(Long.toString(strongAuthTimeout));
        data3.setLayoutParams(lpD);
        row3.addView(data3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getRequiredStrongAuthTimeout(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button3.setLayoutParams(lpB);
        row3.addView(button3);
        tbl.addView(row3);

        ///////////////////////////////////////////////////////////////////////////
        // device owner lock screen info,
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getDeviceOwnerLockScreenInfo()
        CharSequence doLockScreen = dpm.getDeviceOwnerLockScreenInfo();

        TableRow row4 = (TableRow) new TableRow(this);
        TextView setting4 = new TextView(this);
        TextView data4 = new TextView(this);
        Button button4 = new Button(this);
        setting4.setText("Device owner lock screen info");
        setting4.setLayoutParams(lpS);
        row4.addView(setting4);
        data4.setText(doLockScreen);
        data4.setLayoutParams(lpD);
        row4.addView(data4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getDeviceOwnerLockScreenInfo()";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button4.setLayoutParams(lpB);
        row4.addView(button4);
        tbl.addView(row4);

        ///////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getAccountTypesWithManagementDisabled()
        String[] getAccountTypesWithManagementDisabled = dpm.getAccountTypesWithManagementDisabled();
        String accountsDisabled = "";
        if (getAccountTypesWithManagementDisabled!=null){
            for (String str : getAccountTypesWithManagementDisabled){
                accountsDisabled = accountsDisabled + str + " ";
            }
        }
        TableRow row5 = (TableRow) new TableRow(this);
        TextView setting5 = new TextView(this);
        TextView data5 = new TextView(this);
        Button button5 = new Button(this);
        setting5.setText("Accounts With Management disabled");
        setting5.setLayoutParams(lpS);
        row5.addView(setting5);
        if (accountsDisabled==""){
            data5.setText("None");
        }else{
            data5.setText(accountsDisabled);
        }
        data5.setLayoutParams(lpD);
        row5.addView(data5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getAccountTypesWithManagementDisabled()";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button5.setLayoutParams(lpB);
        row5.addView(button5);
        tbl.addView(row5);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/os/UserManager#getUserRestrictions()
        //get list of restrictions applied to user

        Bundle restrictions = umgr.getUserRestrictions();


        if (restrictions.keySet().size()>0){

            for (String key : restrictions.keySet()){
                TableRow row6 = (TableRow) new TableRow(this);
                TextView setting6 = new TextView(this);
                TextView data6 = new TextView(this);
                Button button6 = new Button(this);
                setting6.setText("User Restriction: " + key);
                setting6.setLayoutParams(lpS);
                row6.addView(setting6);

                //builtPolicy = builtPolicy + key + ": " + restrictions.get(key).toString() + pb;
                //System.out.println("restrinction" + key);
                data6.setText(restrictions.get(key).toString());
                data6.setLayoutParams(lpD);
                row6.addView(data6);
                button6.setOnClickListener(new View. OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://developer.android.com/reference/android/os/UserManager#getUserRestrictions()";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                });
                button6.setLayoutParams(lpB);
                row6.addView(button6);
                tbl.addView(row6);
            }
        } else {
            TableRow row6 = (TableRow) new TableRow(this);
            TextView setting6 = new TextView(this);
            TextView data6 = new TextView(this);
            Button button6 = new Button(this);
            setting6.setText("Restrictions applied to user");
            setting6.setLayoutParams(lpS);
            row6.addView(setting6);
            data6.setText("None");
            data6.setLayoutParams(lpD);
            row6.addView(data6);
            button6.setOnClickListener(new View. OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://developer.android.com/reference/android/os/UserManager#getUserRestrictions()";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            button6.setLayoutParams(lpB);
            row6.addView(button6);
            tbl.addView(row6);
        }

         /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getManagedProfileMaximumTimeOff(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getMaximumFailedPasswordsForWipe
        int maxFailedPasswordsForWipe = dpm.getMaximumFailedPasswordsForWipe(null);

        TableRow row9 = (TableRow) new TableRow(this);
        TextView setting9 = new TextView(this);
        TextView data9 = new TextView(this);
        Button button9 = new Button(this);
        setting9.setText("Incorrect password attempts before wipe");
        setting9.setLayoutParams(lpS);
        row9.addView(setting9);
        data9.setText(Integer.toString(maxFailedPasswordsForWipe));
        data9.setLayoutParams(lpD);
        row9.addView(data9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getMaximumFailedPasswordsForWipe";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button9.setLayoutParams(lpB);
        row9.addView(button9);
        tbl.addView(row9);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getMaximumTimeToLock(android.content.ComponentName)
        long maxTimeToLock = dpm.getMaximumTimeToLock(null);

        TableRow row10 = (TableRow) new TableRow(this);
        TextView setting10 = new TextView(this);
        TextView data10 = new TextView(this);
        Button button10 = new Button(this);
        setting10.setText("Max time to lock (ms)");
        setting10.setLayoutParams(lpS);
        row10.addView(setting10);
        data10.setText(Long.toString(maxTimeToLock));
        data10.setLayoutParams(lpD);
        row10.addView(data10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getMaximumTimeToLock(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button10.setLayoutParams(lpB);
        row10.addView(button10);
        tbl.addView(row10);

        /////////////////////////////////////////////////////////////////////////
        // can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getNearbyAppStreamingPolicy()

        /////////////////////////////////////////////////////////////////////////
        // can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getNearbyNotificationStreamingPolicy()

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordExpiration(android.content.ComponentName)
        long passwordExpiration = dpm.getPasswordExpiration(null);

        TableRow row11 = (TableRow) new TableRow(this);
        TextView setting11 = new TextView(this);
        TextView data11 = new TextView(this);
        Button button11 = new Button(this);
        setting11.setText("Password expiration time since epoch (ms)");
        setting11.setLayoutParams(lpS);
        row11.addView(setting11);
        data11.setText(Long.toString(passwordExpiration));
        data11.setLayoutParams(lpD);
        row11.addView(data11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordExpiration(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button11.setLayoutParams(lpB);
        row11.addView(button11);
        tbl.addView(row11);

        ////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordExpirationTimeout(android.content.ComponentName)
        long passwordExpirationTimeout = dpm.getPasswordExpiration(null);

        TableRow row12 = (TableRow) new TableRow(this);
        TextView setting12 = new TextView(this);
        TextView data12 = new TextView(this);
        Button button12 = new Button(this);
        setting12.setText("Minimum password timeout (ms)");
        setting12.setLayoutParams(lpS);
        row12.addView(setting12);
        data12.setText(Long.toString(passwordExpirationTimeout));
        data12.setLayoutParams(lpD);
        row12.addView(data12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordExpirationTimeout(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button12.setLayoutParams(lpB);
        row12.addView(button12);
        tbl.addView(row12);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordHistoryLength(android.content.ComponentName)
        long passwordHistoryLength = dpm.getPasswordHistoryLength(null);

        TableRow row13 = (TableRow) new TableRow(this);
        TextView setting13 = new TextView(this);
        TextView data13 = new TextView(this);
        Button button13 = new Button(this);
        setting13.setText("Password history length");
        setting13.setLayoutParams(lpS);
        row13.addView(setting13);
        data13.setText(Long.toString(passwordHistoryLength));
        data13.setLayoutParams(lpD);
        row13.addView(data13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordHistoryLength(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button13.setLayoutParams(lpB);
        row13.addView(button13);
        tbl.addView(row13);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordQuality(android.content.ComponentName)
        int passwordQuality = dpm.getPasswordQuality(null);

        TableRow row15 = (TableRow) new TableRow(this);
        TextView setting15 = new TextView(this);
        TextView data15 = new TextView(this);
        Button button15 = new Button(this);
        setting15.setText("Password quality");
        setting15.setLayoutParams(lpS);
        row15.addView(setting15);
        //data15.setText(Integer.toString(passwordQuality));

        if(passwordQuality == 0){
            data15.setText("PASSWORD_QUALITY_UNSPECIFIED " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 32768){
            data15.setText("PASSWORD_QUALITY_BIOMETRIC_WEAK " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 65536){
            data15.setText("PASSWORD_QUALITY_SOMETHING " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 131072){
            data15.setText("PASSWORD_QUALITY_NUMERIC " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 196608){
            data15.setText("PASSWORD_QUALITY_NUMERIC_COMPLEX " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 262144){
            data15.setText("PASSWORD_QUALITY_ALPHABETIC " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 327680){
            data15.setText("PASSWORD_QUALITY_ALPHANUMERIC " + "(" + Integer.toString(passwordQuality) + ")");
        } else if (passwordQuality == 393216){
            data15.setText("PASSWORD_QUALITY_COMPLEX " + "(" + Integer.toString(passwordQuality) + ")");
        } else {
            data15.setText("Unknown value for password quality: " + passwordQuality);
        }

        data15.setLayoutParams(lpD);
        row15.addView(data15);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordQuality(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button15.setLayoutParams(lpB);
        row15.addView(button15);
        tbl.addView(row15);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMaximumLength(int)
        int passwordMaximumLength = dpm.getPasswordMaximumLength(passwordQuality);

        TableRow row14 = (TableRow) new TableRow(this);
        TextView setting14 = new TextView(this);
        TextView data14 = new TextView(this);
        Button button14 = new Button(this);
        setting14.setText("Password max length based on above quality type");
        setting14.setLayoutParams(lpS);
        row14.addView(setting14);
        data14.setText(Integer.toString(passwordMaximumLength));
        data14.setLayoutParams(lpD);
        row14.addView(data14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMaximumLength(int)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button14.setLayoutParams(lpB);
        row14.addView(button14);
        tbl.addView(row14);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLength(android.content.ComponentName)
        int passwordMinimumLength = dpm.getPasswordMinimumLength(null);

        TableRow row16 = (TableRow) new TableRow(this);
        TextView setting16 = new TextView(this);
        TextView data16 = new TextView(this);
        Button button16 = new Button(this);
        setting16.setText("Password minimum length");
        setting16.setLayoutParams(lpS);
        row16.addView(setting16);
        data16.setText(Integer.toString(passwordMinimumLength));
        data16.setLayoutParams(lpD);
        row16.addView(data16);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLength(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button16.setLayoutParams(lpB);
        row16.addView(button16);
        tbl.addView(row16);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLetters(android.content.ComponentName)
        int passwordMinimumLetters = dpm.getPasswordMinimumLetters(null);

        TableRow row17 = (TableRow) new TableRow(this);
        TextView setting17 = new TextView(this);
        TextView data17 = new TextView(this);
        Button button17 = new Button(this);
        setting17.setText("Password minimum letters");
        setting17.setLayoutParams(lpS);
        row17.addView(setting17);
        data17.setText(Integer.toString(passwordMinimumLetters));
        data17.setLayoutParams(lpD);
        row17.addView(data17);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLetters(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button17.setLayoutParams(lpB);
        row17.addView(button17);
        tbl.addView(row17);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLowerCase(android.content.ComponentName)
        int passwordMinimumLowerCase = dpm.getPasswordMinimumLowerCase(null);

        TableRow row18 = (TableRow) new TableRow(this);
        TextView setting18 = new TextView(this);
        TextView data18 = new TextView(this);
        Button button18 = new Button(this);
        setting18.setText("Password minimum lowercase");
        setting18.setLayoutParams(lpS);
        row18.addView(setting18);
        data18.setText(Integer.toString(passwordMinimumLowerCase));
        data18.setLayoutParams(lpD);
        row18.addView(data18);
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLowerCase(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button18.setLayoutParams(lpB);
        row18.addView(button18);
        tbl.addView(row18);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNonLetter(android.content.ComponentName)
        int passwordMinimumNonLetter = dpm.getPasswordMinimumNonLetter(null);

        TableRow row19 = (TableRow) new TableRow(this);
        TextView setting19 = new TextView(this);
        TextView data19 = new TextView(this);
        Button button19 = new Button(this);
        setting19.setText("Password minimum non-letter");
        setting19.setLayoutParams(lpS);
        row19.addView(setting19);
        data19.setText(Integer.toString(passwordMinimumNonLetter));
        data19.setLayoutParams(lpD);
        row19.addView(data19);
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNonLetter(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button19.setLayoutParams(lpB);
        row19.addView(button19);
        tbl.addView(row19);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNumeric(android.content.ComponentName)
        int passwordMinimumNumeric = dpm.getPasswordMinimumNumeric(null);

        TableRow row20 = (TableRow) new TableRow(this);
        TextView setting20 = new TextView(this);
        TextView data20 = new TextView(this);
        Button button20 = new Button(this);
        setting20.setText("Password minimum numeric");
        setting20.setLayoutParams(lpS);
        row20.addView(setting20);
        data20.setText(Integer.toString(passwordMinimumNumeric));
        data20.setLayoutParams(lpD);
        row20.addView(data20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNumeric(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button20.setLayoutParams(lpB);
        row20.addView(button20);
        tbl.addView(row20);


        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumSymbols(android.content.ComponentName)
        int passwordMinimumSymbols = dpm.getPasswordMinimumSymbols(null);

        TableRow row21 = (TableRow) new TableRow(this);
        TextView setting21 = new TextView(this);
        TextView data21 = new TextView(this);
        Button button21 = new Button(this);
        setting21.setText("Password minimum symbols");
        setting21.setLayoutParams(lpS);
        row21.addView(setting21);
        data21.setText(Integer.toString(passwordMinimumSymbols));
        data21.setLayoutParams(lpD);
        row21.addView(data21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumSymbols(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button21.setLayoutParams(lpB);
        row21.addView(button21);
        tbl.addView(row21);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumUpperCase(android.content.ComponentName)
        int passwordMinimumUpperCase = dpm.getPasswordMinimumUpperCase(null);

        TableRow row22 = (TableRow) new TableRow(this);
        TextView setting22 = new TextView(this);
        TextView data22 = new TextView(this);
        Button button22 = new Button(this);
        setting22.setText("Password minimum uppercase");
        setting22.setLayoutParams(lpS);
        row22.addView(setting22);
        data22.setText(Integer.toString(passwordMinimumSymbols));
        data22.setLayoutParams(lpD);
        row22.addView(data22);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumUpperCase(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button22.setLayoutParams(lpB);
        row22.addView(button22);
        tbl.addView(row22);

        /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getRequiredPasswordComplexity()

        /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPermissionPolicy(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPermittedCrossProfileNotificationListeners(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPersonalAppsSuspendedReasons(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryption(android.content.ComponentName)
        boolean commandedStorageEncryption = dpm.getStorageEncryption(null);

        TableRow row24 = (TableRow) new TableRow(this);
        TextView setting24 = new TextView(this);
        TextView data24 = new TextView(this);
        Button button24 = new Button(this);
        setting24.setText("Encryption required");
        setting24.setLayoutParams(lpS);
        row24.addView(setting24);
        data24.setText(Boolean.toString(commandedStorageEncryption));
        data24.setLayoutParams(lpD);
        row24.addView(data24);
        button24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryption(android.content.ComponentName)";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button24.setLayoutParams(lpB);
        row24.addView(button24);
        tbl.addView(row24);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryptionStatus()
        int storageEncryptionStatus = dpm.getStorageEncryptionStatus();

        TableRow row23 = (TableRow) new TableRow(this);
        TextView setting23 = new TextView(this);
        TextView data23 = new TextView(this);
        Button button23 = new Button(this);
        setting23.setText("Encryption Status");
        setting23.setLayoutParams(lpS);
        row23.addView(setting23);
        data23.setText(Integer.toString(storageEncryptionStatus));
        data23.setLayoutParams(lpD);
        row23.addView(data23);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryptionStatus()";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button23.setLayoutParams(lpB);
        row23.addView(button23);
        tbl.addView(row23);

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getSystemUpdatePolicy()
        //https://developer.android.com/reference/android/app/admin/SystemUpdatePolicy
        // this will need to be parsed out into several items as it returns a policy type

        /////////////////////////////////////////////////////////////////////////
        //can't do without being profile owner
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getTrustAgentConfiguration(android.content.ComponentName,%20android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isDeviceIdAttestationSupported()
        boolean isDeviceIdAttestationSupported = dpm.isDeviceIdAttestationSupported();
        TableRow row25 = (TableRow) new TableRow(this);
        TextView setting25 = new TextView(this);
        TextView data25 = new TextView(this);
        Button button25 = new Button(this);
        setting25.setText("Is Device ID Attestation Supported");
        setting25.setLayoutParams(lpS);
        row25.addView(setting25);
        data25.setText(Boolean.toString(isDeviceIdAttestationSupported));
        data25.setLayoutParams(lpD);
        row25.addView(data25);
        button25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isDeviceIdAttestationSupported()";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        button25.setLayoutParams(lpB);
        row25.addView(button25);
        tbl.addView(row25);

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isUniqueDeviceAttestationSupported()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            boolean isUniqueDeviceAttestationSupported = dpm.isUniqueDeviceAttestationSupported();
            TableRow row27 = (TableRow) new TableRow(this);
            TextView setting27 = new TextView(this);
            TextView data27 = new TextView(this);
            Button button27 = new Button(this);
            setting27.setText("StrongBox Keymaster attestation supported");
            setting27.setLayoutParams(lpS);
            row27.addView(setting27);
            data27.setText(Boolean.toString(isUniqueDeviceAttestationSupported));
            data27.setLayoutParams(lpD);
            row27.addView(data27);
            button27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isOrganizationOwnedDeviceWithManagedProfile()";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            button27.setLayoutParams(lpB);
            row27.addView(button27);
            tbl.addView(row27);

        }

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isCommonCriteriaModeEnabled(android.content.ComponentName)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            boolean isCommonCriteriaModeEnabled = dpm.isCommonCriteriaModeEnabled(null);
            TableRow row26 = (TableRow) new TableRow(this);
            TextView setting26 = new TextView(this);
            TextView data26 = new TextView(this);
            Button button26 = new Button(this);
            setting26.setText("Is Common Criteria Mode Enabled");
            setting26.setLayoutParams(lpS);
            row26.addView(setting26);
            data26.setText(Boolean.toString(isCommonCriteriaModeEnabled));
            data26.setLayoutParams(lpD);
            row26.addView(data26);
            button26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isCommonCriteriaModeEnabled(android.content.ComponentName)";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            button26.setLayoutParams(lpB);
            row26.addView(button26);
            tbl.addView(row26);
        }

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isOrganizationOwnedDeviceWithManagedProfile()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            boolean isOrganizationOwnedDeviceWithManagedProfile = dpm.isOrganizationOwnedDeviceWithManagedProfile();
            TableRow row28 = (TableRow) new TableRow(this);
            TextView setting28 = new TextView(this);
            TextView data28 = new TextView(this);
            Button button28 = new Button(this);
            setting28.setText("Was device provisioned as org owned device");
            setting28.setLayoutParams(lpS);
            row28.addView(setting28);
            data28.setText(Boolean.toString(isOrganizationOwnedDeviceWithManagedProfile));
            data28.setLayoutParams(lpD);
            row28.addView(data28);
            button28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isOrganizationOwnedDeviceWithManagedProfile()";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            button28.setLayoutParams(lpB);
            row28.addView(button28);
            tbl.addView(row28);

        }

        /////////////////////////////////////////////////////////////////////////
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isUsbDataSignalingEnabled()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            boolean isUsbDataSignalingEnabled = dpm.isUsbDataSignalingEnabled();
            TableRow row29 = (TableRow) new TableRow(this);
            TextView setting29 = new TextView(this);
            TextView data29 = new TextView(this);
            Button button29 = new Button(this);
            setting29.setText("Is USB Data Signaling Enabled");
            setting29.setLayoutParams(lpS);
            row29.addView(setting29);
            data29.setText(Boolean.toString(isUsbDataSignalingEnabled));
            data29.setLayoutParams(lpD);
            row29.addView(data29);
            button29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#isUsbDataSignalingEnabled()";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });
            button29.setLayoutParams(lpB);
            row29.addView(button29);
            tbl.addView(row29);

        }
    }

    private void createToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}



