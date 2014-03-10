package com.sakhan.receptionist.datalayer;


public class FeedbackBO
{
	private int		feedbackId;
	private int		organizationId;
	private String	fname;
	private String	lname;
	private String	telephone;
	private String	isAnonymous;
	private String	feedbackText;
	private String	dateTime;

	/**
	 * @param organizationId
	 * @param fname
	 * @param lname
	 * @param telephone
	 * @param isAnonymous
	 * @param feedbackText
	 */
	public FeedbackBO( int organizationId, String fname, String lname, String telephone, String isAnonymous, String feedbackText, String date )
	{

		super();
		this.organizationId = organizationId;
		this.fname = fname;
		this.lname = lname;
		this.telephone = telephone;
		this.isAnonymous = isAnonymous;
		this.feedbackText = feedbackText;
		this.dateTime = date;
	}

	/**
	 * @param feedbackId
	 * @param organizationId
	 * @param fname
	 * @param lname
	 * @param telephone
	 * @param isAnonymous
	 * @param feedbackText
	 */
	public FeedbackBO( int feedbackId, int organizationId, String fname, String lname, String telephone, String isAnonymous, String feedbackText, String date )
	{

		super();
		this.feedbackId = feedbackId;
		this.organizationId = organizationId;
		this.fname = fname;
		this.lname = lname;
		this.telephone = telephone;
		this.isAnonymous = isAnonymous;
		this.feedbackText = feedbackText;
		this.dateTime = date;
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
	 * @return the organizationId
	 */
	public int getOrganizationId()
	{

		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId( int organizationId )
	{

		this.organizationId = organizationId;
	}

	/**
	 * @return the fname
	 */
	public String getFname()
	{

		return fname;
	}

	/**
	 * @param fname
	 *            the fname to set
	 */
	public void setFname( String fname )
	{

		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname()
	{

		return lname;
	}

	/**
	 * @param lname
	 *            the lname to set
	 */
	public void setLname( String lname )
	{

		this.lname = lname;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone()
	{

		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone( String telephone )
	{

		this.telephone = telephone;
	}

	/**
	 * @return the isAnonymous
	 */
	public String getIsAnonymous()
	{

		return isAnonymous;
	}

	/**
	 * @param isAnonymous
	 *            the isAnonymous to set
	 */
	public void setIsAnonymous( String isAnonymous )
	{

		this.isAnonymous = isAnonymous;
	}

	/**
	 * @return the feedbackText
	 */
	public String getFeedbackText()
	{

		return feedbackText;
	}

	/**
	 * @param feedbackText
	 *            the feedbackText to set
	 */
	public void setFeedbackText( String feedbackText )
	{

		this.feedbackText = feedbackText;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime()
	{

		return dateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setDateTime( String dateTime )
	{

		this.dateTime = dateTime;
	}

}
