package com.sakhan.receptionist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Zaheer Ahmad
 *
 */
public class HomeActivity extends Activity
{

	ImageView			imgGetStarted				= null;
	public final int	FEEDBACK_ACTIVITY_REQUEST	= 1;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );

		/*
		 * requestWindowFeature( Window.FEATURE_NO_TITLE );
		 * getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN );
		 */

		setContentView( R.layout.home_screen_layout );

		imgGetStarted = ( ImageView ) findViewById( R.id.home_screen_getStarted_btn );
		imgGetStarted.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				// TODO Auto-generated method stub
				// Intent intent = new Intent( HomeActivity.this,
				// MainMenuActivity.class );
				// startActivity( intent );

				Intent feedbackIntent = new Intent( HomeActivity.this, FeedbackActivity.class );
				startActivityForResult( feedbackIntent, FEEDBACK_ACTIVITY_REQUEST );

			}
		} );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{

		super.onActivityResult(requestCode, resultCode, data);

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
