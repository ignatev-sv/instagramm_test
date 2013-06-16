package com.example.instagramtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import utils.NetworkStatus;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instagramtest.adapters.MainAdapter;
import com.example.instagramtest.items.MainItem;

public class MainActivity extends ListActivity {

	private List<MainItem> mItems;

	private MainAdapter mAdapter;

	private ProgressDialog parseDialog;
	private ProgressBar contentLoadingProgress;

	interface OnLoadCompleate {
		abstract void doneLoadingContent();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		if (NetworkStatus.isNetworkAvailable(MainActivity.this)) {
			try {
				JSONObject content = getContent();
				new ParseJson().execute(content);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		} else {
			Toast.makeText(MainActivity.this,
					"Please Check Your Internet Connection!", Toast.LENGTH_LONG)
					.show();
			finish();
		}

	}

	private void initViews() {
		mItems = new ArrayList<MainItem>();
		mAdapter = new MainAdapter(this, mItems);
		parseDialog = new ProgressDialog(this);
		parseDialog.setMessage("Parsing Result...");

		contentLoadingProgress = (ProgressBar) findViewById(R.id.bar);
	}

	private class ParseJson extends AsyncTask<JSONObject, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			parseDialog.show();
		}

		@Override
		protected Boolean doInBackground(JSONObject... params) {

			// check if server return data
			if (params[0] != null) {
				try {
					// get picture count
					int len = params[0].getJSONArray("data").length();

					JSONObject userObj;

					for (int i = 0; i < len; i++) {
						// get user info
						userObj = params[0].getJSONArray("data")
								.getJSONObject(i).getJSONObject("user");

						// get user picture and nick name
						mItems.add(new MainItem(userObj
								.getString("profile_picture"), userObj
								.getString("username")));
					}
				} catch (JSONException e) {
					e.printStackTrace();
					showErrorMessage();
				}
			} else {
				// showErrorMessage();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setListAdapter(mAdapter);
			parseDialog.dismiss();
		}
	}

	private void showErrorMessage() {
		Toast.makeText(this, getString(R.string.error_mes), Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * Get json content from web
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private JSONObject getContent() throws InterruptedException,
			ExecutionException {

		OnLoadCompleate doneLoading = new OnLoadCompleate() {

			@Override
			public void doneLoadingContent() {
				contentLoadingProgress.setVisibility(View.GONE);
			}
		};

		return new ContentLoader(doneLoading).execute(
				"https://api.instagram.com/v1/tags/snow/media/recent?client_id="
						+ ApplicationData.CLIENT_ID + "&count=1000").get();
	}

}
