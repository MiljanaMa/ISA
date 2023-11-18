import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { ProfileComponent } from './profile/profile.component';
import { MaterialModule } from '../infrastructure/material/material.module';
import { MatSliderModule } from '@angular/material/slider';

@NgModule({
  declarations: [
    NavbarComponent,
    HomeComponent,
    RegistrationComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule
  ],
  exports: [
    NavbarComponent,
    HomeComponent
  ]
})
export class LayoutModule { }
