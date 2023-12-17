import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompanyProfile as Company } from '../model/company-profile-model';
import { CompanyService } from '../company.service';
import { CompanyAdmin } from 'src/app/layout/model/companyAdmin.model';
import { CompanyEquipment } from '../model/companyEquipment.model';
import { Appointment, AppointmentStatus } from '../model/appointment.model';
import { Location } from 'src/app/layout/model/location.model';
import { MatTableDataSource } from '@angular/material/table';
import { ReservationItem } from '../model/reservationCreation.model';
import { DatePipe } from '@angular/common';
import { AuthService } from 'src/app/auth/auth.service';
import { CurrentUser } from 'src/app/auth/model/current-user.model';

@Component({
  selector: 'app-company-profile',
  templateUrl: './company-profile.component.html',
  styleUrls: ['./company-profile.component.css']
})



export class CompanyProfileComponent implements OnInit {
  companyId?: number;
  company: Company | undefined;
  oldCompany: Company | undefined;
  currentUser?: CurrentUser;

  reservationMode = false;

  public companyAdminsDataSource = new MatTableDataSource<CompanyAdmin>();
  public companyEquipmentDataSource = new MatTableDataSource<CompanyEquipment>();
  public appointmentsDataSource = new MatTableDataSource<Appointment>();
  public equipments: CompanyEquipment[] = [];
  public reservationItems: ReservationItem[] = [];
  public availableAppointments: Appointment[] = [];




  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService, private datePipe: DatePipe,
    private authService: AuthService
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

    this.route.params.subscribe(params => {
      this.companyId = +params['id'];
      this.getCompanyDetails(this.companyId);
    });
  }

  getCompanyDetails(id: number): void {
    this.companyService.getCompanyById(id).subscribe(
      (data: Company) => {
        this.company = data;
        console.log(data);
        this.companyAdminsDataSource.data = this.company?.companyAdmins || [];
        this.equipments = this.company?.companyEquipment || [];
        this.companyService.getAppointmentsByCompany(this.company.id).subscribe(
          (appointmentsData: Appointment[]) => {
            this.appointmentsDataSource.data = appointmentsData || [];

            this.appointmentsDataSource.data = this.appointmentsDataSource.data.filter(a => a.status === AppointmentStatus.AVAILABLE);
            this.availableAppointments = this.appointmentsDataSource.data;
          },
          appointmentError => {
            console.log('Error fetching appointments', appointmentError);
          }
        );
      },
      error => {
        console.error('Error fetching company details:', error);

      }
    );
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
  onLocationUpdated(updatedLocation: Location): void {

    this.company!.location = updatedLocation;

  }
  itemsUpdated(updatedItems: ReservationItem[]): void {
    this.reservationItems = updatedItems;
  }
  changeReservationMode(mode: boolean): void {
    this.reservationMode = mode;
  }

  isEquipmentInItems(targetId: number): boolean {
    return this.reservationItems.some(i => i.equipment.id === targetId);
  }

  addToReservation(equipment: CompanyEquipment): void {
    if (this.isEquipmentInItems(equipment.id)) {
      alert("This equipment is already in reservation");
      return;
    }
    let reservationItem = {
      id: 0,
      count: 1,
      equipment: equipment,
    };
    this.reservationItems.push(reservationItem);
    alert("Equipment added to reservation");
  }

}