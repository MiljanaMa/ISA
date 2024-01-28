import { Component, OnInit } from '@angular/core';
import { Contract, ContractStatus } from '../model/contract.model';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyAdminService } from '../company-admin.service';

@Component({
  selector: 'app-contracts',
  templateUrl: './contracts.component.html',
  styleUrls: ['./contracts.component.css']
})
export class ContractsComponent implements OnInit {
  public contracts: Contract[] = [];

  constructor(private authService: AuthService, private service: CompanyAdminService) { }

  ngOnInit(): void {
    this.getContracts();
  }

  getContracts(): void {
    this.service.getContracts().subscribe(
      (data: Contract[]) => {
        this.contracts = data;
      });
  }
  cancel(contract: Contract): void {
    this.service.cancelContract(contract).subscribe(
      (data: Contract) => {
        contract = data;
        alert("Delivery is cancelled for this month");
      });
  }
  formatTime(time: any): String {
    if (!time || time.length !== 2)
      return '';

    const [hours, minutes] = time;
    const formattedHours = this.padWithZero(hours);
    const formattedMinutes = this.padWithZero(minutes);

    return `${formattedHours}:${formattedMinutes}`;
  }
  private padWithZero(value: number | string): string {
    const stringValue = String(value);
    return stringValue.length === 1 ? '0' + stringValue : stringValue;
  }
  public check(date: number): boolean{
    if(date === 1)
      return new Date().getDate() != 1
    return this.addDays(1).getDate() <= date;
  }
  private addDays(days: number): Date {
    var date = new Date();
    date.setDate(date.getDate() + days);
    return date;
  }
}
