import {  CompanyEquipmentProfile } from "../../feature-modules/company/model/companyEquipmentProfile.model";
import { Appointment, CustomAppointment } from "./appointment.model";

export interface ReservationCreation {
    reservationItems: ReservationItem[];
    appointment: Appointment
}
export interface ReservationItem {
    id: number;
    count: number;
    equipment: CompanyEquipmentProfile;
    price: number;
}
export interface CustomReservation {
    reservationItems: ReservationItem[];
    //appointment: CustomAppointment;
    appointment: Appointment;
    companyId: number;
}

