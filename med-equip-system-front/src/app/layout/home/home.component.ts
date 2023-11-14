import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { Company } from '../model/company.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  public companies: Company[] = []; 
  searchQuery: string = '';
  ratingFilter: number = 0;

  constructor(private layoutService: LayoutService) {}
  ngOnInit(): void {
    this.getAllCompanies();
  }
  getAllCompanies(): void {
    this.layoutService.getCompanies().subscribe({
      next: (data) =>  {
        this.companies = data;
      }
    });
  }

  searchCompanies() {
    // Implement the search logic based on searchQuery
    // Update this.companies with filtered results
  }

  filterByRating() {
    // Implement the filtering logic based on ratingFilter
    // Update this.companies with filtered results
  }
}
