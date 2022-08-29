import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageActionComponent } from './new-cottage-action.component';

describe('NewCottageActionComponent', () => {
  let component: NewCottageActionComponent;
  let fixture: ComponentFixture<NewCottageActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageActionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
