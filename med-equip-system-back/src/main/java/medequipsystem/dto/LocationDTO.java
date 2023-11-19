package medequipsystem.dto;

import medequipsystem.domain.Location;

public class LocationDTO {
    private Long id;
    private String street;
    private String streetNumber;
    private String city;
    private String country;
    private int postcode;
    private double longitude;
    private double latitude;

    public  LocationDTO() {

    }

    public LocationDTO(Long id, String street, String streetNumber, String city, String country, int postcode, double longitude, double latitude) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public LocationDTO(Location location) {
        id = location.getId();
        street = location.getStreet();
        streetNumber = location.getStreetNumber();
        city = location.getCity();
        country = location.getCountry();
        postcode = location.getPostcode();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location mapDtoToDomain() {
        Location location = new Location();
        location.setId(this.getId());
        location.setStreet(this.getStreet());
        location.setStreetNumber(this.getStreetNumber());
        location.setCity(this.getCity());
        location.setCountry(this.getCountry());
        location.setPostcode(this.getPostcode());
        location.setLongitude(this.getLongitude());
        location.setLatitude(this.getLatitude());
        return location;
    }
}
