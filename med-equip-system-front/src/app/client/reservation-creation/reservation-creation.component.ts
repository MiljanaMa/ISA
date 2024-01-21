import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ReservationItem } from '../model/reservationCreation.model';
import { FormControl, Validators } from '@angular/forms';
import { Appointment, AppointmentStatus, CustomAppointment } from '../model/appointment.model';
import { CompanyService } from '../../feature-modules/company/company.service';
import { DateAdapter } from '@angular/material/core';
import { CompanyProfile as Company } from '../../feature-modules/company/model/company-profile-model';
import { ClientService } from '../client.service';

@Component({
  selector: 'xp-reservation-creation',
  templateUrl: './reservation-creation.component.html',
  styleUrls: ['./reservation-creation.component.css']
})
export class ReservationCreationComponent {
  @Input() reservationItems: ReservationItem[] | undefined;
  @Input() availableAppointments: Appointment[] | undefined;
  @Input() company: Company | undefined;
  @Output() itemsUpdated = new EventEmitter<ReservationItem[]>();
  @Output() reservationModeChanged = new EventEmitter<boolean>();

  public customAppointments: CustomAppointment[] = [];
  public selectedOption = 'predefined';
  public selectedAppointment: string = '';
  public selectedCustomAppointment: string = '';
  public appointment: Appointment | undefined;

  public minDate: Date = new Date();
  selectedDate: Date | null = null;

  constructor(private clientService: ClientService, private dateAdapter: DateAdapter<Date>, private router: Router) {
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    var tomorrow = new Date(currentDate);
    tomorrow.setDate(currentDate.getDate() + 1);
    this.minDate = tomorrow;

    this.dateAdapter.setLocale('en-US');
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

    const formattedDate = new Date(date).toLocaleDateString('sr-RS');
    return formattedDate;
  }

  onCountChange(item: any): void {
    this.itemsUpdated.emit(this.reservationItems);
  }
  onDateChange(selectedDate: Date): void {
    const offset = selectedDate.getTimezoneOffset()
    selectedDate = new Date(selectedDate.getTime() - (offset * 60 * 1000))

    this.clientService.getCustomAppointments(selectedDate, this.company?.id || 0).subscribe(
      (data: CustomAppointment[]) => {
        this.customAppointments = data;
      },
      error => {
        console.log(error);
      }

    );

  }
  removeItem(item: ReservationItem): void {
    this.reservationItems = this.reservationItems?.filter(i => i.equipment.id !== item.equipment.id);
    this.itemsUpdated.emit(this.reservationItems);
  }
  addMoreItems(): void {
    this.reservationModeChanged.emit(false);
  }

  makeReservation(): void {
    if(this.reservationItems?.length === 0){
      alert("Choose some equipments to finish reservation");
      return;
    }
    if (this.selectedOption === 'predefined') {
      let appointment = this.availableAppointments?.find(a => a.id === Number(this.selectedAppointment));
      if (appointment === undefined) {
        alert("You should select some appointment");
        return;
      }
      let reservation = {
        reservationItems: this.reservationItems || [],
        appointment: appointment,
      };
      this.clientService.makePredefinedReservation(reservation).subscribe(
        (data) => {
          alert("You have succesfully made reservation");
          this.router.navigate(['/reservations']);
          console.log("Reservation creation works");
        },
        error => {
          alert(error.error);
          console.log(error);
        }
      );
      return;
    }
    let appointment = this.customAppointments?.find(a => a.id === Number(this.selectedCustomAppointment));
    if(appointment === undefined){
      alert("You should select some appointment");
      return;
    }
    let reservation = {
      reservationItems: this.reservationItems || [],
      appointment: appointment,
      companyId: this.company?.id || 0
    };
    //drugacije za pravljenje moga
    this.clientService.makeCustomReservation(reservation).subscribe(
      (data) => {
        alert("You have succesfully made reservation");
        this.router.navigate(['/reservations']);
      },
      error => {
        alert("There is not enough items in storage.");
      }

    );
  }
}
