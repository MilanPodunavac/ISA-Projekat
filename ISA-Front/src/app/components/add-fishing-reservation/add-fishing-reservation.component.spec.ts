import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFishingReservationComponent } from './add-fishing-reservation.component';

describe('AddFishingReservationComponent', () => {
  let component: AddFishingReservationComponent;
  let fixture: ComponentFixture<AddFishingReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFishingReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFishingReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
