package com.good.gd.example.skeleton;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Selection;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.good.gd.GDAndroid;
import com.good.gd.GDStateListener;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.auth.NTCredentials;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.net.GDHttpClient;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by developer01 on 19/06/2017.
 */
public class SettingsActivity extends Activity implements GDStateListener {

    private Button validateButton;
    private Button saveButton;
    private TextView errorText;
    private TextView validationError;
    private Document doc;
    private String hostName;
    private EditText mUsername;
    private EditText mPassword;
    private String userName;
    private String password;
    private boolean isFirstLogin;
    private SharedPreferences sharedPref;

    private RelativeLayout progressBarLayout;

    private String validationURL = "http://colleaguedirectory.intranet.group/Pages/PeopleResults.aspx?k=CDappUserCredentialsCheck&exact=true";

    public static boolean isCredentialsSaved = false;
    private boolean isValidated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GDAndroid.getInstance().activityInit(this);
        setContentView(R.layout.activity_settings);

        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setIcon(R.drawable.back_chevron);
        setTitle("Colleague Search");

        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUsername = (EditText) findViewById(R.id.editbox_username);
        mPassword = (EditText) findViewById(R.id.editbox_password);
        mPassword.setOnKeyListener(mValidateButtonKeyListener);
        mPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        errorText = (TextView) findViewById(R.id.error_text);
        validationError = (TextView) findViewById(R.id.validation_failed);

        progressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_view);
        progressBarLayout.setVisibility(View.GONE);

        Selection.setSelection(mUsername.getText(), mUsername.getText().toString().length());

        validateButton = (Button) findViewById(R.id.validate_button);
        saveButton = (Button) findViewById(R.id.save_button);

        sharedPref = this.getSharedPreferences(Constants.prefName, MODE_PRIVATE);

        isFirstLogin = sharedPref.getBoolean(Constants.firstLoginKey, true);
        if(isFirstLogin) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
            mUsername.setText("");
            mUsername.setText(getUsername());
            mPassword.setText(getPassword());
            mUsername.setEnabled(false);
            mPassword.setEnabled(false);
        }


        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ValidateUser().execute(validationURL);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColeagueCredentials(mUsername.getText().toString(), mPassword.getText().toString(), getDomain(mUsername.getText().toString()));
                isCredentialsSaved = true;
                errorText.setVisibility(View.GONE);
            }
        });
    }

    private class ValidateUser extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBarLayout.setVisibility(View.GONE);
            if(s.equals("HTTP/1.1 200 OK")) {
                validationError.setVisibility(View.GONE);
                mUsername.setEnabled(false);
                mPassword.setEnabled(false);
                saveButton.setEnabled(true);
                saveButton.setTextColor(getResources().getColor(R.color.kirk_bal_green_border));
            } else {
                mUsername.setEnabled(true);
                mPassword.setEnabled(true);
                saveButton.setEnabled(false);
                saveButton.setTextColor(getResources().getColor(R.color.globalmenu_nav_drawer_profile_hitstate_color));
                validationError.setVisibility(View.VISIBLE);
            }
        }
    }

    private String downloadUrl(String urlString) throws IOException, JSONException {

        GDHttpClient gdHttpClient = new GDHttpClient();
        //gdHttpClient.disablePeerVerification();
        final HttpGet request = new HttpGet(urlString);

        gdHttpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
                new NTCredentials(getUsernameWithoutDomain(mUsername.getText().toString()),mPassword.getText().toString(), getHostname()
                        , getDomain(mUsername.getText().toString())));

        HttpResponse response = gdHttpClient.execute(request);

        String httpResponseCode = response.getStatusLine().toString();

        return httpResponseCode;
    }

    public String getHostname() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress netHost = InetAddress.getLocalHost();
                    hostName = netHost.getHostName();
                } catch (UnknownHostException ex) {
                    hostName = null;
                }
            }
        });

        t.start();

        return hostName;
    }

    private void saveColeagueCredentials(String username, String password, String domain) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.userKey, username);
        editor.putString(Constants.passKey, password);
        editor.putString(Constants.domainKey, domain);
        editor.putBoolean(Constants.firstLoginKey, false);
        editor.commit();

        setUsername(username);
        setPassword(password);
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };

    private String getUsernameWithoutDomain(String text) {
        String[] parts = text.split("\\\\");
        String domain = parts[0];
        String username = parts[1];
        return username;
    }

    private String getDomain(String text) {
        String[] parts = text.split("\\\\");
        String domain = parts[0];
        String username = parts[1];
        return domain;
    }

    View.OnKeyListener mValidateButtonKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                hideKeyboard(v);
                new ValidateUser().execute(validationURL);
            }
            return false;
        }
    };

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setUsername(String user) {
        this.userName = user;
    }

    private void setPassword(String pass) {
        this.password = pass;
    }

    protected String getUsername() {
        return sharedPref.getString(Constants.userKey, "");
    }

    protected String getPassword() {
        return sharedPref.getString(Constants.passKey, "");
    }

    protected boolean ifIsValidated() {
        return isValidated;
    }


    @Override
    public void onAuthorized() {

    }

    @Override
    public void onLocked() {

    }

    @Override
    public void onWiped() {

    }

    @Override
    public void onUpdateConfig(Map<String, Object> map) {

    }

    @Override
    public void onUpdatePolicy(Map<String, Object> map) {

    }

    @Override
    public void onUpdateServices() {

    }

    @Override
    public void onUpdateDataPlan() {

    }

    @Override
    public void onUpdateEntitlements() {

    }
}
