package com.sakhan.receptionist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.sakhan.receptionist.utils.AppGlobal;

/**
 * @author Zaheer Ahmad
 *
 */
public class FeedbackDetailActivity extends Activity
{

	TextView	tv_keyword;
	TextView	tv_concept;
	TextView	tv_language;
	TextView	tv_sentiment;
	TextView	tv_entity;
	ImageView	im_sentiment;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.feedback_alchemy_layout );

		tv_keyword = ( TextView ) findViewById( R.id.feedback_alchemy_keyword );
		tv_concept = ( TextView ) findViewById( R.id.feedback_alchemy_concept );
		tv_language = ( TextView ) findViewById( R.id.feedback_alchemy_language );
		tv_sentiment = ( TextView ) findViewById( R.id.feedback_alchemy_sentiment );
		tv_entity = ( TextView ) findViewById( R.id.feedback_alchemy_entity );
		im_sentiment = ( ImageView ) findViewById( R.id.detail_feedback_sentimentImage );

		Intent intent = getIntent();
		String keyword = intent.getExtras().getString( AppGlobal.ALCHEMY_KEYWORD );
		String concept = intent.getExtras().getString( AppGlobal.ALCHEMY_CONCEPT );
		String language = intent.getExtras().getString( AppGlobal.ALCHEMY_LANGUAGE );
		String entity = intent.getExtras().getString( AppGlobal.ALCHEMY_ENTITY );
		String sentiment = intent.getExtras().getString( AppGlobal.ALCHEMY_SENTIMENT );

		tv_keyword.setText( Html.fromHtml( "<b>Keywords: </b>" + keyword ) );
		tv_concept.setText( Html.fromHtml( "<b>Concepts: </b>" + concept ) );
		tv_language.setText( Html.fromHtml( "<b>Language: </b>" + language ) );
		tv_sentiment.setText( Html.fromHtml( "<b>Sentiment: </b>" + sentiment ) );
		tv_entity.setText( Html.fromHtml( "<b>Entity: </b>" + entity ) );

		if( sentiment.equalsIgnoreCase( "positive" ) )
		{
			im_sentiment.setImageResource( getResources().getIdentifier( "positive_sentiment", "drawable", getPackageName() ) );
		}
		else
			im_sentiment.setImageResource( getResources().getIdentifier( "negative_sentiment_2", "drawable", getPackageName() ) );
	}
}
