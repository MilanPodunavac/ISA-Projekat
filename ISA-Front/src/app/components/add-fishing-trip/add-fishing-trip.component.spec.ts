import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFishingTripComponent } from './add-fishing-trip.component';

describe('AddFishingTripComponent', () => {
  let component: AddFishingTripComponent;
  let fixture: ComponentFixture<AddFishingTripComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFishingTripComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFishingTripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
