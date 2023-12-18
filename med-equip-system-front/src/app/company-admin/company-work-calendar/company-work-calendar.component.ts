import { Component, OnInit, ViewChild } from '@angular/core';
import { ReservedAppointment } from '../model/reserved-appointment.model';
import { CompanyAdminService } from '../company-admin.service';
import { CalendarOptions, EventContentArg, EventInput } from '@fullcalendar/core';

import { FullCalendarComponent } from '@fullcalendar/angular';
import { MatTableDataSource } from '@angular/material/table';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { Appointment } from 'src/app/feature-modules/company/model/appointment.model';


@Component({
  selector: 'app-company-work-calendar',
  templateUrl: './company-work-calendar.component.html',
  styleUrls: ['./company-work-calendar.component.css']
})
export class CompanyWorkCalendarComponent implements OnInit{

  reservedAppointments: ReservedAppointment[] = [];
  calendarOptions: CalendarOptions = {
  };

  public appointmentsDataSource = new MatTableDataSource<ReservedAppointment>();
  public AllAppointmentsDataSource = new MatTableDataSource<Appointment>();

  public isSelected: boolean = false; 
  public dayInterval: number = 1;
  public weekInterval: number = 1; 
  public monthInterval: number = 1; 

  public companyId: number = 1;

  private selectedDate: Date | undefined; 

  constructor(private service: CompanyAdminService){

  }

  ngOnInit(): void {
    this.loadReservedAppointments();
    this.getAllAppointmentsByCompany();

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


  loadReservedAppointments() {
    //const companyId = 1; 
    //get CompanyId from Admin
    this.service.getReservedAppointments(this.companyId).subscribe(
      (appointmentsData: ReservedAppointment[]) => {
        this.appointmentsDataSource.data = appointmentsData || [];
        this.calendarOptions.events = [];
        this.initializeCalendar();
        //this.reservedAppointments = data;
        //console.log('Reserved Appointments:', this.reservedAppointments);
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

  /*getReservedAppointmentsAsEvents(): EventInput[] {
    return this.appointmentsDataSource.data.map((appointment) => ({
      start: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.startTime[0].toString().padStart(2, '0') + ":" + appointment.startTime[1].toString().padStart(2, '0') + ":00",
      end: this.getDateAsString(new Date(appointment.date)) + 'T' + appointment.endTime[0].toString().padStart(2, '0') + ":" + appointment.endTime[1].toString().padStart(2, '0') + ":00",
      //title: `Client: ${appointment.clientFirstName} ${appointment.clientLastName}`,
      backgroundColor: '#6f4dd5', 
      borderColor: '#6f4dd5', 
      extendedProps: {
        clientFirstName: appointment.clientFirstName,
        clientLastName: appointment.clientLastName,
        adminFirstName: appointment.adminFirstName,
        adminLastName: appointment.adminLastName,
        startTime: appointment.startTime,
        endTime: appointment.endTime
      }
    }));
  }*/

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
  

  
  /*customEventContent(arg: EventContentArg): { domNodes: HTMLElement[] } {
    const container = document.createElement('div');
    container.classList.add('fc-custom-event');
    container.style.backgroundColor = 'lightblue'; // Set background color to purple
  
    const title = document.createElement('div');
    title.classList.add('fc-title');
    title.innerText = arg.event.title;
  
    const details = document.createElement('div');
    details.classList.add('fc-details');
    details.innerHTML = `
    <strong>Start:</strong> ${arg.event.extendedProps['startTime']}<br>
    <strong>End:</strong> ${arg.event.extendedProps['endTime']}<br>
    <strong>Client:</strong> ${arg.event.extendedProps['clientFirstName']} ${arg.event.extendedProps['clientLastName']}<br>
    <strong>Admin:</strong> ${arg.event.extendedProps['adminFirstName']} ${arg.event.extendedProps['adminLastName']}
  `;
  
    container.appendChild(title);
    container.appendChild(details);
  
    return { domNodes: [container] };
  }*/
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
      // For appointments from AllAppointmentsDataSource, show only start and end time
      details.innerHTML = `
        <strong>Start:</strong> ${arg.event.extendedProps['startTime']}<br>
        <strong>End:</strong> ${arg.event.extendedProps['endTime']}<br>
      `;
    } else {
      // For appointments from appointmentsDataSource, show additional client and admin information
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

  /*getAllAppointmentsByCompany(): void{
    this.service.getAppointmentsByCompany(this.companyId).subscribe(
      (appointmentsData : Appointment[] ) => {
        this.AllAppointmentsDataSource.data = appointmentsData || [];
        this.initializeCalendar(); 
    
        
      }, 
      appointmentError => {
        console.log('Error fetching appointments', appointmentError); 
      }
    );
  }*/
  getAllAppointmentsByCompany(): void {
    this.service.getNotReservedAppointmentsByCompany(this.companyId).subscribe(
      (appointmentsData: Appointment[]) => {
        /*const filteredAppointments = appointmentsData.filter(appointment =>
          !this.appointmentsDataSource.data.some(reservedAppointment =>
            reservedAppointment.date === appointment.date &&
            reservedAppointment.startTime[0] === appointment.startTime[0] &&
            reservedAppointment.startTime[1] === appointment.startTime[1] &&
            reservedAppointment.endTime[0] === appointment.endTime[0] &&
            reservedAppointment.endTime[1] === appointment.endTime[1]
          )
        );*/

        this.AllAppointmentsDataSource.data = appointmentsData || [];
        this.initializeCalendar();
      },
      (appointmentError) => {
        console.log('Error fetching not reserved appointments', appointmentError);
      }
    );
  }
  
}
