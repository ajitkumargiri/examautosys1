import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UniversityComponent } from '../list/university.component';
import { UniversityDetailComponent } from '../detail/university-detail.component';
import { UniversityUpdateComponent } from '../update/university-update.component';
import { UniversityRoutingResolveService } from './university-routing-resolve.service';

const universityRoute: Routes = [
  {
    path: '',
    component: UniversityComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UniversityDetailComponent,
    resolve: {
      university: UniversityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UniversityUpdateComponent,
    resolve: {
      university: UniversityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UniversityUpdateComponent,
    resolve: {
      university: UniversityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(universityRoute)],
  exports: [RouterModule],
})
export class UniversityRoutingModule {}
