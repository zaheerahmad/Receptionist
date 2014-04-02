package com.sakhan.receptionist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @author Zaheer Ahmad
 *
 */
public class SlideMainActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );

		/*
		 * requestWindowFeature( Window.FEATURE_NO_TITLE );
		 * getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN );
		 */

		setContentView( R.layout.slide );

		SeekBar sb = ( SeekBar ) findViewById( R.id.myseek );
		sb.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		{

			@Override
			public void onStopTrackingTouch( SeekBar seekBar )
			{

				if( seekBar.getProgress() > 95 )
				{
					Intent intent = new Intent( SlideMainActivity.this, MainMenuActivity.class );
					startActivity( intent );
					seekBar.setProgress( 0 );
				}
				else
				{
					seekBar.setProgress( 0 );
				}

			}

			@Override
			public void onStartTrackingTouch( SeekBar seekBar )
			{

			}

			@Override
			public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser )
			{

				if( progress > 95 )
				{
					Intent intent = new Intent( SlideMainActivity.this, MainMenuActivity.class );
					startActivity( intent );
					seekBar.setProgress( 0 );
				}

			}
		} );
	}

	/*
	 * @Override
	 * public void onBackPressed()
	 * {
	 * // Do Nothing.
	 * }
	 */

}
