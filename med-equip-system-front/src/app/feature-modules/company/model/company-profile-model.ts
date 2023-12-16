import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";
import { Location } from "src/app/layout/model/location.model";
import { CompanyEquipment } from "./companyEquipment.model";
import { Appointment } from "./appointment.model";
export interface CompanyProfile {
    id: number;
    name: string;
    description: string;
    averageRate: number;
    location: Location;
    companyAdmins?: CompanyAdmin[]; 
    companyEquipment? : CompanyEquipment[]; 
    workingHours: string; 
   
}