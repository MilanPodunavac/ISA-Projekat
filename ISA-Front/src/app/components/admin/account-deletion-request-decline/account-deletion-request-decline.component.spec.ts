import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDeletionRequestDeclineComponent } from './account-deletion-request-decline.component';

describe('AccountDeletionRequestDeclineComponent', () => {
  let component: AccountDeletionRequestDeclineComponent;
  let fixture: ComponentFixture<AccountDeletionRequestDeclineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountDeletionRequestDeclineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDeletionRequestDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
