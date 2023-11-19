import { Component } from '@angular/core';
import { CompanyAdmin } from '../model/companyAdmin.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { LayoutService } from '../layout.service';

@Component({
  selector: 'app-company-admin-creation',
  templateUrl: './company-admin-creation.component.html',
  styleUrls: ['./company-admin-creation.component.css']
})
export class CompanyAdminCreationComponent {
  public admin: CompanyAdmin | undefined;
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

  constructor(private layoutService: LayoutService){
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

  isPasswordVisible(): void{
    this.hide = !this.hide;
  }

  isPasswordCheckVisible(): void{
    this.hideCheck = !this.hideCheck;
  }

  register(): void{
    if(this.adminForm.get('email')?.invalid){
      window.alert("Email is not in the correct form.")
    }else if (!this.adminForm.valid) {
      window.alert("All fields are required.");
    }else if(this.adminForm.value.password !== this.adminForm.value.passwordCheck){
      window.alert("Passwords don't match.");
    }else{
      let  admin: CompanyAdmin = {
        id: 0,
        email: this.adminForm.value.email,
        password: this.adminForm.value.password,
        firstName: this.adminForm.value.firstName,
        lastName: this.adminForm.value.lastName,
        city: this.adminForm.value.city,
        country: this.adminForm.value.country,
        phoneNumber: this.adminForm.value.phoneNumber,
      }
      
      this.layoutService.addCompanyAdmin(admin).subscribe({
        next: (admin) => { 
          window.alert("Company admin created.");
        },
        error: () => {
          window.alert("Error in creating company admin");
        }
        
      });
    }  
  }

}
