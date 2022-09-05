import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatReservationOwnerCommentaryComponent } from './boat-reservation-owner-commentary.component';

describe('BoatReservationOwnerCommentaryComponent', () => {
  let component: BoatReservationOwnerCommentaryComponent;
  let fixture: ComponentFixture<BoatReservationOwnerCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatReservationOwnerCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatReservationOwnerCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
