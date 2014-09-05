package com.sakhan.receptionist.datalayer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.sakhan.receptionist.services.DataSendService;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.Utils;
import com.sakhan.receptionist.wrapper.FeedbackAlchemyWrapper;
import com.sakhan.receptionist.wrapper.IAsyncTask;
import com.sakhan.receptionist.wrapper.LoginResponseWrapper;
import com.sakhan.receptionist.wrapper.ResponseStatusWrapper;
import com.sakhan.receptionist.wrapper.ServerResponseWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class DataFetcher extends AsyncTask<String, String, ResponseStatusWrapper>
{

	IAsyncTask	async;
	Context		context;
	boolean		hasInternet	= false;

	/**
	 * @param asyncTask
	 * @param context
	 */
	public DataFetcher( IAsyncTask asyncTask, Context context )
	{

		this.async = asyncTask;
		this.context = context;

	}

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute()
	{

		/**
		 * Check If Network exists, otherwise return immediately.
		 */
		if( Utils.hasInternetConnection( this.context ) )
		{
			this.hasInternet = true;
			async.doWait();
		}
		/**
		 * Network checking ended.
		 */
	}

	static int		lastIndexS		= -1;
	static boolean	isFileWritten	= false;

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected synchronized ResponseStatusWrapper doInBackground( String... params )
	{

		try
		{
			if( !this.hasInternet )
			{
				ResponseStatusWrapper response = new ResponseStatusWrapper();

				response.status = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION;
				response.message = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE;
				return response;
			}

			if( params != null && params[0].equalsIgnoreCase( AppGlobal.DATAFETCHER_ACTION_INSERT_FEEDBACK ) )
			{
				int lastInsertedFeed = -1;

				DatabaseHandler db = new DatabaseHandler( context, AppGlobal.TABLE_FEEDBACK );
				if( db.getFeedbackCount() > 0 )
				{
					FeedbackBO feed = db.getLastFeedbackInserted();
					SharedPreferences prefs = Utils.getSharedPreferences( context );
					boolean isDataDirty = prefs.getBoolean( AppGlobal.APP_PREF_IS_DATA_DIRTY, false );
					int lastFeedbackId = prefs.getInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, -1 );
					if( feed != null && !isDataDirty && feed.getFeedbackId() <= lastFeedbackId )
					{
						ResponseStatusWrapper response = new ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_SUCCESS;
						response.message = AppGlobal.RESPONSE_STATUS_SUCCESS_MESSAGE;

						return response;
					}
					List<FeedbackBO> feedbackList = db.getAllFeedbacksGreaterThanId( lastFeedbackId );
					if( feedbackList != null && feedbackList.size() > 0 )
					{

						List<FeedbackAlchemyWrapper> listFeedbackAlchemyWrapper = new ArrayList<FeedbackAlchemyWrapper>();

						StringBuilder strBuilder = new StringBuilder();

						String timeStamp = new SimpleDateFormat( "yyyy:MM:dd_HH:mm:ss" ).format( new Date() );
						strBuilder.append( timeStamp );
						strBuilder.append( "\r\n\r\n\r\n" );

						for( int i = 0 ; i < feedbackList.size() ; i++ )
						{
							lastInsertedFeed = feedbackList.get( i ).getFeedbackId();

							String sentimentStringAlchemy = Utils.getSentimentText( feedbackList.get( i ).getFeedbackText(), context );
							String conceptsStringAlchemy = Utils.getConceptText( feedbackList.get( i ).getFeedbackText(), context );
							String keywordStringAlchemy = Utils.getKeyWordText( feedbackList.get( i ).getFeedbackText(), context );
							String entitiesStringAlchemy = Utils.getEntityText( feedbackList.get( i ).getFeedbackText(), context );
							String languageStringAlchemy = Utils.getLanguageText( feedbackList.get( i ).getFeedbackText(), context );

							strBuilder.append( "Feedback Id: " + feedbackList.get( i ).getFeedbackId() + "\n" );
							strBuilder.append( "Name: " + feedbackList.get( i ).getFname() + " " + feedbackList.get( i ).getLname() + "\n" );
							strBuilder.append( "Telephone: " + feedbackList.get( i ).getTelephone() + "\n" );
							strBuilder.append( "Feedback: " + feedbackList.get( i ).getFeedbackText() + "\n" );
							strBuilder.append( "Sentiment: " + sentimentStringAlchemy + "\n" );
							strBuilder.append( "Concept: " + conceptsStringAlchemy + "\n" );
							strBuilder.append( "Keyword(s): " + keywordStringAlchemy + "\n" );
							strBuilder.append( "Entity(ies): " + entitiesStringAlchemy + "\n" );
							strBuilder.append( "Language: " + languageStringAlchemy + "\n" );
							strBuilder.append( "\r\n\t________________________\r\n\r\n" );

							DatabaseHandler dbFeedbackAlchemy = new DatabaseHandler( context, AppGlobal.TABLE_FEEDBACK_ALCHEMY );
							dbFeedbackAlchemy.addFeedbackAlchemy( new FeedbackAlchemyBO( feedbackList.get( i ).getFeedbackId(), keywordStringAlchemy, conceptsStringAlchemy, languageStringAlchemy, entitiesStringAlchemy, sentimentStringAlchemy ) );

							listFeedbackAlchemyWrapper.add( new FeedbackAlchemyWrapper( feedbackList.get( i ), sentimentStringAlchemy, languageStringAlchemy, keywordStringAlchemy, entitiesStringAlchemy, conceptsStringAlchemy ) );
						}
						lastIndexS = lastInsertedFeed;
						Gson gson = new Gson();
						String jsonParams = gson.toJson( listFeedbackAlchemyWrapper );

						if( AppGlobal.shouldMaintainLogOfFeeds )
						{
							String str_SaveFolderName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Receptionist-App-Demo-Log-Temp";

							File logFileDirectory = new File( str_SaveFolderName );
							if( !logFileDirectory.exists() )
								logFileDirectory.mkdirs();

							String logFileName = "logFile.txt";

							File file = new File( str_SaveFolderName, logFileName );
							FileOutputStream fOut = null;
							try
							{
								fOut = new FileOutputStream( file, true );
							}
							catch ( FileNotFoundException e )
							{
								e.printStackTrace();
							}

							OutputStreamWriter osw = new OutputStreamWriter( fOut );
							try
							{
								osw.write( strBuilder + "\r\n" );
								osw.flush();
								osw.close();
							}
							catch ( IOException e )
							{
								e.printStackTrace();
							}
						}

						isFileWritten = false;

						URL url = new URL( AppGlobal.SERVER_URL_SAVE_FEEDBACK );

						HttpURLConnection httpUrlConnection = ( HttpURLConnection ) url.openConnection();
						httpUrlConnection.setRequestMethod( "POST" );
						httpUrlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
						// httpUrlConnection.setRequestProperty(
						// "Content-Length", "" + Integer.toString(
						// jsonParams.getBytes().length ) );
						httpUrlConnection.setRequestProperty( "Content-Language", "en-US" );
						httpUrlConnection.setUseCaches( false );
						httpUrlConnection.setDoInput( true );
						httpUrlConnection.setDoOutput( true );

						DataOutputStream outputStream = new DataOutputStream( httpUrlConnection.getOutputStream() );

						outputStream.writeBytes( "requestParam=" + jsonParams );

						outputStream.flush();
						outputStream.close();

						// get response
						InputStream responseStream = new BufferedInputStream( httpUrlConnection.getInputStream() );
						Reader reader = new InputStreamReader( responseStream );
						ServerResponseWrapper serverResponse = gson.fromJson( reader, ServerResponseWrapper.class );

						if( serverResponse.getCode() == 1 )
						{
							ResponseStatusWrapper response = new ResponseStatusWrapper();

							response.status = AppGlobal.RESPONSE_STATUS_SUCCESS;
							response.message = AppGlobal.RESPONSE_STATUS_SUCCESS_MESSAGE;

							prefs.edit().putInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, lastInsertedFeed ).commit();
							isFileWritten = true;
							/*
							 * if( isFileWritten )
							 * {
							 * prefs.edit().putInt(
							 * AppGlobal.APP_PREF_LAST_INSERTED_ID,
							 * lastInsertedFeed ).commit();
							 * isFileWritten = false;
							 * }
							 */
							return response;
						}
						else
						{
							ResponseStatusWrapper response = new ResponseStatusWrapper();

							response.status = AppGlobal.RESPONSE_STATUS_FAIL;
							response.message = AppGlobal.RESPONSE_STATUS_FAIL_MESSAGE;

							if( isFileWritten )
							{
								prefs.edit().putInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, lastInsertedFeed ).commit();
								isFileWritten = false;
							}
							return response;
						}
					}
				}
			}
			if( params != null && params[0].equalsIgnoreCase( AppGlobal.DATAFETCHER_ACTION_LOGIN ) )
			{
				String loginUsername = params[1];
				String loginPassword = params[2];
				String IMEI = params[3];

				String url = AppGlobal.SERVER_URL_LOGIN + "?un=" + loginUsername + "&pwd=" + loginPassword + "&imei=" + IMEI;
				ResponseStatusWrapper response = new ResponseStatusWrapper();

				StringBuilder messageFeedBuilder = new StringBuilder();
				HttpClient httpClient = new DefaultHttpClient();
				try
				{
					// pass search URL string to fetch
					HttpPost httpPost = new HttpPost( url );

					HttpResponse httpResponse = httpClient.execute( httpPost );
					// check status, only proceed if ok
					StatusLine searchStatus = httpResponse.getStatusLine();
					if( searchStatus.getStatusCode() == 200 )
					{
						// get the response
						HttpEntity messageEntity = httpResponse.getEntity();
						InputStream messageContent = messageEntity.getContent();
						// process the results
						InputStreamReader messageInput = new InputStreamReader( messageContent );
						BufferedReader messageReader = new BufferedReader( messageInput );
						String lineIn;
						while ( ( lineIn = messageReader.readLine() ) != null )
						{
							messageFeedBuilder.append( lineIn );
						}
					}

					LoginResponseWrapper loginResponseWrapper = new Gson().fromJson( messageFeedBuilder.toString(), LoginResponseWrapper.class );
					Log.d( "GSON", loginResponseWrapper.getOrganizationAddress() + loginResponseWrapper.getOrganizationContactPerson() );

					if( Utils.isNullOrEmpty( loginResponseWrapper.getOrganizationName() ) )
					{
						response.status = AppGlobal.RESPONSE_STATUS_FAIL_INVALID_LOGIN;
						response.message = AppGlobal.RESPONSE_STATUS_FAIL_INVALID_LOGIN_MESSAGE;
						return response;
					}
					else
					{
						SharedPreferences prefs = Utils.getSharedPreferences( context );
						Editor editor = prefs.edit();
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_ID, loginResponseWrapper.getOrganizationId() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_NAME, loginResponseWrapper.getOrganizationName() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_ADDRESS, loginResponseWrapper.getOrganizationAddress() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_CONTACT_PRESON, loginResponseWrapper.getOrganizationContactPerson() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_TELEPHONE, loginResponseWrapper.getOrganizationTelephone() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_DEVICE_IMEI, loginResponseWrapper.getOrganizationDeviceImei() );
						editor.putString( AppGlobal.APP_PREF_ORGANIZATION_DEVICE_ID, loginResponseWrapper.getDeviceId() );
						editor.commit();
					}

				}
				catch ( Exception e )
				{
					response.status = AppGlobal.RESPONSE_STATUS_FAIL;
					response.message = AppGlobal.RESPONSE_STATUS_FAIL_MESSAGE;
					return response;
				}

				response.status = AppGlobal.RESPONSE_STATUS_SUCCESS;
				response.message = AppGlobal.RESPONSE_STATUS_SUCCESS_MESSAGE;
				return response;
			}

		}
		catch ( Exception ex )
		{
			ResponseStatusWrapper response = new ResponseStatusWrapper();

			response.status = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION;
			response.message = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE;
			response.debugInfo = ex.getMessage();

			if( AppGlobal.shouldMaintainLogOfFeeds && isFileWritten )
			{
				SharedPreferences prefs = Utils.getSharedPreferences( context );
				prefs.edit().putInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, lastIndexS ).commit();
				isFileWritten = false;
			}
			return response;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute( ResponseStatusWrapper response )
	{

		if( response != null )
		{

			if( response.status == AppGlobal.RESPONSE_STATUS_SUCCESS )
				async.success( response );
			else
				async.fail( response );
		}
		DataSendService.isServiceRunning = false;
	}
}
