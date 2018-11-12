export interface IUserloan {
  id?: number;
  cedulaCustomer?: number;
  typeIdCustomer?: string;
  userName?: string;
  password?: string;
}

export class Userloan implements IUserloan {
  constructor(
    public id?: number,
    public cedulaCustomer?: number,
    public typeIdCustomer?: string,
    public userName?: string,
    public password?: string
  ) {}
}
