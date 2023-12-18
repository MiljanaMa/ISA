package medequipsystem.dto;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.mapper.MapperUtils.DTOEntity;

public class CompanyAdminProfileDTO implements DTOEntity {
    private Long id;
    private UserDTO user;


    public CompanyAdminProfileDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }


}
