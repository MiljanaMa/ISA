import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompanyProfile as Company } from '../../feature-modules/company/model/company-profile-model';
import { CompanyService } from '../../feature-modules/company/company.service';
import { CompanyEquipmentProfile } from '../../feature-modules/company/model/companyEquipmentProfile.model';
import { Appointment, AppointmentStatus } from '../model/appointment.model';
import { MatTableDataSource } from '@angular/material/table';
import { ReservationItem } from '../model/reservationCreation.model';
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
  public companyEquipmentDataSource = new MatTableDataSource<CompanyEquipmentProfile>();
  public appointmentsDataSource = new MatTableDataSource<Appointment>();
  public equipments: CompanyEquipmentProfile[] = [];
  public filteredEquipments: CompanyEquipmentProfile[] = [];
  public reservationItems: ReservationItem[] = [];
  public availableAppointments: Appointment[] = [];
  public inputSearch: string = '';
  public inputPrice: number = 0;
  public inputType: string = '';
  public minDate: Date = new Date();

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService,
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
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    var tomorrow = new Date(currentDate);
    tomorrow.setDate(currentDate.getDate() + 1);
    this.minDate = tomorrow;
    console.log(this.minDate);

    this.route.params.subscribe(params => {
      this.companyId = +params['id'];
      this.getCompanyDetails(this.companyId);
    });
  }

  getCompanyDetails(id: number): void {
    this.companyService.getCompanyById(id).subscribe(
      (data: Company) => {
        this.company = data;
        this.equipments = this.company?.companyEquipment || [];
        this.equipments = this.equipments.filter(e => e.count > 0);
        this.filteredEquipments = this.equipments;
        this.companyService.getAppointmentsByCompany(this.company.id).subscribe(
          (appointmentsData: Appointment[]) => {
            this.appointmentsDataSource.data = appointmentsData || [];

            this.appointmentsDataSource.data = this.appointmentsDataSource.data.filter(a => a.status === AppointmentStatus.AVAILABLE && new Date(a.date) >= this.minDate);
            this.availableAppointments = this.appointmentsDataSource.data.filter(appointment => {
              const appointmentDate = new Date(appointment.date);
              const today = new Date();
              today.setHours(0, 0, 0, 0);
              return appointmentDate > today;
            });
            this.appointmentsDataSource.data = this.appointmentsDataSource.data.sort((b, a) => new Date(b.date).getTime() - new Date(a.date).getTime());
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

  itemsUpdated(updatedItems: ReservationItem[]): void {
    this.reservationItems = updatedItems;
  }
  changeReservationMode(mode: boolean): void {
    this.reservationMode = mode;
  }

  isEquipmentInItems(targetId: number): boolean {
    return this.reservationItems.some(i => i.equipment.id === targetId);
  }


  addToReservation(equipment: CompanyEquipmentProfile): void {
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

  searchEquipments(): void {
    this.filteredEquipments = this.equipments.filter(
      equipment => equipment.price >= this.inputPrice
    );

    if (this.inputType !== '') {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.type === this.inputType
      );
    }

    if (this.inputSearch !== '') {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.name.toLowerCase().includes(this.inputSearch.toLowerCase())
      );
    }
  }

  clearSearch(): void {
    this.inputPrice = 0;
    this.inputType = '';
    this.inputSearch = '';
    this.filteredEquipments = this.equipments;
  }
  formatTime(time: any): String {
    if (!time || time.length !== 2) {
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
      return '';
    }
  
    const formattedDate = new Date(date).toLocaleDateString('sr-RS');
    return formattedDate;
  }

}