import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemAdmin } from './model/system-admin.model';
import { PasswordChange } from './model/password-change.model';

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

  /*updatePassword(password: string, oldPassword: string, userId: number): Observable<SystemAdmin> {
    const body = { password, oldPassword, userId };

    return this.http.post<SystemAdmin>(`http://localhost:8092/api/systemadmins/updatePassword`, body);
  }*/

  updatePassword(passwordChange: PasswordChange): Observable<SystemAdmin> {

    return this.http.post<SystemAdmin>(`http://localhost:8092/api/systemadmins/updatePassword`, passwordChange);
  }
  



}
