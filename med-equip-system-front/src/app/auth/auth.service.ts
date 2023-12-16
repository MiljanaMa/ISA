import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/env/environment';
import { Login } from './model/login.model';
import { AuthenticationResponse } from './model/authentification-response.model';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { CurrentUser } from './model/current-user.model';
import { ClientRegistration } from './model/client-registration.model';
import { SystemAdmin } from '../system-admin/model/system-admin.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public currentUser = new BehaviorSubject<CurrentUser>({id: 0, email: "", role: null });

  constructor(private http: HttpClient, private router: Router) {}

  registerClient(client: ClientRegistration): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(environment.apiHost + 'auth/register', client)
      .pipe(
        tap((authenticationResponse) => {
          this.router.navigate(['/login'])
        })
      );
  }

  login(login: Login): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(environment.apiHost + 'auth/login', login)
      .pipe(
        tap((authenticationResponse) => {
          window.localStorage.setItem('jwt', authenticationResponse.accessToken);
          this.setCurrentUser();
        })
      );
  }

  setCurrentUser(): void{
    this.getCurrentUser().subscribe({
      next: (user) => {
        this.currentUser.next(user);
      },
      error: (err) => {
        console.log('Error: set current user')
        window.localStorage.removeItem('jwt');
        this.currentUser.next({id: 0, email: "", role: null });
      },
    });
  }

  getCurrentUser(): Observable<CurrentUser>{
    return this.http.get<CurrentUser>(environment.apiHost + 'auth/whoami');
  }

  logout(): void {
    this.router.navigate(['/']).then(_ => {
      window.localStorage.removeItem('jwt');
      this.currentUser.next({id: 0, email: "", role: null });
    }
    );
  }

  /*getByUserId(userId: number): Observable<SystemAdmin>{
    return this.http.get<SystemAdmin>(`http://localhost:8092/api/systemadmins/getbyuserid/${userId}`);
  }*/

  getByUserId(userId: number): Observable<SystemAdmin>{
    return this.http.get<SystemAdmin>(environment.apiHost + 'systemadmins/getbyuserid/' + userId);
  }

  
}
