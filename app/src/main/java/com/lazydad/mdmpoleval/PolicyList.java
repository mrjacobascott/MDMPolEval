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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PolicyList extends AppCompatActivity {
    TextView policyList;
    //private Object DevicePolicyManager;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_list);
        createToast("Importing Policy");
        String pb = System.getProperty("line.separator");
        this.policyList = findViewById(R.id.policy);
        String builtPolicy = "";

        // init table info and styling
        ViewGroup.LayoutParams lpS = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .45);
        ViewGroup.LayoutParams lpD = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .45);
        ViewGroup.LayoutParams lpB = new TableRow.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, (float) .1);

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
        tbl.addView(initRow);


        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        v.setBackgroundColor(Color.rgb(51, 51, 51));
        tbl.addView(v);


        //create the different managers
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        UserManager umgr = (UserManager)getSystemService(USER_SERVICE);
        //DeviceAdminReceiver dar = (DeviceAdminReceiver) getSystemService(DEVICE_POLICY_SERVICE);
        //ComponentName admin = (DeviceAdminReceiver) .getWho();

        // here is where I start importing policy
        ///////////////////////////////////////////////////////////////////////////
        // checking if app is ran in work profile
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getActiveAdmins()
        List<ComponentName> activeAdmins = dpm.getActiveAdmins();
        String admins = "";
        if (activeAdmins != null){
            for (ComponentName admin : activeAdmins){
                admins = admins + " " + admin.getPackageName();
            }
            //admins = "Running with a management agent present" + pb + "The management agent is: " + admins + pb;
        } else {
            admins = "Not Managed" + pb;
        }
        //builtPolicy = admins;

        TableRow row1 = (TableRow) new TableRow(this);
        //row1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT));
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
        //builtPolicy = builtPolicy + "Camera is disabled: " + cameraBlocked + pb;

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
        //builtPolicy = builtPolicy + "Strong Auth Timeout (ms): " + strongAuthTimeout + pb;

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
        //builtPolicy = builtPolicy + "Device owner lock screen info: " + doLockScreen + pb;

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
                String url = "https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getRequiredStrongAuthTimeout(android.content.ComponentName)";
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
            builtPolicy = builtPolicy + "Restrictions applied to user: " + pb;

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

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getKeepUninstalledPackages(android.content.ComponentName)

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
        row12.addView(data11);
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
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordHistoryLength(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMaximumLength(int)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLength(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLetters(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumLowerCase(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNonLetter(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumNumeric(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumSymbols(android.content.ComponentName)

        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordMinimumUpperCase(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPasswordQuality(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPermissionPolicy(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPermittedCrossProfileNotificationListeners(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getPersonalAppsSuspendedReasons(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryption(android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getStorageEncryptionStatus()
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getSystemUpdatePolicy()
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //https://developer.android.com/reference/android/app/admin/DevicePolicyManager#getTrustAgentConfiguration(android.content.ComponentName,%20android.content.ComponentName)
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //
        /////////////////////////////////////////////////////////////////////////
        //TODO add this
        //


        ///////////////////
        //TODO look through the 'isXYZ' options

        //I need to return the policy
        policyList.setText(builtPolicy);
    }

    private void createToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}