import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

type EntityResponseType = HttpResponse<IUserloan>;
type EntityArrayResponseType = HttpResponse<IUserloan[]>;

@Injectable({ providedIn: 'root' })
export class UserloanService {
  public resourceUrl = SERVER_API_URL + 'api/userloans';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/userloans';

  constructor(private http: HttpClient) {}

  create(userloan: IUserloan): Observable<EntityResponseType> {
    return this.http.post<IUserloan>(this.resourceUrl, userloan, { observe: 'response' });
  }

  update(userloan: IUserloan): Observable<EntityResponseType> {
    return this.http.put<IUserloan>(this.resourceUrl, userloan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserloan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserloan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserloan[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
