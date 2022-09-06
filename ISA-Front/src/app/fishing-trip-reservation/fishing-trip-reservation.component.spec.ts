import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingTripReservationComponent } from './fishing-trip-reservation.component';

describe('FishingTripReservationComponent', () => {
  let component: FishingTripReservationComponent;
  let fixture: ComponentFixture<FishingTripReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingTripReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingTripReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
