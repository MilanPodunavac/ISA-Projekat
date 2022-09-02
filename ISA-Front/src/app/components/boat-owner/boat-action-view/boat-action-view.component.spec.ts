import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatActionViewComponent } from './boat-action-view.component';

describe('BoatActionViewComponent', () => {
  let component: BoatActionViewComponent;
  let fixture: ComponentFixture<BoatActionViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatActionViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatActionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
