package com.sakhan.receptionist.Activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sakhan.receptionist.datalayer.DataFetcher;
import com.sakhan.receptionist.services.CheckAppVersionService;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.SAutoBgButton;
import com.sakhan.receptionist.utils.Utils;
import com.sakhan.receptionist.wrapper.IAsyncTask;
import com.sakhan.receptionist.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class HomeActivity extends Activity
{

	ProgressDialog		loadingDialog				= null;
	ImageView			imgGetStarted				= null;
	public final int	FEEDBACK_ACTIVITY_REQUEST	= 1;
	Dialog				dialog						= null;
	SAutoBgButton		loginDialogButton;
	EditText			editTextUsername;
	EditText			editTextPassword;
	String				loginUsername				= null;
	String				loginPassword				= null;
	String				imeistring					= null;
	// String imsistring = null;
	SharedPreferences	pref						= null;
	public AlertDialog	myAlertDialog;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.home_screen_layout );

		dialog = new Dialog( HomeActivity.this );
		dialog.setContentView( R.layout.login_dialog_home_screen );
		dialog.setTitle( "Please enter Login details!" );

		loginDialogButton = ( SAutoBgButton ) dialog.findViewById( R.id.login_dialog_btnLogin );
		editTextUsername = ( EditText ) dialog.findViewById( R.id.login_dialog_edit_text_username );
		editTextPassword = ( EditText ) dialog.findViewById( R.id.login_dialog_edit_text_password );

		final Calendar TIME = Calendar.getInstance();
		TIME.set( Calendar.MINUTE, 0 );
		TIME.set( Calendar.SECOND, 0 );
		TIME.set( Calendar.MILLISECOND, 0 );

		final AlarmManager m = ( AlarmManager ) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
		final Intent i = new Intent( getApplicationContext(), CheckAppVersionService.class );
		PendingIntent serviceIntent = PendingIntent.getService( getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
		m.setRepeating( AlarmManager.RTC, TIME.getTime().getTime(), 2 * 60 * 1000, serviceIntent );
		// }

		/*
		 * requestWindowFeature( Window.FEATURE_NO_TITLE );
		 * getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN );
		 */
		pref = Utils.getSharedPreferences( getApplicationContext() );
		if( !pref.getBoolean( AppGlobal.APP_PREF_LOGIN_VALID, false ) )
		{
			pref.edit().putBoolean( AppGlobal.APP_PREF_LOGIN_VALID, false ).commit();

		}

		TelephonyManager telephonyManager;
		telephonyManager = ( TelephonyManager ) getSystemService( Context.TELEPHONY_SERVICE );

		imeistring = telephonyManager.getDeviceId();
		// imsistring = telephonyManager.getSubscriberId();

		loginDialogButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				loginUsername = editTextUsername.getText().toString();
				loginPassword = editTextPassword.getText().toString();
				if( !Utils.isNullOrEmpty( loginUsername ) && !Utils.isNullOrEmpty( loginPassword ) )
				{
					new DataFetcher( new IAsyncTask()
					{

						@Override
						public void success( ResponseStatusWrapper response )
						{

							if( loadingDialog != null )
							{
								loadingDialog.dismiss();
								pref.edit().putBoolean( AppGlobal.APP_PREF_LOGIN_VALID, true ).commit();
								Intent feedbackIntent = new Intent( HomeActivity.this, FeedbackActivity.class );
								startActivityForResult( feedbackIntent, FEEDBACK_ACTIVITY_REQUEST );
							}

						}

						@Override
						public void fail( ResponseStatusWrapper response )
						{

							loadingDialog.dismiss();
							Toast.makeText( getApplicationContext(), response.message, Toast.LENGTH_SHORT ).show();

						}

						@Override
						public void doWait()
						{

							loadingDialog = new ProgressDialog( HomeActivity.this );
							loadingDialog.setMessage( AppGlobal.TOAST_PLEASE_WAIT );
							loadingDialog.setCancelable( false );
							loadingDialog.show();

						}
					}, getApplicationContext() ).execute( AppGlobal.DATAFETCHER_ACTION_LOGIN, loginUsername, loginPassword, imeistring );
				}
				else
					Toast.makeText( getApplicationContext(), "Please enter username or password", Toast.LENGTH_SHORT ).show();
			}
		} );

		imgGetStarted = ( ImageView ) findViewById( R.id.home_screen_getStarted_btn );
		imgGetStarted.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				if( !pref.getBoolean( AppGlobal.APP_PREF_LOGIN_VALID, false ) )
				{
					dialog.show();
				}
				else
				{
					Intent feedbackIntent = new Intent( HomeActivity.this, FeedbackActivity.class );
					startActivityForResult( feedbackIntent, FEEDBACK_ACTIVITY_REQUEST );
				}

				// TODO Auto-generated method stub
				// Intent intent = new Intent( HomeActivity.this,
				// MainMenuActivity.class );
				// startActivity( intent );
			}
		} );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{

		super.onActivityResult( requestCode, resultCode, data );

		if( requestCode == FEEDBACK_ACTIVITY_REQUEST )
		{
			// Make sure the request was successful
			if( resultCode == RESULT_OK )
			{
				// The user picked a contact.
				// The Intent's data Uri identifies which contact was selected.

				// Do something with the contact here (bigger example below)
				Intent feedbackIntent = new Intent( HomeActivity.this, MainMenuActivity.class );
				startActivity( feedbackIntent );
			}
		}
	}

	@Override
	public void onBackPressed()
	{

	}

	protected void onResume()
	{

		super.onResume();
		registerReceiver( appVersionUpdatedBroadcastReceiver, new IntentFilter( CheckAppVersionService.INTENT_VERSION_UPDATED ) );
	}

	@Override
	protected void onPause()
	{

		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver( appVersionUpdatedBroadcastReceiver );
	}

	private BroadcastReceiver	appVersionUpdatedBroadcastReceiver	= new BroadcastReceiver()
																	{
																		@Override
																		public void onReceive( Context context, Intent intent )
																		{

																			if( myAlertDialog != null && myAlertDialog.isShowing() )
																				return;

																			// Toast.makeText(
																			// getApplicationContext(),
																			// "App Version Updated",
																			// Toast.LENGTH_LONG
																			// ).show();
																			AlertDialog.Builder builder = new AlertDialog.Builder( HomeActivity.this );
																			builder.setTitle( "Download Latest Version" ).setMessage( "New version available for this app, Download Now?" );
																			builder.setPositiveButton( "YES", new DialogInterface.OnClickListener()
																			{

																				@Override
																				public void onClick( DialogInterface dialog, int which )
																				{

																					// TODO
																					// Auto-generated
																					// method
																					// stub
																					String url = Utils.getLatestBuildUrl( getApplicationContext() );
																					if( !url.equals( "" ) )
																					{
																						Intent i = new Intent( Intent.ACTION_VIEW );
																						i.setData( Uri.parse( url ) );
																						startActivity( i );
																					}
																				}
																			} );
																			builder.setNegativeButton( "NO", null );
																			builder.setCancelable( false );
																			myAlertDialog = builder.create();
																			myAlertDialog.show();
																		}
																	};

}
