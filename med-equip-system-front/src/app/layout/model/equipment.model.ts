import { Company } from "./company.model";

export enum EquipmentType{
    DIAGNOSTIC = 'DIAGNOSTIC',
    LIFE_SUPPORT = 'LIFE_SUPPORT',
    LABORATORY = 'LABORATORY',
    SURGICAL = 'SURGICAL',
    OTHER = 'OTHER'
}

export interface CompanyEquipment{
    id?: number;
    name: string;
    type: EquipmentType;
    description: string;
    price: number;
    company: Company;
    count: number;
}