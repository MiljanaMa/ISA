import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layout/home/home.component';
import { CompanyCreationComponent } from './layout/company-creation/company-creation.component';
import { CompanyProfileComponent } from './client/company-profile/company-profile.component';
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
import { ChangePasswordComponent } from './system-admin/change-password/change-password.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { LocationSimulatorComponent } from './company-admin/location-simulator/location-simulator.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '', component: HomeComponent},
  {path: 'company-profile/:id', component: CompanyProfileComponent},
  {path: 'equipmentSearch', component: EquipmentSearchComponent},
  
  {path: 'profile', component: ClientProfileComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_CLIENT' }},
  {path: 'reservations', component: ReservationsComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_CLIENT' }},

  {path: 'admin-profile/:id', component: AdminProfileComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_COMPADMIN' }},
  {path: 'company-profile-admin/:id', component: CompanyProfileAdminComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_COMPADMIN' }}, 
  {path: 'company-work-calendar', component: CompanyWorkCalendarComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_COMPADMIN' }},
  {path: 'location-simulator', component: LocationSimulatorComponent},

  {path: 'companyadminCreation', component: CompanyAdminCreationComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_SYSADMIN' }},
  {path: 'systemAdminCreation', component: SystemAdminCreationComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_SYSADMIN' }},
  {path: 'companyCreation', component: CompanyCreationComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_SYSADMIN' }},
  {path: 'systemAdminChangePassword', component: ChangePasswordComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_SYSADMIN' }},
  {path: 'systemAdminChangePassword', component: ChangePasswordComponent, canActivate: [AuthGuard], data: { requiredRole: 'ROLE_SYSADMIN' }},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
