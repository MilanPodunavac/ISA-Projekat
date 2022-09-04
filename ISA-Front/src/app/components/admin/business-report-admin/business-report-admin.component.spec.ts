import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessReportAdminComponent } from './business-report-admin.component';

describe('BusinessReportAdminComponent', () => {
  let component: BusinessReportAdminComponent;
  let fixture: ComponentFixture<BusinessReportAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessReportAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessReportAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
