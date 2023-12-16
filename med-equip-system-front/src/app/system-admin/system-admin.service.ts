import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemAdmin } from './model/system-admin.model';

@Injectable({
  providedIn: 'root'
})
export class SystemAdminService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Array<SystemAdmin>>{
    return this.http.get<Array<SystemAdmin>>(`http://localhost:8092/api/systemadmins/getAll`);
  }

  addSystemAdmin(admin: SystemAdmin): Observable<SystemAdmin>{
    return this.http.post<SystemAdmin>(`http://localhost:8092/api/systemadmins/create`, admin)
  } 



}
