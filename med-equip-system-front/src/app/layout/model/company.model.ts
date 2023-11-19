import { CompanyAdmin } from './companyAdmin.model';
import { Location } from './location.model';
export interface Company {
    id: number;
    name: string;
    description: string;
    averageRate: number;
    location: Location;
    companyAdmins?: CompanyAdmin[]; 
}