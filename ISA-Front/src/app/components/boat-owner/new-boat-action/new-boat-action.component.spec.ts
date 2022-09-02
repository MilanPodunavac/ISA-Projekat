import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBoatActionComponent } from './new-boat-action.component';

describe('NewBoatActionComponent', () => {
  let component: NewBoatActionComponent;
  let fixture: ComponentFixture<NewBoatActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewBoatActionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBoatActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
