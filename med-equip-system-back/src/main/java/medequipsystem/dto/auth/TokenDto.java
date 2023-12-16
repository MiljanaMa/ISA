package medequipsystem.dto.auth;

public class TokenDto {

    private String accessToken;
    private Long expiresIn;

    public TokenDto() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public TokenDto(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
