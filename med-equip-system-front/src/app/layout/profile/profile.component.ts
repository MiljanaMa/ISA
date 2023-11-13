import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../layout.service';
import { User, UserType } from '../model/user.model';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{

  public user: User | undefined;
  public userId: number = -1;

  constructor(private layoutService: LayoutService, private currentRoute: ActivatedRoute){}

  ngOnInit(): void {
    this.currentRoute.params.subscribe((params: Params) => {
      this.userId = params['id'];
    });

    this.getById();
  }

  getById(): void {
    this.layoutService.getUserById(this.userId).subscribe({
      next: (user) =>  {
        this.user = user;
      }
    });
  }

}
