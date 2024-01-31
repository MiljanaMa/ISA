import { Company } from "src/app/layout/model/company.model";
import { CompanyEquipment } from "src/app/layout/model/equipment.model";

export interface Contract{
    id?: number;
    date: number;
    time: string;
    total: number;
    company: Company;
    companyEquipment: CompanyEquipment;
    status: ContractStatus;
}
export enum ContractStatus {

    INACTIVE = 'INACTIVE',
    ACTIVE = 'ACTIVE',
    CANCELLED = 'CANCELLED',
    INVALID = 'INVALID',
  }