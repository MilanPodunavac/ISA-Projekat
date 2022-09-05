import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBoatAvailabilityPeriodComponent } from './new-boat-availability-period.component';

describe('NewBoatAvailabilityPeriodComponent', () => {
  let component: NewBoatAvailabilityPeriodComponent;
  let fixture: ComponentFixture<NewBoatAvailabilityPeriodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBoatAvailabilityPeriodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBoatAvailabilityPeriodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
