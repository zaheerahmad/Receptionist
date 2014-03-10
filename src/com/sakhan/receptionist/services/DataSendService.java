package com.sakhan.receptionist.services;

import com.sakhan.receptionist.datalayer.DataFetcher;
import com.sakhan.receptionist.datalayer.DatabaseHandler;
import com.sakhan.receptionist.datalayer.FeedbackBO;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.Utils;
import com.sakhan.receptionist.wrapper.IAsyncTask;
import com.sakhan.receptionist.wrapper.ResponseStatusWrapper;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

public class DataSendService extends Service
{
	public static boolean	isServiceRunning	= false;

	@Override
	public void onCreate()
	{

		// TODO Auto-generated method stub
		super.onCreate();

		if( AppGlobal.isDebugMode )
			Toast.makeText( getApplicationContext(), "Service Started...", Toast.LENGTH_LONG ).show();
	}

	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{

		// TODO do something useful
		if( isServiceRunning )
			return super.onStartCommand( intent, flags, startId );
		isServiceRunning = true;
		if( AppGlobal.isDebugMode )
			Toast.makeText( getApplicationContext(), "Service Started...", Toast.LENGTH_LONG ).show();

		new DataFetcher( new IAsyncTask()
		{

			@Override
			public void success( ResponseStatusWrapper response )
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_DATA_UPLOADED_SUCCESSFULLY, Toast.LENGTH_LONG ).show();

				DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_FEEDBACK );

				FeedbackBO feedback = db.getLastFeedbackInserted();
				SharedPreferences prefs = Utils.getSharedPreferences( getApplicationContext() );
				if( feedback != null && feedback.getFeedbackId() <= prefs.getInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, -1 ) )
				{
					// SharedPreferences prefs = Utils.getSharedPreferences(
					// getApplicationContext() );
					prefs.edit().putBoolean( AppGlobal.APP_PREF_IS_DATA_DIRTY, false ).commit();
				}
				isServiceRunning = false;
			}

			@Override
			public void fail( ResponseStatusWrapper response )
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
				{
					Toast.makeText( getApplicationContext(), "Data Uploading Error: " + response.message, Toast.LENGTH_LONG ).show();
					if( !Utils.isNullOrEmpty( response.debugInfo ) )
						Toast.makeText( getApplicationContext(), "Error Debug Info: " + response.debugInfo, Toast.LENGTH_LONG ).show();
				}
				isServiceRunning = false;
			}

			@Override
			public void doWait()
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_INTERNET_CONNECTION_FOUND, Toast.LENGTH_LONG ).show();
			}
		}, getApplicationContext() ).execute( new String[] { AppGlobal.DATAFETCHER_ACTION_INSERT_FEEDBACK } );
		return super.onStartCommand( intent, flags, startId );
	}

	@Override
	public void onDestroy()
	{

		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind( Intent intent )
	{

		// TODO for communication return IBinder implementation
		return null;
	}

}
