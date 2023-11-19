import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';
import { CompanyAdmin } from '../model/companyAdmin.model';

@Component({
  selector: 'app-company-creation',
  templateUrl: './company-creation.component.html',
  styleUrls: ['./company-creation.component.css']
})
export class CompanyCreationComponent {
  companyForm: FormGroup;
  isAddingAdmin: boolean = false;
  companyToCreate?: Company;
  
  public companyPls : Company;
  public adminsArray: CompanyAdmin[] = [];

  constructor(private fb: FormBuilder, private layoutService: LayoutService) {
    this.companyPls = {id: -1, name: '', description: '', averageRate: 0.0, location: {id: -1, longitude: 0, latitude:0,  street: '', streetNumber: '', city: '', country: '', postcode: 0}, companyAdmins: [] };
    this.companyForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      averageRate: [0, Validators.required],
      location: this.fb.group({
        street: ['', Validators.required],
        streetNumber: ['', Validators.required],
        city: ['', Validators.required],
        country: ['', Validators.required],
        postcode: ['', Validators.required],
      }),
      admins: this.fb.array([]) 
    });
  }
  

  /*get adminsArray() {
    return this.companyForm.get('admins') as FormArray;
  }*/

  addAdmin() {
    const adminFormGroup = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      phoneNumber: ['', Validators.required],
    });
  
    const admin: CompanyAdmin = {
      email: adminFormGroup.value.email || '',
      password: adminFormGroup.value.password || '',
      firstName: adminFormGroup.value.firstName || '',
      lastName: adminFormGroup.value.lastName || '',
      city: adminFormGroup.value.city || '',
      country: adminFormGroup.value.country || '',
      phoneNumber: adminFormGroup.value.phoneNumber || '',
    };
  
    this.adminsArray.push(admin);
    console.log(this.adminsArray);
  
    //const company: Company = this.companyForm.value;
     this.companyToCreate = this.companyForm.value;

    //companyToCreate.admins.push(admin);

    if(this.companyToCreate){
      this.companyToCreate.companyAdmins = [];
    }

    for(let a of this.adminsArray){
      this.companyToCreate?.companyAdmins?.push(a); 
    }  
  
    console.log(this.companyToCreate?.companyAdmins);

  }

  onSubmit() {
    if (this.companyForm.valid) {
      if(this.companyToCreate){
        let company : Company = this.companyToCreate;
        console.log(company);
        console.log(this.companyToCreate);
        this.layoutService.createCompany(company).subscribe(
          (createdCompany: Company) => {
            console.log('Company created successfully:', createdCompany);
          },
          (error) => {
            console.error('Error creating company:', error);
          }
        );
      }
    }
  }

  toggleAddingAdmin() {
    this.isAddingAdmin = !this.isAddingAdmin;
  }
}
