import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExamApplicationFormComponent } from '../list/exam-application-form.component';
import { ExamApplicationFormDetailComponent } from '../detail/exam-application-form-detail.component';
import { ExamApplicationFormUpdateComponent } from '../update/exam-application-form-update.component';
import { ExamApplicationFormRoutingResolveService } from './exam-application-form-routing-resolve.service';

const examApplicationFormRoute: Routes = [
  {
    path: '',
    component: ExamApplicationFormComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExamApplicationFormDetailComponent,
    resolve: {
      examApplicationForm: ExamApplicationFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExamApplicationFormUpdateComponent,
    resolve: {
      examApplicationForm: ExamApplicationFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExamApplicationFormUpdateComponent,
    resolve: {
      examApplicationForm: ExamApplicationFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(examApplicationFormRoute)],
  exports: [RouterModule],
})
export class ExamApplicationFormRoutingModule {}
