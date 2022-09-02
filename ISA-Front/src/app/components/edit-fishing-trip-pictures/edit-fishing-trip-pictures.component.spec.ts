import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFishingTripPicturesComponent } from './edit-fishing-trip-pictures.component';

describe('EditFishingTripPicturesComponent', () => {
  let component: EditFishingTripPicturesComponent;
  let fixture: ComponentFixture<EditFishingTripPicturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditFishingTripPicturesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFishingTripPicturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
