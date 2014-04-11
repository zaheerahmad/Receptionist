package com.sakhan.receptionist.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sakhan.receptionist.datalayer.DatabaseHandler;
import com.sakhan.receptionist.datalayer.FeedbackBO;
import com.sakhan.receptionist.services.DataSendService;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.SAutoBgButton;
import com.sakhan.receptionist.utils.Utils;

/**
 * @author Zaheer Ahmad
 * 
 */
public class FeedbackActivity extends Activity
{

	private SAutoBgButton	sendButton;
	private EditText		textFName;
	private EditText		textLName;
	private EditText		textPhoneNumber;
	private EditText		textFeedback;
	private RelativeLayout	relativeLayout;
	private long			timer	= 60000;
	CountDownTimer			cDT;
	DatabaseHandler			db;

	// private SAutoBgButton homeBtn;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		db = new DatabaseHandler( FeedbackActivity.this, AppGlobal.TABLE_FEEDBACK );

		cDT = new CountDownTimer( timer, 1000 )
		{

			@Override
			public void onFinish()
			{

				finish();
			}

			@Override
			public void onTick( long arg0 )
			{

			}

		};

		cDT.start();

		// countTime( timer );
		super.onCreate( savedInstanceState );
		/*
		 * requestWindowFeature( Window.FEATURE_NO_TITLE );
		 * getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN );
		 */

		/*
		 * Intent dataServiceIntent = new Intent( FeedbackActivity.this,
		 * DataSendService.class );
		 * startService( dataServiceIntent );
		 */

		setContentView( R.layout.feedback );

		if( !DataSendService.isServiceRunning )
		{
			final Calendar TIME = Calendar.getInstance();
			TIME.set( Calendar.MINUTE, 0 );
			TIME.set( Calendar.SECOND, 0 );
			TIME.set( Calendar.MILLISECOND, 0 );

			final AlarmManager m = ( AlarmManager ) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
			final Intent i = new Intent( getApplicationContext(), DataSendService.class );
			PendingIntent serviceIntent = PendingIntent.getService( getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
			m.setRepeating( AlarmManager.RTC, TIME.getTime().getTime(), AppGlobal.SERVICE_DELAY, serviceIntent );
		}
		// setupUI( findViewById( R.id.main_parent ) );

		textFName = ( EditText ) findViewById( R.id.et_userFName );
		// textLName = ( EditText ) findViewById( R.id.et_userLName );
		// textLName.setVisibility( View.GONE );
		textPhoneNumber = ( EditText ) findViewById( R.id.et_cellNo );
		textFeedback = ( EditText ) findViewById( R.id.et_feedback );
		relativeLayout = ( RelativeLayout ) findViewById( R.id.feedback_relative_layout_parent );
		sendButton = ( SAutoBgButton ) findViewById( R.id.btn_sendFeedback );

		textFName.setOnFocusChangeListener( new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange( View arg0, boolean hasFocus )
			{
			
				if(hasFocus)
				{
					cDT.cancel();
					cDT.start();
				}
				
			}
		} );
		
		/*textFName.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				cDT.cancel();
				cDT.start();
			}
		} );*/

