import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingInstructorListComponent } from './fishing-instructor-list.component';

describe('FishingInstructorListComponent', () => {
  let component: FishingInstructorListComponent;
  let fixture: ComponentFixture<FishingInstructorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingInstructorListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingInstructorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
