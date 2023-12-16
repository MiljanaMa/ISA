import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompanyProfile as Company }  from '../model/company-profile-model';
import { CompanyService } from '../company.service';
import { CompanyAdmin } from 'src/app/layout/model/companyAdmin.model';
import { CompanyEquipment } from '../model/companyEquipment.model';
import { Appointment, AppointmentStatus } from '../model/appointment.model';
import { Location } from 'src/app/layout/model/location.model';
import { MatTableDataSource } from '@angular/material/table';
import { ReservationItem } from '../model/reservationCreation.model';

@Component({
  selector: 'app-company-profile',
  templateUrl: './company-profile.component.html',
  styleUrls: ['./company-profile.component.css']
})



export class CompanyProfileComponent implements OnInit {
  companyId?: number;
  company: Company|undefined;
  oldCompany: Company|undefined;

 

  editMode = false;
  reservationMode = false; 

  public companyAdminsDataSource = new MatTableDataSource<CompanyAdmin>();
  public companyEquipmentDataSource = new MatTableDataSource<CompanyEquipment>();
  public appointmentsDataSource = new MatTableDataSource<Appointment>();
  public equipments: CompanyEquipment[] = [];
  public reservationItems: ReservationItem[] = [];
  public availableAppointments: Appointment[] = [];
 
 


  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService
  ) {}

  ngOnInit(): void {
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
        this.appointmentsDataSource.data = this.company?.appointments || [];
        let appointments = this.company.appointments || [];
        this.availableAppointments = appointments.filter(a => a.status === AppointmentStatus.AVAILABLE);
      },
      error => {
        console.error('Error fetching company details:', error);
       
      }
    );
  }
  toggleEditMode() {
  
    this.editMode = !this.editMode;
    
  }

  saveChanges():void {
    if(this.company){
      this.companyService.updateCompany(this.company).subscribe(
        () => {this.toggleEditMode();}, 
        error => {
          console.log(error); 
        }

      );  
    
      }
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

  addToReservation(equipment: CompanyEquipment):void {
    if(this.isEquipmentInItems(equipment.id)){
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