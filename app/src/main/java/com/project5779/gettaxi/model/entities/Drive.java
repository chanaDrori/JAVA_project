package com.project5779.gettaxi.model.entities;

import android.location.Location;

public class Drive
{
    private StateOfDrive state;
    private Location startPoint;
    private Location endPoint;
    private String startTime;
    private String endTime;
    private String nameClient;
    private String phoneClient;
    private String emailClient;

   /* public Drive(StateOfDrive _state, String _startPoint, String _endPoint, String _startTime, String _endTime,
                String _nameClient, String _phoneClient, String _emailClient)
    {
        this(_state,_startPoint ,_endPoint ,_startTime ,_endTime ,_nameClient ,_phoneClient ,_emailClient );
    }
    */

    /**
     * constructor of drive.
     * @param _state enum- state of the ride (start/work/end)
     * @param _startPoint string - The starting point of the ride
     * @param _endPoint string - The ending point of the ride
     * @param _startTime string - The start time of the ride
     * @param _endTime string - The end time of the ride
     * @param _nameClient string - The client's name
     * @param _phoneClient string - The client's phone number
     * @param _emailClient string - The client's mail address
     */
    public Drive(StateOfDrive _state, Location _startPoint, Location _endPoint, String _startTime, String _endTime,
                 String _nameClient, String _phoneClient, String _emailClient)
    {
        this.state = _state;
        this.startPoint = _startPoint;
        this.endPoint = _endPoint;
        this.startTime = _startTime;
        this.endTime = _endTime;
        this.nameClient = _nameClient;
        this.phoneClient = _phoneClient;
        this.emailClient = _emailClient;
        
    }

    /**
     * empty constructor
     */
    public Drive(){}

    /**
     * getter
     * @return state. enum- state of the ride (start/work/end)
     */
    public StateOfDrive getState() {
        return state;
    }

    /**
     * getter
     * @return endPoint. string - The ending point of the ride
     */
    public Location getEndPoint() {
        return endPoint;
    }

    /**
     * getter
     * @return emailClient. string - The client's mail address
     */
    public String getEmailClient() {
        return emailClient;
    }

    /**
     * getter
     * @return endTime. string - The end time of the ride
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * getter
     * @return nameClient. string - The client's name
     */
    public String getNameClient() {
        return nameClient;
    }

    /**
     * getter
     * @return phoneClient. string - The client's phone number
     */
    public String getPhoneClient() {
        return phoneClient;
    }

    /**
     * getter
     * @return startPoint. string - The starting point of the ride
     */
    public Location getStartPoint() {
        return startPoint;
    }

    /**
     * getter
     * @return startTime. string - The start time of the ride
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * setter
     * @param endPoint string - The ending point of the ride
     */
    public void setEndPoint(Location endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * setter
     * @param emailClient string - The client's mail address
     */
    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    /**
     * setter
     * @param endTime string - The end time of the ride
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * setter
     * @param nameClient string - The client's name
     */
    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    /**
     * setter
     * @param phoneClient string - The client's phone number
     */
    public void setPhoneClient(String phoneClient) {
        this.phoneClient = phoneClient;
    }

    /**
     * setter
     * @param startPoint string - The starting point of the ride
     */
    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * setter
     * @param startTime string - The start time of the ride
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * setter
     * @param state enum- state of the ride (start/work/end)
     */
    public void setState(StateOfDrive state) {
        this.state = state;
    }

    /**
     *
     * @param obj object for compare.
     * @return true if the object equals to this Drive.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Drive drive = (Drive) obj;
        return (this.startPoint.equals(drive.getStartPoint()) &&
                this.state.equals( drive.getState()) &&
                this.endPoint.equals(drive.getEndPoint()) &&
                this.emailClient.equals(drive.getEmailClient()) &&
                this.endTime.equals(drive.getEndTime()) &&
                this.nameClient.equals(drive.getNameClient()) &&
                this.phoneClient.equals(drive.getPhoneClient()) &&
                this.startTime.equals(drive.getStartTime()));
    }
}
