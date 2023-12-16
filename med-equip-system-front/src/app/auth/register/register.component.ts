import { Component } from '@angular/core';
import { ClientRegistration } from '../model/client-registration.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  public client: ClientRegistration | undefined;
  public clientForm: FormGroup;
  public hide: boolean = true;
  public hideCheck: boolean = true;
  

  public notEmptyString: ValidatorFn = (control: AbstractControl): {[key: string]: any} | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };

  constructor(private authService: AuthService, private router: Router)
  {
    this.clientForm = new FormGroup({
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
    });
  }

  register(): void{
    if(this.clientForm.get('email')?.invalid){
      window.alert("Email is not in the correct form.")
    }else if (!this.clientForm.valid) {
      window.alert("All fields are required.");
    }else if(this.clientForm.value.password !== this.clientForm.value.passwordCheck){
      window.alert("Passwords don't match.");
    }else{
      let  client: ClientRegistration = {
        id: 0,
        email: this.clientForm.value.email,
        password: this.clientForm.value.password,
        firstName: this.clientForm.value.firstName,
        lastName: this.clientForm.value.lastName,
        city: this.clientForm.value.city,
        country: this.clientForm.value.country,
        phoneNumber: this.clientForm.value.phoneNumber,
        jobTitle: this.clientForm.value.jobTitle,
        hospitalInfo: this.clientForm.value.hospitalInfo
      }
      
      this.authService.registerClient(client).subscribe({
        next: (client) => { 
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
