package com.sakhan.receptionist.datalayer;

public class FeedbackAlchemyBO
{

	private int		feedbackAlchemyId;
	private int		feedbackId;
	private String	keyword;
	private String	concept;
	private String	language;
	private String	entity;
	private String	sentiment;

	/**
	 * @param feedbackId
	 * @param keyword
	 * @param concept
	 * @param language
	 * @param entity
	 * @param sentiment
	 */
	public FeedbackAlchemyBO( int feedbackId, String keyword, String concept, String language, String entity, String sentiment )
	{

		super();
		this.feedbackId = feedbackId;
		this.keyword = keyword;
		this.concept = concept;
		this.language = language;
		this.entity = entity;
		this.sentiment = sentiment;
	}

	/**
	 * @param feedbackAlchemyId
	 * @param feedbackId
	 * @param keyword
	 * @param concept
	 * @param language
	 * @param entity
	 * @param sentiment
	 */
	public FeedbackAlchemyBO( int feedbackAlchemyId, int feedbackId, String keyword, String concept, String language, String entity, String sentiment )
	{

		super();
		this.feedbackAlchemyId = feedbackAlchemyId;
		this.feedbackId = feedbackId;
		this.keyword = keyword;
		this.concept = concept;
		this.language = language;
		this.entity = entity;
		this.sentiment = sentiment;
	}

	/**
	 * @return the feedbackAlchemyId
	 */
	public int getFeedbackAlchemyId()
	{

		return feedbackAlchemyId;
	}

	/**
	 * @param feedbackAlchemyId
	 *            the feedbackAlchemyId to set
	 */
	public void setFeedbackAlchemyId( int feedbackAlchemyId )
	{

		this.feedbackAlchemyId = feedbackAlchemyId;
	}

	/**
	 * @return the feedbackId
	 */
	public int getFeedbackId()
	{

		return feedbackId;
	}

	/**
	 * @param feedbackId
	 *            the feedbackId to set
	 */
	public void setFeedbackId( int feedbackId )
	{

		this.feedbackId = feedbackId;
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
}
