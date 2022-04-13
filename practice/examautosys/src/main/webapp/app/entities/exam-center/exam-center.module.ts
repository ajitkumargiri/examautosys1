import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExamCenterComponent } from './list/exam-center.component';
import { ExamCenterDetailComponent } from './detail/exam-center-detail.component';
import { ExamCenterUpdateComponent } from './update/exam-center-update.component';
import { ExamCenterDeleteDialogComponent } from './delete/exam-center-delete-dialog.component';
import { ExamCenterRoutingModule } from './route/exam-center-routing.module';

@NgModule({
  imports: [SharedModule, ExamCenterRoutingModule],
  declarations: [ExamCenterComponent, ExamCenterDetailComponent, ExamCenterUpdateComponent, ExamCenterDeleteDialogComponent],
  entryComponents: [ExamCenterDeleteDialogComponent],
})
export class ExamCenterModule {}
