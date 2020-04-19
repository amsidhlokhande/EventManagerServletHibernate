package org.hibenate.tutorial.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.dto.Event;
import org.hibernate.util.HibernateUtil;

public class EventManagerServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
		try
		{
		   //Begin your unit of work
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			
			// Process request and render page
			   //Write HTML Header
			
			PrintWriter out=resp.getWriter();
			out.println("<HTML><HEAD><TITLE>EVENT MANAGER</TITLE></HEAD><BODAY>");
			
			
			if("store".equals(req.getParameter("action")))
			{
				
				String eventTitle=req.getParameter("eventTitle");
				String eventDate=req.getParameter("eventDate");
				if("".equals(eventTitle)|| "".equals(eventDate))
				{
					out.println("<b><i>Please enter event title and date.</i></b>");
				}
				else
				{
					createAndStoreEvent(eventTitle,sdf.parse(eventDate));
					out.println("<b><i>Added event.</i></b>");
				}
				
			}
			//Print Page
			printEventForm(out);
			out.println("<HR COLOR='RED'>");
			listEvents(out,sdf);
			out.println("</BODY></HTML>");
            out.flush();
            out.close();
			//End unit of work
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		}catch(Exception ex)
		{
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			ex.printStackTrace();
		}
		
	}
	public void createAndStoreEvent(String eventTitle,Date eventDate)
	{
		Event event=new Event();
		event.setTitle(eventTitle);
		event.setDate(eventDate);
		HibernateUtil.getSessionFactory().getCurrentSession().save(event);
		
		
		
	}
	public void printEventForm(PrintWriter out)
	{
		out.println("<h2><CENTER>Add new event:</CENTER></h2>");
		out.println("<form action='eventmanager'>");
		out.println("<TABLE BORDER='1'ALIGN='CENTER'>");
		out.println("<TR><TD>Event Title</TD><TD><input type='textfield' name='eventTitle' length='50' /></TD></TR>");
		out.println("<TR><TD>Date(eg. 24.10.2012)</TD><TD><input type='textfield' name='eventDate' lenght='10'/></TD></TR>");
		out.println("<TR><TD COLSPAN='2' ALIGN='CENTER'><input type='submit' name='action' value='store'></TD></TR>");
		out.println("</form>");
		
	}
	public void listEvents(PrintWriter out,SimpleDateFormat sdf)
	{
		@SuppressWarnings("unchecked")
		List<Event> eventList=(List<Event>)HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from org.hibernate.dto.Event").list();
		out.flush();
		out.println("<h2>Events in database:</h2>");
		out.println("<TABLE BORDER='1'ALIGN='CENTER'>");
		out.println("<TR><TH>EVENT_ID</TH><TH>EVENT_TITLE</TH><TH>EVENT_DATE</TH></TR>");
		for(Event event:eventList)
		{
		  out.println("<TR>");
		  out.println("<TD>"+ event.getEventId()+"</TD>");
		  out.println("<TD>"+ event.getTitle()+"</TD>");
		  out.println("<TD>"+ sdf.format(event.getDate())+"</TD>");
		  out.println("</TR>");
		}
		
		out.println("</TABLE>");
		
	}
}
