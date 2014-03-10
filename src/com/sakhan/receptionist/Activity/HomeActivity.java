package com.sakhan.receptionist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity
{

	ImageView	imgGetStarted	= null;

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
				Intent intent = new Intent( HomeActivity.this, MainMenuActivity.class );
				startActivity( intent );

			}
		} );
	}

	@Override
	public void onBackPressed()
	{

	}

}
