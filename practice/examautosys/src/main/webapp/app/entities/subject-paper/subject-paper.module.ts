import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubjectPaperComponent } from './list/subject-paper.component';
import { SubjectPaperDetailComponent } from './detail/subject-paper-detail.component';
import { SubjectPaperUpdateComponent } from './update/subject-paper-update.component';
import { SubjectPaperDeleteDialogComponent } from './delete/subject-paper-delete-dialog.component';
import { SubjectPaperRoutingModule } from './route/subject-paper-routing.module';

@NgModule({
  imports: [SharedModule, SubjectPaperRoutingModule],
  declarations: [SubjectPaperComponent, SubjectPaperDetailComponent, SubjectPaperUpdateComponent, SubjectPaperDeleteDialogComponent],
  entryComponents: [SubjectPaperDeleteDialogComponent],
})
export class SubjectPaperModule {}
