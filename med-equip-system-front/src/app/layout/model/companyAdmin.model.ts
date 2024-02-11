import { User } from "./user.model";

export interface CompanyAdmin {
    id?: number; 
    user: User; 
    companyId?: number; 
    firstTime: boolean; 
  }