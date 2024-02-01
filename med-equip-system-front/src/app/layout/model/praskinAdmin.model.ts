import { CompanyProfile } from "src/app/feature-modules/company/model/company-profile-model";
import { Company } from "./company.model";
import { User } from "./user.model";

export interface CompanyAdminReal{
    id?: number, 
    user: User, 
    company: CompanyProfile, 
    firstTime: boolean 
}