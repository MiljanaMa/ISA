import { Component, OnInit } from '@angular/core';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { Reservation } from '../model/reservation.model';
import { AuthService } from 'src/app/auth/auth.service';
import { ClientService } from '../client.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  currentUser?: CurrentUser;
  public userReservations: Reservation[] = [];
  public takenReservations: Reservation[] = [];
  public reservedReservations: Reservation[] = [];
  public sortType: string = 'DATE';
  public orderType: string = 'DESC';

  constructor(private authService: AuthService, private clientService: ClientService
  ) { }

  async ngOnInit(): Promise<void> {
    if (window.localStorage.getItem('jwt')) {
      await this.authService.setCurrentUser();
    }
    this.authService.currentUser.subscribe((user) => {
      if (user) {
        this.currentUser = user;
      }
    });
    this.getUserReservations();
  }

  getUserReservations(): void {
    this.clientService.getUserReservations().subscribe(
      (data: Reservation[]) => {
        this.userReservations = data;
        this.takenReservations = this.userReservations.filter(r => r.status === 'TAKEN');
        this.reservedReservations = this.userReservations.filter(r => r.status === 'RESERVED');
        this.onSortChange();
      });
  }
  formatTime(time: any): String {
    if (!time || time.length !== 2)
      return '';

    const [hours, minutes] = time;
    const formattedHours = this.padWithZero(hours);
    const formattedMinutes = this.padWithZero(minutes);

    return `${formattedHours}:${formattedMinutes}`;
  }
  private padWithZero(value: number | string): string {
    const stringValue = String(value);
    return stringValue.length === 1 ? '0' + stringValue : stringValue;
  }
  formatDate(date: any): string {
    if (!date)
      return '';
    return new Date(date).toLocaleDateString('sr-RS');
  }
  onSortChange(): void {
    if(this.sortType === 'DATE' && this.orderType === 'DESC')
      this.takenReservations = this.takenReservations.sort((b, a) => {
        const dateComparison = new Date(a.appointment.date).getTime() - new Date(b.appointment.date).getTime();
        if (dateComparison === 0)
          return this.compareHours(a, b);

      return dateComparison;
    });
    else if(this.sortType === 'DATE' && this.orderType === 'ASC')
      this.takenReservations = this.takenReservations.sort((b, a) => 
      {
        const dateComparison = new Date(b.appointment.date).getTime() - new Date(a.appointment.date).getTime();
        if (dateComparison === 0)
          return this.compareHours(b, a)
        
      return dateComparison;
    });
    else if(this.sortType === 'DURATION' && this.orderType === 'ASC')
      this.takenReservations = this.takenReservations.sort((b, a) => 
    this.calculateDuration(b.appointment.startTime, b.appointment.endTime) - this.calculateDuration(a.appointment.startTime, a.appointment.endTime));
    else if(this.sortType === 'DURATION' && this.orderType === 'DESC')
      this.takenReservations = this.takenReservations.sort((b, a) =>
    this.calculateDuration(a.appointment.startTime, a.appointment.endTime) - this.calculateDuration(b.appointment.startTime, b.appointment.endTime));
    //treba da se ubaci price u slucaju da neko izmjeni cijenu
    
    /*else if(this.sortType === 'PRICE' && this.orderType === 'DESC')
      this.takenReservations = this.takenReservations.sort((b, a) => a.price - b.averageRate);
    else if(this.sortType === 'PRICE' && this.orderType === 'ASC')
      this.takenReservations = this.takenReservations.sort((b, a) => b.averageRate - a.averageRate);*/
  }
  private compareHours(a: Reservation, b: Reservation) {
    if(a.appointment.startTime[0] > b.appointment.startTime[0])
      return 1;
    else if(a.appointment.startTime[0] === b.appointment.startTime[0])
      return this.compareMinutes(a, b)
    return -1;
  }

  private compareMinutes(a: Reservation, b: Reservation) {
    if(a.appointment.startTime[1] > b.appointment.startTime[1])
      return 1;
    else if(a.appointment.startTime[1] < b.appointment.startTime[1])
      return -1;
    return 0;
  }
  private calculateDuration(start: any, end: any): number {  
    return ((end[0] *60 + end[1]) - (start[0] *60 + start[1]));
  }
}
