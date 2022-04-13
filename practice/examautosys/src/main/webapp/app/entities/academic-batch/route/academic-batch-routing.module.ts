import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AcademicBatchComponent } from '../list/academic-batch.component';
import { AcademicBatchDetailComponent } from '../detail/academic-batch-detail.component';
import { AcademicBatchUpdateComponent } from '../update/academic-batch-update.component';
import { AcademicBatchRoutingResolveService } from './academic-batch-routing-resolve.service';

const academicBatchRoute: Routes = [
  {
    path: '',
    component: AcademicBatchComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcademicBatchDetailComponent,
    resolve: {
      academicBatch: AcademicBatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcademicBatchUpdateComponent,
    resolve: {
      academicBatch: AcademicBatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcademicBatchUpdateComponent,
    resolve: {
      academicBatch: AcademicBatchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(academicBatchRoute)],
  exports: [RouterModule],
})
export class AcademicBatchRoutingModule {}
