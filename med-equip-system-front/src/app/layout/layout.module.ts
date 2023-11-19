import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { ProfileComponent } from './profile/profile.component';
import { MaterialModule } from '../infrastructure/material/material.module';
import { MatSliderModule } from '@angular/material/slider';
import { CompanyCreationComponent } from './company-creation/company-creation.component';
import { CompanyAdminCreationComponent } from './company-admin-creation/company-admin-creation.component';
import { EquipmentSearchComponent } from './equipment-search/equipment-search.component';

@NgModule({
  declarations: [
    NavbarComponent,
    HomeComponent,
    RegistrationComponent,
    ProfileComponent,
    CompanyCreationComponent,
    CompanyAdminCreationComponent,
    EquipmentSearchComponent
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
