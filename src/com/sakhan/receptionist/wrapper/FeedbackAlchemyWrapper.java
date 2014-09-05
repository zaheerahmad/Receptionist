package com.sakhan.receptionist.wrapper;

import com.sakhan.receptionist.datalayer.FeedbackBO;
import com.sakhan.receptionist.utils.AppGlobal;

/**
 * @author Zaheer Ahmad
 * 
 */
public class FeedbackAlchemyWrapper
{
	FeedbackBO	feedback;
	String		sentiment;
	String		language;
	String		keyword;
	String		entity;
	String		concept;
	String		appVersion; // Adding because I want to send this appVersion to
							// server.

	/**
	 * @param feedback
	 * @param sentiment
	 * @param language
	 * @param keyword
	 * @param entity
	 * @param concept
	 */
	public FeedbackAlchemyWrapper( FeedbackBO feedback, String sentiment, String language, String keyword, String entity, String concept )
	{

		super();
		this.feedback = feedback;
		this.sentiment = sentiment;
		this.language = language;
		this.keyword = keyword;
		this.entity = entity;
		this.concept = concept;
		this.appVersion = AppGlobal.appVersion;
	}

	/**
	 * @return the feedback
	 */
	public FeedbackBO getFeedback()
	{

		return feedback;
	}

	/**
	 * @param feedback
	 *            the feedback to set
	 */
	public void setFeedback( FeedbackBO feedback )
	{

		this.feedback = feedback;
	}

	/**
	 * @return the sentiment
	 */
	public String getSentiment()
	{

		return sentiment;
	}

	/**
	 * @param sentiment
	 *            the sentiment to set
	 */
	public void setSentiment( String sentiment )
	{

		this.sentiment = sentiment;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{

		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage( String language )
	{

		this.language = language;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword()
	{

		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword( String keyword )
	{

		this.keyword = keyword;
	}

	/**
	 * @return the entity
	 */
	public String getEntity()
	{

		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity( String entity )
	{

		this.entity = entity;
	}

	/**
	 * @return the concept
	 */
	public String getConcept()
	{

		return concept;
	}

	/**
	 * @param concept
	 *            the concept to set
	 */
	public void setConcept( String concept )
	{

		this.concept = concept;
	}

	/**
	 * @return the appVersion
	 */
	public String getAppVersion()
	{

		return appVersion;
	}

	/**
	 * @param appVersion
	 *            the appVersion to set
	 */
	public void setAppVersion( String appVersion )
	{

		this.appVersion = appVersion;
	}

}
