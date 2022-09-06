import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBoatComplaintComponent } from './new-boat-complaint.component';

describe('NewBoatComplaintComponent', () => {
  let component: NewBoatComplaintComponent;
  let fixture: ComponentFixture<NewBoatComplaintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBoatComplaintComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBoatComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
