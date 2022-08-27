import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingInstructorComponent } from './fishing-instructor.component';

describe('FishingInstructorComponent', () => {
  let component: FishingInstructorComponent;
  let fixture: ComponentFixture<FishingInstructorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingInstructorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingInstructorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
