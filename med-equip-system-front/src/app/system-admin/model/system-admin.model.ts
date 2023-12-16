export interface SystemAdmin{
    id?: number;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    city: string;
    country: string;
    phoneNumber: string;
    isMain: boolean; //sa isMain nije radilo jer 'is' samo izbaci, pa ne moze da se pristupi sa systemAdmi.isMain vec sa .main
    isInititialPasswordChanged: boolean;
}