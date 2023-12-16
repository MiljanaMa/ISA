import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyProfileComponent } from './company-profile/company-profile.component';
import { MatTableModule } from '@angular/material/table';
import { LayoutModule } from 'src/app/layout/layout.module';
import { FormsModule } from '@angular/forms';
import { ReservationCreationComponent } from './reservation-creation/reservation-creation.component';

@NgModule({
  declarations: [
    CompanyProfileComponent,
    ReservationCreationComponent, 
  ],
  imports: [
    CommonModule,
    MatTableModule, 
    LayoutModule, 
    FormsModule
  
  ], 
})
export class CompanyModule { }
