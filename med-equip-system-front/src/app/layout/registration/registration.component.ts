import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { User, UserType } from '../model/user.model';
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
  public hide: boolean = true;
  public hideCheck: boolean = true;
  

  public notEmptyString: ValidatorFn = (control: AbstractControl): {[key: string]: any} | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };

  constructor(private layoutService: LayoutService, private router: Router)
  {
    this.userForm = new FormGroup({
      id: new FormControl(-1, [Validators.required]),
      email: new FormControl('',[
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password: new FormControl('', [Validators.required, this.notEmptyString]),
      passwordCheck: new FormControl('', [Validators.required, this.notEmptyString]),
      firstName: new FormControl('', [Validators.required, this.notEmptyString]),
      lastName: new FormControl('', [Validators.required, this.notEmptyString]),
      city: new FormControl('', [Validators.required, this.notEmptyString]),
      country: new FormControl('', [Validators.required, this.notEmptyString]),
      phoneNumber: new FormControl('', [Validators.required, this.notEmptyString]),
      jobTitle: new FormControl('', [Validators.required, this.notEmptyString]),
      hospitalInfo: new FormControl('', [Validators.required, this.notEmptyString]),
      userType: new FormControl(UserType.CUSTOMER, [Validators.required])
    });
  }

  register(): void{
    if(this.userForm.get('email')?.invalid){
      window.alert("Email is not in the correct form.")
    }else if (!this.userForm.valid) {
      window.alert("All fields are required.");
    }else if(this.userForm.value.password !== this.userForm.value.passwordCheck){
      window.alert("Passwords don't match.");
    }else{
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
        hospitalInfo: this.userForm.value.hospitalInfo,
        userType: UserType.CUSTOMER,
        penaltyPoints: 0,
        points: 0,
        loyaltyType: 'NONE',
        discount: 0.0
      }
      
      this.layoutService.addUser(user).subscribe({
        next: (user) => { 
          window.alert("The activation link has been sent to your email. Please confirm your email address and activate your account.");
         // this.router.navigate(['/profile', user.id]);       
        },
        error: () => {
          window.alert("The provided email address is already associated with an existing user account.");
        }
        
      });
    }  
  }

  isPasswordVisible(): void{
    this.hide = !this.hide;
  }

  isPasswordCheckVisible(): void{
    this.hideCheck = !this.hideCheck;
  }
}
