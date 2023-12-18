import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from './model/client.model';
import { Observable } from 'rxjs';
import { Reservation } from './model/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient) { }

  getCurrentClient(): Observable<Client>{
    return this.http.get<Client>(`http://localhost:8092/api/clients/getCurrent`)
  }

  updateClient(client: Client): Observable<Client>{
    return this.http.post<Client>(`http://localhost:8092/api/clients/update`, client);
  }

  updatePassword(password: string): Observable<Client>{
    return this.http.post<Client>(`http://localhost:8092/api/clients/updatePassword`, password);
  }
  getUserReservations(): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`http://localhost:8092/api/reservations/user`)
  }

  
}
