import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ReservedAppointment } from './model/reserved-appointment.model';
import { Appointment } from '../feature-modules/company/model/appointment.model';
import { CurrentUser } from '../auth/model/current-user.model';
import { environment } from 'src/env/environment';
import { CompanyAdmin } from '../layout/model/companyAdmin.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyAdminService {

  public currentUser = new BehaviorSubject<CurrentUser>({id: 0, email: "", role: null });

  constructor(private http: HttpClient) { }

  getReservedAppointments(companyId: number): Observable<Array<ReservedAppointment>> {
    return this.http.get<Array<ReservedAppointment>>(`http://localhost:8092/api/appointments/companycalendar/${companyId}`);
  }

  getNotReservedAppointmentsByCompany(companyId: number): Observable<Appointment[]>{
    return this.http.get<Appointment[]>(`http://localhost:8092/api/appointments/notreservedappointments/${companyId}`);
  }

  getCurrentUser(): Observable<CurrentUser>{
    return this.http.get<CurrentUser>(environment.apiHost + 'auth/whoami');
  }

  getCompanyAdminByUserId(id: number): Observable<CompanyAdmin>{ 
    return this.http.get<CompanyAdmin>(`http://localhost:8092/api/companyadmins/user/${id}`);
  }
}
