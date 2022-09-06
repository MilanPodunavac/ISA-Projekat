import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPublicInfoComponent } from './user-public-info.component';

describe('UserPublicInfoComponent', () => {
  let component: UserPublicInfoComponent;
  let fixture: ComponentFixture<UserPublicInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPublicInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPublicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
