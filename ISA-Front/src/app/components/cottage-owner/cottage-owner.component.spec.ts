import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageOwnerComponent } from './cottage-owner.component';

describe('CottageOwnerComponent', () => {
  let component: CottageOwnerComponent;
  let fixture: ComponentFixture<CottageOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
