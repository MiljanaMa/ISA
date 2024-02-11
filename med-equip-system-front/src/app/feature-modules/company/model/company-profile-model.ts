import { CompanyAdmin } from "src/app/layout/model/companyAdmin.model";
import { Location } from "src/app/layout/model/location.model";
import { CompanyEquipmentProfile } from "./companyEquipmentProfile.model";
import { Appointment } from "../../../client/model/appointment.model";
export interface CompanyProfile {
    id: number;
    name: string;
    description: string;
    averageRate: number;
    location: Location;
    companyAdmins?: CompanyAdmin[]; 
    companyEquipment? : CompanyEquipmentProfile[]; 
    workingHours: string; 
   
}