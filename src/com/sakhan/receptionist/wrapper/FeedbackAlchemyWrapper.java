package com.sakhan.receptionist.wrapper;

import com.sakhan.receptionist.datalayer.FeedbackBO;

public class FeedbackAlchemyWrapper
{
	FeedbackBO	feedback;
	String		sentiment;
	String		language;
	String		keyword;
	String		entity;
	String		concept;

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

}
