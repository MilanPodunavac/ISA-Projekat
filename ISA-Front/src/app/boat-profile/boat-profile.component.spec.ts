import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatProfileComponent } from './boat-profile.component';

describe('BoatProfileComponent', () => {
  let component: BoatProfileComponent;
  let fixture: ComponentFixture<BoatProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
