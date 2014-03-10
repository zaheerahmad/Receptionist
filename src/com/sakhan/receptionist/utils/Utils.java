package com.sakhan.receptionist.utils;

import java.io.InputStream;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.view.inputmethod.InputMethodManager;

import com.sakhan.receptionist.Alchemy.api.AlchemyAPI;
import com.sakhan.receptionist.Alchemy.api.AlchemyAPI_NamedEntityParams;

public class Utils
{
	public static void hideSoftKeyboard( Activity activity )
	{

		InputMethodManager inputMethodManager = ( InputMethodManager ) activity.getSystemService( Activity.INPUT_METHOD_SERVICE );
		inputMethodManager.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), 0 );
	}

	@TargetApi( VERSION_CODES.HONEYCOMB )
	public static void enableStrictMode()
	{

		if( Utils.hasGingerbread() )
		{
			StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
			StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

			if( Utils.hasHoneycomb() )
			{
				threadPolicyBuilder.penaltyFlashScreen();
				// vmPolicyBuilder.setClassInstanceLimit( NewsFragment.class, 1
				// );
			}
			StrictMode.setThreadPolicy( threadPolicyBuilder.build() );
			StrictMode.setVmPolicy( vmPolicyBuilder.build() );
		}
	}

	public static boolean hasFroyo()
	{

		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	// public static boolean hasKitKat() {
	// return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	// }

	public static void CopyStream( InputStream is, OutputStream os )
	{

		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for( ; ; )
			{
				int count = is.read( bytes, 0, buffer_size );
				if( count == -1 )
					break;
				os.write( bytes, 0, count );
			}
		}
		catch ( Exception ex )
		{
		}
	}

	public static String html2text( String html )
	{

		String str = Jsoup.parse( html ).text();
		str = str.replaceAll( "&nbsp;", "" );
		str = str.replaceAll( "&amp;", "" );
		str = str.replaceAll( "<p>", "" );
		str = str.replaceAll( "</p>", "" );
		str = str.replaceAll( "<ul>", "" );
		str = str.replaceAll( "</ul>", "" );
		str = str.replaceAll( "<li>", "" );
		str = str.replaceAll( "</li>", "" );
		return str;
	}

	public static boolean hasInternetConnection( Context context )
	{

		ConnectivityManager cm = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo wifiNetwork = cm.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
		if( wifiNetwork != null && wifiNetwork.isConnected() )
		{
			return true;
		}
		NetworkInfo mobileNetwork = cm.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
		if( mobileNetwork != null && mobileNetwork.isConnected() )
		{
			return true;
		}
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if( activeNetwork != null && activeNetwork.isConnected() )
		{
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty( String string )
	{

		if( string != null && !string.equals( "" ) )
			return false;
		return true;
	}

	public static String getConceptText( String msg, Context context )
	{

		Document doc = null;
		AlchemyAPI api = null;
		try
		{
			api = AlchemyAPI.GetInstanceFromString( AppGlobal.AlchemyAPI_Key );
		}
		catch ( IllegalArgumentException ex )
		{
			return "Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.";
		}
		try
		{
			doc = api.TextGetRankedConcepts( msg );// URLGetRankedConcepts( msg
													// );
		}
		catch ( Throwable t )
		{
			return "Error: " + t.getMessage();
		}
		StringBuilder strBuilder = new StringBuilder();
		Element root = doc.getDocumentElement();
		NodeList keywords = root.getElementsByTagName( "concepts" );
		for( int i = 0 ; i < keywords.getLength() ; i++ )
		{
			if( i < keywords.getLength() )
			{
				Node keyword = keywords.item( i );
				if( keyword != null )
				{
					for( int j = 0 ; j < keyword.getChildNodes().getLength() ; j++ )
					{
						if( keyword.getChildNodes().item( j ).getNodeName().equalsIgnoreCase( "concept" ) )
						{
							NodeList nodeList = keyword.getChildNodes().item( j ).getChildNodes();
							if( nodeList != null )
							{
								for( int k = 0 ; k < nodeList.getLength() ; k++ )
								{
									Node n = nodeList.item( k );
									strBuilder.append( n.getTextContent() + "\n" );
								}
							}
						}
					}
				}

			}
		}
		return Utils.isNullOrEmpty( strBuilder.toString().trim() ) ? "" : strBuilder.toString().trim();
	}

	public static String getKeyWordText( String msg, Context context )
	{

		Document doc = null;
		AlchemyAPI api = null;
		try
		{
			api = AlchemyAPI.GetInstanceFromString( AppGlobal.AlchemyAPI_Key );
		}
		catch ( IllegalArgumentException ex )
		{
			return "Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.";
		}
		try
		{
			doc = api.TextGetRankedKeywords( msg );// URLGetRankedConcepts( msg
													// );
		}
		catch ( Throwable t )
		{
			return "Error: " + t.getMessage();
		}
		StringBuilder strBuilder = new StringBuilder();
		Element root = doc.getDocumentElement();
		NodeList keywords = root.getElementsByTagName( "keywords" );
		for( int i = 0 ; i < keywords.getLength() ; i++ )
		{
			if( i < keywords.getLength() )
			{
				Node keyword = keywords.item( i );
				if( keyword != null )
				{
					for( int j = 0 ; j < keyword.getChildNodes().getLength() ; j++ )
					{
						if( keyword.getChildNodes().item( j ).getNodeName().equalsIgnoreCase( "keyword" ) )
						{
							NodeList nodeList = keyword.getChildNodes().item( j ).getChildNodes();
							if( nodeList != null )
							{
								for( int k = 0 ; k < nodeList.getLength() ; k++ )
								{
									Node n = nodeList.item( k );
									if( n.getNodeName().equalsIgnoreCase( "text" ) )
									{
										strBuilder.append( n.getTextContent() + "\n" );
										break;
									}
								}
							}
						}
					}
				}

			}
		}
		return Utils.isNullOrEmpty( strBuilder.toString().trim() ) ? "" : strBuilder.toString().trim();
	}

	public static String getLanguageText( String msg, Context context )
	{

		Document doc = null;
		AlchemyAPI api = null;
		try
		{
			api = AlchemyAPI.GetInstanceFromString( AppGlobal.AlchemyAPI_Key );
		}
		catch ( IllegalArgumentException ex )
		{
			return "Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.";
		}
		try
		{
			doc = api.TextGetLanguage( msg );// URLGetRankedConcepts( msg );
		}
		catch ( Throwable t )
		{
			return "Error: " + t.getMessage();
		}

		StringBuilder strBuilder = new StringBuilder();
		if( doc == null )
			return null;
		Element root = doc.getDocumentElement();
		NodeList items = root.getElementsByTagName( "language" );
		for( int i = 0 ; i < items.getLength() ; i++ )
		{
			Node languageNode = items.item( i );
			if( languageNode != null )
			{
				String astring = languageNode.getNodeValue();
				if( languageNode.getChildNodes().item( 0 ) != null )
					astring = languageNode.getChildNodes().item( 0 ).getNodeValue();
				strBuilder.append( astring + "\n" );
			}
		}
		return Utils.isNullOrEmpty( strBuilder.toString().trim() ) ? "" : strBuilder.toString().trim();
	}

	public static String getSentimentText( String msg, Context context )
	{

		Document doc = null;
		AlchemyAPI api = null;
		try
		{
			api = AlchemyAPI.GetInstanceFromString( AppGlobal.AlchemyAPI_Key );
		}
		catch ( IllegalArgumentException ex )
		{
			return "Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.";
		}
		try
		{
			AlchemyAPI_NamedEntityParams nep = new AlchemyAPI_NamedEntityParams();
			nep.setSentiment( true );
			doc = api.TextGetTextSentiment( msg, nep );
			// ShowDocInTextView( doc, true );
		}
		catch ( Throwable t )
		{
			return "Error: " + t.getMessage();
		}
		StringBuilder strBuilder = new StringBuilder();
		Element root = doc.getDocumentElement();
		NodeList sentiments = root.getElementsByTagName( "docSentiment" );
		for( int i = 0 ; i < sentiments.getLength() ; i++ )
		{
			if( i < sentiments.getLength() )
			{
				Node sentiment = sentiments.item( i );
				if( sentiment != null )
				{
					Node aNode = sentiment.getChildNodes().item( 1 );
					if( aNode != null )
					{
						Node bNode = aNode.getChildNodes().item( 0 );
						if( bNode != null )
						{
							strBuilder.append( bNode.getNodeValue() + "\n" );
						}
					}
				}
			}
		}
		return Utils.isNullOrEmpty( strBuilder.toString().trim() ) ? "" : strBuilder.toString().trim();
	}

	public static String getEntityText( String msg, Context context )
	{

		Document doc = null;
		AlchemyAPI api = null;
		try
		{
			api = AlchemyAPI.GetInstanceFromString( AppGlobal.AlchemyAPI_Key );
		}
		catch ( IllegalArgumentException ex )
		{
			return "Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.";
		}
		try
		{
			doc = api.TextGetRankedNamedEntities( msg );// URLGetRankedConcepts(
														// msg );
		}
		catch ( Throwable t )
		{
			return "Error: " + t.getMessage();
		}
		StringBuilder strBuilder = new StringBuilder();
		Element root = doc.getDocumentElement();
		NodeList keywords = root.getElementsByTagName( "entities" );
		for( int i = 0 ; i < keywords.getLength() ; i++ )
		{
			if( i < keywords.getLength() )
			{
				Node keyword = keywords.item( i );
				if( keyword != null )
				{
					for( int j = 0 ; j < keyword.getChildNodes().getLength() ; j++ )
					{
						if( keyword.getChildNodes().item( j ).getNodeName().equalsIgnoreCase( "entity" ) )
						{
							NodeList nodeList = keyword.getChildNodes().item( j ).getChildNodes();
							if( nodeList != null )
							{
								for( int k = 0 ; k < nodeList.getLength() ; k++ )
								{
									Node n = nodeList.item( k );
									strBuilder.append( n.getTextContent() + "\n" );
								}
							}
						}
					}
				}

			}
		}
		return Utils.isNullOrEmpty( strBuilder.toString().trim() ) ? "" : strBuilder.toString().trim();
	}
	
	public static SharedPreferences getSharedPreferences(Context context)
	{

		return context.getSharedPreferences( AppGlobal.APP_PREF_NAME, Context.MODE_PRIVATE );
	}
}
