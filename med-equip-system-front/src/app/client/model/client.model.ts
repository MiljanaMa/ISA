export interface Client {
    id?: number;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    city: string;
    country: string;
    phoneNumber: string;
    jobTitle: string;
    hospitalInfo: string;
    penaltyPoints: number;
    points: number;
    loyaltyType: string;
    discount: number;
}
export interface Passwords {
    userId: number;
    newPassword: string;
    oldPassword: string;
}