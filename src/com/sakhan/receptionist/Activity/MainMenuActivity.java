package com.sakhan.receptionist.Activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sakhan.receptionist.datalayer.DatabaseHandler;
import com.sakhan.receptionist.datalayer.FeedbackAlchemyBO;
import com.sakhan.receptionist.datalayer.FeedbackBO;
import com.sakhan.receptionist.services.CheckAppVersionService;
import com.sakhan.receptionist.utils.AppGlobal;
import com.sakhan.receptionist.utils.ElipsizingTextView;
import com.sakhan.receptionist.utils.SAutoBgButton;
import com.sakhan.receptionist.utils.Utils;

/**
 * @author Zaheer Ahmad
 * 
 */
public class MainMenuActivity extends Activity
{
	CountDownTimer						cDT;
	SAutoBgButton						feedbackBtn;
	// SAutoBgButton homeBtn;
	ImageView							copyRightTextImageView, copyRightLogoImageView;
	RelativeLayout						relativeLayout;
	ListView							feedbackListView;

	public static FeedbackListAdapter	feedbackAdapter;
	public static List<FeedbackBO>		listFeedback;
	private long						timer	= 20000;
	public AlertDialog					myAlertDialog;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_menu_cover_layout );

		feedbackBtn = ( SAutoBgButton ) findViewById( R.id.main_menu_cover_FeedbackButton );
		copyRightTextImageView = ( ImageView ) findViewById( R.id.main_menu_cover_copyright_text );
		copyRightLogoImageView = ( ImageView ) findViewById( R.id.main_menu_cover_copyright_logo );
		relativeLayout = ( RelativeLayout ) findViewById( R.id.main_menu_cover_relative_layout );
		feedbackListView = ( ListView ) findViewById( R.id.main_menu_cover_list_of_all_feedbacks );

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

		feedbackBtn.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{

				cDT.cancel();
				cDT.start();
				// TODO Auto-generated method stub
				Intent feedbackIntent = new Intent( MainMenuActivity.this, FeedbackActivity.class );
				startActivity( feedbackIntent );
			}
		} );

		copyRightTextImageView.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				cDT.cancel();
				cDT.start();

			}
		} );

		copyRightLogoImageView.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				cDT.cancel();
				cDT.start();

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

		feedbackListView.setOnItemClickListener( new OnItemClickListener()
		{

			@Override
			public void onItemClick( AdapterView<?> arg0, View arg1, int arg2, long arg3 )
			{

				cDT.cancel();
				cDT.start();

			}

		} );

		// homeBtn = ( SAutoBgButton ) findViewById(
		// R.id.main_menu_cover_homeButton );
		/*
		 * homeBtn.setOnClickListener( new View.OnClickListener()
		 * {
		 * @Override
		 * public void onClick( View v )
		 * {
		 * MainMenuActivity.this.finish();
		 * }
		 * } );
		 */

		DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_FEEDBACK );
		this.listFeedback = db.getAllFeedbacks();

		feedbackAdapter = new FeedbackListAdapter( getApplicationContext(), -1, this.listFeedback );
		feedbackListView.setAdapter( feedbackAdapter );

	}

	public class FeedbackListAdapter extends ArrayAdapter<FeedbackBO>
	{
		Context				context;
		List<FeedbackBO>	feedback;

		public FeedbackListAdapter( Context context, int resource, List<FeedbackBO> list )
		{

			super( context, resource, list );
			this.context = context;
			this.feedback = list;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView( int position, View convertView, ViewGroup parent )
		{

			ViewHolder holder;
			View view = convertView;
			if( view == null )
			{
				view = getLayoutInflater().inflate( R.layout.list_of_all_feedback_item_layout, parent, false );
			}
			holder = new ViewHolder();

			holder.name = ( TextView ) view.findViewById( R.id.tv_name );
			holder.feedback = ( ElipsizingTextView ) view.findViewById( R.id.tv_feedback );
			holder.feedbackId = ( TextView ) view.findViewById( R.id.tv_feedback_id );
			holder.dateTime = ( TextView ) view.findViewById( R.id.tv_dateandtime );

			holder.feedbackId.setText( String.valueOf( MainMenuActivity.listFeedback.get( position ).getFeedbackId() ) );
			if( MainMenuActivity.listFeedback.get( position ).getIsAnonymous().equalsIgnoreCase( "true" ) )
				holder.name.setText( "Anonymous" );
			else
				holder.name.setText( MainMenuActivity.listFeedback.get( position ).getFname() + " " + MainMenuActivity.listFeedback.get( position ).getLname() );
			holder.feedback.setText( MainMenuActivity.listFeedback.get( position ).getFeedbackText() );
			holder.dateTime.setText( MainMenuActivity.listFeedback.get( position ).getDateTime() );

			view.setTag( holder );

			LinearLayout ll = ( LinearLayout ) view.findViewById( R.id.feedback_list_ll );
			ll.setOnClickListener( new View.OnClickListener()
			{

				@Override
				public void onClick( View v )
				{

					try
					{
						// TODO Auto-generated method stub
						LinearLayout ll = ( LinearLayout ) v;
						LinearLayout ll2 = ( LinearLayout ) ll.getChildAt( 1 );
						TextView tv = ( TextView ) ll2.getChildAt( 0 );

						int feedbackId = Integer.parseInt( tv.getText().toString() );
						DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_FEEDBACK_ALCHEMY );
						FeedbackAlchemyBO feed = db.getFeedbackAlchemyByFeedbackId( feedbackId );
						if( feed != null )
						{
							Intent intentDetailFeedback = new Intent( MainMenuActivity.this, FeedbackDetailActivity.class );
							intentDetailFeedback.putExtra( AppGlobal.ALCHEMY_ENTITY, feed.getEntity() );
							intentDetailFeedback.putExtra( AppGlobal.ALCHEMY_KEYWORD, feed.getKeyword() );
							intentDetailFeedback.putExtra( AppGlobal.ALCHEMY_CONCEPT, feed.getConcept() );
							intentDetailFeedback.putExtra( AppGlobal.ALCHEMY_LANGUAGE, feed.getLanguage() );
							intentDetailFeedback.putExtra( AppGlobal.ALCHEMY_SENTIMENT, feed.getSentiment() );
							startActivity( intentDetailFeedback );
						}
					}
					catch ( Exception ex )
					{
					}
				}
			} );

			return view;
		}

		class ViewHolder
		{
			TextView			name;
			ElipsizingTextView	feedback;
			TextView			feedbackId;
			TextView			dateTime;
		}
	}

	protected void onResume()
	{

		super.onResume();
		MainMenuActivity.feedbackAdapter.notifyDataSetChanged();
		registerReceiver( appVersionUpdatedBroadcastReceiver, new IntentFilter( CheckAppVersionService.INTENT_VERSION_UPDATED ) );
	}

	@Override
	protected void onPause()
	{

		// TODO Auto-generated method stub
		super.onPause();
		// unregisterReceiver( internetConnectionFoundBroadCast );
		unregisterReceiver( appVersionUpdatedBroadcastReceiver );
	}

	@Override
	public void onBackPressed()
	{

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
																			AlertDialog.Builder builder = new AlertDialog.Builder( MainMenuActivity.this );
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
