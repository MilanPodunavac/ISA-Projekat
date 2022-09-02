import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailablePeriodComponent } from './available-period.component';

describe('AvailablePeriodComponent', () => {
  let component: AvailablePeriodComponent;
  let fixture: ComponentFixture<AvailablePeriodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailablePeriodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailablePeriodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
