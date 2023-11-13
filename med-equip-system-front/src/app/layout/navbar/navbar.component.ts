import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { LayoutService } from '../layout.service';
import { PagedResult } from 'src/app/shared/model/paged-result';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  users: User[] = [];

  constructor(private layoutService: LayoutService){}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void{
    this.layoutService.getUsers().subscribe({
      next: (response: Array<User>) => {
        this.users = response;
        console.log("Uslo");
        console.log(response);
      },
      error: () => {
        console.log("greska");
      }
    });
  }
}
