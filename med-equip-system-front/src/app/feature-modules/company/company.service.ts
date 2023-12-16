import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompanyProfile } from './model/company-profile-model';
import { CompanyEquipment } from './model/companyEquipment.model';
import { ReservationCreation } from './model/reservationCreation.model';

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
  makeReservation(reservation: ReservationCreation): Observable<void>{
    return this.http.post<void>(`http://localhost:8092/api/reservations/create`, reservation);
  }
}
