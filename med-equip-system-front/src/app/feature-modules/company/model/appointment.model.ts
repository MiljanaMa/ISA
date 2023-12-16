export interface Appointment {
    id: number;
    date: Date; // You might need to handle LocalDate format accordingly
    startTime: string; // LocalTime might need formatting as well
    endTime: string; // LocalTime might need formatting as well
    status: AppointmentStatus;
}

export enum AppointmentStatus {
    RESERVED = 'RESERVED',
    AVAILABLE = 'AVAILABLE'
}