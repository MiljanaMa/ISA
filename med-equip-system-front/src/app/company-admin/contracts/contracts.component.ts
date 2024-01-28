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
  public today: Date = new Date();
  constructor(private authService: AuthService, private service: CompanyAdminService
  ) { }

  ngOnInit(): void {
    this.setDate();
    this.getContracts();
  }

  private setDate() {
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    var tomorrow = new Date(currentDate);
    tomorrow.setDate(currentDate.getDate() + 2);
    this.today = tomorrow;
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
  public parseDateString(date: string) {
    const parts = date.split('.');
    const dateObject = new Date(Number(parts[2]), Number(parts[1]) - 1, Number(parts[0]));
    return dateObject;
  }
}
