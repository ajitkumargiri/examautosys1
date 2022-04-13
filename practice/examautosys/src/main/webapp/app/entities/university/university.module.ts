import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UniversityComponent } from './list/university.component';
import { UniversityDetailComponent } from './detail/university-detail.component';
import { UniversityUpdateComponent } from './update/university-update.component';
import { UniversityDeleteDialogComponent } from './delete/university-delete-dialog.component';
import { UniversityRoutingModule } from './route/university-routing.module';

@NgModule({
  imports: [SharedModule, UniversityRoutingModule],
  declarations: [UniversityComponent, UniversityDetailComponent, UniversityUpdateComponent, UniversityDeleteDialogComponent],
  entryComponents: [UniversityDeleteDialogComponent],
})
export class UniversityModule {}
