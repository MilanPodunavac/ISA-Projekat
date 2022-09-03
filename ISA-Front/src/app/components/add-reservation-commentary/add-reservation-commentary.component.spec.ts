import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReservationCommentaryComponent } from './add-reservation-commentary.component';

describe('AddReservationCommentaryComponent', () => {
  let component: AddReservationCommentaryComponent;
  let fixture: ComponentFixture<AddReservationCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddReservationCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReservationCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
