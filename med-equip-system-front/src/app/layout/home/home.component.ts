import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyAdmin } from '../model/companyAdmin.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public companies: Company[] = [];
  public filteredCompanies: Company[] = [];
  public selectedRating: number = 0;
  public inputSearch: string = '';


  constructor(private layoutService: LayoutService, private router: Router, private authService: AuthService) { }

  loggedUser: CompanyAdmin| undefined; 

  ngOnInit(): void {
    this.getAllCompanies();

    this.authService.getCurrentUser().subscribe(
      (data) => {
        let user = data; 
        if(user){
     
        
          if(user.role?.name === "ROLE_COMPADMIN"){
            this.layoutService.getAdminByUserId(user.id).subscribe( 
              
            (data) => {
              this.loggedUser = data; 
              
            
            }, 
            error => {
              console.log(error); 
            }); 
          }
        }
      }
    ); 

   
    
  }
  onSubmit(form: any) {
    if(this.loggedUser){
      this.loggedUser.user.password = form.value.password; 
      this.loggedUser.firstTime = true;  
      this.layoutService.updateAdmin(this.loggedUser).subscribe(
        (data) => {

        }, 
        error => {
          console.log(error); 
        }
      ); 
    }
  }

  getAllCompanies(): void {
    this.layoutService.getCompanies().subscribe({
      next: (data) => {
        this.companies = data;
        this.filteredCompanies = data;
      }
    });
  }
  searchCompanies(): void {
    this.filteredCompanies = this.companies.filter(company =>
      company.averageRate >= this.selectedRating);

    if (this.inputSearch !== '') {
      this.filteredCompanies = this.filteredCompanies.filter(company =>
        company.name.toLowerCase().includes(this.inputSearch.toLowerCase()) ||
        company.location.city.toLowerCase().includes(this.inputSearch.toLowerCase()));
    }
  }
  clearSearch(): void {
    this.selectedRating = 0;
    this.inputSearch = '';
    this.filteredCompanies = this.companies;
  }

  viewCompanyProfile(companyId: number): void {
   
    this.router.navigate(['/company-profile', companyId]);
  }

}
