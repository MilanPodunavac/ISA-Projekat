import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltySystemProviderPointsComponent } from './loyalty-system-provider-points.component';

describe('LoyaltySystemProviderPointsComponent', () => {
  let component: LoyaltySystemProviderPointsComponent;
  let fixture: ComponentFixture<LoyaltySystemProviderPointsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoyaltySystemProviderPointsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltySystemProviderPointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
