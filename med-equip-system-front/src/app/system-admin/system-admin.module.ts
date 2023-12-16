import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemAdminCreationComponent } from './system-admin-creation/system-admin-creation.component';
import { MaterialModule } from '../infrastructure/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSliderModule } from '@angular/material/slider';
import { MatIconModule } from '@angular/material/icon';



@NgModule({
  declarations: [
    SystemAdminCreationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule,
    MatIconModule,

  ],
  exports: [

  ]
})
export class SystemAdminModule { }
