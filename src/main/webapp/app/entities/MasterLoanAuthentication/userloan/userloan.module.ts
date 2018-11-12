import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MasterLoanAuthenticationSharedModule } from 'app/shared';
import {
  UserloanComponent,
  UserloanDetailComponent,
  UserloanUpdateComponent,
  UserloanDeletePopupComponent,
  UserloanDeleteDialogComponent,
  userloanRoute,
  userloanPopupRoute
} from './';

const ENTITY_STATES = [...userloanRoute, ...userloanPopupRoute];

@NgModule({
  imports: [MasterLoanAuthenticationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserloanComponent,
    UserloanDetailComponent,
    UserloanUpdateComponent,
    UserloanDeleteDialogComponent,
    UserloanDeletePopupComponent
  ],
  entryComponents: [UserloanComponent, UserloanUpdateComponent, UserloanDeleteDialogComponent, UserloanDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MasterLoanAuthenticationUserloanModule {}
