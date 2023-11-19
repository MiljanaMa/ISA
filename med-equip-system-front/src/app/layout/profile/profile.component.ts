import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { User, UserType } from '../model/user.model';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public user?: User;
  public userId: number = -1;
  public userForm: FormGroup;
  public passwordForm: FormGroup;
  public updateMode: boolean = false;
  public updatePasswordMode: boolean = false;

  constructor(private layoutService: LayoutService, private currentRoute: ActivatedRoute) {
    this.userForm = new FormGroup({
      id: new FormControl(this.user?.id, [Validators.required]),
      firstName: new FormControl(this.user?.firstName, [Validators.required, this.notEmptyString]),
      lastName: new FormControl(this.user?.lastName, [Validators.required, this.notEmptyString]),
      city: new FormControl(this.user?.city, [Validators.required, this.notEmptyString]),
      country: new FormControl(this.user?.country, [Validators.required, this.notEmptyString]),
      phoneNumber: new FormControl(this.user?.phoneNumber, [Validators.required, this.notEmptyString]),
      jobTitle: new FormControl(this.user?.jobTitle, [Validators.required, this.notEmptyString]),
      hospitalInfo: new FormControl(this.user?.hospitalInfo, [Validators.required, this.notEmptyString]),
    });

    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      newPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      passwordCheck: new FormControl('', [Validators.required, this.notEmptyString]),
    });

  }


  ngOnInit(): void {
    this.currentRoute.params.subscribe((params: Params) => {
      this.userId = params['id'];
    });

    this.getById();

  }

  getById(): void {
    this.layoutService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.user = user;

        this.userForm.patchValue({
          id: this.user.id,
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          city: this.user.city,
          country: this.user.country,
          phoneNumber: this.user.phoneNumber,
          jobTitle: this.user.jobTitle,
          hospitalInfo: this.user.hospitalInfo
        });
        this.passwordForm.patchValue({
          oldPassword: '',
          newPassword: '',
          passwordCheck: ''
        });
      }
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
    this.getById();
  }
  update(): void {
    if (!this.userForm.valid) {
      window.alert("All fields are required.");
    }
    else if (this.userForm.value.password !== this.userForm.value.passwordCheck) {
      window.alert("Passwords don't match.");
    }
    else {
      let user: User = {
        id: this.user?.id,
        email: '',
        password: '',
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
        loyaltyType: '',
        discount: 0

      }

      this.layoutService.updateUser(user).subscribe({
        next: (user) => {
          window.alert("You have been successfully update your profile.");
          this.getById();
          this.updateMode = false;
        },
        error: () => {
          window.alert("Something went wrong try again.");
        }

      });
    }
  }
  updatePassword(): void {
    if (!this.passwordForm.valid) {
      window.alert("All fields are required.");
    }
    else if (this.user?.password !== this.passwordForm.value.oldPassword) {
      window.alert("Wrong old password.");
    }
    else if (this.passwordForm.value.newPassword !== this.passwordForm.value.passwordCheck) {
      window.alert("Passwords don't match.");
    }
    else {
      this.layoutService.updatePassword(this.user?.id || 0, this.passwordForm.value.newPassword).subscribe({
        next: (user) => {
          window.alert("You have been successfully update your profile.");
          this.getById();
          this.updatePasswordMode = false;
        },
        error: () => {
          window.alert("Something went wrong try again.");
        }

      });

    }
  }
}

