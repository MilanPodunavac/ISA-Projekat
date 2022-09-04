import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplaintResponseFishingInstructorComponent } from './complaint-response-fishing-instructor.component';

describe('ComplaintResponseFishingInstructorComponent', () => {
  let component: ComplaintResponseFishingInstructorComponent;
  let fixture: ComponentFixture<ComplaintResponseFishingInstructorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComplaintResponseFishingInstructorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComplaintResponseFishingInstructorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
