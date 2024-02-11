import { CompanyAdmin } from './companyAdmin.model';
import { Location } from './location.model';
import { CompanyEquipment } from './equipment.model';
import { Appointment } from '../../client/model/appointment.model';
export interface Company {
    id: number;
    name: string;
    description: string;
    averageRate: number;
    location: Location;
    companyAdmins?: CompanyAdmin[]; 
    companyEquipment? : CompanyEquipment[]; 
    appointments? : Appointment[]; 

}