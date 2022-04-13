import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AcademicBatchComponent } from './list/academic-batch.component';
import { AcademicBatchDetailComponent } from './detail/academic-batch-detail.component';
import { AcademicBatchUpdateComponent } from './update/academic-batch-update.component';
import { AcademicBatchDeleteDialogComponent } from './delete/academic-batch-delete-dialog.component';
import { AcademicBatchRoutingModule } from './route/academic-batch-routing.module';

@NgModule({
  imports: [SharedModule, AcademicBatchRoutingModule],
  declarations: [AcademicBatchComponent, AcademicBatchDetailComponent, AcademicBatchUpdateComponent, AcademicBatchDeleteDialogComponent],
  entryComponents: [AcademicBatchDeleteDialogComponent],
})
export class AcademicBatchModule {}
