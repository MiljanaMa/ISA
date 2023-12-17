import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";


export interface Appointment {
    id?: number;
    date: Date; 
    startTime: string;
    endTime: string; 
    status: AppointmentStatus;
    companyAdmin?: CompanyAdmin; 
}

export enum AppointmentStatus {

    RESERVED = 'RESERVED',
    AVAILABLE = 'AVAILABLE'
}

