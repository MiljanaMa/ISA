import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layout/home/home.component';
import { RegistrationComponent } from './layout/registration/registration.component';
import { ProfileComponent } from './layout/profile/profile.component';
import { CompanyCreationComponent } from './layout/company-creation/company-creation.component';
import { CompanyProfileComponent } from './feature-modules/company/company-profile/company-profile.component';
import { AdminProfileComponent } from './layout/admin-profile/admin-profile.component';
import { EquipmentSearchComponent } from './layout/equipment-search/equipment-search.component';

const routes: Routes = [

  {path: 'registration', component: RegistrationComponent},
  {path: 'profile/:id', component: ProfileComponent},
  {path: '', component: HomeComponent},
  {path: 'companyCreation', component: CompanyCreationComponent},
  { path: 'admin-profile/:id', component: AdminProfileComponent },
  { path: 'company-profile/:id', component: CompanyProfileComponent },
  {path: 'equipmentSearch', component: EquipmentSearchComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
