import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFishingInstructorReviewComponent } from './new-fishing-instructor-review.component';

describe('NewFishingInstructorReviewComponent', () => {
  let component: NewFishingInstructorReviewComponent;
  let fixture: ComponentFixture<NewFishingInstructorReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewFishingInstructorReviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFishingInstructorReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
