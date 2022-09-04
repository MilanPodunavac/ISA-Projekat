import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomeRecordsComponent } from './income-records.component';

describe('IncomeRecordsComponent', () => {
  let component: IncomeRecordsComponent;
  let fixture: ComponentFixture<IncomeRecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IncomeRecordsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomeRecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
