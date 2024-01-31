import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { Reservation } from 'src/app/client/model/reservation.model';
import { CompanyAdminService } from '../company-admin.service';

@Component({
  selector: 'app-admins-reservations',
  templateUrl: './admins-reservations.component.html',
  styleUrls: ['./admins-reservations.component.css']
})
export class AdminsReservationsComponent implements OnInit {
  currentUser?: CurrentUser;
  public allAdminsReservations: Reservation[] = [];
  public filteredReservations: Reservation[] = [];
  public filterType: string = 'ANY';

  constructor(private authService: AuthService, private companyAdminService: CompanyAdminService
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
    this.getAdminsReservations();
  }

  getAdminsReservations(): void {
    this.companyAdminService.getAdminsReservations().subscribe(
      (data: Reservation[]) => {
        this.allAdminsReservations = data;
        this.onFilterChange();
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

  onFilterChange(): void{
    if(this.filterType === 'ANY')
      this.filteredReservations = this.allAdminsReservations;
    else if(this.filterType === 'TAKING_REQUESTED')
      this.filteredReservations = this.allAdminsReservations.filter( r => r.status === 'TAKING_REQUESTED');
    else if(this.filterType === 'TAKEN')
      this.filteredReservations = this.allAdminsReservations.filter( r => r.status === 'TAKEN');
    else if(this.filterType === 'RESERVED')
      this.filteredReservations = this.allAdminsReservations.filter( r => r.status === 'RESERVED');
    else if(this.filterType === 'EXPIRED')
      this.filteredReservations = this.allAdminsReservations.filter( r => r.status === 'EXPIRED');
    else
      this.filteredReservations = this.allAdminsReservations.filter( r => r.status === 'CANCELLED');
  }

  giveReservation(reservation: Reservation): void {
    this.companyAdminService.giveReservation(reservation.id).subscribe(
      (data: { message: string }) => {
        console.log(data.message);
        alert(data.message);
      },
      error => {
        console.error('Error taking reservation:', error);
      }
    );
  }

}
