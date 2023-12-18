import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CompanyAdmin } from '../model/companyAdmin.model';
import { LayoutService } from '../layout.service';
import { ActivatedRoute } from '@angular/router';
import * as CryptoJs from 'crypto-js'; 
import { Params
 } from '@angular/router';
 import { User } from '../model/user.model';
@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  public admin?: CompanyAdmin;
  public adminId? : number; 
  public adminForm: FormGroup;
  public updateMode: boolean = false;
  public hidePassword: boolean = true;

  constructor(private formBuilder: FormBuilder, private layoutService: LayoutService, private currentRoute: ActivatedRoute ) {
    this.adminForm = this.formBuilder.group({
      id: [this.admin?.id],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      companyId: [this.admin?.companyId]
    });
  }

  ngOnInit(): void {
    this.currentRoute.params.subscribe((params: Params) => {
      this.adminId = params['id'];
      console.log(this.adminId); 
    });
    this.getById();
  }

  getById(): void {
    if(this.adminId){
     this.layoutService.getAdminById(this.adminId).subscribe({
        next: (admin: CompanyAdmin) => {
         this.admin = admin;
          this.adminForm.patchValue({ 
              email: this.admin.user.email,
              password: '' , 
              firstName: this.admin.user.firstName, 
              lastName: this.admin.user.lastName, 
              city: this.admin.user.city, 
              country: this.admin.user.country, 
              phoneNumber: this.admin.user.phoneNumber, 
          });
         },
        error: (error) => {
         console.error('Error fetching admin details', error);
        }
     });
    }
  }

  showUpdateForm(): void {
    this.updateMode = true;
  }

  cancelUpdate(): void {
    this.updateMode = false;
    this.getById(); // Refresh admin details on cancel
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }

  update(): void {
    if (this.adminForm.valid) {
    
    
      let updatedAdmin = {
        id: this.adminId,  
        user: {
          id: this.admin?.user.id, 
          email: this.adminForm.value.email, 
          password: this.adminForm.value.password, 
          firstName: this.adminForm.value.firstName, 
          lastName: this.adminForm.value.lastName, 
          city: this.adminForm.value.city, 
          country: this.adminForm.value.country, 
          phoneNumber: this.adminForm.value.phoneNumber, 
        

        }, 
        firstTime: true

      }
      console.log(updatedAdmin); 
      this.layoutService.updateAdmin(updatedAdmin).subscribe({
       next: () => {
     
           //this.getById(); 
          this.updateMode = false;
            }, 
        error: (error) => {
          console.error('Error updating admin details', error);
          window.alert('Failed to update admin details. Please try again.');
         }
         });
    } else {
      window.alert('Please fill in all required fields.');
    }
  }
}
