import { CompanyEquipment } from "./companyEquipment.model";
import { Appointment, CustomAppointment } from "./appointment.model";
import { Time } from "@angular/common";

export interface ReservationCreation {
    reservationItems: ReservationItem[];
    appointment: Appointment
}
export interface CustomReservation {
    reservationItems: ReservationItem[];
    appointment: CustomAppointment;
    companyId: number;
}
export interface ReservationItem {
    id: number;
    count: number;
    equipment: CompanyEquipment;
}

