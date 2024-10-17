package com.code.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.code.conn.DbConnection;
import com.code.entity.Event;
import com.code.entity.ParticipantDetails;

public class EventDeatils 
{
	// Check Registration Available or Not
	// Check Already registrer or Not
	// if yes cancel registration
	// check Geust Status --if Yes View Gest Details
	// Mark Event as Complete If Event is Completed Add Winners
	// View Participant List
	// view Winners List
	static Connection con = DbConnection.getConnection();
	GlobalFunction gf = new GlobalFunction();

	// Check Registration Available or Not
	public boolean checkEventRegistration(String eid) {
		boolean result = false;
		try {
			HashMap<String, String> eventDetails = gf.getEventDetails(eid);
			LocalDate regSdate = LocalDate.parse(eventDetails.get("reg_sdate"));
			LocalDate regLdate = LocalDate.parse(eventDetails.get("reg_ldate"));
			LocalDate cdate = LocalDate.now();
			long diff1 = ChronoUnit.DAYS.between(regSdate, cdate);
			long diff2 = ChronoUnit.DAYS.between(regLdate, cdate);
			// Output the difference
			System.out.println("Difference in days: " + diff1);
			System.out.println("Difference in days: " + diff2);
			if (diff1 >= 0 && diff2 <= 0) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("Exc checkEventRegistrationStatus " + e);
		}
		return result;
	}

	// Check user Already register or Not
	public boolean checkEventRegistrationStatus(String e_id, String st_id) {
		boolean result = false;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM `participant_details` where e_id='"
							+ e_id
							+ "' AND st_id='"
							+ st_id
							+ "' AND status='RegistrationDone'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		System.out.println(e_id + " reg sts " + result);
		return result;
	}

	public static boolean checkEventRegistration1() {
		boolean result = false;
		try {
			// HashMap<String, String> eventDetails = gf.getEventDetails(eid);
			LocalDate regSdate = LocalDate.parse("2024-03-28");
			LocalDate regLdate = LocalDate.parse("2024-03-29");
			LocalDate cdate = LocalDate.now();
			long diff1 = ChronoUnit.DAYS.between(regSdate, cdate);
			long diff2 = ChronoUnit.DAYS.between(regLdate, cdate);
			// Output the difference
			System.out.println("Difference in days: " + diff1);
			System.out.println("Difference in days: " + diff2);
			if (diff1 >= 0 && diff2 <= 0) {
				System.out.println("true");
				result = true;
			}

		} catch (Exception e) {
			System.out.println("Exc checkEventRegistrationStatus " + e);
		}
		return result;
	}

