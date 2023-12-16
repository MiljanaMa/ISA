export interface Role {
    id: number;
    name: string;
}

export interface CurrentUser {
    id: number;
    email: string;
    role: Role | null;
}