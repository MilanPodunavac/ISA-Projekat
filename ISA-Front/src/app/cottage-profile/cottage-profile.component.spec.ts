import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageProfileComponent } from './cottage-profile.component';

describe('CottageProfileComponent', () => {
  let component: CottageProfileComponent;
  let fixture: ComponentFixture<CottageProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