	public HashMap<String, String> getParticipaeEvent(String e_id, String st_id) {

		HashMap<String, String> eventDetails = new HashMap<>();

		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM `participant_details` where e_id='"
							+ e_id + "' AND st_id='" + st_id + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				eventDetails.put("p_id", rs.getString("p_id"));
				eventDetails.put("st_id", rs.getString("st_id"));
				eventDetails.put("e_id", rs.getString("e_id"));
				eventDetails.put("payment_status",
						rs.getString("payment_status"));
				eventDetails.put("status", rs.getString("status"));
				eventDetails.put("reg_date", rs.getString("reg_date"));
				eventDetails.put("winning_status",
						rs.getString("winning_status"));
			}

		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return eventDetails;
	}

	public List<HashMap<String, String>> getListParticipaeEvent(String e_id,
			String st_id) {
		List<HashMap<String, String>> listEventDetails = new ArrayList<>();
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM `participant_details` where e_id='"
							+ e_id + "' AND st_id='" + st_id + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, String> eventDetails = new HashMap<>();
				eventDetails.put("p_id", rs.getString("p_id"));
				eventDetails.put("st_id", rs.getString("st_id"));
				eventDetails.put("e_id", rs.getString("e_id"));
				eventDetails.put("payment_status",
						rs.getString("payment_status"));
				eventDetails.put("status", rs.getString("status"));
				eventDetails.put("reg_date", rs.getString("reg_date"));
				eventDetails.put("winning_status",
						rs.getString("winning_status"));
				listEventDetails.add(eventDetails);
			}

		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return listEventDetails;
	}

	public List<HashMap<String, String>> getListEnrollmentEvent(String st_id) {
		List<HashMap<String, String>> listEventDetails = new ArrayList<>();
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM `participant_details` where st_id='"
							+ st_id + "'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, String> eventDetails = new HashMap<>();
				eventDetails.put("p_id", rs.getString("p_id"));
				eventDetails.put("st_id", rs.getString("st_id"));
				eventDetails.put("e_id", rs.getString("e_id"));
				eventDetails.put("payment_status",
						rs.getString("payment_status"));
				eventDetails.put("status", rs.getString("status"));
				eventDetails.put("reg_date", rs.getString("reg_date"));
				eventDetails.put("winning_status",
						rs.getString("winning_status"));
				listEventDetails.add(eventDetails);
			}

		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return listEventDetails;
	}

	public List<Event> getAllEnrollEventDetailsList(String st_id) {
		List<Event> listOfEvents = new ArrayList<>();

		try {

			PreparedStatement ps1 = con
					.prepareStatement("SELECT * FROM `participant_details` where st_id='"
							+ st_id
							+ "' AND status='RegistrationDone' AND winning_status='NA'");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				String e_id = rs1.getString("e_id");

				String p_id = rs1.getString("p_id");
				String payment_status = rs1.getString("payment_status");
				String status = rs1.getString("status");
				String reg_date = rs1.getString("status");
				String winning_status = rs1.getString("winning_status");
				String feedback_status = rs1.getString("feedback_status");

				ParticipantDetails pd = new ParticipantDetails();
				pd.setpId(p_id);
				pd.setStatus(status);
				pd.seteId(e_id);
				pd.setPaymentStatus(payment_status);
				pd.setRegDate(reg_date);
				pd.setWinningStatus(winning_status);
				pd.setFeedbackStatus(feedback_status);

				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM `event_details` where delete_status='No' AND e_id='"
								+ e_id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Event eventDetails = new Event();

					eventDetails.seteId(rs.getString("e_id"));
					eventDetails.settId(rs.getString("t_id"));
					eventDetails.setEventName(rs.getString("event_name"));
					eventDetails.setEventPlace(rs.getString("event_place"));
					eventDetails.setAboutEvent(rs.getString("about_event"));
					eventDetails.setEventFees(rs.getString("event_fees"));
					eventDetails.setRegSDate(rs.getString("reg_sdate"));
					eventDetails.setRegLDate(rs.getString("reg_ldate"));
					eventDetails.setEventDate(rs.getString("event_date"));
					eventDetails.setBannerName(rs.getString("event_banner"));
					eventDetails.setStatus(rs.getString("status"));
					eventDetails.setGuestStatus(rs.getString("guest_status"));
					eventDetails.setWinnerStatus(rs.getString("winner_status"));

					eventDetails.setParticipantDetails(pd);
					listOfEvents.add(eventDetails);
				}
			}
		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return listOfEvents;
	}

	public List<ParticipantDetails> getAllparticipatingList(String e_id) {
		List<ParticipantDetails> pStList = new ArrayList<>();
		GlobalFunction gf = new GlobalFunction();
		try {

			PreparedStatement ps1 = con
					.prepareStatement("SELECT * FROM `participant_details` where e_id='"
							+ e_id + "' AND status='RegistrationDone'");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				String st_id = rs1.getString("st_id");
				ParticipantDetails pd = new ParticipantDetails();
				pd.setStId(st_id);
				HashMap<String, String> studentDetails = gf
						.getStudentDetails(st_id);
				String st_name = studentDetails.get("st_name");
				pd.setStName(st_name);
				pStList.add(pd);
			}
		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return pStList;
	}

	public List<String> getWinnersList(String w_id) {
		List<String> winnersList = new ArrayList<>();
		GlobalFunction gf = new GlobalFunction();
		try {

			PreparedStatement ps1 = con
					.prepareStatement("SELECT * FROM `winners_details` where w_id='"
							+ w_id + "'");
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {

				HashMap<String, String> studentDetails1 = gf
						.getStudentDetails(rs1.getString("first_winner"));
				HashMap<String, String> studentDetails2 = gf
						.getStudentDetails(rs1.getString("second_winner"));
				HashMap<String, String> studentDetails3 = gf
						.getStudentDetails(rs1.getString("third_winner"));

				String first_winner = studentDetails1.get("st_name");
				String second_winner = studentDetails2.get("st_name");
				String third_winner = studentDetails3.get("st_name");

				winnersList.add(first_winner);
				winnersList.add(second_winner);
				winnersList.add(third_winner);
			}
		} catch (Exception e) {
			System.out.println("Exc " + e);
		}
		return winnersList;
	}

	public static void main(String[] args) {
		checkEventRegistration1();
	}
}
