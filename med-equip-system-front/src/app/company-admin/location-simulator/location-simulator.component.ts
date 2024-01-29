import { Component, OnInit } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { CurrentLocation } from '../model/currentLocation.model';


@Component({
  selector: 'app-location-simulator',
  templateUrl: './location-simulator.component.html',
  styleUrls: ['./location-simulator.component.css']
})
export class LocationSimulatorComponent{

  private serverUrl = 'http://localhost:8092/socket'
  private stompClient: any;

  isLoaded: boolean = false;
  isCustomSocketOpened = false;
  currentLocation:  CurrentLocation = { id: 0, latitude: 0.0, longitude: 0.1 };

  constructor() { }

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
        const locationResult: CurrentLocation = JSON.parse(message.body);
        console.log("REZULTAT: ",locationResult);
        this.handleResult(message);
      });
    }
  }

  clickButton() : void {

      this.stompClient.send("/socket-subscriber/send/message", {},  JSON.stringify(this.currentLocation));
  }

  handleResult(message: { body: string; }) {
    if (message.body) {
      let locationResult: CurrentLocation = JSON.parse(message.body);
      this.currentLocation = locationResult;
    }
  }

}
