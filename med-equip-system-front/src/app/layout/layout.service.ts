import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PagedResult } from '../shared/model/paged-result';
import { User } from './model/user.model';
import { Company } from './model/company.model';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<Array<User>>{
    return this.http.get<Array<User>>(`http://localhost:8092/api/users/all`);
  }
  getCompanies(): Observable<Array<Company>>{
    return this.http.get<Array<Company>>(`http://localhost:8092/api/companies/all`);
  }
}
