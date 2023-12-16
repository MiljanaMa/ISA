import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompanyProfile as Company }  from '../model/company-profile-model';
import { CompanyService } from '../company.service';
import { CompanyAdmin } from 'src/app/layout/model/companyAdmin.model';
import { CompanyEquipment } from '../model/companyEquipment.model';
import { Appointment, AppointmentStatus } from '../model/appointment.model';
import { Location } from 'src/app/layout/model/location.model';
import { MatTableDataSource } from '@angular/material/table';
import timeGridPlugin from '@fullcalendar/timegrid';
import { CalendarOptions, EventInput} from '@fullcalendar/core'; 

@Component({
  selector: 'app-company-profile',
  templateUrl: './company-profile.component.html',
  styleUrls: ['./company-profile.component.css']
})



export class CompanyProfileComponent implements OnInit {
  companyId?: number;
  company: Company|undefined;
  oldCompany: Company|undefined; 

 
  calendarOptions: CalendarOptions = {}; 

  editMode = false; 

  public companyAdminsDataSource = new MatTableDataSource<CompanyAdmin>();
  public companyEquipmentDataSource = new MatTableDataSource<CompanyEquipment>();
  public appointmentsDataSource = new MatTableDataSource<Appointment>();

  
 


  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.companyId = +params['id'];
      this.getCompanyDetails(this.companyId);

      this.calendarOptions = {
        plugins: [timeGridPlugin],
        initialView: 'timeGridDay', 
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'timeGridDay,timeGridWeek'
        },
        slotDuration: '01:00:00', 
        allDaySlot: false,
       
      };
      
    });
  }
  getCompanyDetails(id: number): void {
    this.companyService.getCompanyById(id).subscribe(
      (data: Company) => {
        this.company = data;
        console.log(data); 
        this.companyAdminsDataSource.data = this.company?.companyAdmins || [];
        console.log(this.company?.companyEquipment);
        this.companyEquipmentDataSource.data = this.company?.companyEquipment || [];
        console.log(this.companyEquipmentDataSource.data); 
        
        this.companyService.getAppointmentsByCompany(this.company.id).subscribe(
          (appointmentsData : Appointment[] ) => {
            this.appointmentsDataSource.data = appointmentsData || [];
            this.initializeCalendar(); 
        
            
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

  initializeCalendar(): void {

    this.calendarOptions.events = this.getAppointmentsAsEvents(); 
  }
  
  
  getAppointmentsAsEvents(): EventInput[] {
    console.log(this.appointmentsDataSource.data); 
    
    return this.appointmentsDataSource.data.map((appointment) => ({
      
      title: `Admin: ${appointment.companyAdmin.firstName} ${appointment.companyAdmin.lastName}`,
      start: appointment.date + 'T' + appointment.startTime,
      end: appointment.date + 'T' + appointment.endTime,
      color: appointment.status === AppointmentStatus.Reserved ? 'red': 'green'
    
    }));
   
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

  }