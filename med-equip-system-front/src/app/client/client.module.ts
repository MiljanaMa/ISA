import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientProfileComponent } from './client-profile/client-profile.component';
import { MaterialModule } from '../infrastructure/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSliderModule } from '@angular/material/slider';
import { ReservationsComponent } from './reservations/reservations.component';
import { MatTabsModule } from '@angular/material/tabs';



@NgModule({
  declarations: [
    ClientProfileComponent,
    ReservationsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule,
    MatTabsModule
  ]
})
export class ClientModule { }
