import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Userloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';
import { UserloanService } from './userloan.service';
import { UserloanComponent } from './userloan.component';
import { UserloanDetailComponent } from './userloan-detail.component';
import { UserloanUpdateComponent } from './userloan-update.component';
import { UserloanDeletePopupComponent } from './userloan-delete-dialog.component';
import { IUserloan } from 'app/shared/model/MasterLoanAuthentication/userloan.model';

@Injectable({ providedIn: 'root' })
export class UserloanResolve implements Resolve<IUserloan> {
  constructor(private service: UserloanService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Userloan> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Userloan>) => response.ok),
        map((userloan: HttpResponse<Userloan>) => userloan.body)
      );
    }
    return of(new Userloan());
  }
}

export const userloanRoute: Routes = [
  {
    path: 'userloan',
    component: UserloanComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userloans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userloan/:id/view',
    component: UserloanDetailComponent,
    resolve: {
      userloan: UserloanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userloans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userloan/new',
    component: UserloanUpdateComponent,
    resolve: {
      userloan: UserloanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userloans'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userloan/:id/edit',
    component: UserloanUpdateComponent,
    resolve: {
      userloan: UserloanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userloans'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userloanPopupRoute: Routes = [
  {
    path: 'userloan/:id/delete',
    component: UserloanDeletePopupComponent,
    resolve: {
      userloan: UserloanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userloans'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
