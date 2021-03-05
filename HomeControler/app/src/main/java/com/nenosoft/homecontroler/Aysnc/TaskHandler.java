package com.nenosoft.homecontroler.Aysnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.Helper;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.JsonParser;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.info.Control_Info;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.info.UserInfo;
import com.nenosoft.homecontroler.com.nenosoft.homecontroler.Utils.Utils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskHandler extends AsyncTask<String, Object, Integer> {

    private String TAG = "AsyncTask";

    private Context context = null;
    private int ServiceAction;

    public static final int LOG_IN = 1;
    public static final int CTRL_INFO = 2;
    public static final int CTRL_COMMAND = 3;
    private boolean isJArray = false;

    private int _taskStatus = 0;
    String LOGIN_STATUS;
    String FORGOT_STATUS;

    JsonParser jsonParser;
    public Bitmap myBitmap;
    private String class_id;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String URL = "url";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String ur;
    String SERVER;
    String log_in;
    String ctr_info;
    String ctr_cmd;

    private ArrayList<UserInfo> UserInfo_List = new ArrayList<UserInfo>();
    private ArrayList<Control_Info> ControlInfo_List = new ArrayList<Control_Info>();

    UserInfo userInfo;
    Control_Info controlinfo_list;

    public TaskHandler(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        ur = sharedpreferences.getString(URL, "");
        SERVER = "http://" + ur + Utils.SERVER;
        log_in = SERVER + "?action=login";
        ctr_info = SERVER + "?action=info";
        ctr_cmd = SERVER + "?action=light_1";
    }

    @Override
    protected Integer doInBackground(String... params) {
        // TODO Auto-generated method stub

        String FeedURL = "";

        ServiceAction = Integer.parseInt(params[0]);

        if (ServiceAction == LOG_IN) {

            FeedURL = log_in;
            FeedURL = FeedURL + "&user_name=" + Helper.EncodeURL(params[1])
                    + "&password=" + params[2];
            isJArray = true;

        } else if (ServiceAction == CTRL_INFO) {

            FeedURL = ctr_info;
            FeedURL = FeedURL + "&user_name=" + Helper.EncodeURL(params[1]);
            isJArray = true;

        } else if (ServiceAction == CTRL_COMMAND) {

            FeedURL = ctr_cmd;
            FeedURL = FeedURL + "&user_name=" + Helper.EncodeURL(params[1])
                    + "&light_num=" + Helper.EncodeURL(params[2]) + "&light_ctr=" + Helper.EncodeURL(params[3]);
            isJArray = true;

        }

        if (isJArray) {
            jsonParser = new JsonParser();
            try {
                HandleResponseArray(jsonParser.GetJasonObject(FeedURL, false));
            } catch (Exception e) {
                System.out.println("");
            }
            System.out.println("");
        } else {
            jsonParser = new JsonParser();
            try {
                // HandleResponse(jsonParser.GetJasonObject(FeedURL, false));
            } catch (Exception ex) {
                Logger.getLogger(TaskHandler.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }

        return _taskStatus;

    }

    @Override
    protected void onPostExecute(Integer result) {

        if (ServiceAction == LOG_IN) {
            Intent intent = new Intent(Utils.ACTION_LOG_IN);
            intent.putExtra("userinfolist",
                    (ArrayList<UserInfo>) UserInfo_List);
            if (context != null) {
                context.sendBroadcast(intent);
            }
        } else if (ServiceAction == CTRL_INFO || ServiceAction == CTRL_COMMAND) {

            Intent intent = new Intent(Utils.ACTION_CTR_INFO);
            intent.putExtra("ctrlinfolist",
                    (ArrayList<Control_Info>) ControlInfo_List);
            if (context != null) {
                context.sendBroadcast(intent);
            }
        }
        super.onPostExecute(result);

    }

    private void HandleResponseArray(JSONObject jObj) {

        if (jObj == null) {
            Log.d(TAG, "JSON object is null");
            return;
        }
        if (ServiceAction == LOG_IN) {
            LOGIN(jObj);
        } else if (ServiceAction == CTRL_INFO || ServiceAction == CTRL_COMMAND) {
            Ctr_info(jObj);
        }


    }

    private void Ctr_info(JSONObject jObj) {
        isJArray = true;
        try {
            JSONArray posts = jObj.getJSONArray("Response");
            for (int i = 0; i < posts.length(); i++) {

                JSONObject objJson = new JSONObject();
                objJson = posts.getJSONObject(i);
                if (objJson != null)
                    ctrInfo(objJson);
                _taskStatus = CTRL_INFO;
            }

        } catch (Exception exp) {
            _taskStatus = CTRL_INFO;
        }

    }

    private void loginInfo(JSONObject objJson) {
        // TODO Auto-generated method stub
        userInfo = new UserInfo();
        try {
            userInfo.setUserid(objJson.getString("user"));
            UserInfo_List.add(userInfo);
            int s = UserInfo_List.size();
            System.out.print("abc");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void LOGIN(JSONObject jObj) {
        // TODO Auto-generated method stub

        try {
            JSONArray posts = jObj.getJSONArray("Response");
            for (int i = 0; i < posts.length(); i++) {

                JSONObject objJson = new JSONObject();
                objJson = posts.getJSONObject(i);
                if (objJson != null)
                    loginInfo(objJson);
                _taskStatus = LOG_IN;
            }

        } catch (Exception exp) {
            _taskStatus = LOG_IN;
        }

    }

    private void ctrInfo(JSONObject objJson) {
        // TODO Auto-generated method stub
        controlinfo_list = new Control_Info();
        try {
            controlinfo_list.setCtr_1(objJson.getString("Ctr_1"));
            controlinfo_list.setCtr_2(objJson.getString("Ctr_2"));
            controlinfo_list.setCtr_3(objJson.getString("Ctr_3"));

            ControlInfo_List.add(controlinfo_list);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
