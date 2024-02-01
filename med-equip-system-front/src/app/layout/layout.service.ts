import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from './model/company.model';
import { CompanyAdmin } from './model/companyAdmin.model';
import { CompanyEquipment } from './model/equipment.model';
import { SystemAdmin } from '../system-admin/model/system-admin.model';
import { CompanyAdminReal } from './model/praskinAdmin.model';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  constructor(private http: HttpClient) { }

  getCompanies(): Observable<Array<Company>>{
    return this.http.get<Array<Company>>(`http://localhost:8092/api/companies/all`);
  }

  createCompany(company: Company): Observable<Company> {
    return this.http.post<Company>(`http://localhost:8092/api/companies/create`, company);
  }

  reverseGeocode(latitude: number, longitude: number): Observable<any> {
    let apiUrl = 'https://nominatim.openstreetmap.org/reverse';

    const url = `${apiUrl}?lat=${latitude}&lon=${longitude}&format=json`;

    return this.http.get(url);
  }
  getAdminById(adminId: number): Observable<CompanyAdmin>{
      return this.http.get<CompanyAdmin>(`http://localhost:8092/api/companyadmins/${adminId}`);
  }
  updateAdmin(admin : CompanyAdmin): Observable<CompanyAdmin>{
      return this.http.put<CompanyAdmin>(`http://localhost:8092/api/companyadmins/update`,admin); 
  }
  getEquipments(): Observable<Array<CompanyEquipment>>{
    return this.http.get<Array<CompanyEquipment>>(`http://localhost:8092/api/equipments/all`);

  }

  addCompanyAdmin(admin: CompanyAdmin): Observable<CompanyAdmin>{
    return this.http.post<CompanyAdmin>(`http://localhost:8092/api/companyadmins/create`, admin);
  }

  getFreeAdmins(): Observable<Array<CompanyAdmin>>{
    return this.http.get<Array<CompanyAdmin>>(`http://localhost:8092/api/companyadmins/free`);
  }

  getAdminByUserId(userId: number): Observable<CompanyAdmin>{
    return this.http.get<CompanyAdmin>(`http://localhost:8092/api/companyadmins/byUser/${userId}`);
  }
  
  getAdminOkByUserId(userId: number): Observable<CompanyAdminReal> {
    return this.http.get<CompanyAdminReal>(`http://localhost:8092/api/companyadmins/byUser/${userId}`);
  }


  
}
