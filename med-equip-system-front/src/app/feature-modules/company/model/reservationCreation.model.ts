import { CompanyEquipment } from "./companyEquipment.model";
import { Appointment } from "./appointment.model";

export interface ReservationCreation {
    reservationItems: ReservationItem[];
    appointment: Appointment
}
export interface ReservationItem {
    id: number;
    count: number;
    equipment: CompanyEquipment;
}