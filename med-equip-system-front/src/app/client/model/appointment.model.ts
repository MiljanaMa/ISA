import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";


export interface Appointment {
    id?: number;
    date: Date; 
    startTime: string;
    endTime: string; 
    status: AppointmentStatus;
    companyAdmin?: CompanyAdmin; 
}
export interface CustomAppointment {
    id?: number;
    date: Date; 
    startTime: string;
    endTime: string; 
    status: AppointmentStatus;
}

export enum AppointmentStatus {

    RESERVED = 'RESERVED',
    AVAILABLE = 'AVAILABLE'
}

