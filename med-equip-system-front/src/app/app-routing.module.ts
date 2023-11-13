import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationComponent } from './layout/registration/registration.component';
import { ProfileComponent } from './layout/profile/profile.component';

const routes: Routes = [

  {path: 'registration', component: RegistrationComponent},
  {path: 'profile/:id', component: ProfileComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
