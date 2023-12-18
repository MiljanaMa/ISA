import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservedAppointment } from './model/reserved-appointment.model';
import { Appointment } from '../feature-modules/company/model/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyAdminService {

  constructor(private http: HttpClient) { }

  getReservedAppointments(companyId: number): Observable<Array<ReservedAppointment>> {
    return this.http.get<Array<ReservedAppointment>>(`http://localhost:8092/api/appointments/companycalendar/${companyId}`);
  }

  getNotReservedAppointmentsByCompany(companyId: number): Observable<Appointment[]>{
    return this.http.get<Appointment[]>(`http://localhost:8092/api/appointments/notreservedappointments/${companyId}`);
  }
}
