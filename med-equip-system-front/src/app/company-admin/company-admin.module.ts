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


@NgModule({
  declarations: [
    CompanyWorkCalendarComponent
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
    
  ]
})
export class CompanyAdminModule { }
