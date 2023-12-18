export interface ReservedAppointment{
    id?: number;
    date: Date;
    startTime: string;
    endTime: string;
    clientFirstName: string;
    clientLastName: string;
    adminFirstName: string;
    adminLastName: string;
}