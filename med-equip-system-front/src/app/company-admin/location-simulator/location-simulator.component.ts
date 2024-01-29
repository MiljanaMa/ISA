import { Component, OnInit, ViewChild } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { CurrentLocation } from '../model/currentLocation.model';
import { SimulatorMapComponent } from '../simulator-map/simulator-map.component';
import { CompanyAdminService } from '../company-admin.service';


@Component({
  selector: 'app-location-simulator',
  templateUrl: './location-simulator.component.html',
  styleUrls: ['./location-simulator.component.css']
})
export class LocationSimulatorComponent{

  @ViewChild(SimulatorMapComponent) mapComponent!: SimulatorMapComponent;
  private serverUrl = 'http://localhost:8092/socket'
  private stompClient: any;

  isLoaded: boolean = false;
  isCustomSocketOpened = false;
  currentLocation:  CurrentLocation = {latitude: 45.240025, longitude: 19.825688};

  ngOnInit() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.isLoaded = true;
      that.openGlobalSocket()
    });

  }

  openGlobalSocket() {
    if (this.isLoaded) {
      this.stompClient.subscribe("/socket-publisher", (message: { body: string; }) => {
        this.handleResult(message);
        this.mapComponent.updateMarkerPosition(this.currentLocation.latitude, this.currentLocation.longitude);
      });
    }
  }

  handleResult(message: { body: string; }) {
    if (message.body) {
      let locationResult: CurrentLocation = JSON.parse(message.body);
      this.currentLocation = locationResult;
    }
  }

  startDelivery() : void {
       this.stompClient.send("/socket-subscriber/send/message", {},  JSON.stringify(this.currentLocation));
  }

}
