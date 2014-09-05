/**
 * 
 */
package com.sakhan.receptionist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sakhan.receptionist.datalayer.CheckVersionAsyncTask;
import com.sakhan.receptionist.utils.Utils;
import com.sakhan.receptionist.wrapper.IAsyncTask;
import com.sakhan.receptionist.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class CheckAppVersionService extends Service
{
	public static String	INTENT_VERSION_UPDATED	= "app_version_updated";

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind( Intent arg0 )
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate()
	{

		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{

		// CheckVersionAsyncTask asyncTask = new CheckVersionAsyncTask();

		int appVersion = Utils.getAppVersion( getApplicationContext() );
		new CheckVersionAsyncTask( getApplicationContext(), 1, appVersion, new IAsyncTask()
		{
			
			@Override
			public void success( ResponseStatusWrapper response )
			{
			
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fail( ResponseStatusWrapper response )
			{
			
				// TODO Auto-generated method stub
				// It means new version is available...
				// Prompt user to update.
				Intent intent = new Intent( INTENT_VERSION_UPDATED );
				sendBroadcast( intent );
			}
			
			@Override
			public void doWait()
			{
			
				// TODO Auto-generated method stub
				
			}
		} ).execute( "" );
		return 0;
	}
}
