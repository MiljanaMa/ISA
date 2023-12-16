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
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { DateAdapter } from '@angular/material/core';
import { Time } from '@angular/common';

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
  addMode = false 
  minDate: Date; 

  public companyAdminsDataSource = new MatTableDataSource<CompanyAdmin>();
  public companyEquipmentDataSource = new MatTableDataSource<CompanyEquipment>();
  public appointmentsDataSource = new MatTableDataSource<Appointment>();

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService, 
    private dateAdapter: DateAdapter<Date> 
  ) {

    this.minDate = new Date(); // Initialize minDate with today's date
    this.dateAdapter.setLocale('en-US'); // Set your desired locale
  }


  adminsList: CompanyAdmin[] |undefined; 

  selectedAdmin: CompanyAdmin | null = null; 
  startTime: string | null = null; 
  endTime: string | null = null; 
  selectedDate: Date | null = null;

 
 

  onFormSubmit(): void {
    if(this.selectedDate && this.startTime && this.endTime && this.selectedAdmin){
     let appointment = {
     
        date: this.formatDateToYYYYMMDD(this.selectedDate), 
        startTime: this.startTime+":00", 
        endTime: this.endTime + ":00", 
        status: AppointmentStatus.Available, 
        companyAdmin: this.selectedAdmin

      }
      this.companyService.createAppointment(appointment).subscribe(
        (response) => {
          this.appointmentsDataSource.data.push(response);
          this.calendarOptions.events = []; 
          this.initializeCalendar();  
        },
        (error) => {  
          console.error("Error creating appointment:", error);
        }
      );
    }
    

    this.addMode = false; 
  }
  formatDateToYYYYMMDD(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // Adding 1 because months are zero-based
    const day = ('0' + date.getDate()).slice(-2);
  
    return `${year}-${month}-${day}`;
    
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.companyId = +params['id'];
      this.getCompanyDetails(this.companyId);
      
      this.calendarOptions = {
        plugins: [timeGridPlugin],
        initialView: 'timeGridDay', 
        headerToolbar: {
          left: 'prev,next today addButton',
          center: 'title',
          right: 'timeGridDay,timeGridWeek'
        },
        
        customButtons: {
          addButton: {
            text: 'Add Appointment',
            click: () => {
              this.onAddAppointmentClick(); 
            }
          }
        },
        slotDuration: '01:00:00', 
        allDaySlot: false,
       
      };
      
    });
  }




  onAddAppointmentClick(): void {
    this.addMode = !this.addMode; 
    this.adminsList = this.company?.companyAdmins; 
  }

  isFormValid(): boolean {
    const timeRegex = /^\d{2}:\d{2}$/; 
    const now = new Date(); 

    const isToday = this.selectedDate?.getDate() === now.getDate() &&
    this.selectedDate?.getMonth() === now.getMonth() &&
    this.selectedDate?.getFullYear() === now.getFullYear();

    return !!this.startTime?.match(timeRegex) && !!this.endTime?.match(timeRegex) && 
    this.startTime < this.endTime &&
    (!isToday || this.startTime > now.getHours()+':'+now.getMinutes()) && 
    this.selectedAdmin!=null && this.selectedDate!=null
    && this.inFreeSlots() &&this.inWorkingHours();
  }

  inWorkingHours(): boolean{
    
    if(this.startTime && this.endTime && this.company){
    
      let workingStart = this.company?.workingHours.split('-')[0]; 
      let workingEnd = this.company?.workingHours.split('-')[1]; 
      
      
      return this.startTime >= workingStart.padStart(5, '0') && this.endTime <= workingEnd.padStart(5, '0'); 
    }
    return false; 
  }

  inFreeSlots():boolean {
    if(this.startTime && this.endTime){
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
    if(this.selectedDate){
      let sDate = this.selectedDate; 
      this.appointmentsDataSource.data 
        .filter(a => a.date === this.formatDateToYYYYMMDD(sDate))
        .forEach((a) => {
          let currentTime = a.startTime;
    
          while (currentTime <= a.endTime) {
            timeSlots.push(currentTime);
            const [hours, minutes] = currentTime.split(':').map(Number);
            const date = new Date(2000, 0, 1, hours, minutes);
            date.setMinutes(date.getMinutes() + 1);
            currentTime = date.toLocaleTimeString('en-US', {
              hour: '2-digit',
              minute: '2-digit',
            });
          }
        });
    }
  
    return timeSlots;
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

        this.calendarOptions.businessHours =  {
        
          daysOfWeek: [0, 1, 2, 3, 4, 5, 6], 
          startTime: this.company?.workingHours.split('-')[0],
          endTime: this.company?.workingHours.split('-')[1],
          color: 'green', // Color to represent business hours
          textColor: 'black', // Text color for business hours
        },
        
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
      
      title: `Admin: ${appointment.companyAdmin?.firstName} ${appointment.companyAdmin?.lastName}`,
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