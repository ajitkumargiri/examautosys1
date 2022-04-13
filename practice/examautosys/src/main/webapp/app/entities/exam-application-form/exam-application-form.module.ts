import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExamApplicationFormComponent } from './list/exam-application-form.component';
import { ExamApplicationFormDetailComponent } from './detail/exam-application-form-detail.component';
import { ExamApplicationFormUpdateComponent } from './update/exam-application-form-update.component';
import { ExamApplicationFormDeleteDialogComponent } from './delete/exam-application-form-delete-dialog.component';
import { ExamApplicationFormRoutingModule } from './route/exam-application-form-routing.module';

@NgModule({
  imports: [SharedModule, ExamApplicationFormRoutingModule],
  declarations: [
    ExamApplicationFormComponent,
    ExamApplicationFormDetailComponent,
    ExamApplicationFormUpdateComponent,
    ExamApplicationFormDeleteDialogComponent,
  ],
  entryComponents: [ExamApplicationFormDeleteDialogComponent],
})
export class ExamApplicationFormModule {}
