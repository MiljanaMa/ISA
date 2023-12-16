import { Time } from "@angular/common";
import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";


export interface Appointment {
    id: number;
    date: Date; 
    startTime: Time;
    endTime: Time; 
    status: AppointmentStatus;
    companyAdmin: CompanyAdmin; 
}

export enum AppointmentStatus {
    Reserved = 'RESERVED',
    Available = 'AVAILABLE'
}