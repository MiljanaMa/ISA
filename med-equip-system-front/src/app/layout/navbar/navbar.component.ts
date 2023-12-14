import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { CurrentUser } from 'src/app/auth/model/current-user.model';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{

  public currentUser: CurrentUser | undefined;

  constructor(private authService: AuthService){}

  async ngOnInit(): Promise<void> {

    if(window.localStorage.getItem('jwt')){
      await this.authService.setCurrentUser();    
    }

    this.authService.currentUser.subscribe(user => {
      if (user) {
        console.log("User(navbar): ", user.email, " Role: ", user.role?.name);
        this.currentUser = user;
      }
    });
  }

  onLogout(): void {
    this.authService.logout();
  }

}
