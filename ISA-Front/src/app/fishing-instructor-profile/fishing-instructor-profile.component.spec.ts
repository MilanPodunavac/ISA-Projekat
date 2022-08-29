import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingInstructorProfileComponent } from './fishing-instructor-profile.component';

describe('FishingInstructorProfileComponent', () => {
  let component: FishingInstructorProfileComponent;
  let fixture: ComponentFixture<FishingInstructorProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingInstructorProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingInstructorProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
