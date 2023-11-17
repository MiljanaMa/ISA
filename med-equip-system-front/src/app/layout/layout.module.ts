import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { RegistrationComponent } from './registration/registration.component';
import { ReactiveFormsModule  } from '@angular/forms';
import { ProfileComponent } from './profile/profile.component';
import { MaterialModule } from '../infrastructure/material/material.module';

@NgModule({
  declarations: [
    NavbarComponent,
    RegistrationComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  exports: [
    NavbarComponent
  ]
})
export class LayoutModule { }
