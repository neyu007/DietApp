package com.example.dietaryapplication;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This AsyncTask provides the ability to execute HTTP GET request. This is used
 * when accessing the Parse Service in the Background thread. The request
 * response is returned to the caller via a callback interface.
 * 
 */
public class AsyncTaskToGet extends AsyncTask<Void, Void, String> {

	AsyncTaskListener asyncTaskListener;

	// private SharedPreferences.Editor sharedPreferencesEditor;

	public void setMainAsyncTaskListener(AsyncTaskListener myAsyncTaskListener) {

		this.asyncTaskListener = myAsyncTaskListener;

	}

	private Context context;
	private String url;
	private ProgressDialog progressDialog;

	/**
	 * Constructor for AsyncTaskToGet class.
	 * 
	 * @param context
	 * @param url
	 * 
	 */
	public AsyncTaskToGet(Context context, String url) {
		this.context = context;
		this.url = url;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Loading, please wait...");
		progressDialog.show();
		progressDialog.setCancelable(false);
	}

	@Override
	protected String doInBackground(Void... arg0) {
		try {
			return getJsonObjectReturnString(url);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":{\"error\": \"" + e.getMessage() + "\"}}";
		}
	}

	@Override
	protected void onProgressUpdate(Void... unused) {
	}

	@Override
	protected void onPostExecute(String result) {
		if (progressDialog.isShowing() && progressDialog != null)
			progressDialog.dismiss();

		if (result != null && result.length() != 0) {
			if (result.contains("error"))
				asyncTaskListener.asyncTaskFailed(result);
			else
				asyncTaskListener.asyncTaskSuccessful(result);
		}else{
			asyncTaskListener.asyncTaskFailed("{\"result\":{\"error\":\"Data length of response from API is 0.\"}}");
		}
	}

	/**
	 * A HTTP GET Method A method used to process request and get data from
	 * parse service
	 * 
	 * @param url
	 *            This parameter is required and should not be null.
	 * @param sharedPrefs
	 *            A sharedpreferences where X-Parse-Application-ID and
	 *            X-Parse-Rest-Api-Key are stored and needed in the HTTP
	 *            Request. A required parameter and should not be null.
	 * @param sessionToken
	 *            This is not a required parameter.
	 */
	public static String getJsonObjectReturnString(String url) {

		String result = "";	
		
		String appId = Constants.X_PARSE_APP_ID_VALUE;
		String apiKey = Constants.X_PARSE_REST_API_KEY_VALUE;		

		HttpGet request = new HttpGet(url);

		Log.i("TAG", url);
		request.addHeader("Content-Type", "application/json");
		request.addHeader(Constants.X_PARSE_APPLICATION_ID, appId);
		request.addHeader(Constants.X_PARSE_REST_API_KEY, apiKey);

		try {

			HttpClient httpClient = new DefaultHttpClient();
			httpClient = sslClient(httpClient);
			HttpResponse response = httpClient.execute(request);

			Log.i("TAG",
					"Status Code = "
							+ String.valueOf(response.getStatusLine()
									.getStatusCode()));

			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				Log.i("TAG", "Response = " + result);
			} else if (response.getStatusLine().getStatusCode() >= 500) {
				result = "";
				Log.i("TAG", "Response = 500, " + result);
			} else if (response.getStatusLine().getStatusCode() == 404) {
				result = "{\"result\":{\"error\":\"Api Endpoint Not Found.\"}}";
				Log.i("TAG", "Response = " + result);
			} else {
				result = EntityUtils.toString(response.getEntity());
				Log.i("TAG", "Response = " + result);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	static HttpClient sslClient(HttpClient client) {
		try {
			X509TrustManager tm = new X509TrustManager() {

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {

				}
			};
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			sr.register(new Scheme("https", ssf, 443));

			return new DefaultHttpClient(ccm, client.getParams());
		} catch (Exception ex) {
			return null;
		}
	}
}