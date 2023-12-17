package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.ReservationItem;
import medequipsystem.mapper.MapperUtils.DTOEntity;

public class ReservationItemDTO implements DTOEntity {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("count")
    private int count;
    @JsonProperty("equipment")
    private CompanyEquipmentDTO equipment;

    public ReservationItemDTO(){}
    /*public ReservationItemDTO(ReservationItem item) {
        this.id = item.getId();
        this.count = item.getCount();
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CompanyEquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(CompanyEquipmentDTO equipment) {
        this.equipment = equipment;
    }
}
