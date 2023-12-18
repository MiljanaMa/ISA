import {  CompanyEquipmentProfile } from "./companyEquipmentProfile.model";
import { Appointment } from "./appointment.model";
import { Time } from "@angular/common";

export interface ReservationCreation {
    reservationItems: ReservationItem[];
    appointment: Appointment
}
export interface ReservationItem {
    id: number;
    count: number;
    equipment: CompanyEquipmentProfile;
}

