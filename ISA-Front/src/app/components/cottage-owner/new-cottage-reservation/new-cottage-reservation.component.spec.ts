import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageReservationComponent } from './new-cottage-reservation.component';

describe('NewCottageReservationComponent', () => {
  let component: NewCottageReservationComponent;
  let fixture: ComponentFixture<NewCottageReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
