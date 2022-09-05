import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoyaltySystemClientPointsComponent } from './loyalty-system-client-points.component';

describe('LoyaltySystemClientPointsComponent', () => {
  let component: LoyaltySystemClientPointsComponent;
  let fixture: ComponentFixture<LoyaltySystemClientPointsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoyaltySystemClientPointsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltySystemClientPointsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
