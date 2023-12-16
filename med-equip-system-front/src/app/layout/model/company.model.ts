import { CompanyAdmin } from './companyAdmin.model';
import { Location } from './location.model';
import { CompanyEquipment } from '../../feature-modules/company/model/companyEquipment.model';
import { Appointment } from '../../feature-modules/company/model/appointment.model';
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