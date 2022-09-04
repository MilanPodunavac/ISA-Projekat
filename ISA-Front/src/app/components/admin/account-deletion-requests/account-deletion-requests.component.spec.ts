import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDeletionRequestsComponent } from './account-deletion-requests.component';

describe('AccountDeletionRequestsComponent', () => {
  let component: AccountDeletionRequestsComponent;
  let fixture: ComponentFixture<AccountDeletionRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountDeletionRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDeletionRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
