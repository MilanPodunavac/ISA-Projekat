import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatReservationViewComponent } from './boat-reservation-view.component';

describe('BoatReservationViewComponent', () => {
  let component: BoatReservationViewComponent;
  let fixture: ComponentFixture<BoatReservationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatReservationViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatReservationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
