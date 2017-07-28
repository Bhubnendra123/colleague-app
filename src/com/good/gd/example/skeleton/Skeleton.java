/*
 *  This file contains Good Sample Code subject to the Good Dynamics SDK Terms and Conditions.
 *  (c) 2013 Good Technology Corporation. All rights reserved.
 */

package com.good.gd.example.skeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.good.gd.GDAndroid;
import com.good.gd.GDStateListener;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.auth.NTCredentials;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.net.GDHttpClient;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;


/* Skeleton - the entry point activity which will start authorization with Good Dynamics
 * and once done launch the application UI.
 */
public class Skeleton extends Activity implements GDStateListener,AdapterView.OnItemClickListener {

	private ListView mColleagueListview;
	private ArrayList<Colleague> colleaguesList = new ArrayList<>();
	private ArrayList<String> colleaguesDetailsLinkList = new ArrayList<>();
	private ArrayList<String> colleaguesDetailsList = new ArrayList<>();
	private ArrayList<Colleague> colleaguesSearchList = new ArrayList<>();
	private List<String> mDetailsArrayList2 = new ArrayList<String>();
	private ArrayList<String> nextPageLinkList3 = new ArrayList<>();

	private ArrayList<String> nextPageLinkList = new ArrayList<>();
	private ArrayList<String> nextPageLinkList2 = new ArrayList<>();
	private static final String TAG = Skeleton.class.getSimpleName();

	private Switch msearchToggle;

	private RelativeLayout mSearchDisableContainer;
	private RelativeLayout mSearchEnableContainer;
	private RelativeLayout dividerLayout;
	private RelativeLayout mainLayout;
	private RelativeLayout progressBarLayout;

	private EditText mSearchEditText;
	private String hostName;
	private String resultString = "";
	private String imageUrl;
	private Bitmap colleagueImage;
	private static Bitmap profileBitmap;
	private ImageView search;
	private ImageView searchCancel;
	private ImageView userImage;
	private ProgressBar progressBar;
	private TextView searchEnableText;
	private TextView searchResultErrorText;
	private Document responseDocument;
	private SharedPreferences sharedPreferences;

	protected static boolean isDetailView = false;
	protected static boolean isFirstLogin;
	private boolean isToggleChecked = true;
	private SettingsActivity mSettingsActivity;

	private List<Colleague> colleagueList;

	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GDAndroid.getInstance().activityInit(this);
		setContentView(R.layout.activity_main);

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

		mSettingsActivity = new SettingsActivity();
		colleaguesList = new ArrayList<Colleague>();
		colleaguesSearchList = new ArrayList<Colleague>();
		colleaguesDetailsList = new ArrayList<>();
		colleaguesDetailsLinkList = new ArrayList<>();
		nextPageLinkList3 = new ArrayList<>();


