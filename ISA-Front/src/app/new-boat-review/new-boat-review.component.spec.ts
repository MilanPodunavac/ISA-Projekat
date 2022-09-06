import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBoatReviewComponent } from './new-boat-review.component';

describe('NewBoatReviewComponent', () => {
  let component: NewBoatReviewComponent;
  let fixture: ComponentFixture<NewBoatReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBoatReviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBoatReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
