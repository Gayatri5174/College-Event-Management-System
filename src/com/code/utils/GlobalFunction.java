package com.code.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.code.conn.DbConnection;
import com.code.entity.Event;
import com.code.entity.Guest;
import com.code.entity.ParticipantDetails;
import com.code.entity.Participate;

public class GlobalFunction {

	static Connection con=DbConnection.getConnection();
	
	public String generatePassword() 
	{
		char[] chars = "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
	
	public List<Event> getAllEventDetails()
	{
		
		List<Event> listOfEvents=new ArrayList<>();
		
		
			try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `event_details` where delete_status='No' ORDER BY e_id DESC");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				Event eventDetails=new Event();
				eventDetails.seteId(rs.getString("e_id"));
				eventDetails.settId(rs.getString("t_id"));
				eventDetails.setEventName(rs.getString("event_name"));
				eventDetails.setEventPlace(rs. getString("event_place"));
				eventDetails.setAboutEvent(rs.getString("about_event"));
				eventDetails.setEventFees(rs.getString("event_fees"));
				eventDetails.setRegSDate(rs.getString("reg_sdate"));
				eventDetails.setRegLDate(rs.getString("reg_ldate"));
				eventDetails.setEventDate(rs.getString("event_date"));
				eventDetails.setBannerName(rs.getString("event_banner"));				
				eventDetails.setStatus(rs.getString("status"));
				eventDetails.setGuestStatus(rs.getString("guest_status"));
				eventDetails.setWinnerStatus(rs.getString("winner_status"));
				listOfEvents.add(eventDetails);
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return listOfEvents;
	}
	
	
	
	public List<Event> getTeacherUploadedAllEventDetails(String t_id)
	{
		
		List<Event> listOfEvents=new ArrayList<>();
		
		
			try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `event_details` where delete_status='No' AND t_id='"+t_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				Event eventDetails=new Event();
				eventDetails.seteId(rs.getString("e_id"));
				eventDetails.settId(rs.getString("t_id"));
				eventDetails.setEventName(rs.getString("event_name"));
				eventDetails.setEventPlace(rs. getString("event_place"));
				eventDetails.setAboutEvent(rs.getString("about_event"));
				eventDetails.setEventFees(rs.getString("event_fees"));
				eventDetails.setRegSDate(rs.getString("reg_sdate"));
				eventDetails.setRegLDate(rs.getString("reg_ldate"));
				eventDetails.setEventDate(rs.getString("event_date"));
				eventDetails.setBannerName(rs.getString("event_banner"));				
				eventDetails.setStatus(rs.getString("status"));
				eventDetails.setGuestStatus(rs.getString("guest_status"));
				eventDetails.setWinnerStatus(rs.getString("winner_status"));
				listOfEvents.add(eventDetails);
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return listOfEvents;
	}
	
	
	public List<Event> getSingleEventDetailsList(String e_id)
	{
		List<Event> listOfEvents=new ArrayList<>();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `event_details` where delete_status='No' AND e_id='"+e_id+"'");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{ 
				Event eventDetails=new Event();
				eventDetails.seteId(rs.getString("e_id"));
				eventDetails.settId(rs.getString("t_id"));
				eventDetails.setEventName(rs.getString("event_name"));
				eventDetails.setEventPlace(rs. getString("event_place"));
				eventDetails.setAboutEvent(rs.getString("about_event"));
				eventDetails.setEventFees(rs.getString("event_fees"));
				eventDetails.setRegSDate(rs.getString("reg_sdate"));
				eventDetails.setRegLDate(rs.getString("reg_ldate"));
				eventDetails.setEventDate(rs.getString("event_date"));
				eventDetails.setBannerName(rs.getString("event_banner"));				
				eventDetails.setStatus(rs.getString("status"));
				eventDetails.setGuestStatus(rs.getString("guest_status"));
				eventDetails.setWinnerStatus(rs.getString("winner_status"));
				listOfEvents.add(eventDetails);
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return listOfEvents;
	}
	
	
	public List<Guest> getAllGuestDetailsList()
	{
		List<Guest> listOfGuest=new ArrayList<>();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `geust_details` where status='Available'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				Guest guestDetails=new Guest();
				guestDetails.setgId(rs.getString("g_id"));
				guestDetails.setgName(rs.getString("g_name"));
				guestDetails.setStatus(rs.getString("gender"));
				guestDetails.setgMobile(rs.getString("about_geust"));
				guestDetails.setGender(rs.getString("g_email"));
				guestDetails.setgEmail(rs.getString("g_mobile"));
				guestDetails.setAboutGuest(rs.getString("status"));
				listOfGuest.add(guestDetails);
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return listOfGuest;
	}
	
	public Guest getGuestDetails(String g_id)
	{
		Guest guestDetails=new Guest();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `geust_details` where g_id='"+g_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				guestDetails.setgId(rs.getString("g_id"));
				guestDetails.setgName(rs.getString("g_name"));
				guestDetails.setStatus(rs.getString("status"));
				guestDetails.setgMobile(rs.getString("g_mobile"));
				guestDetails.setGender(rs.getString("gender"));
				guestDetails.setgEmail(rs.getString("g_email"));
				guestDetails.setAboutGuest(rs.getString("about_geust"));
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return guestDetails;
	}
	

	public Guest getGuestDetailsByGName(String gname)
	{
		Guest guestDetails=new Guest();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `geust_details` where g_name='"+gname+"'");
			System.out.println("--------------------------------------------");
			System.out.println("PS "+ps);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				guestDetails.setgId(rs.getString("g_id"));
				guestDetails.setgName(rs.getString("g_name"));
				guestDetails.setGender(rs.getString("gender"));
				guestDetails.setStatus(rs.getString("status"));
				guestDetails.setgEmail(rs.getString("g_email"));
				guestDetails.setgMobile(rs.getString("g_mobile"));
				guestDetails.setAboutGuest(rs.getString("about_geust"));
			}
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return guestDetails;
	}
	

	

