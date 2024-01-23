import { Appointment } from "src/app/client/model/appointment.model";
import { ReservationItem } from "src/app/client/model/reservationCreation.model";
import { Client } from "./client.model";

export interface Reservation {

    reservationItems: ReservationItem[];
    appointment: Appointment;
    client: Client;
    status: string;
}
export interface QRCode {
    qrCode: Uint8Array;
    status: ReservationStatus;
}
export enum ReservationStatus {

    RESERVED = 'RESERVED',
    CANCELLED = 'CANCELLED',
    TAKEN = 'TAKEN',
    EXPIRED = 'EXPIRED',
  }


