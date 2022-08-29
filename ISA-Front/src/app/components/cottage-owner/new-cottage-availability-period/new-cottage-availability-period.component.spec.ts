import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageAvailabilityPeriodComponent } from './new-cottage-availability-period.component';

describe('NewCottageAvailabilityPeriodComponent', () => {
  let component: NewCottageAvailabilityPeriodComponent;
  let fixture: ComponentFixture<NewCottageAvailabilityPeriodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageAvailabilityPeriodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageAvailabilityPeriodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
