import { Component, OnInit } from '@angular/core';
import { CompanyAdminService } from '../company-admin.service';
import { ActivatedRoute } from '@angular/router';
import { Client } from 'src/app/client/model/client.model';

@Component({
  selector: 'app-user-reservations',
  templateUrl: './user-reservations.component.html',
  styleUrls: ['./user-reservations.component.css']
})
export class UserReservationsComponent implements OnInit{

  constructor(private service: CompanyAdminService, private route: ActivatedRoute){}

  displayedColumns: string[] = ['email', 'firstName', 'lastName', 'phoneNumber', 'points'];

  dataSource: Client[] = []; 
  ngOnInit(): void {
   
    this.route.params.subscribe(params =>{
      const id = +params['id']; 
      if(id){
      this.service.getUsersThatReserved(id).subscribe(
        (response) => {
          this.dataSource = response; 
        }
      );
      }
    }); 
  }
}

