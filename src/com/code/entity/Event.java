package com.code.entity;


public class Event 
{

	private String eId;
    private String tId;
    private String eventName;
    private String eventPlace;
    private String aboutEvent;
    private String eventFees;
    private String regSDate;
    private String regLDate;
    private String eventDate;
    private String bannerName;
    private String status;
    private String guestStatus;
    private String winnerStatus;
    
    private ParticipantDetails participantDetails; 
    
    
	public ParticipantDetails getParticipantDetails() {
		return participantDetails;
	}
	public void setParticipantDetails(ParticipantDetails participantDetails) {
		this.participantDetails = participantDetails;
	}
	
	public String geteId() {
		return eId;
	}
	public void seteId(String eId) {
		this.eId = eId;
	}
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventPlace() {
		return eventPlace;
	}
	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}
	public String getAboutEvent() {
		return aboutEvent;
	}
	public void setAboutEvent(String aboutEvent) {
		this.aboutEvent = aboutEvent;
	}
	public String getEventFees() {
		return eventFees;
	}
	public void setEventFees(String eventFees) {
		this.eventFees = eventFees;
	}
	public String getRegSDate() {
		return regSDate;
	}
	public void setRegSDate(String regSDate) {
		this.regSDate = regSDate;
	}
	public String getRegLDate() {
		return regLDate;
	}
	public void setRegLDate(String regLDate) {
		this.regLDate = regLDate;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}	
	public String getBannerName() {
		return bannerName;
	}
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGuestStatus() {
		return guestStatus;
	}
	public void setGuestStatus(String guestStatus) {
		this.guestStatus = guestStatus;
	}
	public String getWinnerStatus() {
		return winnerStatus;
	}
	public void setWinnerStatus(String winnerStatus) {
		this.winnerStatus = winnerStatus;
	}


}
