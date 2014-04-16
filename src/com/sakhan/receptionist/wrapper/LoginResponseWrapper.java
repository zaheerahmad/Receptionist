package com.sakhan.receptionist.wrapper;

public class LoginResponseWrapper
{
	private String	organizationId;
	private String	organizationName;
	private String	organizationAddress;
	private String	organizationContactPerson;
	private String	organizationTelephone;
	private String	organizationDeviceImei;
	private String	organizationDeviceImsi;

	public LoginResponseWrapper( String organizationId, String organizationName, String organizationAddress, String organizationContactPerson, String organizationTelephone, String organizationDeviceImei, String organizationDeviceImsi )
	{

		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.organizationAddress = organizationAddress;
		this.organizationContactPerson = organizationContactPerson;
		this.organizationTelephone = organizationTelephone;
		this.organizationDeviceImei = organizationDeviceImei;
		this.organizationDeviceImsi = organizationDeviceImsi;
	}

	public String getOrganizationId()
	{

		return organizationId;
	}

	public void setOrganizationId( String organizationId )
	{

		this.organizationId = organizationId;
	}

	public String getOrganizationName()
	{

		return organizationName;
	}

	public void setOrganizationName( String organizationName )
	{

		this.organizationName = organizationName;
	}

	public String getOrganizationAddress()
	{

		return organizationAddress;
	}

	public void setOrganizationAddress( String organizationAddress )
	{

		this.organizationAddress = organizationAddress;
	}

	public String getOrganizationContactPerson()
	{

		return organizationContactPerson;
	}

	public void setOrganizationContactPerson( String organizationContactPerson )
	{

		this.organizationContactPerson = organizationContactPerson;
	}

	public String getOrganizationTelephone()
	{

		return organizationTelephone;
	}

	public void setOrganizationTelephone( String organizationTelephone )
	{

		this.organizationTelephone = organizationTelephone;
	}

	public String getOrganizationDeviceImei()
	{

		return organizationDeviceImei;
	}

	public void setOrganizationDeviceImei( String organizationDeviceImei )
	{

		this.organizationDeviceImei = organizationDeviceImei;
	}

	public String getOrganizationDeviceImsi()
	{

		return organizationDeviceImsi;
	}

	public void setOrganizationDeviceImsi( String organizationDeviceImsi )
	{

		this.organizationDeviceImsi = organizationDeviceImsi;
	}

}
