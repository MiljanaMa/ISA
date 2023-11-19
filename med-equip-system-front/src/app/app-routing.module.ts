import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layout/home/home.component';
import { RegistrationComponent } from './layout/registration/registration.component';
import { ProfileComponent } from './layout/profile/profile.component';
import { CompanyCreationComponent } from './layout/company-creation/company-creation.component';

const routes: Routes = [

  {path: 'registration', component: RegistrationComponent},
  {path: 'profile/:id', component: ProfileComponent},
  {path: '', component: HomeComponent},
  {path: 'companyCreation', component: CompanyCreationComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
