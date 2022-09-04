import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationsCommentaryComponent } from './reservations-commentary.component';

describe('ReservationsCommentaryComponent', () => {
  let component: ReservationsCommentaryComponent;
  let fixture: ComponentFixture<ReservationsCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReservationsCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationsCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
