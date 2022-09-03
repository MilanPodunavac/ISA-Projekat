import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatReservationComponent } from './boat-reservation.component';

describe('BoatReservationComponent', () => {
  let component: BoatReservationComponent;
  let fixture: ComponentFixture<BoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
