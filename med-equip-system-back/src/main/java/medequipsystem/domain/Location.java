package medequipsystem.domain;
import javax.persistence.*;
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "streetNumber", nullable = false)
    private String streetNumber;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "postcode", nullable = false)
    private int postcode;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @Column(name = "latitude", nullable = false)
    private double latitude;

    public Location() {
    }

    public Location(Long id, String street, String streetNumber, String city, String country, int postcode, double longitude, double latitude) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public void setStreetNumber(String number) {
        this.streetNumber = number;
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
}
