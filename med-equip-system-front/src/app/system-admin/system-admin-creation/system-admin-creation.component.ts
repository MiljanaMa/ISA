import { Component } from '@angular/core';
import { SystemAdmin } from '../model/system-admin.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { SystemAdminService } from '../system-admin.service';

@Component({
  selector: 'app-system-admin-creation',
  templateUrl: './system-admin-creation.component.html',
  styleUrls: ['./system-admin-creation.component.css']
})
export class SystemAdminCreationComponent {

  public admin: SystemAdmin | undefined;
  public adminForm: FormGroup;
  public hide: boolean = true;
  public hideCheck: boolean = true;

  public notEmptyString: ValidatorFn = (control: AbstractControl): {[key: string]: any} | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };

  constructor(private systemAdminService: SystemAdminService){
    this.adminForm = new FormGroup({
      id: new FormControl(-1,[Validators.required] ),
      email: new FormControl('', [Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password: new FormControl('', [Validators.required, this.notEmptyString]),
      passwordCheck: new FormControl('', [Validators.required, this.notEmptyString]),
      firstName: new FormControl('', [Validators.required, this.notEmptyString]),
      lastName: new FormControl('', [Validators.required, this.notEmptyString]),
      city: new FormControl('', [Validators.required, this.notEmptyString]),
      country: new FormControl('', [Validators.required, this.notEmptyString]),
      phoneNumber: new FormControl('', [Validators.required, this.notEmptyString]),
    }) 
  }

  register(): void{
    if(this.adminForm.get('email')?.invalid){
      window.alert("Email is not in the correct form.")
    }else if (!this.adminForm.valid) {
      window.alert("All fields are required.");
    }else if(this.adminForm.value.password !== this.adminForm.value.passwordCheck){
      window.alert("Passwords don't match.");
    }else{
      let  admin: SystemAdmin = {
        id: 0,
        email: this.adminForm.value.email,
        password: this.adminForm.value.password,
        firstName: this.adminForm.value.firstName,
        lastName: this.adminForm.value.lastName,
        city: this.adminForm.value.city,
        country: this.adminForm.value.country,
        phoneNumber: this.adminForm.value.phoneNumber,
        isMain: false,
        isInititialPasswordChanged: false
      }
      
      this.systemAdminService.addSystemAdmin(admin).subscribe({
        next: (admin) => { 
          window.alert("System admin created.");
        },
        error: () => {
          window.alert("Error in creating system admin");
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
