import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User, UserType } from '../model/user.model';
import { ReactiveFormsModule } from '@angular/forms';
import { LayoutService } from '../layout.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  public user: User | undefined;
  public userForm: FormGroup;

  constructor(private layoutService: LayoutService, private router: Router)
  {
    this.userForm = new FormGroup({
      id: new FormControl(0, [Validators.required]),
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      passwordCheck: new FormControl('', [Validators.required]),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
      jobTitle: new FormControl('', [Validators.required]),
      companyInformation: new FormControl('', [Validators.required]),
      userType: new FormControl(UserType.CUSTOMER, [Validators.required])
    });
  }

  register(): void{
    let  user: User = {
      id: 0,
      email: this.userForm.value.email,
      password: this.userForm.value.password,
      firstName: this.userForm.value.firstName,
      lastName: this.userForm.value.lastName,
      city: this.userForm.value.city,
      country: this.userForm.value.country,
      phoneNumber: this.userForm.value.phoneNumber,
      jobTitle: this.userForm.value.jobTitle,
      companyInformation: this.userForm.value.companyInformation,
      userType: UserType.CUSTOMER
    }

    this.layoutService.addUser(user).subscribe({
      next: (user) => { 
        window.alert("You have been successfully registered.");
        this.router.navigate(['/profile', user.id]);
      }
    });
  }
}
