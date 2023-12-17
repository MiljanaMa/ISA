import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationItem } from '../model/reservationCreation.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Appointment, AppointmentStatus } from '../model/appointment.model'; 
import { CompanyService } from '../company.service';

@Component({
  selector: 'xp-reservation-creation',
  templateUrl: './reservation-creation.component.html',
  styleUrls: ['./reservation-creation.component.css']
})
export class ReservationCreationComponent {
  @Input() reservationItems : ReservationItem[] | undefined;
  @Input() availableAppointments : Appointment[] | undefined;
  @Output() itemsUpdated = new EventEmitter<ReservationItem[]>();
  @Output() reservationModeChanged = new EventEmitter<boolean>();
  public selectedOption = 'predefined';
  public selectedAppointment : string = '';
  public appointment: Appointment | undefined;
  //appointmentForm: FormGroup;
  public minDate : Date = new Date();
  formBuilder: any;
  durationOptions = [15, 20, 25, 30, 35, 40, 45, 50, 55, 60];

  constructor(private companyService: CompanyService) {
    //const today = new Date();
    //this.minDate = today.toISOString().split('T')[0];

    // Initialize the form with validators
    /*this.appointmentForm = this.formBuilder.group({
      appointmentDate: [this.appointment?.date, [Validators.required, this.validateDate]],
      startTime: [this.appointment?.startTime, Validators.required],
      duration: [this.appointment?.duration, Validators.required]
    });*/
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
  
  createFormControl(value: number): FormControl {
    return new FormControl(value, [
      Validators.required,
      Validators.min(1), // Ensure count is not negative
      Validators.pattern(/^\d+$/), // Ensure count is a positive integer
    ]);
  }

  onCountChange(item: any): void {
    // Emit the event when the count is changed
    this.itemsUpdated.emit(this.reservationItems);
  }
  removeItem(item: ReservationItem): void {
    this.reservationItems = this.reservationItems?.filter(i => i===item);
    this.itemsUpdated.emit(this.reservationItems);
  }
  addMoreItems(): void {
    // Emit the event when the count is changed
    this.reservationModeChanged.emit(false);
  }

  makeReservation():void {
    let emptyAppointment = {
      id: 0,
      date: new Date(), // You might need to handle LocalDate format accordingly
      startTime: (new Date()).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: false }), // LocalTime might need formatting as well
      endTime: (new Date()).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: false }), // LocalTime might need formatting as well
      status: AppointmentStatus.AVAILABLE
    }
    let appointment = this.availableAppointments?.find(a => a.id === Number(this.selectedAppointment));
    let reservation = {
        reservationItems: this.reservationItems || [],
        appointment:  appointment || emptyAppointment,
    };
    //sredi undefined appointment
    this.companyService.makeReservation(reservation).subscribe(
        () => {
          //dodaj da ti ispise da si uspjesno dodao-toast
        }, 
        error => {
          console.log(error); 
        }

      );
  }
}
