import { Component, OnInit } from '@angular/core';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { QRCode, Reservation } from '../model/reservation.model';
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
  public expiredReservations: Reservation[] = [];
  public qrCodes: QRCode[] = [];
  public filteredQrCodes: QRCode[] = [];
  public sortType: string = 'DATE';
  public orderType: string = 'DESC';
  public filterType: string = 'ANY';
  public uploadedReservationId: number | null = null;
  uploadedImage: string | undefined;


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
    this.getQrCodes();
  }

  getUserReservations(): void {
    this.clientService.getUserReservations().subscribe(
      (data: Reservation[]) => {
        this.userReservations = data;
        this.takenReservations = this.userReservations.filter(r => r.status === 'TAKEN');
        this.reservedReservations = this.userReservations.filter(r => r.status === 'RESERVED');
        this.expiredReservations = this.userReservations.filter(r => r.status === 'EXPIRED');
        this.onSortChange();
      });
  }
  getQrCodes(): void {
    this.clientService.getQrCodes().subscribe(
      (data: QRCode[]) => {
        this.qrCodes = data;
        this.filteredQrCodes = data;
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
    else if(this.sortType === 'PRICE' && this.orderType === 'ASC')
      this.takenReservations = this.takenReservations.sort((b, a) => this.calculatePrice(b) - this.calculatePrice(a));
    else if(this.sortType === 'PRICE' && this.orderType === 'DESC')
      this.takenReservations = this.takenReservations.sort((b, a) => this.calculatePrice(a) - this.calculatePrice(b));
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
  private calculatePrice(r: Reservation): number {  
    let total = r.reservationItems.reduce((accumulator, currentValue) => accumulator + currentValue.price, 0);
    return total;
  }
  getQRCodeDataURL(qrCode: QRCode): string {
    return `data:image/png;base64,${qrCode.qrCode}`;
  }
  onFilterChange(): void{
    if(this.filterType === 'ANY')
      this.filteredQrCodes = this.qrCodes;
    else if(this.filterType === 'TAKEN')
      this.filteredQrCodes = this.qrCodes.filter( q => q.status === 'TAKEN');
    else if(this.filterType === 'RESERVED')
      this.filteredQrCodes = this.qrCodes.filter( q => q.status === 'RESERVED');
    else if(this.filterType === 'EXPIRED')
      this.filteredQrCodes = this.qrCodes.filter( q => q.status === 'EXPIRED');
    else
      this.filteredQrCodes = this.qrCodes.filter( q => q.status === 'CANCELLED');
  }

  /*onFileChange(event: any): void {
    const file = event.target.files[0];
    this.clientService.uploadQRCode(file).subscribe(
      (data: { message: string, reservationId: number }) => {
        console.log(data.message);
        this.uploadedReservationId = data.reservationId;
        alert(data.message);
      },
      error => {
        console.error('Error uploading QR code:', error);
        alert('Error uploading QR code.');
      }
    );
  }*/

  onFileChange(event: any): void {
    const file = event.target.files[0];
    
    if (file) {
      this.readAndConvertImage(file).then(base64Image => {
        this.uploadedImage = 'data:image/png;base64,' + base64Image;
        
        this.clientService.uploadQRCode(file).subscribe(
          (data: { message: string, reservationId: number }) => {
            console.log(data.message);
            this.uploadedReservationId = data.reservationId;
          },
          error => {
            console.error('Error uploading QR code:', error);
          }
        );
      });
    }
  }
  private readAndConvertImage(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
  
      reader.onloadend = () => {
        const base64String = reader.result?.toString().split(',')[1] || '';
        resolve(base64String);
      };
  
      reader.onerror = (error) => {
        reject(error);
      };
  
      reader.readAsDataURL(file);
    });
  }
  

  takeReservation(): void {
    if (this.uploadedReservationId) {
      this.clientService.takeReservation(this.uploadedReservationId).subscribe(
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

}
