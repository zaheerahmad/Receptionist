/**
 * 
 */
package com.sakhan.receptionist.datalayer;

import java.io.InputStream;
import java.util.Scanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.sakhan.receptionist.services.ServiceHandler;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.Utils;
import com.sakhan.receptionist.wrapper.IAsyncTask;
import com.sakhan.receptionist.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class CheckVersionAsyncTask extends AsyncTask<String, Void, Boolean>
{
	private Context		context;
	private int			jobCode;
	private int			currentVersion;
	private IAsyncTask	asyncTask;

	public void setParameters( Context context, int jobCode, int currVersion, IAsyncTask async )
	{

		this.context = context;
		this.jobCode = jobCode;
		this.currentVersion = currVersion;
		this.asyncTask = async;

	}

	public CheckVersionAsyncTask( Context context, int jobCode, int currVersion, IAsyncTask async )
	{

		this.context = context;
		this.jobCode = jobCode;
		this.currentVersion = currVersion;
		this.asyncTask = async;
	}

	@Override
	protected Boolean doInBackground( String... params )
	{

		if( this.jobCode == 1 )
		{
			try
			{
				InputStream inputStream = ServiceHandler.makeServiceCall( AppGlobal.URL_APP_VERSION, null );
				Scanner s = null;
				s = new Scanner( inputStream );
				s.useDelimiter( "\\Z" );

				String response = s.next();

				int versionResponse = Integer.parseInt( response );
				if( versionResponse > currentVersion )
				{
					SharedPreferences prefs = Utils.getSharedPreferences( context );
					prefs.edit().putInt( "app_version", versionResponse ).commit();
					return false;
				}
			}
			catch ( Exception ex )
			{

			}
		}
		return true;
	}

	@Override
	protected void onPostExecute( Boolean result )
	{

		ResponseStatusWrapper responseWrapper = new ResponseStatusWrapper();
		if( result )
		{
			responseWrapper.message = "Success";
			this.asyncTask.success( responseWrapper );
		}
		else
		{
			responseWrapper.message = "Fail";
			this.asyncTask.fail( responseWrapper );
		}
	}

}
