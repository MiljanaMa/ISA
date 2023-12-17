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
          });
  }
  formatTime(time: any): String {
    if (!time || time.length !== 2) {
      // Handle invalid or unexpected input
      return '';
    }

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
    if (!date) {
      // Handle invalid or unexpected input
      return '';
    }
  
    const formattedDate = new Date(date).toLocaleDateString('sr-RS'); // Adjust the locale as needed
    return formattedDate;
  }
}
