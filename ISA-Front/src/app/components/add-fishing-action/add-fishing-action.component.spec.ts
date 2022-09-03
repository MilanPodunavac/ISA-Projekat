import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFishingActionComponent } from './add-fishing-action.component';

describe('AddFishingActionComponent', () => {
  let component: AddFishingActionComponent;
  let fixture: ComponentFixture<AddFishingActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFishingActionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFishingActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
