package medequipsystem.dto;

public class PasswordChangeDTO {
    private String newPassword;
    private String oldPassword;
    //uzmi i izvuci iz principle user id, nemoj ga stavljati ovako
    private Long userId;

    public PasswordChangeDTO(){

    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
