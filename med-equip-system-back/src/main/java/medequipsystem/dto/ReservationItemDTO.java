package medequipsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.ReservationItem;

public class ReservationItemDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("count")
    private int count;
    @JsonProperty("equipment")
    private CompanyEquipmentDTO equipment;

    public ReservationItemDTO(){}
    /*public ReservationItemDTO(ReservationItem item){
        this(item.getId(), item.getCount(), item.getEquipment());
    }

    public ReservationItemDTO(Long id, int count, CompanyEquipment equipment) {
        this.id = id;
        this.count = count;
        this.equipment = equipment;
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
