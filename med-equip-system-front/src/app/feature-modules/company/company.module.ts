import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { CompanyProfileComponent } from './company-profile/company-profile.component';
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
import { CompanyProfileAdminComponent } from './company-profile-admin/company-profile-admin.component'; 


import { ReservationCreationComponent } from './reservation-creation/reservation-creation.component';


@NgModule({
  declarations: [
    CompanyProfileComponent,
    CompanyProfileAdminComponent, 
    ReservationCreationComponent, 
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
    MatButtonModule

  ], 
  providers: [DatePipe],
})
export class CompanyModule { }
