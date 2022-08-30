import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageReservationViewComponent } from './cottage-reservation-view.component';

describe('CottageReservationViewComponent', () => {
  let component: CottageReservationViewComponent;
  let fixture: ComponentFixture<CottageReservationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageReservationViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageReservationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
