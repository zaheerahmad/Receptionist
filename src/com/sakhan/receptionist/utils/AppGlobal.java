package com.sakhan.receptionist.utils;

/**
 * @author Zaheer Ahmad
 *
 */
public class AppGlobal
{
	public static final boolean	isDebugMode											= false;
	public static final boolean	shouldMaintainLogOfFeeds							= false;

	// Response Statuses
	public static final int		RESPONSE_STATUS_FAIL								= 1;
	public static final String	RESPONSE_STATUS_FAIL_MESSAGE						= "Some error occured while processing request.";

	public static final int		RESPONSE_STATUS_SUCCESS								= 2;
	public static final String	RESPONSE_STATUS_SUCCESS_MESSAGE						= "Request succefully completed!";

	public static final int		RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION			= 3;
	public static final String	RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE	= "No network connection found!";

	public static final int		RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION				= 4;
	public static final String	RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE		= "Some random error occured, please try again later!";

	/**
	 * Databases constants
	 */
	// Database Version
	public static final int		DATABASE_VERSION									= 1;

	// Database Name
	public static final String	DATABASE_NAME										= "db_receptionist";

	/****
	 * Feedback Table
	 */

	public static final String	TABLE_FEEDBACK										= "tbl_feedback";
	public static final String	FEEDBACK_ID											= "feedbackId";
	public static final String	ORGANIZATION_ID										= "organizationId";
	public static final String	FNAME												= "fname";
	public static final String	LNAME												= "lname";
	public static final String	TELEPHONE											= "telephone";
	public static final String	IS_ANONYMOUS										= "isAnonymous";
	public static final String	FEEDBACK_TEXT										= "rawText";
	public static final String	DATE_TIME											= "date_time";

	/****
	 * Feedback Table Ended
	 */

	/****
	 * Feedback Alchemy Table
	 */

	public static final String	TABLE_FEEDBACK_ALCHEMY								= "tbl_feedback_alchemy";
	public static final String	ALCHEMY_FEEDBACK_ID									= "feedback_alchemy_Id";
	public static final String	FEEDBACK_ID_FK										= "feedback_id";
	public static final String	ALCHEMY_KEYWORD										= "keyword";
	public static final String	ALCHEMY_CONCEPT										= "concept";
	public static final String	ALCHEMY_SENTIMENT									= "sentiment";
	public static final String	ALCHEMY_ENTITY										= "entity";
	public static final String	ALCHEMY_LANGUAGE									= "language";

	/****
	 * Feedback Table Ended
	 */

	/**
	 * Databases constants ended
	 */

	// Activity Toast Messages
	public static final String	TOAST_MISSING_MANDATORY_FIELDS						= "Couldn't process request, Some mandatory fields are missing";
	public static final String	TOAST_THANKS_FOR_FEEDBACK							= "Thank you for your feedback!";
	public static final String	TOAST_INTERNET_CONNECTION_FOUND						= "Internet connection on your device found! Uploading your data on server";
	public static final String	TOAST_DATA_UPLOADED_SUCCESSFULLY					= "Data has been uploaded successfully on server!";

	// Constants to figure out whether data needs to be inserted or not..
	/*
	 * public static boolean isDataDiry = false;
	 * public static int lastFeedbackId = -1;
	 */

	// DataFetcher Actions
	public static final String	DATAFETCHER_ACTION_INSERT_FEEDBACK					= "_insert_feedback_";

	// Broadcast Filters
	public static final String	BROADCAST_FILTER_INTERNET_CONNECTION_FOUND			= "_internet_connection_found_";

	// Server URLs
	public static final String	SERVER_URL_SAVE_FEEDBACK							= "http://www.zaheer.amirhameed.com/webservices/SaveFeedback.php";

	public static final String	AlchemyAPI_Key										= "3bb77eb7d9347bdd4807989f1e9876de91e2cb67";

	public static final long	SERVICE_DELAY										= 1 * 60 * 1000;

	public static final String	APP_PREF_IS_DATA_DIRTY								= "_isDataDirty_";
	public static final String	APP_PREF_LAST_INSERTED_ID							= "_lastInsertedId_";
	public static final String	APP_PREF_NAME										= "RECEPTIONIST_APP_PREFS";

}
