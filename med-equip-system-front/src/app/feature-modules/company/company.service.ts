import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from 'src/app/layout/model/company.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private baseUrl = 'http://localhost:8092/api/companies';

  constructor(private http: HttpClient) { }

  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.baseUrl}/all`);
  }

  getCompanyById(companyId: number): Observable<Company> {
    return this.http.get<Company>(`${this.baseUrl}/${companyId}`);
  }

  updateCompany(company: Company): Observable<Company>{
    return this.http.put<Company>(`${this.baseUrl}/update/${company.id}`, company);
  }
}
