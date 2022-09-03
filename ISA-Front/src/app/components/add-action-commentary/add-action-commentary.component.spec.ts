import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddActionCommentaryComponent } from './add-action-commentary.component';

describe('AddActionCommentaryComponent', () => {
  let component: AddActionCommentaryComponent;
  let fixture: ComponentFixture<AddActionCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddActionCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddActionCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
