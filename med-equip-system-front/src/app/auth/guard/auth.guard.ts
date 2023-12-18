import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../auth.service";
import { CurrentUser } from "../model/current-user.model";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
  })
  export class AuthGuard implements CanActivate {
    constructor( private router: Router, private authService: AuthService) {}
  
    canActivate(): boolean{
          
      const user: Observable<CurrentUser> = this.authService.getCurrentUser();
      if (user) {
        this.router.navigate(['/login']);
        return false;
      }
      return true;
    }
  }