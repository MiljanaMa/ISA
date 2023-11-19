import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import * as L from 'leaflet';
import { EventEmitter } from '@angular/core';
import { Output } from '@angular/core';
import { Location } from '../model/location.model';
import { LayoutService } from '../layout.service';

@Component({
  selector: 'app-company-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class CompanyMapComponent implements OnInit {
  @Input() companyLocation: Location | undefined;
  @Input() editable = false; 
  @Output() locationUpdated: EventEmitter<Location> = new EventEmitter<Location>();

  constructor(private layoutService: LayoutService) { }

  private map: any;
  private marker: any;

  ngOnInit(): void {
    if (this.companyLocation) {
      this.initMap(this.companyLocation);
    }
  }

  initMap(location: Location): void {
    console.log(location); 
    this.map = L.map('map').setView([location.latitude, location.longitude], 13);
    console.log("da"); 

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(this.map);

    this.map.on('click', (e: any) => {
     

      this.layoutService.reverseGeocode(e.latlng.lat, e.latlng.lng).subscribe((data: any) =>{
          if(this.companyLocation){
            
            const newLocation: Location = {
                id: this.companyLocation?.id,
                latitude: data.lat, 
                longitude: data.lon, 
                street: data.address.road, 
                streetNumber: data.address.house_number, 
                city: data.address.city, 
                country: data.address.country, 
                postcode: parseInt(data.address.postcode), 



            }; 
            this.updateLocation(newLocation);
           
        }
      });
     
    });

    this.marker = L.marker([location.latitude, location.longitude]).addTo(this.map)
      .bindPopup('Company Location')
      .openPopup();
  }

  updateLocation(newLocation: Location): void {
    if(this.editable){
      if (this.marker) {
        this.map.removeLayer(this.marker); 
      }

      this.marker = L.marker([newLocation.latitude, newLocation.longitude]).addTo(this.map)
        .bindPopup('Company Location')
        .openPopup();

      this.locationUpdated.emit(newLocation); 
  }
  }

}
