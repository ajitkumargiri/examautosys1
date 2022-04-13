import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubjectPaperComponent } from '../list/subject-paper.component';
import { SubjectPaperDetailComponent } from '../detail/subject-paper-detail.component';
import { SubjectPaperUpdateComponent } from '../update/subject-paper-update.component';
import { SubjectPaperRoutingResolveService } from './subject-paper-routing-resolve.service';

const subjectPaperRoute: Routes = [
  {
    path: '',
    component: SubjectPaperComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubjectPaperDetailComponent,
    resolve: {
      subjectPaper: SubjectPaperRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubjectPaperUpdateComponent,
    resolve: {
      subjectPaper: SubjectPaperRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubjectPaperUpdateComponent,
    resolve: {
      subjectPaper: SubjectPaperRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subjectPaperRoute)],
  exports: [RouterModule],
})
export class SubjectPaperRoutingModule {}
