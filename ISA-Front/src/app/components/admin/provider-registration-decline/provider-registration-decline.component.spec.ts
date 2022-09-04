import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderRegistrationDeclineComponent } from './provider-registration-decline.component';

describe('ProviderRegistrationDeclineComponent', () => {
  let component: ProviderRegistrationDeclineComponent;
  let fixture: ComponentFixture<ProviderRegistrationDeclineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProviderRegistrationDeclineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderRegistrationDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
