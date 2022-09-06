import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFishingInstructorComplaintComponent } from './new-fishing-instructor-complaint.component';

describe('NewFishingInstructorComplaintComponent', () => {
  let component: NewFishingInstructorComplaintComponent;
  let fixture: ComponentFixture<NewFishingInstructorComplaintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewFishingInstructorComplaintComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFishingInstructorComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
