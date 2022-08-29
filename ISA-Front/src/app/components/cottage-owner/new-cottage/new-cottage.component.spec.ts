import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageComponent } from './new-cottage.component';

describe('NewCottageComponent', () => {
  let component: NewCottageComponent;
  let fixture: ComponentFixture<NewCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
