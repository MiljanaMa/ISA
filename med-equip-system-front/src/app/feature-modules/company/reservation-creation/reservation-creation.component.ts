import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationItem } from '../model/reservationCreation.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Appointment, AppointmentStatus, CustomAppointment } from '../model/appointment.model';
import { CompanyService } from '../company.service';
import { DateAdapter } from '@angular/material/core';
import { CompanyProfile as Company } from '../model/company-profile-model';

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
  public showForm: boolean = false;
  //appointmentForm: FormGroup;
  public minDate: Date = new Date();
  formBuilder: any;
  startTime: string | null = null;
  endTime: string | null = null;
  selectedDate: Date | null = null;

  constructor(private companyService: CompanyService, private dateAdapter: DateAdapter<Date>, private router: Router) {
    this.minDate = new Date(); // Initialize minDate with today's date
    this.dateAdapter.setLocale('en-US'); // Set your desired locale
  }
  onFormSubmit(): void {
    if (this.selectedDate && this.startTime && this.endTime) {
      let appointment = {

        date: this.formatDateToYYYYMMDD(this.selectedDate),
        startTime: this.startTime + ":00",
        endTime: this.endTime + ":00",
        status: AppointmentStatus.AVAILABLE,
        companyAdmin: undefined //provjeri da li ovo utice

      }
    }
  }

  formatDateToYYYYMMDD(date: Date): Date {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // Adding 1 because months are zero-based
    const day = ('0' + date.getDate()).slice(-2);

    return new Date(`${year}-${month}-${day}`);

  }
  isFormValid(): boolean {
    const timeRegex = /^\d{2}:\d{2}$/;
    const now = new Date();

    const isToday = this.selectedDate?.getDate() === now.getDate() &&
      this.selectedDate?.getMonth() === now.getMonth() &&
      this.selectedDate?.getFullYear() === now.getFullYear();

    return !!this.startTime?.match(timeRegex) && !!this.endTime?.match(timeRegex) &&
      this.startTime < this.endTime &&
      (!isToday || this.startTime > now.getHours() + ':' + now.getMinutes()) && this.selectedDate != null
      && this.inFreeSlots() && this.inWorkingHours();
  }

  inFreeSlots(): boolean {
    if (this.startTime && this.endTime) {
      let allTimeSlots = this.generateTakenTimeSlots();
      let requiredTimeSlots = this.generateTimeSlotsInRange(this.startTime, this.endTime);

      return (requiredTimeSlots.filter(slot => allTimeSlots.includes(slot))).length === 0

    }
    return false
  }
  generateTimeSlotsInRange(start: string, end: string): string[] {
    const timeSlots: string[] = [];
    let currentTime = start;

    while (currentTime <= end) {
      timeSlots.push(currentTime);
      const [hours, minutes] = currentTime.split(':').map(Number);
      const date = new Date(2000, 0, 1, hours, minutes);
      date.setMinutes(date.getMinutes() + 1);
      currentTime = date.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit',
      });
    }

    return timeSlots;
  }

  generateTakenTimeSlots(): string[] {
    const timeSlots: string[] = [];
    if (this.selectedDate) {
      let sDate = this.selectedDate;
    }

    return timeSlots;
  }

  inWorkingHours(): boolean {

    if (this.startTime && this.endTime && this.company) {

      let workingStart = this.company?.workingHours.split('-')[0];
      let workingEnd = this.company?.workingHours.split('-')[1];


      return this.startTime >= workingStart.padStart(5, '0') && this.endTime <= workingEnd.padStart(5, '0');
    }
    return false;
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
  onDateChange(selectedDate: Date): void {
    const offset = selectedDate.getTimezoneOffset()
    selectedDate = new Date(selectedDate.getTime() - (offset * 60 * 1000))

    this.companyService.getCustomAppointments(selectedDate, this.company?.id || 0).subscribe(
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
    // Emit the event when the count is changed
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
      this.companyService.makePredefinedReservation(reservation).subscribe(
        (data) => {
          alert("You have succesfully made reservation");
          this.router.navigate(['/reservations']);
        },
      );
      return;
    }
    let emptyAppointment = {
      id: 0,
      date: new Date(), // You might need to handle LocalDate format accordingly
      startTime: (new Date()).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: false }), // LocalTime might need formatting as well
      endTime: (new Date()).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: false }), // LocalTime might need formatting as well
      status: AppointmentStatus.AVAILABLE
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
    this.companyService.makeCustomReservation(reservation).subscribe(
      (data) => {
        alert("You have succesfully made reservation");
        this.router.navigate(['/reservations']);
      },
      error => {
        console.log(error);
      }

    );
  }
}
