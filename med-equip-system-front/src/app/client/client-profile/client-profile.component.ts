import { Component } from '@angular/core';
import { Client, Passwords } from '../model/client.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ClientService } from '../client.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent {
  public client?: Client;
  public userId: number = -1;
  public clientForm: FormGroup;
  public passwordForm: FormGroup;
  public updateMode: boolean = false;
  public updatePasswordMode: boolean = false;
  public hideOld: boolean = true;
  public hideNew: boolean = true;
  public hideCheck: boolean = true;

  constructor(private clientService: ClientService, private authService: AuthService) {
    this.clientForm = new FormGroup({
      id: new FormControl(this.client?.id, [Validators.required]),
      firstName: new FormControl(this.client?.firstName, [Validators.required, this.notEmptyString]),
      lastName: new FormControl(this.client?.lastName, [Validators.required, this.notEmptyString]),
      city: new FormControl(this.client?.city, [Validators.required, this.notEmptyString]),
      country: new FormControl(this.client?.country, [Validators.required, this.notEmptyString]),
      phoneNumber: new FormControl(this.client?.phoneNumber, [Validators.required, this.notEmptyString]),
      jobTitle: new FormControl(this.client?.jobTitle, [Validators.required, this.notEmptyString]),
      hospitalInfo: new FormControl(this.client?.hospitalInfo, [Validators.required, this.notEmptyString]),
    });

    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      newPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      passwordCheck: new FormControl('', [Validators.required, this.notEmptyString]),
    });

  }

  ngOnInit(): void {
    this.getCurrentClient();
  }

  getCurrentClient(): void {
      this.clientService.getCurrentClient().subscribe({
        next: (client) => {
          this.client = client;
  
          this.clientForm.patchValue({
            id: this.client.id,
            firstName: this.client.firstName,
            lastName: this.client.lastName,
            city: this.client.city,
            country: this.client.country,
            phoneNumber: this.client.phoneNumber,
            jobTitle: this.client.jobTitle,
            hospitalInfo: this.client.hospitalInfo
          });
          this.resetPasswordUpdateForm();
        }
      });
  }
  public resetPasswordUpdateForm(){
    this.passwordForm.patchValue({
      oldPassword: '',
      newPassword: '',
      passwordCheck: ''
    });
  }
  public notEmptyString: ValidatorFn = (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };
  showUpdateForm(): void {
    this.updateMode = true;
  }
  showPasswordForm(): void {
    this.updatePasswordMode = true;
  }
  cancelUpdate(): void {
    this.updateMode = false;
    this.updatePasswordMode = false;
    this.getCurrentClient();
  }
  
  isOldPasswordVisible(): void{
    this.hideOld = !this.hideOld;
  }
  isNewPasswordVisible(): void{
    this.hideNew = !this.hideNew;
  }
  isCheckPasswordVisible(): void{
    this.hideCheck = !this.hideCheck;
  }

  update(): void {
    if (!this.clientForm.valid)
      window.alert("All fields are required.");
    else 
    {
      let client: Client = {
        id: this.client?.id,
        email: '',
        password: '',
        firstName: this.clientForm.value.firstName,
        lastName: this.clientForm.value.lastName,
        city: this.clientForm.value.city,
        country: this.clientForm.value.country,
        phoneNumber: this.clientForm.value.phoneNumber,
        jobTitle: this.clientForm.value.jobTitle,
        hospitalInfo: this.clientForm.value.hospitalInfo,
        penaltyPoints: 0,
        points: 0,
        loyaltyType: '',
        discount: 0

      }

      this.clientService.updateClient(client).subscribe({
        next: (client) => {
          window.alert("You have been successfully updated your profile.");
          this.getCurrentClient();
          this.updateMode = false;
        },
        error: () => {
          window.alert("Something went wrong try again.");
        }

      });
    }
  }
  updatePassword(): void {
    if (!this.passwordForm.valid)
      window.alert("All fields are required.");
    else if (this.passwordForm.value.newPassword !== this.passwordForm.value.passwordCheck)
      window.alert("Passwords don't match.");
    else 
    {
      let passwords : Passwords = {
        userId : 0,
        newPassword : this.passwordForm.value.newPassword,
        oldPassword : this.passwordForm.value.oldPassword
      } 
      this.clientService.updatePassword(passwords).subscribe({
        next: (client) => {
          window.alert("You have been successfully updated your password.");
          this.getCurrentClient();
          this.updatePasswordMode = false;
          this.resetPasswordUpdateForm();
        },
        error: (err) => {
          if(err.error.status === 400)
            window.alert("Wrong old password");
        }

      });

    }
  }
}
