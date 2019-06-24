package com.example.database_practice_sqllite;

public class employeeData {

    String name , latitudeLocation , longitudeLocation ;

    public employeeData(String name, String latitudeLocation, String longitudeLocation) {
        this.name = name;
        this.latitudeLocation = latitudeLocation;
        this.longitudeLocation = longitudeLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitudeLocation() {
        return latitudeLocation;
    }

    public void setLatitudeLocation(String latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public String getLongitudeLocation() {
        return longitudeLocation;
    }

    public void setLongitudeLocation(String longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }
}
