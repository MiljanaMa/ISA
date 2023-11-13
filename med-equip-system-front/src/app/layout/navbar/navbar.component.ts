import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { LayoutService } from '../layout.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  users: User[] = [];

  constructor(private layoutService: LayoutService){}

  ngOnInit(): void {

  }

}
