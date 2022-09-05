import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltySystemClientPointsNeededComponent } from './loyalty-system-client-points-needed.component';

describe('LoyaltySystemClientPointsNeededComponent', () => {
  let component: LoyaltySystemClientPointsNeededComponent;
  let fixture: ComponentFixture<LoyaltySystemClientPointsNeededComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoyaltySystemClientPointsNeededComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltySystemClientPointsNeededComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