	public HashMap<String,String> getEventDetails(String e_id)
	{
		
		HashMap<String,String> eventDetails=new HashMap<>();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `event_details` where e_id='"+e_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				eventDetails.put("e_id", rs.getString("e_id"));
				eventDetails.put("t_id", rs.getString("t_id"));
				eventDetails.put("event_name", rs.getString("event_name"));
				eventDetails.put("event_place", rs. getString("event_place"));
				eventDetails.put("about_event", rs.getString("about_event"));
				eventDetails.put("event_fees", rs.getString("event_fees"));
				eventDetails.put("reg_sdate", rs.getString("reg_sdate"));
				eventDetails.put("reg_ldate", rs.getString("reg_ldate"));
				eventDetails.put("event_date", rs.getString("event_date"));
				eventDetails.put("status", rs.getString("status"));
				eventDetails.put("guest_status", rs.getString("guest_status"));
				eventDetails.put("winner_status", rs.getString("winner_status"));
				eventDetails.put("event_banner", rs.getString("event_banner"));
				
			}
			
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return eventDetails;
	}
	
	
	public HashMap<String,String> getStudentDetails(String st_id)
	{
		
		HashMap<String,String> studentDetails=new HashMap<>();
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `student_details` where id='"+st_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				studentDetails.put("id", rs.getString("id"));
				studentDetails.put("st_name", rs.getString("st_name"));
				studentDetails.put("gender", rs.getString("gender"));
				studentDetails.put("mobile", rs. getString("mobile"));
				studentDetails.put("email", rs.getString("email"));
				studentDetails.put("address", rs.getString("address"));
				studentDetails.put("erp_no", rs.getString("erp_no"));
				studentDetails.put("password", rs.getString("password"));
				studentDetails.put("college_name", rs.getString("college_name"));
				studentDetails.put("profile_pic_name", rs.getString("profile_pic_name"));
				studentDetails.put("acc_create_date", rs.getString("acc_create_date"));
			}
			
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return studentDetails;
	}
	
	
	public int getParticipateCount(String e_id)
	{
		int count=0;
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `participant_details` where e_id='"+e_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				++count;
			}
			
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return count;
	}
	
	
	public int getParticipateFeedbackStatus(String e_id,String st_id)
	{
		int count=0;
		
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `feedback_details` where event_id='"+e_id+"' AND st_id='"+st_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				++count;
			}
			
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return count;
	}
	
	
	
	
	
	public ArrayList<Participate> getParticipateList(String e_id) 
	{
		ArrayList<Participate> plist=new ArrayList<>();
		try 
		{
			PreparedStatement ps=con.prepareStatement("SELECT * FROM `participant_details` where e_id='"+e_id+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ 
				
				String st_id = rs.getString("st_id");
				Participate p=new Participate();
				
				HashMap<String, String> studentDetails = getStudentDetails(st_id);
				
				p.setStName(studentDetails.get("st_name"));
				p.setEnrollNumber(studentDetails.get("erp_no"));
				p.setGender(studentDetails.get("gender"));
				p.setMobile(studentDetails.get("mobile"));
				p.setClgName(studentDetails.get("college_name"));
				
				p.setPaymentSts(rs.getString("payment_status"));
				p.setRegSts(rs.getString("status"));
				p.setRegDate(rs.getString("reg_date"));
				
				plist.add(p);
				
			}
			
		} catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		
		
		return plist;
	}

	
}
