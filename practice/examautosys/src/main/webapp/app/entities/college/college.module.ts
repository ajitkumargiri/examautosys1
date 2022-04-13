import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CollegeComponent } from './list/college.component';
import { CollegeDetailComponent } from './detail/college-detail.component';
import { CollegeUpdateComponent } from './update/college-update.component';
import { CollegeDeleteDialogComponent } from './delete/college-delete-dialog.component';
import { CollegeRoutingModule } from './route/college-routing.module';

@NgModule({
  imports: [SharedModule, CollegeRoutingModule],
  declarations: [CollegeComponent, CollegeDetailComponent, CollegeUpdateComponent, CollegeDeleteDialogComponent],
  entryComponents: [CollegeDeleteDialogComponent],
})
export class CollegeModule {}
