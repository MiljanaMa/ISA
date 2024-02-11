import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyAdmin } from '../model/companyAdmin.model';
import { FormsModule } from '@angular/forms';
import { CurrentUser } from 'src/app/auth/model/current-user.model';

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
  public companyAdmin: CompanyAdmin | undefined;
  public adminsCompanyId: number = 0;
  public isCompanyAdmin: boolean = false;
  public currentUser: CurrentUser | undefined;



  constructor(private layoutService: LayoutService, private router: Router, private authService: AuthService) { }

  loggedUser: CompanyAdmin| undefined; 

  ngOnInit(): void {
    this.getAllCompanies();
    this.getCurrentUser();

    this.authService.getCurrentUser().subscribe(
      (data) => {
        let user = data; 
        if(user){
     
        
          if(user.role?.name === "ROLE_COMPADMIN"){
            this.layoutService.getAdminByUserId(user.id).subscribe( 
              
            (data) => {
              this.loggedUser = data; 
              this.isCompanyAdmin = true;
              this.getCompanyAdmin(user.id);
            
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
        this.filterCompaniesByRole();
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
    this.filterCompaniesByRole();
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

  filterCompaniesByRole(): void {
    if (this.isCompanyAdmin) {
      this.filteredCompanies = this.filteredCompanies.filter(
        company => company.id === this.adminsCompanyId
      );
    }
  }

  getCompanyAdmin(userId: number): void {
    this.authService.getCompanyAdminByUserId(userId).subscribe({
      next: (companyAdmin) => {
        console.log('company admin whole:' , companyAdmin);
        this.companyAdmin = companyAdmin;
        console.log("this.companyAdmin: ", this.companyAdmin);
          
        this.adminsCompanyId = companyAdmin.companyId || 0;        
        this.getAllCompanies();

      },
      error: (error) => {
        console.error('Error fetching company admin:', error);
      }
    });
  }

  getCurrentUser(): void{
    this.authService.currentUser.subscribe((user) => {
      if (user) {
        console.log("User(navbar): ", user.email, " Role: ", user.role?.name);
        this.currentUser = user;
  
        if (user.role?.name === 'ROLE_COMPADMIN') {
          this.isCompanyAdmin = true;
          this.getCompanyAdmin(user.id);
        }
      }
    });
  }

}