		textPhoneNumber.setOnFocusChangeListener( new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange( View arg0, boolean hasFocus )
			{
			
				if(hasFocus)
				{
					cDT.cancel();
					cDT.start();
				}
				
			}
		} );

		textFeedback.setOnFocusChangeListener( new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange( View arg0, boolean hasFocus )
			{
			
				if(hasFocus)
				{
					cDT.cancel();
					cDT.start();
				}
				
			}
		} );

		relativeLayout.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				cDT.cancel();
				cDT.start();
			}
		} );

		// homeBtn = ( SAutoBgButton ) findViewById(
		// R.id.main_menu_cover_homeButton );
		// homeBtn.setOnClickListener( new View.OnClickListener()
		// {
		// @Override
		// public void onClick( View v )
		// {
		//
		// Intent intent = new Intent( getApplicationContext(),
		// HomeActivity.class );
		// intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		// startActivity( intent );
		// }
		// } );

		sendButton.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				cDT.cancel();
				cDT.start();
				// TODO Auto-generated method stub

				if( Utils.isNullOrEmpty( textFName.getText().toString() ) || Utils.isNullOrEmpty( textFeedback.getText().toString() ) || Utils.isNullOrEmpty( textPhoneNumber.getText().toString() ) )
				{
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_MISSING_MANDATORY_FIELDS, Toast.LENGTH_LONG ).show();
				}
				else
				{
					String fname = Utils.isNullOrEmpty( textFName.getText().toString() ) ? "" : textFName.getText().toString();
					String lname = "";// Utils.isNullOrEmpty(
										// textLName.getText().toString() ) ? ""
										// : textLName.getText().toString();
					String telephone = Utils.isNullOrEmpty( textPhoneNumber.getText().toString() ) ? "" : textPhoneNumber.getText().toString();
					// String isAnonymous = checkBoxIsAnonymous.isChecked() ?
					// "true" : "false";
					String feedbackText = Utils.isNullOrEmpty( textFeedback.getText().toString() ) ? "" : textFeedback.getText().toString();

					Log.d( "Insert: ", "Inserting .." );

					if( AppGlobal.isDebugMode )
						Toast.makeText( getApplicationContext(), "Inserting Data in Feedback Table", Toast.LENGTH_LONG ).show();

					FeedbackBO feed = new FeedbackBO( 1, fname, lname, telephone, "false", feedbackText, new SimpleDateFormat( "yyyy:MM:dd HH:mm:ss" ).format( new Date() ) );
					db.addFeedback( feed );

					clearFields();

					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_THANKS_FOR_FEEDBACK, Toast.LENGTH_LONG ).show();

					if( MainMenuActivity.listFeedback != null && MainMenuActivity.listFeedback.size() > 0 )
						MainMenuActivity.listFeedback.add( feed );
					// MainMenuActivity.feedbackAdapter.notifyDataSetChanged();

					setResult( RESULT_OK );
					finish();

					Log.d( "Reading: ", "Reading all feedbacks.." );

					if( AppGlobal.isDebugMode )
						Toast.makeText( getApplicationContext(), "Reading all Feedbacks..", Toast.LENGTH_LONG ).show();
					List<FeedbackBO> feedbacks = db.getAllFeedbacks();
					db.close();
					for( FeedbackBO feedback : feedbacks )
					{
						String log = "Org Id: " + feedback.getOrganizationId() + " ,Name: " + feedback.getFname() + " " + feedback.getLname() + " ,Phone: " + feedback.getTelephone() + " ,Feedback Text: " + feedback.getFeedbackText() + " ,Is Anonymous?: " + feedback.getIsAnonymous();
						Log.d( "Feedback {" + feedback.getFeedbackId() + "}: ", log );
					}
					SharedPreferences prefs = Utils.getSharedPreferences( getApplicationContext() );
					prefs.edit().putBoolean( AppGlobal.APP_PREF_IS_DATA_DIRTY, true ).commit();

					// AppGlobal.isDataDiry = true;
				}
			}
		} );
	}

	protected void onResume()
	{

		super.onResume();
		db = new DatabaseHandler( FeedbackActivity.this, AppGlobal.TABLE_FEEDBACK );
		// registerReceiver( internetConnectionFoundBroadCast, new IntentFilter(
		// AppGlobal.BROADCAST_FILTER_INTERNET_CONNECTION_FOUND ) );
	}

	@Override
	protected void onPause()
	{

		// TODO Auto-generated method stub
		super.onPause();
		if( db != null )
			db.close();
		// unregisterReceiver( internetConnectionFoundBroadCast );
	}

	@Override
	protected void onDestroy()
	{

		super.onDestroy();
		if( db != null )
			db.close();
	}

	public void clearFields()
	{

		textFName.setText( "" );
		// textLName.setText( "" );
		textPhoneNumber.setText( "" );
		textFeedback.setText( "" );
	}

	@Override
	public void onBackPressed()
	{

	}
}
