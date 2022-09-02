import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDeletionRequestComponent } from './account-deletion-request.component';

describe('AccountDeletionRequestComponent', () => {
  let component: AccountDeletionRequestComponent;
  let fixture: ComponentFixture<AccountDeletionRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountDeletionRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDeletionRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
