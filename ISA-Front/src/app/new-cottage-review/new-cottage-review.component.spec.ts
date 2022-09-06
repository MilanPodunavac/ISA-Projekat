import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageReviewComponent } from './new-cottage-review.component';

describe('NewCottageReviewComponent', () => {
  let component: NewCottageReviewComponent;
  let fixture: ComponentFixture<NewCottageReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageReviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
