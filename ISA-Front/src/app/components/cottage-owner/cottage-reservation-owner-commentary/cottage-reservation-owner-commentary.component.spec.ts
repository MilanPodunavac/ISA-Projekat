import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageReservationOwnerCommentaryComponent } from './cottage-reservation-owner-commentary.component';

describe('CottageReservationOwnerCommentaryComponent', () => {
  let component: CottageReservationOwnerCommentaryComponent;
  let fixture: ComponentFixture<CottageReservationOwnerCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageReservationOwnerCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageReservationOwnerCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
