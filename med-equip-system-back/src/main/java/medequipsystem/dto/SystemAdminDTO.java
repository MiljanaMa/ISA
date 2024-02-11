package medequipsystem.dto;

import medequipsystem.domain.SystemAdmin;

public class SystemAdminDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phoneNumber;
    private boolean isMain;
    private boolean isInitialPasswordChanged;
   // private Long userId;

    public SystemAdminDTO(){}

    public SystemAdminDTO(SystemAdmin systemAdmin){
        this.id = systemAdmin.getId();
        this.email = systemAdmin.getUser().getEmail();
        this.password = systemAdmin.getUser().getPassword();
        this.firstName = systemAdmin.getUser().getFirstName();
        this.lastName = systemAdmin.getUser().getLastName();
        this.city = systemAdmin.getUser().getCity();
        this.country = systemAdmin.getUser().getCountry();
        this.phoneNumber = systemAdmin.getUser().getPhoneNumber();
        this.isMain = systemAdmin.isMain();
        this.isInitialPasswordChanged = systemAdmin.isInitialPasswordChanged();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isInitialPasswordChanged() {
        return isInitialPasswordChanged;
    }

    public void setInitialPasswordChanged(boolean initialPasswordChanged) {
        isInitialPasswordChanged = initialPasswordChanged;
    }

   /* public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }*/
}
