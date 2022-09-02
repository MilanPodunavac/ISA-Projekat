import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBoatReservationComponent } from './new-boat-reservation.component';

describe('NewBoatReservationComponent', () => {
  let component: NewBoatReservationComponent;
  let fixture: ComponentFixture<NewBoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
