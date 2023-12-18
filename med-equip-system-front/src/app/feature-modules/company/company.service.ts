import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompanyProfile } from './model/company-profile-model';
import { CompanyEquipmentProfile } from './model/companyEquipmentProfile.model';
import { ReservationCreation } from './model/reservationCreation.model';
import { CompanyEquipment } from 'src/app/layout/model/equipment.model';
import { Appointment, CustomAppointment } from './model/appointment.model';
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

  deleteEquipment(equipmentId: number): Observable<void>{
    return this.http.delete<void>(`http://localhost:8092/api/equipments/${equipmentId}`); 
  }

  updateEquipment(equipment: CompanyEquipmentProfile, company: CompanyProfile): Observable<void> {
    return this.http.put<void>('http://localhost:8092/api/equipments/update', { equipDto: equipment, companyDto: company }); 
  }
  createEquipment(equipment: CompanyEquipmentProfile, company: CompanyProfile): Observable<CompanyEquipmentProfile> {

    return this.http.post<CompanyEquipmentProfile>('http://localhost:8092/api/equipments/create', { equipDto: equipment, companyDto: company });
  }
  

  makeCustomReservation(reservation: CustomReservation): Observable<string>{
    return this.http.post<string>(`http://localhost:8092/api/reservations/create/custom`, reservation);

  }
  getCustomAppointments(date: Date, companyId: number): Observable<CustomAppointment[]>{
    let params = new HttpParams().set('date', date.toISOString().split('T')[0]).set('companyId', companyId || '');

    return this.http.get<CustomAppointment[]>(`http://localhost:8092/api/appointments/custom`, {params});
  }

}
