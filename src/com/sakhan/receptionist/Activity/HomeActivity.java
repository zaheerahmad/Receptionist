package com.sakhan.receptionist.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sakhan.receptionist.datalayer.DataFetcher;
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
	String				imsistring					= null;
	SharedPreferences	pref						= null;

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
		imsistring = telephonyManager.getSubscriberId();

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
					}, getApplicationContext() ).execute( AppGlobal.DATAFETCHER_ACTION_LOGIN, loginUsername, loginPassword, imeistring, imsistring );
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

}
