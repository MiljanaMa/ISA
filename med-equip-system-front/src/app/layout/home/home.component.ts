import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';

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

  constructor(private layoutService: LayoutService) { }

  ngOnInit(): void {
    this.getAllCompanies();
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

}
