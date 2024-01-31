import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyWorkCalendarComponent } from './company-work-calendar/company-work-calendar.component';
import { MatTableModule } from '@angular/material/table';
import { LayoutModule } from 'src/app/layout/layout.module';
import { FormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { LocationSimulatorComponent } from './location-simulator/location-simulator.component';
import { SimulatorMapComponent } from './simulator-map/simulator-map.component';
import { ContractsComponent } from './contracts/contracts.component';
import { AdminsReservationsComponent } from './admins-reservations/admins-reservations.component';
import { MatTabsModule } from '@angular/material/tabs';


@NgModule({
  declarations: [
    CompanyWorkCalendarComponent,
    LocationSimulatorComponent,
    SimulatorMapComponent,
    ContractsComponent,
    AdminsReservationsComponent

  ],
  imports: [
    CommonModule,
    MatTableModule, 
    LayoutModule, 
    FormsModule, 
    FullCalendarModule, 
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule, 
    MatButtonModule,
    MatTabsModule
    
  ]
})
export class CompanyAdminModule { }
