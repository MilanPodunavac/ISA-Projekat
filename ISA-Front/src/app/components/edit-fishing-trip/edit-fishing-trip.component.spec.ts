import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFishingTripComponent } from './edit-fishing-trip.component';

describe('EditFishingTripComponent', () => {
  let component: EditFishingTripComponent;
  let fixture: ComponentFixture<EditFishingTripComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditFishingTripComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFishingTripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
