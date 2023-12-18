import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layout/home/home.component';
import { CompanyCreationComponent } from './layout/company-creation/company-creation.component';
import { CompanyProfileComponent } from './feature-modules/company/company-profile/company-profile.component';
import { AdminProfileComponent } from './layout/admin-profile/admin-profile.component';
import { EquipmentSearchComponent } from './layout/equipment-search/equipment-search.component';
import { CompanyAdminCreationComponent } from './layout/company-admin-creation/company-admin-creation.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ClientProfileComponent } from './client/client-profile/client-profile.component';
import { SystemAdminCreationComponent } from './system-admin/system-admin-creation/system-admin-creation.component';
import { CompanyProfileAdminComponent } from './feature-modules/company/company-profile-admin/company-profile-admin.component';
import { CompanyWorkCalendarComponent } from './company-admin/company-work-calendar/company-work-calendar.component';
import { ReservationsComponent } from './client/reservations/reservations.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ClientProfileComponent},
  {path: 'reservations', component: ReservationsComponent},
  {path: '', component: HomeComponent},
  {path: 'companyCreation', component: CompanyCreationComponent},
  { path: 'admin-profile/:id', component: AdminProfileComponent },
  { path: 'company-profile/:id', component: CompanyProfileComponent },
  {path: 'equipmentSearch', component: EquipmentSearchComponent},
  {path: 'companyadminCreation', component: CompanyAdminCreationComponent},
  {path: 'systemAdminCreation', component: SystemAdminCreationComponent},
  {path: 'company-profile-admin/:id', component: CompanyProfileAdminComponent}, 
  {path: 'company-work-calendar', component: CompanyWorkCalendarComponent},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
