import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDeletionRequestAcceptComponent } from './account-deletion-request-accept.component';

describe('AccountDeletionRequestAcceptComponent', () => {
  let component: AccountDeletionRequestAcceptComponent;
  let fixture: ComponentFixture<AccountDeletionRequestAcceptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountDeletionRequestAcceptComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDeletionRequestAcceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
