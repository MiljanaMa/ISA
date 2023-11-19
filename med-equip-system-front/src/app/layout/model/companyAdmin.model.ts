export interface CompanyAdmin {
    id?: number; 
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    city: string;
    country: string;
    phoneNumber: string;
    companyId?: number; 
  }