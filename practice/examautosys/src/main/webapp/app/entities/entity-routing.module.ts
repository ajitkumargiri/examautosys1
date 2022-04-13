import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'university',
        data: { pageTitle: 'Universities' },
        loadChildren: () => import('./university/university.module').then(m => m.UniversityModule),
      },
      {
        path: 'college',
        data: { pageTitle: 'Colleges' },
        loadChildren: () => import('./college/college.module').then(m => m.CollegeModule),
      },
      {
        path: 'course',
        data: { pageTitle: 'Courses' },
        loadChildren: () => import('./course/course.module').then(m => m.CourseModule),
      },
      {
        path: 'branch',
        data: { pageTitle: 'Branches' },
        loadChildren: () => import('./branch/branch.module').then(m => m.BranchModule),
      },
      {
        path: 'academic-batch',
        data: { pageTitle: 'AcademicBatches' },
        loadChildren: () => import('./academic-batch/academic-batch.module').then(m => m.AcademicBatchModule),
      },
      {
        path: 'session',
        data: { pageTitle: 'Sessions' },
        loadChildren: () => import('./session/session.module').then(m => m.SessionModule),
      },
      {
        path: 'student',
        data: { pageTitle: 'Students' },
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'Addresses' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'exam',
        data: { pageTitle: 'Exams' },
        loadChildren: () => import('./exam/exam.module').then(m => m.ExamModule),
      },
      {
        path: 'exam-application-form',
        data: { pageTitle: 'ExamApplicationForms' },
        loadChildren: () => import('./exam-application-form/exam-application-form.module').then(m => m.ExamApplicationFormModule),
      },
      {
        path: 'subject-paper',
        data: { pageTitle: 'SubjectPapers' },
        loadChildren: () => import('./subject-paper/subject-paper.module').then(m => m.SubjectPaperModule),
      },
      {
        path: 'exam-center',
        data: { pageTitle: 'ExamCenters' },
        loadChildren: () => import('./exam-center/exam-center.module').then(m => m.ExamCenterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
