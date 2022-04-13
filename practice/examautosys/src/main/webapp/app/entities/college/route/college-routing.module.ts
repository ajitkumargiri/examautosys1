import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CollegeComponent } from '../list/college.component';
import { CollegeDetailComponent } from '../detail/college-detail.component';
import { CollegeUpdateComponent } from '../update/college-update.component';
import { CollegeRoutingResolveService } from './college-routing-resolve.service';

const collegeRoute: Routes = [
  {
    path: '',
    component: CollegeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollegeDetailComponent,
    resolve: {
      college: CollegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollegeUpdateComponent,
    resolve: {
      college: CollegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollegeUpdateComponent,
    resolve: {
      college: CollegeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(collegeRoute)],
  exports: [RouterModule],
})
export class CollegeRoutingModule {}
