import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";


export interface Appointment {
    id: number;
    date: string; // You might need to handle LocalDate format accordingly
    startTime: string; // LocalTime might need formatting as well
    endTime: string; // LocalTime might need formatting as well
    status: AppointmentStatus;
}

export enum AppointmentStatus {
    Reserved = 'Reserved',
    Available = 'Available'
}