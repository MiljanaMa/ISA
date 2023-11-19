import { Component, OnInit } from '@angular/core';
import { CompanyEquipment } from '../model/equipment.model';
import { LayoutService } from '../layout.service';

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

  constructor(private layoutService: LayoutService){ }

  ngOnInit(): void {
    this.getAllEquipments();
  }
  getAllEquipments(): void {
    this.layoutService.getEquipments().subscribe({
      next: (data) => {
        this.equipments = data;
        this.filteredEquipments = data;
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
  }

  clearSearch(): void{
    this.inputPrice = 0;
    this.inputType = '';
    this.inputSearch = '';
    this.filteredEquipments = this.equipments;
  }
}

