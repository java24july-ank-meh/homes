package com.revature.application.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="MAINTENANCE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "maintenanceId")
public class Maintenance
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int maintenanceId;
	private String type;
	private String location;
	private String description;
	private Date submitDate;
	private Date resolveDate;
	private boolean isResolved;
	private int unitId;
	private int submittedBy;
	
	public Maintenance() {};

	public Maintenance(int maintenanceId, String type, String location, String description, Date submitDate,
			Date resolveDate, boolean isResolved, int unitId, int submittedBy)
	{
		super();
		this.maintenanceId = maintenanceId;
		this.type = type;
		this.location = location;
		this.description = description;
		this.submitDate = (Date) new java.util.Date();
		this.resolveDate = resolveDate;
		this.isResolved = isResolved;
		this.unitId = unitId;
		this.submittedBy = submittedBy;
	}

	public int getMaintenanceId()
	{
		return maintenanceId;
	}

	public void setMaintenanceId(int maintenanceId)
	{
		this.maintenanceId = maintenanceId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getSubmitDate()
	{
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate)
	{
		this.submitDate = submitDate;
	}

	public boolean isResolved()
	{
		return this.isResolved;
	}

	public void setResolved(boolean isResolved)
	{
		this.isResolved = isResolved;
	}
	
	public Date getResolveDate()
	{
		return this.resolveDate;
	}

	public void setResolveDate(Date resolveDate)
	{
		this.resolveDate = resolveDate;
	}

	public int getUnitId()
	{
		return this.unitId;
	}

	public void setUnitId(int unitId)
	{
		this.unitId = unitId;
	}

	public int getSubmittedBy()
	{
		return this.submittedBy;
	}

	public void setSubmittedBy(int submittedBy)
	{
		this.submittedBy = submittedBy;
	}

	@Override
	public String toString()
	{
		return "Maintenance [maintenanceId=" + maintenanceId + ", type=" + type + ", location=" + location
				+ ", description=" + description + ", submitDate=" + submitDate + ", resolveDate=" + resolveDate
				+ ", isResolved=" + isResolved + ", unitId=" + unitId + ", submittedBy=" + submittedBy + "]";
	}
	
}