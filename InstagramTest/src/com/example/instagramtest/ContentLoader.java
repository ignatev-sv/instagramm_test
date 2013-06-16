package com.example.instagramtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.instagramtest.MainActivity.OnLoadCompleate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * this class load json data from backend and return it to parent
 * @author sergey
 *
 */
public class ContentLoader extends AsyncTask<String, Integer, JSONObject> {

	
	private OnLoadCompleate doneLoadingListener;

	public ContentLoader(OnLoadCompleate doneLoading) {
		doneLoadingListener  = doneLoading;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			URL example = new URL(params[0]);

			URLConnection tc = example.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			String line;
			StringBuffer buf = new StringBuffer();
			while ((line = in.readLine()) != null) {
				buf.append(line);
			}
			Log.d("Server response", buf.toString());

			return new JSONObject(buf.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		doneLoadingListener.doneLoadingContent();
	}
}
