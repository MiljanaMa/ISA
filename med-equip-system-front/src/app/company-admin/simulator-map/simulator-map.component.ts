import { Component, Input, OnInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-simulator-map',
  templateUrl: './simulator-map.component.html',
  styleUrls: ['./simulator-map.component.css']
})
export class SimulatorMapComponent implements OnInit{

  @Input() markerLatitude: number = 0;
  @Input() markerLongitude: number = 0;
  private marker: any;
  public map: any;

  ngOnInit(): void {
    this.initMap();
    this.addMarker([this.markerLatitude, this.markerLongitude]);
  }

  initMap(): void {
    this.map = L.map('map').setView([45.2396, 19.8227], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(this.map);
  }

  addMarker(coordinates: L.LatLngExpression): void {
    this.marker = L.marker(coordinates).addTo(this.map);
  }

  updateMarkerPosition(newLatitude: number, newLongitude: number): void {
    if (this.marker) {
      this.map.removeLayer(this.marker);
    }

    this.addMarker([newLatitude, newLongitude]);
  }
}
