package com.nenosoft.homecontroler;

import com.nenosoft.homecontroler.Aysnc.TaskHandler;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.ConnectionDetector;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.Utils;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.info.UserInfo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends ActionBarActivity {

    TaskHandler task;
    ConnectionDetector cd;
    ArrayList<UserInfo> USER_INFO = new ArrayList<UserInfo>();
    EditText user_name, password;
    private ProgressDialog loginDialog;
    private static final int INTERNET_DIALOG = 0;
    private static final int LOGIN_DIALOG = 1;
    private static final int ERROR_DIALOG = 2;
    String USERNAME, PASSWORD;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String UNAME = "user_name";
    public static final String URL = "url";
    TextView forgot;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user_name = (EditText) findViewById(R.id.ed_username);
        password = (EditText) findViewById(R.id.ed_pass);
        user_name.setText("usman");
        password.setText("invalid");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Login.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_LOG_IN);
        registerReceiver(LOGIN_RECIVER, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unregisterReceiver(LOGIN_RECIVER);
    }

    public void ONURLCLICK(View c) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("URL");
        alert.setMessage("Enter URL...");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(sharedpreferences.getString(URL, ""));
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                editor.putString(URL, value);
                editor.commit();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();

    }

    public void ONclick(View v) {
        //
        // Intent i = new Intent(Login.this, AuditMain.class);
        // startActivity(i);
        cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            showDialog(INTERNET_DIALOG);
            return;
        }
        USERNAME = user_name.getText().toString();
        PASSWORD = password.getText().toString();

        if (USERNAME.length() == 0) {
            user_name.setError("Error");

        } else if (password.length() == 0) {

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT)
                    .show();

        } else {
            showDialog(LOGIN_DIALOG);
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case LOGIN_DIALOG:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("Signing in ..");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

            case ERROR_DIALOG:
                AlertDialog.Builder b2 = new Builder(this);
                b2.setTitle("Sorry!");
                b2.setMessage("Wrong User ID or Password!");
                b2.setNeutralButton("OK", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        removeDialog(ERROR_DIALOG);
                    }
                });
                b2.setCancelable(true);

                return b2.create();
            case INTERNET_DIALOG:
                AlertDialog.Builder b3 = new Builder(this);
                b3.setTitle("Problem!");
                b3.setMessage("Internet Connection Problem!");
                b3.setNeutralButton("OK", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        removeDialog(INTERNET_DIALOG);
                    }
                });
                b3.setCancelable(true);

                return b3.create();

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case LOGIN_DIALOG:
                new TaskHandler(this).execute(String.valueOf(TaskHandler.LOG_IN),
                        USERNAME, PASSWORD);
                break;

        }

    }

    BroadcastReceiver LOGIN_RECIVER = new BroadcastReceiver()

    {
        public void onReceive(android.content.Context context, Intent intent) {
            final Bundle extra = intent.getExtras();
            ArrayList<UserInfo> userinfo = (ArrayList<UserInfo>) extra
                    .getSerializable("userinfolist");
            if (userinfo.size() > 0) {
                editor.putString(UNAME, userinfo.get(0).getUserid().trim());
                editor.commit();
                Intent inti = new Intent(Login.this, MainActivity.class);
                startActivity(inti);
                Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                showDialog(ERROR_DIALOG);
            }

            removeDialog(LOGIN_DIALOG);
        }

    };
}
