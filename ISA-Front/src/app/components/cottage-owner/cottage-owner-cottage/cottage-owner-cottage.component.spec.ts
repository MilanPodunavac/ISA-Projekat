import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageOwnerCottageComponent } from './cottage-owner-cottage.component';

describe('CottageOwnerCottageComponent', () => {
  let component: CottageOwnerCottageComponent;
  let fixture: ComponentFixture<CottageOwnerCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageOwnerCottageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
