import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, Routes } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { CurrentUser } from 'src/app/auth/model/current-user.model';
import { SystemAdmin } from 'src/app/system-admin/model/system-admin.model';
import { ChangePasswordComponent } from 'src/app/system-admin/change-password/change-password.component';
import { LayoutService } from '../layout.service';
import { CompanyAdmin } from '../model/companyAdmin.model';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{

  public currentUser: CurrentUser | undefined;
  public systemAdmin: SystemAdmin | undefined;
  public isMainAdmin: boolean = false;
  public isInitPasswordChanged: boolean = false;
  public companyId: Number|undefined; 
  public adminId: Number|undefined; 

  constructor(private authService: AuthService, private router: Router, private layoutService: LayoutService){}

  async ngOnInit(): Promise<void> {
    if (window.localStorage.getItem('jwt')) {
      await this.authService.setCurrentUser();
    }
  
    this.authService.currentUser.subscribe((user) => {
      if (user) {
        console.log("User(navbar): ", user.email, " Role: ", user.role?.name);
        this.currentUser = user;
  
        if (user.role?.name === 'ROLE_SYSADMIN') {
          this.getSystemAdmin(user.id);
        }
        else if(user.role?.name === 'ROLE_COMPADMIN'){
          this.layoutService.getAdminOkByUserId(user.id).subscribe((response) => {
            this.companyId = response.company.id;  
            this.adminId = response.id; 
          })
        }
      }
    });

    this.handleInitPasswordChanged();
  }
  
  getSystemAdmin(userId: number): void {
    this.authService.getByUserId(userId).subscribe({
      next: (systemAdmin) => {
        console.log('system admin whole:' , systemAdmin);
        this.systemAdmin = systemAdmin;
        console.log("this.systemAdmin: ", this.systemAdmin);
        if(this.systemAdmin.main){  // skrnavo, ali mora ovako za sad
          this.isMainAdmin = true;
        }
        if(this.systemAdmin.inititialPasswordChanged){
          this.isInitPasswordChanged = true;
        }
        this.handleInitPasswordChanged();
      },
      error: (error) => {
        console.error('Error fetching system admin:', error);
      }
    });
  }
  
  handleInitPasswordChanged(): void {
    if (this.isInitPasswordChanged) {
      if (window.confirm("You must change your initial password 123")) {
        this.router.navigate(['/systemAdminChangePassword']);
      }
    }
  }

  onLogout(): void {
    this.authService.logout();
  }

}
