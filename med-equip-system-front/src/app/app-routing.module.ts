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
import { ContractsComponent } from './company-admin/contracts/contracts.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '', component: HomeComponent},
  {path: 'profile', component: ClientProfileComponent, canActivate: [AuthGuard],},
  {path: 'reservations', component: ReservationsComponent, canActivate: [AuthGuard],},
  {path: 'companyCreation', component: CompanyCreationComponent, canActivate: [AuthGuard],},
  { path: 'admin-profile/:id', component: AdminProfileComponent, canActivate: [AuthGuard],},
  { path: 'company-profile/:id', component: CompanyProfileComponent, canActivate: [AuthGuard],},
  {path: 'equipmentSearch', component: EquipmentSearchComponent},
  {path: 'companyadminCreation', component: CompanyAdminCreationComponent, canActivate: [AuthGuard],},
  {path: 'systemAdminCreation', component: SystemAdminCreationComponent, canActivate: [AuthGuard],},
  {path: 'company-profile-admin/:id', component: CompanyProfileAdminComponent, canActivate: [AuthGuard],}, 
  {path: 'company-work-calendar', component: CompanyWorkCalendarComponent, canActivate: [AuthGuard],},
  {path: 'systemAdminChangePassword', component: ChangePasswordComponent, canActivate: [AuthGuard]},
  {path: 'contracts', component: ContractsComponent, canActivate: [AuthGuard]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
