import { Component, OnInit } from '@angular/core';
import { CompanyEquipment } from '../model/equipment.model';
import { LayoutService } from '../layout.service';
import { AuthService } from 'src/app/auth/auth.service';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { CompanyAdmin } from '../model/companyAdmin.model';

@Component({
  selector: 'app-equipment-search',
  templateUrl: './equipment-search.component.html',
  styleUrls: ['./equipment-search.component.css']
})

  export class EquipmentSearchComponent implements OnInit {
  public equipments: CompanyEquipment[] = [];
  public filteredEquipments: CompanyEquipment[] = [];
  public inputSearch: string = '';
  public inputPrice: number = 0;
  public inputType: string = '';
  public currentUser: CurrentUser | undefined;
  public companyAdmin: CompanyAdmin | undefined;
  public adminsCompanyId: number = 0;
  public isCompanyAdmin: boolean = false;


  constructor(private layoutService: LayoutService, private authService: AuthService){ }

  ngOnInit(): void {
    this.getAllEquipments();
    this.getCurrentUser();
  }
  getAllEquipments(): void {
    this.layoutService.getEquipments().subscribe({
      next: (data) => {
        this.equipments = data;
        this.filteredEquipments = data;
        this.filterEquipmentsByRole();
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

  searchEquipments(): void{
    this.filteredEquipments = this.equipments.filter(
      equipment => equipment.price >= this.inputPrice
    );
  
    if (this.inputType !== '') {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.type === this.inputType
      );
    }
  
    if (this.inputSearch !== '') {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.name.toLowerCase().includes(this.inputSearch.toLowerCase())
      );
    }

    if (this.isCompanyAdmin) {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.company.id === this.adminsCompanyId
      );
    }
  }

  clearSearch(): void{
    this.inputPrice = 0;
    this.inputType = '';
    this.inputSearch = '';
    this.filteredEquipments = this.equipments;
    this.filterEquipmentsByRole();
  }

  getCompanyAdmin(userId: number): void {
    this.authService.getCompanyAdminByUserId(userId).subscribe({
      next: (companyAdmin) => {
        console.log('company admin whole:' , companyAdmin);
        this.companyAdmin = companyAdmin;
        console.log("this.companyAdmin: ", this.companyAdmin);
          
        this.adminsCompanyId = companyAdmin.companyId || 0;        
      },
      error: (error) => {
        console.error('Error fetching company admin:', error);
      }
    });
  }

  filterEquipmentsByRole(): void {
    if (this.isCompanyAdmin) {
      this.filteredEquipments = this.filteredEquipments.filter(
        equipment => equipment.company.id === this.adminsCompanyId
      );
    }
  }
}