		msearchToggle = (Switch) findViewById(R.id.search_toggle);
		msearchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked) {
					isToggleChecked = true;
				} else {
					isToggleChecked = false;
				}
			}
		});
		mSearchEditText = (EditText) findViewById(R.id.search_edit_text);
		searchEnableText = (TextView) findViewById(R.id.search_enable_text);
		searchResultErrorText = (TextView) findViewById(R.id.search_result_error);
		userImage = (ImageView) findViewById(R.id.image);

		mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
		mSearchEnableContainer = (RelativeLayout) findViewById(R.id.search_enable_container);
		mSearchDisableContainer = (RelativeLayout) findViewById(R.id.search_disable_container);
		dividerLayout = (RelativeLayout) findViewById(R.id.divider_layout);
		progressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_view);
		progressBarLayout.setVisibility(View.GONE);

		sharedPreferences = getSharedPreferences(Constants.prefName, MODE_PRIVATE);
		isFirstLogin = sharedPreferences.getBoolean(Constants.firstLoginKey, true);
		if(isFirstLogin) {
			mSearchEnableContainer.setVisibility(View.GONE);
			mSearchDisableContainer.setVisibility(View.VISIBLE);
			searchEnableText.setVisibility(View.VISIBLE);
			dividerLayout.setVisibility(View.VISIBLE);
		} else {
			SettingsActivity.isCredentialsSaved = true;
			searchEnableText.setVisibility(View.GONE);
			dividerLayout.setVisibility(View.VISIBLE);
		}

		mColleagueListview = (ListView) findViewById(R.id.colleagueListView);
		mColleagueListview.setEmptyView(findViewById(R.id.dividerView));

		search = (ImageView) findViewById(R.id.searchImage);
		searchCancel = (ImageView) findViewById(R.id.search_cancel);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyboard(v);
				colleaguesList.clear();
				colleaguesDetailsLinkList.clear();
				colleaguesSearchList.clear();
				colleaguesDetailsList.clear();
				mDetailsArrayList2.clear();
				if(isToggleChecked) {
					new RetrieveDataTask().execute(getResources().getString(R.string.base_url) +
							getSearchText(mSearchEditText.getText().toString()) + getResources().getString(R.string.search_exact));
				} else {
					new RetrieveDataTask().execute(getResources().getString(R.string.base_url)
							+ getSearchText(mSearchEditText.getText().toString()) + getResources().getString(R.string.search_not_exact));
				}
			}
		});
		mSearchEditText.setOnKeyListener(mSearchKeyListener);

		searchCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSearchEditText.setText("");
			}
		});

		mSearchEditText.addTextChangedListener(mSearchEditTextWatcher);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (SettingsActivity.isCredentialsSaved) {
			searchEnableText.setVisibility(View.INVISIBLE);
			mainLayout.removeView(mSearchDisableContainer);
			mSearchDisableContainer.setVisibility(View.GONE);
			mSearchEnableContainer.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(Skeleton.this, SettingsActivity.class);
		startActivity(intent);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	/*
                 * Activity specific implementation of GDStateListener.
                 *
                 * If a singleton event Listener is set by the application (as it is in this case) then setting
                 * Activity specific implementations of GDStateListener is optional
                 */
	@Override
	public void onAuthorized() {
		//If Activity specific GDStateListener is set then its onAuthorized( ) method is called when 
		//the activity is started if the App is already authorized 
		Log.i(TAG, "onAuthorized()");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		isDetailView = true;
		new RetrieveDataTask().execute(colleaguesDetailsLinkList.get(position));
	}

	private class RetrieveDataTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBarLayout.setVisibility(View.VISIBLE);
			//progressBar.setVisibility(View.VISIBLE);
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

			colleaguesList = getColleagueSearchList(s);
			colleaguesDetailsLinkList = getColleagueDetailsList(s);

			String nextPageLink = "";
			String id = "";
			Elements csd = responseDocument.select("div[class=srch-Page srch-Page-bg]").select("a");
			for(Element link: csd) {
				if(link.attr("id").equals("SRP_NextImg")) {
					nextPageLink = link.attr("href");
				}
			}

			if (!nextPageLink.equals("")) {
					new RetrieveDataTask().execute(getResources().getString(R.string.base_url2) + nextPageLink);
			} else {
				if (s.equals("BAD REQUEST")) {
					searchResultErrorText.setText("BAD REQUEST");
				} else if (s.equals("Unauthorized")) {
					searchResultErrorText.setText("Unauthorized");
				} else if (s.equals("Connection Failed")) {
					searchResultErrorText.setText("Connection Failed");
				} else {
					progressBarLayout.setVisibility(View.GONE);
					//progressBar.setVisibility(View.GONE);
					String imageUrl = "";
					if (responseDocument != null) {
						Element image = responseDocument.select("img[alt=User Photo]").first();
						if (image != null) {
							imageUrl = image.absUrl("src");
						}
					}

					if (isDetailView) {
						if (!imageUrl.equals("")) {
							new ImageLoadTask(s, imageUrl).execute();
						} else {
							showDetailsActivity(s, null);
						}
					} else {
						createList(s, colleaguesList);
					}
				}
			}
		}
	}

	private void showDetailsActivity(String resultString, Bitmap image) {
		Colleague colleague = getColleagueDetails(resultString);

		if(colleague.getName().equals("")) {
			Toast.makeText(this, getResources().getString(R.string.details_failed_error), Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(this, ColleagueDetailsActivity.class);
			intent.putExtra("colleague_details", colleague);
			intent.putExtra("Image", image);
			intent.putExtra("username", getUsernameWithoutDomain());
			intent.putExtra("password", getPassword());
			intent.putExtra("domain", getDomain());
			startActivity(intent);
		}
	}

	private void createList(String resultString, ArrayList<Colleague> colleagueList1) {
		mColleagueListview.setAdapter(null);
		setResultString(resultString);
		//colleaguesList = getColleagueSearchList(getResultString());
		dividerLayout.setVisibility(View.GONE);
		ColleagueListAdapter mAdapter = new ColleagueListAdapter(getApplicationContext(), colleagueList1);
		mAdapter.notifyDataSetChanged();
		mColleagueListview.setAdapter(mAdapter);
		mColleagueListview.setOnItemClickListener(this);
	}

	private void addList(String nextPage) {
		colleaguesList = getColleagueSearchList(nextPage);
		ColleagueListAdapter mAdapter = new ColleagueListAdapter(getApplicationContext(), colleaguesList);
		mAdapter.notifyDataSetChanged();
	}

	private String downloadUrl(String urlString) throws IOException, JSONException {

		GDHttpClient gdHttpClient1 = new GDHttpClient();
		final HttpGet request1 = new HttpGet(urlString);
		gdHttpClient1.getCredentialsProvider().setCredentials(AuthScope.ANY, new NTCredentials(getUsernameWithoutDomain(), getPassword(), getHostname(), getDomain()));
		HttpResponse response1 = gdHttpClient1.execute(request1);
		InputStream stream1 = response1.getEntity().getContent();

		StringBuilder sb = new StringBuilder();

		if(response1.getStatusLine().getStatusCode() == 200) {
			try {
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(stream1), 65728);
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			responseDocument = Jsoup.parse(sb.toString());
		} else if(response1.getStatusLine().getStatusCode() == 400) {
			sb.append("BAD REQUEST");
		} else if(response1.getStatusLine().getStatusCode() == 401) {
			sb.append("Unauthorized");
		} else {
			sb.append("Connection Failed");
		}
		return sb.toString();
	}

	private ArrayList<String> getColleagueDetailsList(String resultString) {
		Document doc = Jsoup.parse(resultString);
		Elements colleagueNameElements = doc.select("td[class=ms-vb]").select("td[width=20%]");

		for (Element element : colleagueNameElements) {
			Elements href = element.getElementsByTag("a");
			String link = href.attr("href");
			colleaguesDetailsList.add(link);
		}

		return colleaguesDetailsList;
	}

	private ArrayList<Colleague> getColleagueSearchList(String resultString) {
		Document doc = Jsoup.parse(resultString);
		Elements colleagueNameElements = doc.select("td[class=ms-vb]").select("td[width=20%]");
		Elements colleagueElements = doc.select("tr").select("td[class=ms-vb]");
		/*Element csd = doc.select("div[class=srch-Page srch-Page-bg]").select("a").first();

		nextPageLink = csd.attr("href");*/

		ArrayList<String> mDetailsArrayList3 = new ArrayList<>();
		for(Element element : colleagueElements) {
			String text = element.text();

			mDetailsArrayList3.add(text);
		}

		int i=0;
		while (i < mDetailsArrayList3.size()) {
			String name = mDetailsArrayList3.get(i);
			i = i+1;
			String title = mDetailsArrayList3.get(i);
			i = i+1;
			String department = mDetailsArrayList3.get(i);
			i = i+1;
			String location = mDetailsArrayList3.get(i);
			i = i+1;
			String landline = mDetailsArrayList3.get(i);
			i = i+1;
			String mobile = mDetailsArrayList3.get(i);
			i = i+1;
			String email = mDetailsArrayList3.get(i);
			i = i+1;

			Colleague colleague = new Colleague(name,title,department,location, landline, mobile, email);
			colleaguesSearchList.add(colleague);
		}

		return colleaguesSearchList;
	}

	protected Colleague getColleagueDetails(String resultString) {
		Document doc = Jsoup.parse(resultString);

		String title = doc.title();
		String firstName = doc.getElementsContainingOwnText("First name").select("a").text();
		String lastName = doc.getElementsContainingOwnText("Last name").select("a").text();
		String userID = doc.getElementsContainingOwnText("User ID").select("a").text();
		String division = doc.getElementsContainingOwnText("Division").select("a").text();
		String businessUnit = doc.getElementsContainingOwnText("Business Unit").select("a").text();
		String department = doc.getElementsContainingOwnText("Department").select("a").text();
		String jobTitle = doc.getElementsContainingOwnText("Job Title").select("a").text();
		String postalAddress = doc.getElementsContainingOwnText("Postal Address").select("a").text();
		String costCentre = doc.getElementsContainingOwnText("Cost Centre").select("a").text();
		String lineManager = doc.getElementsContainingOwnText("Line Manager").select("a").text();
		String internalLandline = doc.getElementsContainingOwnText("Internal Landline").select("a").text();
		String landlineText = doc.getElementsContainingOwnText("Landline").select("a").text();
		String landline = "";
		if(internalLandline.equals("")) {
			landline = landlineText;
		} else {
			landline = getLandline(landlineText);
		}
		String mobile = doc.getElementsContainingOwnText("Mobile").select("a").text();
		String altContactNumber = doc.getElementsContainingOwnText("Alternative Contact Number").select("a").text();
		String displayName = doc.getElementsContainingOwnText("Display Name").select("a").text();
		String email = doc.getElementsContainingOwnText("Email Address").select("a").text();
		String homePhone = doc.getElementsContainingOwnText("Home phone").select("a").text();
		String secretary = doc.getElementsContainingOwnText("Secretary").select("a").text();
		String country = doc.getElementsContainingOwnText("Country").select("a").text();
		String website = doc.getElementsContainingOwnText("Website").select("a").text();
		String addLocation = doc.getElementsContainingOwnText("Additional location").select("a").text();
		String axisgroup = doc.getElementsContainingOwnText("AXIS Support group").select("a").text();
		String mwotGroup = doc.getElementsContainingOwnText("Master Work Order Tool (MWOT) Group").select("a").text();

		String name = firstName + " " + lastName;
		Elements href = doc.getElementsContainingOwnText("Line Manager").select("a");
		String lineManagerDetailsLink = href.attr("href");

		Colleague colleague = new Colleague(title, name, mobile, altContactNumber, homePhone, internalLandline, landline, postalAddress,
				email, lineManager, secretary, jobTitle, department, businessUnit, division, userID, country, costCentre, website,
				addLocation, axisgroup, mwotGroup, lineManagerDetailsLink);

		return colleague;
	}

	private void setResultString(String result) {
		resultString = result;
	}

	private String getResultString() {
		return resultString;
	}


	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private String responseString;

		public ImageLoadTask(String response, String url) {
			this.url = url;
			this.responseString = response;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {

				GDHttpClient gdHttpClient = new GDHttpClient();
				final HttpGet request = new HttpGet(url);

				gdHttpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new NTCredentials(getUsernameWithoutDomain(), getPassword(), getHostname(), getDomain()));

				HttpResponse response = gdHttpClient.execute(request);
				InputStream input = response.getEntity().getContent();

				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			setImageBitmap(result);
			showDetailsActivity(responseString, result);
		}

	}

	private class MoreButtonOnClickListener implements View.OnClickListener{
		List<View.OnClickListener> listeners;

		public MoreButtonOnClickListener(){
			listeners = new ArrayList<View.OnClickListener>();
		}

		public void addOnClickListener(View.OnClickListener listener){
			listeners.add(listener);
		}

		@Override
		public void onClick(View v){
			for(View.OnClickListener listener : listeners){
				listener.onClick(v);
			}
		}
	}

	private String getLandline(String text) {
		String string = text;
		String[] parts = string.split(" ");
		String part1 = parts[0];
		String part2 = parts[1];
		return part2;
	}

	private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	private void setImageBitmap(Bitmap image) {
		colleagueImage = image;
	}

	protected Bitmap getImageBitmap() {
		return colleagueImage;
	}

	private String getUsernameWithoutDomain() {
		String text = sharedPreferences.getString(Constants.userKey, "");

		String[] parts = text.split("\\\\");
		String domain = parts[0];
		String username = parts[1];
		return username;
	}

	private String getPassword() {
		String password = sharedPreferences.getString(Constants.passKey, "");

		return password;
	}

	private String getDomain() {
		String domain = sharedPreferences.getString(Constants.domainKey, "");

		return domain;
	}

	private String getSearchText(String text) {
		return text.replaceAll("\\s+","");
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

	TextWatcher mSearchEditTextWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {

			if(mSearchEditText.getText().toString().equals("")) {
				search.setEnabled(false);
				mSearchEditText.setOnKeyListener(null);
			} else {
				search.setEnabled(true);
				mSearchEditText.setOnKeyListener(mSearchKeyListener);
			}
		}
	};

	View.OnKeyListener mSearchKeyListener = new View.OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
				hideKeyboard(v);
				colleaguesList.clear();
				colleaguesDetailsLinkList.clear();
				colleaguesSearchList.clear();
				colleaguesDetailsList.clear();
				mDetailsArrayList2.clear();
				if(isToggleChecked) {
					new RetrieveDataTask().execute(getResources().getString(R.string.base_url)
							+ getSearchText(mSearchEditText.getText().toString()) + getResources().getString(R.string.search_exact));
				} else {
					new RetrieveDataTask().execute(getResources().getString(R.string.base_url)
							+ getSearchText(mSearchEditText.getText().toString()) + getResources().getString(R.string.search_not_exact));
				}
			}
			return false;
		}
	};

	@Override
	public void onLocked() {
		Log.i(TAG, "onLocked()");
	}

	@Override
	public void onWiped() {
		Log.i(TAG, "onWiped()");
	}

	@Override
	public void onUpdateConfig(Map<String, Object> settings) {
		Log.i(TAG, "onUpdateConfig()");
	}

	@Override
	public void onUpdatePolicy(Map<String, Object> policyValues) {
		Log.i(TAG, "onUpdatePolicy()");
	}

	@Override
	public void onUpdateServices() {
		Log.i(TAG, "onUpdateServices()");
	}
	
	@Override
	public void onUpdateDataPlan(){
		Log.i(TAG, "onUpdateDataPlan()");
	}

    @Override
    public void onUpdateEntitlements() {
        Log.i(TAG, "onUpdateEntitlements()");
    }
}
