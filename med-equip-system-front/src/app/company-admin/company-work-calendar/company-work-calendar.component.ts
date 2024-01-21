import { Component, OnInit, ViewChild } from '@angular/core';
import { ReservedAppointment } from '../model/reserved-appointment.model';
import { CompanyAdminService } from '../company-admin.service';
import { CalendarOptions, EventContentArg, EventInput } from '@fullcalendar/core';

import { FullCalendarComponent } from '@fullcalendar/angular';
import { MatTableDataSource } from '@angular/material/table';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { Appointment } from 'src/app/client/model/appointment.model';
import { BehaviorSubject } from 'rxjs';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { CompanyAdmin } from 'src/app/layout/model/companyAdmin.model';


@Component({
  selector: 'app-company-work-calendar',
  templateUrl: './company-work-calendar.component.html',
  styleUrls: ['./company-work-calendar.component.css']
})
export class CompanyWorkCalendarComponent implements OnInit{

  public currentUser = new BehaviorSubject<CurrentUser>({id: 0, email: "", role: null });
  public companyAdmin: CompanyAdmin | undefined;
  reservedAppointments: ReservedAppointment[] = [];
  calendarOptions: CalendarOptions = {
  };

  public appointmentsDataSource = new MatTableDataSource<ReservedAppointment>();
  public AllAppointmentsDataSource = new MatTableDataSource<Appointment>();

  public isSelected: boolean = false; 
  public dayInterval: number = 1;
  public weekInterval: number = 1; 
  public monthInterval: number = 1; 

  public companyId: number | undefined;
  public currentUserId: number | undefined;

  public companyIdZakucano = 1; // trenutno resenje, postoji kod gde metode cekaju da se ucita id kompanije(prvenstveno usera i companyAdmina, mora na drugi nacin)

  private selectedDate: Date | undefined; 

  constructor(private service: CompanyAdminService){

  }

  // ***********************NE BRISI KOMENTARE, FUNKCIONALAN KOD!!!
  /*async ngOnInit(): Promise<void> {        
    await this.getCurrentUserId();
    await this.loadReservedAppointments();
    await this.getAllAppointmentsByCompany();
    this.getCalendarOptions();
  }*/
  
  async ngOnInit(): Promise<void> {
    this.getCurrentUserId();
    //this.getCompanyAdmin(this.currentUserId);
    this.loadReservedAppointments();
    this.getAllAppointmentsByCompany();
    this.getCalendarOptions();

  }
  
