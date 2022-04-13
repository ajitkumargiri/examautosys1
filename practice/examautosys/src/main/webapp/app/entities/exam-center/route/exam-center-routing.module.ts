import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExamCenterComponent } from '../list/exam-center.component';
import { ExamCenterDetailComponent } from '../detail/exam-center-detail.component';
import { ExamCenterUpdateComponent } from '../update/exam-center-update.component';
import { ExamCenterRoutingResolveService } from './exam-center-routing-resolve.service';

const examCenterRoute: Routes = [
  {
    path: '',
    component: ExamCenterComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExamCenterDetailComponent,
    resolve: {
      examCenter: ExamCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExamCenterUpdateComponent,
    resolve: {
      examCenter: ExamCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExamCenterUpdateComponent,
    resolve: {
      examCenter: ExamCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(examCenterRoute)],
  exports: [RouterModule],
})
export class ExamCenterRoutingModule {}
