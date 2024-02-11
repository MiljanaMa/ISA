import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client, Passwords } from './model/client.model';
import { Observable } from 'rxjs';
import { QRCode, Reservation } from './model/reservation.model';
import { CustomReservation, ReservationCreation } from './model/reservationCreation.model';
import { CustomAppointment } from './model/appointment.model';
import { environment } from 'src/env/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient) { }

  //profile
  getCurrentClient(): Observable<Client>{
    return this.http.get<Client>(environment.apiHost +`clients/getCurrent`)
  }

  updateClient(client: Client): Observable<Client>{
    return this.http.post<Client>(environment.apiHost +`clients/update`, client);
  }

  updatePassword(passwords: Passwords): Observable<Client>{
    return this.http.post<Client>(environment.apiHost + `clients/updatePassword`, passwords);
  }
  //reservations
  getUserReservations(): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(environment.apiHost +`reservations/user`)
  }
  makePredefinedReservation(reservation: ReservationCreation): Observable<string>{
    return this.http.post<string>(environment.apiHost +`reservations/create/predefined`, reservation);

  }
  makeCustomReservation(reservation: CustomReservation): Observable<string>{
    return this.http.post<string>(environment.apiHost +`reservations/create/custom`, reservation);

  }
  getCustomAppointments(date: Date, companyId: number): Observable<CustomAppointment[]>{
    let params = new HttpParams().set('date', date.toISOString().split('T')[0]).set('companyId', companyId || '');

    return this.http.get<CustomAppointment[]>(environment.apiHost +`appointments/custom`, {params});
  }
  getQrCodes(): Observable<QRCode[]>{
    return this.http.get<QRCode[]>(environment.apiHost +`reservations/qrCodes`)
  }

  cancelReservation(reservation: Reservation): Observable<string>{
    return this.http.post<string> (environment.apiHost+ `reservations/cancel`, reservation);
  }

  uploadQRCode(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    return this.http.post(environment.apiHost +`reservations/uploadQRCode`, formData, { headers });
  }

  requestTaking(reservationId: number): Observable<any> {
    return this.http.post(environment.apiHost +`reservations/requesttaking`, reservationId );
  }
  
}
