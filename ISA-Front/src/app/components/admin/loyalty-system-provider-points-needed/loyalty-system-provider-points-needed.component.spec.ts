import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltySystemProviderPointsNeededComponent } from './loyalty-system-provider-points-needed.component';

describe('LoyaltySystemProviderPointsNeededComponent', () => {
  let component: LoyaltySystemProviderPointsNeededComponent;
  let fixture: ComponentFixture<LoyaltySystemProviderPointsNeededComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoyaltySystemProviderPointsNeededComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltySystemProviderPointsNeededComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
