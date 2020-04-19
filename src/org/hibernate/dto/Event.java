package org.hibernate.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class Event
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long eventId;
	@Column(name = "EVENT_TITLE")
	private String title;
	@Column(name = "EVENT_DATE")
	private Date date;

	public Long getEventId()
	{
		return eventId;
	}

	public void setEventId(Long eventId)
	{
		this.eventId = eventId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

}
