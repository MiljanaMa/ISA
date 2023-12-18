import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompanyProfile } from './model/company-profile-model';
import { Appointment } from './model/appointment.model';
import { CompanyEquipmentProfile } from './model/companyEquipmentProfile.model';
import { ReservationCreation } from './model/reservationCreation.model';
import { CompanyEquipment } from 'src/app/layout/model/equipment.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private baseUrl = 'http://localhost:8092/api/companies';

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
  makeReservation(reservation: ReservationCreation): Observable<void>{
    return this.http.post<void>(`http://localhost:8092/api/reservations/create`, reservation);

  }

  deleteEquipment(equipmentId: number): Observable<void>{
    return this.http.delete<void>(`http://localhost:8092/api/equipments/${equipmentId}`); 
  }

  updateEquipment(equipment: CompanyEquipmentProfile): Observable<void> {
    return this.http.put<void>('http://localhost:8092/api/equipments/update', equipment); 
  }
  createEquipment(equipment: CompanyEquipmentProfile, company: CompanyProfile): Observable<CompanyEquipmentProfile> {

    return this.http.post<CompanyEquipmentProfile>('http://localhost:8092/api/equipments/create', { equipDto: equipment, companyDto: company });
  }
  
}
