export enum UserType{
    CUSTOMER = 'CUSTOMER',
    SYS_ADMIN = 'SYS_ADMIN',
    COMP_ADMIN = 'COMP_ADMIN'
}

export interface User {
    id?: number;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    city: string;
    country: string;
    phoneNumber: string;
    jobTitle: string;
    companyInformation: string;
    userType: UserType
}