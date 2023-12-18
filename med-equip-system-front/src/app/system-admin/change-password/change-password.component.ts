import { Component, OnInit } from '@angular/core';
import { SystemAdmin } from '../model/system-admin.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { SystemAdminService } from '../system-admin.service';
import { AuthService } from 'src/app/auth/auth.service';
import { BehaviorSubject } from 'rxjs';
import { CurrentUser } from 'src/app/auth/model/current-user.model';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit{
  public systemAdmin?: SystemAdmin;
  public userId: number = -1;
  public passwordForm: FormGroup;
  public hideOld: boolean = true;
  public hideNew: boolean = true;
  public hideCheck: boolean = true;
  public updatePasswordMode: boolean = true;
  public currentUserId?: number = 6; ;
  public currentUser = new BehaviorSubject<CurrentUser>({id: 0, email: "", role: null });



  constructor(private systemAdminService: SystemAdminService, private authService: AuthService){
    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      newPassword: new FormControl('', [Validators.required, this.notEmptyString]),
      passwordCheck: new FormControl('', [Validators.required, this.notEmptyString]),
    });
  }

  public notEmptyString: ValidatorFn = (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };

  ngOnInit(): void {
    this.getCurrentUserId();
  }

  async getCurrentUserId(): Promise<void> {
    try {
      const user = await this.authService.getCurrentUser().toPromise();
      if(user)
      this.currentUser.next(user);
      this.currentUserId = user?.id;
      console.log("current user ://////// ", this.currentUser);
      if (user &&user.id > 0) {
        await this.getSystemAdmin(user.id);
      }
    } catch (error) {
      console.log('Error getting current user:', error);
    }
  }

  async getSystemAdmin(userId: number): Promise<void> {
    try {
      const systemAdmin = await this.authService.getByUserId(userId).toPromise();
      this.systemAdmin = systemAdmin;

      this.passwordForm.patchValue({
        oldPassword: '',
        newPassword: '',
        passwordCheck: ''
      });
      
    } catch (error) {
      console.error('Error fetching system admin:', error);
    }
  }


  updatePassword(): void {
    if (!this.passwordForm.valid) {
      window.alert("All fields are required.");
    }
    
    else {
      this.systemAdminService.updatePassword(this.passwordForm.value.newPassword, this.passwordForm.value.oldPassword, this.currentUserId || 6).subscribe({
        next: (client) => {
          window.alert("You have been successfully update your profile.");
          //this.getSystemAdmin();

          this.updatePasswordMode = false;
        },
        error: () => {
          window.alert("Old password does not match. Error, try again or something.");
        }

      });

    }
  }

  showPasswordForm(): void {
    this.updatePasswordMode = true;
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
}
