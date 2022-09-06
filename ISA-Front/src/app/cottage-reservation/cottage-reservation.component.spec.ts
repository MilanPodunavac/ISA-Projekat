import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageReservationComponent } from './cottage-reservation.component';

describe('CottageReservationComponent', () => {
  let component: CottageReservationComponent;
  let fixture: ComponentFixture<CottageReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
