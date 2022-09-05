import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatOwnerBoatComponent } from './boat-owner-boat.component';

describe('BoatOwnerBoatComponent', () => {
  let component: BoatOwnerBoatComponent;
  let fixture: ComponentFixture<BoatOwnerBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatOwnerBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatOwnerBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
