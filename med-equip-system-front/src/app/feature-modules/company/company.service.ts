import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompanyProfile } from './model/company-profile-model';
import { Appointment, CustomAppointment } from './model/appointment.model';
import { CompanyEquipment } from './model/companyEquipment.model';
import { CustomReservation, ReservationCreation } from './model/reservationCreation.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private baseUrl = 'http://localhost:8092/api/companies';
  datePipe: any;

  constructor(private http: HttpClient) { }

  getAllCompanies(): Observable<CompanyProfile[]> {
    return this.http.get<CompanyProfile[]>(`${this.baseUrl}/all`);
  }

  getCompanyById(companyId: number): Observable<CompanyProfile> {
    return this.http.get<CompanyProfile>(`${this.baseUrl}/${companyId}`);
  }

  updateCompany(company: CompanyProfile): Observable<void>{
    return this.http.put<void>(`${this.baseUrl}/update/${company.id}`, company);
  }


  getAppointmentsByCompany(companyId: number): Observable<Appointment[]>{
    return this.http.get<Appointment[]>(`http://localhost:8092/api/appointments/company/${companyId}`);
  }
  
  createAppointment(appointment: Appointment): Observable<Appointment>{
    return this.http.post<Appointment>(`http://localhost:8092/api/appointments/create`, appointment);  
  }
  makePredefinedReservation(reservation: ReservationCreation): Observable<string>{
    return this.http.post<string>(`http://localhost:8092/api/reservations/create/predefined`, reservation);

  }
  makeCustomReservation(reservation: CustomReservation): Observable<string>{
    return this.http.post<string>(`http://localhost:8092/api/reservations/create/custom`, reservation);

  }
  getCustomAppointments(date: Date, companyId: number): Observable<CustomAppointment[]>{
    let params = new HttpParams().set('date', date.toISOString().split('T')[0]).set('companyId', companyId || '');

    return this.http.get<CustomAppointment[]>(`http://localhost:8092/api/appointments/custom`, {params});
  }
}
