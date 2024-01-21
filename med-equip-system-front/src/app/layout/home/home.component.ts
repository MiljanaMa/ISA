import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyAdmin } from '../model/companyAdmin.model';
import { FormsModule } from '@angular/forms';

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
  public sortType: string = 'NAME';
  public orderType: string = 'DESC';


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
        this.onSortChange();
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
  onSortChange(): void {
    if(this.sortType === 'NAME' && this.orderType === 'ASC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => b.name.toUpperCase().localeCompare(a.name.toUpperCase()));
    else if(this.sortType === 'NAME' && this.orderType === 'DESC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => a.name.toUpperCase().localeCompare(b.name.toUpperCase()));
    else if(this.sortType === 'CITY' && this.orderType === 'ASC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => b.location.city.toUpperCase().localeCompare(a.location.city.toUpperCase()));
    else if(this.sortType === 'CITY' && this.orderType === 'DESC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => a.location.city.toUpperCase().localeCompare(b.location.city.toUpperCase()));
    else if(this.sortType === 'RATING' && this.orderType === 'DESC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => a.averageRate - b.averageRate);
    else if(this.sortType === 'RATING' && this.orderType === 'ASC')
      this.filteredCompanies = this.filteredCompanies.sort((b, a) => b.averageRate - a.averageRate);
  }

  viewCompanyProfile(companyId: number): void {
   
    this.router.navigate(['/company-profile', companyId]);
  }

}
