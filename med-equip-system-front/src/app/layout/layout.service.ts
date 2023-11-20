import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './model/user.model';
import { Company } from './model/company.model';
import { LoyaltyProgram } from './model/loyaltyProgram';
import { CompanyAdmin } from './model/companyAdmin.model';
import { CompanyEquipment } from './model/equipment.model';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<Array<User>>{
    return this.http.get<Array<User>>(`http://localhost:8092/api/users`);
  }

  addUser(user: User): Observable<User>{
    return this.http.post<User>(`http://localhost:8092/api/users`, user);
  }

  getUserById(userId: number): Observable<User>{
    return this.http.get<User>(`http://localhost:8092/api/users/${userId}`)
  }
  getCompanies(): Observable<Array<Company>>{
    return this.http.get<Array<Company>>(`http://localhost:8092/api/companies/all`);
  }
  updateUser(user: User): Observable<User>{
    return this.http.put<User>(`http://localhost:8092/api/users/update`, user);
  }
  updatePassword(userId: number, password: string): Observable<User>{
    return this.http.put<User>(`http://localhost:8092/api/users/updatePassword/${userId}`, password);
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
      return this.http.put<CompanyAdmin>(`http://localhost:8092/api/companyadmins/update/${admin?.id}`,admin); 
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
}
