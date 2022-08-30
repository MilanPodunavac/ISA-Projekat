import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingTripProfileComponent } from './fishing-trip-profile.component';

describe('FishingTripProfileComponent', () => {
  let component: FishingTripProfileComponent;
  let fixture: ComponentFixture<FishingTripProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingTripProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingTripProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
