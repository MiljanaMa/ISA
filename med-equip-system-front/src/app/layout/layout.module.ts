import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { MaterialModule } from '../infrastructure/material/material.module';
import { MatSliderModule } from '@angular/material/slider';
import { CompanyCreationComponent } from './company-creation/company-creation.component';
import { CompanyAdminCreationComponent } from './company-admin-creation/company-admin-creation.component';
import { CompanyMapComponent } from './map/map.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { EquipmentSearchComponent } from './equipment-search/equipment-search.component';

@NgModule({
  declarations: [
    NavbarComponent,
    HomeComponent,
    CompanyCreationComponent,
    CompanyAdminCreationComponent,
    CompanyMapComponent,
    AdminProfileComponent,
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
    HomeComponent,
    CompanyMapComponent, 
  
  ]
})
export class LayoutModule { }
