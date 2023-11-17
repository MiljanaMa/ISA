import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './model/user.model';
import { Company } from './model/company.model';

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
    return this.http.put<User>(`http://localhost:8092/api/users`, user);
  }
}
