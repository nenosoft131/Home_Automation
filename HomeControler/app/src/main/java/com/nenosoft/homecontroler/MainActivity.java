package com.nenosoft.homecontroler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nenosoft.homecontroler.Aysnc.TaskHandler;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.Utils;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.info.Control_Info;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.info.UserInfo;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String UNAME = "user_name";
    private static final int LOADING_DIALOG = 1;
    private static final int ERROR_DIALOG = 2;
    private static final int CONTROL_DIALOG = 3;
    private ProgressDialog loginDialog;
    String light_ctr, light_num;
    private ProgressDialog controlDialog;
    String status;
    Button btn_1, btn_2, btn_3;
    boolean ct_btn_1, ct_btn_2, ct_btn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MainActivity.MODE_PRIVATE);
        btn_1 = (Button) findViewById(R.id.button1);
        btn_2 = (Button) findViewById(R.id.button2);
        btn_3 = (Button) findViewById(R.id.button3);
        String name = sharedpreferences.getString(UNAME, "");
        showDialog(LOADING_DIALOG);
    }

    public void btn_1_click(View v) {
        light_ctr = "Ctr_1";
        if (!ct_btn_1) {
            light_num = "1";
            status = "Turing ON..";
        } else {
            light_num = "0";
            status = "Turing OFF..";
        }
        showDialog(CONTROL_DIALOG);
    }

    public void btn_2_click(View v) {
        light_ctr = "Ctr_2";
        if (!ct_btn_2) {
            light_num = "1";
            status = "Turing ON..";
        } else {
            light_num = "0";
            status = "Turing OFF..";
        }
        showDialog(CONTROL_DIALOG);
    }

    public void btn_3_click(View v) {
        light_ctr = "Ctr_3";
        if (!ct_btn_3) {
            light_num = "1";
            status = "Turing ON..";
        } else {
            light_num = "0";
            status = "Turing OFF..";
        }
        showDialog(CONTROL_DIALOG);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
        IntentFilter filter = new IntentFilter(Utils.ACTION_CTR_INFO);
        registerReceiver(Control_Info_RECIVER, filter);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        unregisterReceiver(Control_Info_RECIVER);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver Control_Info_RECIVER = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle extra = intent.getExtras();
            ArrayList<Control_Info> ctrinfo = (ArrayList<Control_Info>) extra
                    .getSerializable("ctrlinfolist");
            if (ctrinfo.size() > 0) {
                Setupbuttons(ctrinfo);
            } else {
                showDialog(ERROR_DIALOG);
            }
            removeDialog(CONTROL_DIALOG);
            removeDialog(LOADING_DIALOG);
        }

        private void Setupbuttons(ArrayList<Control_Info> ctrinfo) {
            if (ctrinfo.get(0).getCtr_1().equals("1")) {
                ct_btn_1 = true;
                btn_1.setBackgroundResource(R.drawable.icon_on);
            } else {
                ct_btn_1 = false;
                btn_1.setBackgroundResource(R.drawable.icon_off);
            }
            if (ctrinfo.get(0).getCtr_2().equals("1")) {
                ct_btn_2 = true;
                btn_2.setBackgroundResource(R.drawable.icon_on);
            } else {
                ct_btn_2 = false;
                btn_2.setBackgroundResource(R.drawable.icon_off);
            }
            if (ctrinfo.get(0).getCtr_3().equals("1")) {
                ct_btn_3 = true;
                btn_3.setBackgroundResource(R.drawable.icon_on);
            } else {
                ct_btn_3 = false;
                btn_3.setBackgroundResource(R.drawable.icon_off);
            }
        }


    };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case LOADING_DIALOG:
                loginDialog = new ProgressDialog(this);
                loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loginDialog.setMessage("Loading...");
                loginDialog.setIndeterminate(true);
                loginDialog.setCancelable(true);
                return loginDialog;

            case CONTROL_DIALOG:
                controlDialog = new ProgressDialog(this);
                controlDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                controlDialog.setMessage(status);
                controlDialog.setIndeterminate(true);
                controlDialog.setCancelable(true);
                return controlDialog;

            case ERROR_DIALOG:
                AlertDialog.Builder b2 = new AlertDialog.Builder(this);
                b2.setTitle("Alert");
                b2.setMessage("Error While Data Featching.");
                b2.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        removeDialog(ERROR_DIALOG);
                    }
                });
                b2.setCancelable(true);
                return b2.create();

        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {

        switch (id) {

            case LOADING_DIALOG:
                new TaskHandler(this).execute(String.valueOf(TaskHandler.CTRL_INFO),
                        sharedpreferences.getString(UNAME, ""));
                break;
            case CONTROL_DIALOG:
                new TaskHandler(this).execute(String.valueOf(TaskHandler.CTRL_COMMAND),
                        sharedpreferences.getString(UNAME, ""), light_num, light_ctr);
                break;

        }

    }

}
