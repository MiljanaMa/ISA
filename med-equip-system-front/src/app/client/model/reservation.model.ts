import { Appointment } from "src/app/feature-modules/company/model/appointment.model";
import { ReservationItem } from "src/app/feature-modules/company/model/reservationCreation.model";
import { Client } from "./client.model";

export interface Reservation {

    reservationItems: ReservationItem[];
    appointment: Appointment;
    client: Client;
}

