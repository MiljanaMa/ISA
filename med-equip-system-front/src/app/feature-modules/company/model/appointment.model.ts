
import { Time } from "@angular/common";
import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";


export interface Appointment {
    id?: number;
    date: string; 
    startTime: string;
    endTime: string; 
    status: AppointmentStatus;
    companyAdmin: CompanyAdmin; 
}

export enum AppointmentStatus {

    RESERVED = 'RESERVED',
    AVAILABLE = 'AVAILABLE'
}

