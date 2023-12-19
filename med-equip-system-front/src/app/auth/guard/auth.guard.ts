import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../auth.service";
import { CurrentUser } from "../model/current-user.model";
import { Observable, map, take } from "rxjs";
import { __await } from "tslib";

@Injectable({
    providedIn: 'root',
  })
  export class AuthGuard implements CanActivate {
    constructor( private router: Router, private authService: AuthService) {}

    canActivate(): Observable<boolean> {
        return  this.authService.getCurrentUser().pipe(
          take(1),
          map((user: CurrentUser | null) => {
            if (user && user.email !== '') {
              return true; 
            } else {
              this.router.navigate(['/login']);
              return false;
            }
          })
        );
      }
      
}