import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router } from "@angular/router";
import { AuthService } from "../auth.service";
import { CurrentUser } from "../model/current-user.model";
import { Observable, map, take } from "rxjs";
import { __await } from "tslib";

@Injectable({
    providedIn: 'root',
  })
  export class AuthGuard implements CanActivate {
    constructor( private router: Router, private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
      const requiredRoleName = route.data['requiredRole'] as string;
        return  this.authService.getCurrentUser().pipe(
          take(1),
          map((user: CurrentUser | null) => {
            if (user && user.email !== '') {
              return user.role !== null && user.role.name === requiredRoleName;     
            } else {
              this.router.navigate(['']);
              return false;
            }
          })
        );
      }

     
}