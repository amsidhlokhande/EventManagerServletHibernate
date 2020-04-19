package org.hibernate.eventmgr;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.dto.Event;
import org.hibernate.util.HibernateUtil;

public class EventManager
{
	public EventManager()
	{
		
	}
	public static void main(String[] args)
	{
		
		EventManager mgr=new EventManager();
        if("store".equals(args[0]))
        {
        	mgr.storeAndCreateEvent("My Event",new Date());
        }
        else
        {
        	List<Event> eventList=mgr.listEvent();
        	for(Event event:eventList)
        	{
        		System.out.println(event.getEventId()+":"+event.getTitle()+":"+event.getDate());
        	}
        	
        }
        HibernateUtil.getSessionFactory().close();
        	
	}
	public void storeAndCreateEvent(String title,Date date)
	{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Event event=new Event();
		event.setTitle(title);
		event.setDate(date);
		
		session.save(event);
				
		session.getTransaction().commit();
		
	}
    @SuppressWarnings("unchecked")
	private List<Event> listEvent()
    {
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
    	session.beginTransaction();
    	
    	List<Event> result=session.createQuery("from Event").list();
    	session.getTransaction().commit();
    	return result;
    }
}