  getCalendarOptions(): void{
    
    this.calendarOptions = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      initialView: 'dayGridMonth', 
      headerToolbar: {
        left: 'prev,next,today',
        center: 'title',
        right: 'timeGridDay,timeGridWeek,dayGridMonth,timeGridMonth,timeGridYear'
      },
      
      slotDuration: '00:15:00', 
      allDaySlot: false,
      eventContent: this.customEventContent.bind(this) 
    };
  }

    // ***********************NE BRISI KOMENTARE, FUNKCIONALAN KOD!!!
    
 /* async getCurrentUserId(): Promise<void> {
    try {
      const user = await this.service.getCurrentUser().toPromise();
      if(user)
      this.currentUser.next(user);
      this.currentUserId = user?.id;
      console.log("current user ://////// ", this.currentUser);
      if (user &&user.id > 0) {
        await this.getCompanyAdmin(user.id);
      }
    } catch (error) {
      console.log('Error getting current user:', error);
    }
  }
  
  async getCompanyAdmin(userId: number): Promise<void> {
    try {
      const companyAdmin = await this.service.getCompanyAdminByUserId(userId).toPromise();
      this.companyAdmin = companyAdmin;
      if (companyAdmin?.companyId == 0 || companyAdmin?.companyId === undefined) {
        this.companyId = 1;
      } else {
        this.companyId = companyAdmin.companyId;
      }
    } catch (error) {
      console.error('Error fetching company admin:', error);
    }
  }*/

  async getCurrentUserId(): Promise<void>{
    await this.service.getCurrentUser().subscribe({
      next: (user) => {
        this.currentUser.next(user);
        this.currentUserId = user.id;
        console.log("current user ://////// ", this.currentUser);
        if(user.id > 0){
          this.getCompanyAdmin(user.id);
        }
      },
      error: (err) => {
        console.log('Error getting current user')
      }
    });
  }

  async getCompanyAdmin(userId: number): Promise<void>{
    await this.service.getCompanyAdminByUserId(userId).subscribe({
      next: (companyAdmin: CompanyAdmin ) => {
        this.companyAdmin = companyAdmin;
        if(companyAdmin.companyId == 0 || undefined){
          this.companyId = 1;   //TODO: popraviti bag sa dobavljanjem kompanije kod compAdmina jer ne nadje kompaniju
        } else{
          this.companyId = companyAdmin.companyId;
        }
      },
      error: (error) => {
        console.error('Error fetching company admin:', error);
      }
    });
  }

  async loadReservedAppointments(): Promise<void> {
    //const companyId = 1; 
    //get CompanyId from Admin
    await this.service.getReservedAppointments(this.companyIdZakucano || 0).subscribe(
      (appointmentsData: ReservedAppointment[]) => {
        this.appointmentsDataSource.data = appointmentsData || [];
        this.calendarOptions.events = [];
        this.initializeCalendar();
      },
      (error) => {
        console.error('Error fetching reserved appointments:', error);
      }
    );
  }

  convertToDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }
  
  initializeCalendar(): void {
    this.calendarOptions.events = this.getReservedAppointmentsAsEvents(); 
    console.log(this.calendarOptions.events); 
    
  }

  getReservedAppointmentsAsEvents(): EventInput[] {
    const reservedEvents = this.appointmentsDataSource.data.map((appointment) => ({
      start: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.startTime[0].toString().padStart(2, '0') + ":" + appointment.startTime[1].toString().padStart(2, '0') + ":00",
      end: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.endTime[0].toString().padStart(2, '0') + ":" + appointment.endTime[1].toString().padStart(2, '0') + ":00",
      backgroundColor: '#b7e868', 
      borderColor: '#b7e868', 
      extendedProps: {
        clientFirstName: appointment.clientFirstName,
        clientLastName: appointment.clientLastName,
        adminFirstName: appointment.adminFirstName,
        adminLastName: appointment.adminLastName,
        startTime: appointment.startTime,
        endTime: appointment.endTime
      }
    }));
  
    const allAppointmentsEvents = this.AllAppointmentsDataSource.data.map((appointment) => ({
      start: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.startTime[0].toString().padStart(2, '0') + ":" + appointment.startTime[1].toString().padStart(2, '0') + ":00",
      end: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.endTime[0].toString().padStart(2, '0') + ":" + appointment.endTime[1].toString().padStart(2, '0') + ":00",
      backgroundColor: 'lightblue', 
      borderColor: 'lightblue', 
      extendedProps: {
        startTime: appointment.startTime,
        endTime: appointment.endTime
      },
      classNames: ['green-event']

    }));
  
    return [...reservedEvents, ...allAppointmentsEvents];
  }
  
  customEventContent(arg: EventContentArg): { domNodes: HTMLElement[] } {
    const container = document.createElement('div');
    container.classList.add('fc-custom-event');
    container.style.backgroundColor = arg.event.backgroundColor || 'lightblue'; 
    container.style.width = '100%';
  
    const title = document.createElement('div');
    title.classList.add('fc-title');
    title.innerText = arg.event.title;
  
    const details = document.createElement('div');
    details.classList.add('fc-details');
  
    if (arg.event.backgroundColor === 'lightblue') {
      details.innerHTML = `
        <strong>Start:</strong> ${arg.event.extendedProps['startTime']}<br>
        <strong>End:</strong> ${arg.event.extendedProps['endTime']}<br>
      `;
    } else {
      details.innerHTML = `
        <strong>Start:</strong> ${arg.event.extendedProps['startTime']}<br>
        <strong>End:</strong> ${arg.event.extendedProps['endTime']}<br>
        <strong>Client:</strong> ${arg.event.extendedProps['clientFirstName']} ${arg.event.extendedProps['clientLastName']}<br>
        <strong>Admin:</strong> ${arg.event.extendedProps['adminFirstName']} ${arg.event.extendedProps['adminLastName']}
      `;
    }
  
    container.appendChild(title);
    container.appendChild(details);
  
    return { domNodes: [container] };
  }
  
  getDateAsString(date: Date): string{
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2); 
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  async getAllAppointmentsByCompany(): Promise<void> {
    await this.service.getNotReservedAppointmentsByCompany(this.companyIdZakucano || 0).subscribe(
      (appointmentsData: Appointment[]) => {
        this.AllAppointmentsDataSource.data = appointmentsData || [];
        this.initializeCalendar();
      },
      (appointmentError) => {
        console.log('Error fetching not reserved appointments', appointmentError);
      }
    );
  }
  
}
